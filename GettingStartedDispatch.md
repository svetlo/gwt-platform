**GWTP Project Has Moved** <br />
https://github.com/ArcBees/GWTP

-<br />
-<br />
-<br />



# Introduction #
GWT client-server communication is handled using [Asynchronous Calls](http://code.google.com/webtoolkit/doc/latest/DevGuideServerCommunication.html#DevGuideGettingUsedToAsyncCalls). On larger applications, this can quickly get messy. GWTP introduces a Dispatch framework, which complements GWT `AsyncCallback`. Dispatch centralizes all client-server communication, making possible to implement features such as caching, batching, centralized failure handling, undo/redo and XSRF protection. For more details, see [Best Practices For Architecting Your GWT App](http://code.google.com/events/io/2009/sessions/GoogleWebToolkitBestPractices.html) and [Command pattern](http://en.wikipedia.org/wiki/Command_pattern)

# How it works #
A server call is initiated with
```
dispatcher.execute(Action action, AsyncCallback callback);
```

[Here's a sample](http://code.google.com/p/gwt-platform/source/browse/gwtp-samples/gwtp-sample-basic/src/main/java/com/gwtplatform/samples/basic/client/ResponsePresenter.java). When you perform a command, the `Action` ([sample](http://code.google.com/p/gwt-platform/source/browse/gwtp-samples/gwtp-sample-basic/src/main/java/com/gwtplatform/samples/basic/shared/SendTextToServer.java)) and `Result` ([sample](http://code.google.com/p/gwt-platform/source/browse/gwtp-samples/gwtp-sample-basic/src/main/java/com/gwtplatform/samples/basic/shared/SendTextToServerResult.java)) objects define what the input and output will be. They do not define _how_ the action will take place.

In the Command Pattern these would usually be called the Command and Response, but both terms are quite overloaded in common Java APIs, particularly in GWT, so Action and Result are being used in this API instead.

An `Action` will always have a specific `Result` type. A `Result` may be returned by more than one `Action` type, however. Both could be [auto-generated](BoilerplateGeneration#Generate_Action_and_Result.md).

The `ActionHandler` defines all the logic regarding how this action should be handled  ([sample](http://code.google.com/p/gwt-platform/source/browse/gwtp-samples/gwtp-sample-basic/src/main/java/com/gwtplatform/samples/basic/server/SendTextToServerHandler.java)).


# Setting up server #

In the default setup, App Engine uses the [web.xml](http://code.google.com/appengine/docs/java/config/webxml.html) deployment descriptor to map the URL of a request to the code that should handle it. When using gwt-platform, we will [map all calls to a Guice fillter](http://code.google.com/p/google-guice/wiki/Servlets), and then [setup a GuiceServletContextListener](http://code.google.com/p/google-guice/wiki/ServletModule). (_TODO:_ describe how this is done with Spring.)

See [sample web.xml](http://code.google.com/p/gwt-platform/source/browse/gwtp-samples/gwtp-sample-basic/src/main/webapp/WEB-INF/web.xml) and [sample GuiceServletContextListener](http://code.google.com/p/gwt-platform/source/browse/gwtp-samples/gwtp-sample-basic/src/main/java/com/gwtplatform/samples/basic/server/MyGuiceServletContextListener.java).

In our `GuiceServletContextListener`, we will setup two servlet modules: `MyHandlerModule` ([sample](http://code.google.com/p/gwt-platform/source/browse/gwtp-samples/gwtp-sample-basic/src/main/java/com/gwtplatform/samples/basic/server/ServerModule.java)) and `DispatchServletModule` ([sample](http://code.google.com/p/gwt-platform/source/browse/gwtp-samples/gwtp-sample-basic/src/main/java/com/gwtplatform/samples/basic/server/DispatchServletModule.java)).

**`MyHandlerModule`**  extends Dispatch `HandlerModule` and binds Actions to ActionHandlers and ActionValidators :
```
bindHandler(SendTextToServer.class, SendTextToServerHandler.class);
// or
bindHandler(SendTextToServer.class, SendTextToServerHandler.class, SendTextToServerValidator.class);
```

An `ActionValidator` determines whether or not the current client can execute the `Action`. For more details, see [introduction to ActionValidators](IntroductionActionValidator.md)

**`DispatchServletModule`** extends [Guice ServletModule](http://google-guice.googlecode.com/svn/trunk/javadoc/com/google/inject/servlet/ServletModule.html) and maps request URLs to handler classes. Think of the `ServletModule` as an in-code replacement for the web.xml deployment descriptor. Filters and servlets are configured here using normal Java method calls.


# Advanced features #

[Introduction to ActionValidators](IntroductionActionValidator.md)

[Client action handlers](ClientActionHandlers.md)


## Protecting against XSRF attacks ##

To protect your application against XSRF attacks, as described in <a href='https://developers.google.com/web-toolkit/articles/security_for_gwt_applications'>Security for GWT Applications </a>, you have to specify the name of the security cookie you want to use. Do this by binding a string constant annotated with `@SecurityCookie` both on the client and on the server. On the client, you can do this in the `configure()` method of any of your client modules. On the server side, you can do it in your `configureServlets` method of your `servletModule`. The code to do this is:
```
    bindConstant().annotatedWith( SecurityCookie.class ).to("MYCOOKIE");
```

You should also make sure your `Action.isSecured` methods return `true` for the actions you want to secure against XSRF attacks. One way to do this is to have your actions inherit from `ActionImpl`.

The cookie should contain a session-dependent random number that cannot be easily guessed by an attacker. You can set this cookie yourself as soon as the page is loaded, or you can use the `"JSESSIONID"` cookie, which can be easily enabled on a Tomcat server or on Google AppEngine.

If you don't want to use the `"JSESSIONID"` cookie, say because you don't want to enable it on AppEngine, then you can add either `HttpSessionSecurityCookieFilter` or `RandomSessionSecurityCookieFilter` to your list of filters. To do so, add the following line at the top of your `configureServlets` method:
```
    filter("*.html").through( HttpSessionSecurityCookieFilter.class );
```
Note that you will have to make sure your application `html` file is not served as a static page. To do this, add this to your `appengine-web.xml`:
```
<static-files>
    <exclude path="/**.html" />
</static-files>
```

## Not securing against XSRF attacks ##

Some public actions do not have to be secured against XSRF attacks. For example, an action to establish a session, or to obtain the welcome message of your site. To indicate that such actions do not have to be secured against XSRF attacks your action `isSecured` method should return false. Alternatively you can extend `UnsecuredActionImpl`.

## Cancelling a request ##
`DispatchRequest` is a common interface that is returned when you execute an action. This interface gives you the possibility to cancel a request or see if this request is still being processed.

```
DispatchRequest request = dispatcher.execute(new AnAction(); new AsyncCallback<AResult>() {
  @Override
  public void onSuccess(Result result) {        
  }
});

if (request.isPending()) {
  request.cancel(); //Will cancel request
}
```