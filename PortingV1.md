

# Introduction #

The various subversions of V1.0 can introduce breaking changes which might force you to update your code slightly. This page documents which changes are needed between each subversion.


# V0.2 #

This section describes the changes needed to go from V0.1 to V0.2.

## Distpatch module ##

Christian Goudreau worked on an interesting system to add automatic server-side validation of actions. For more details see [Introduction to action validators](IntroductionActionValidator.md).

### Package and class name change ###

As a design choice, the dispatch module is now dependent on guice which removes the need for abstract classes and guice-specific implementations, significantly simplifying the module. We also revised a number of othe package, class and interface names, in part for greater consistency with standard GWT naming practices. Here are the changes needed to your client code:
  * `com.philbeaudoin.gwtp.dispatch.client.gin.StandardDispatchModule`
    * Becomes `...DispatchAsyncModule`
  * `com.philbeaudoin.gwtp.dispatch.server.guice.GuiceStandardDispatchServlet`
    * Becomes `com.philbeaudoin.gwtp.dispatch.server.DispatchServiceImpl`
  * `com.philbeaudoin.gwtp.dispatch.server.guice.ActionHandlerModule`
    * Becomes `...HandlerModule`
  * `com.philbeaudoin.gwtp.dispatch.server.guice.ServerDispatchModule`
    * Becomes `...DispatchModule`
  * `com.philbeaudoin.gwtp.dispatch.server.ActionHandler`
    * Becomes `com.philbeaudoin.gwtp.dispatch.server.actionHandler.ActionHandler`
  * `com.philbeaudoin.gwtp.dispatch.client.secure.SecureDispatchService`
    * Removed.

### New `Action.getServiceName` method ###

Actions can now be mapped to different urls on the server side. This is accomplished by overriding the `getServiceName` method. If you're fine with the default service name of `"dispatch/"` then you can simply have your actions inherit from `ActionImpl`. If you don't want protection against XSRF attacks (see below), you can inherit from `UnsecuredActionImpl` instead.

### Update your dispatcher url ###

This step is not absolutely needed if you keep the standard dispatch url, but it makes the code cleaner. In your class derived from `ServletModule` (often called `DispatchServletModule`), you should serve the following url:
```
serve("/myproject/" + Action.DEFAULT_SERVICE_NAME).with(DispatchServiceImpl.class);
```

### Protecting against XSRF attacks ###

To protect your application against XSRF attacks, as described in this <a href='http://groups.google.com/group/Google-Web-Toolkit/web/security-for-gwt-applications'>document</a> under XSRF and GWT, you have to specify the name of the security cookie you want to use. Do this by binding a string constant annotated with `@SecurityCookie` both on the client and on the server. On the client, you can do this in the `configure()` method of any of your client modules. On the server side, you can do it in your `configureServlets` method of your `servletModule`. The code to do this is:
```
    bindConstant().annotatedWith( SecurityCookie.class ).to("MYCOOKIE");
```

You should also make sure your `Action.isSecured` methods return `true` for the actions you want to secure against XSRF attacks. One way to do this is to have your actions inherit from `ActionImpl`.

The cookie should contain a session-dependent random number that cannot be easily guessed by an attacker. You can set this cookie yourself as soon as the page is loaded, or you can use the `"JSESSIONID"` cookie, which can be easily enabled on a Tomcat server or on Google AppEngine.

If you don't want to use the `"JSESSIONID"` cookie, say because you don't want to enable it on AppEngine, then you can add either `HttpSessionSecurityCookieFilter` or `RandomSessionSecurityCookieFilter` to your list of filters. To do so, add the following line at the top of your `configureServlets` method:
```
    filter("*").through( HttpSessionSecurityCookieFilter.class );
```
You will also have to make sure your `.html` file is not served statically, otherwise this filter will be bypassed. To do so, add the following to your `appengine-web.xml` file:
```
  <static-files>
    <exclude path="/*.html" />
  </static-files>
```


## MVP Module ##

The MVP module has no breaking change, but there are a few new recommended practices.

### Use `ProxyPlace` and `TabContentProxyPlace` ###

The old way of defining your `Proxy` interface that was also a `Place` was to extend both `Proxy` and `Place`. To simplify things, we introduced a new `ProxyPlace` interface. For proxies that extends `TabContentProxy` and `Place` there is the new `TabContentProxyPlace`. The old way will still work, but the newer is a little more concise. Here is an exam[le:

```
  // Before:
  public interface MyProxy extends Proxy<MyPresenter>, Place {}  

  // Now:
  public interface MyProxy extends ProxyPlace<MyPresenter> {}  
```

```
  // Before:
  public interface MyProxy extends TabContentProxy<MyPresenter>, Place {}

  // Now:
  public interface MyProxy extends TabContentProxyPlace<MyPresenter> {}  
```

### Use `TabView` ###

If you were using tabbed presenters in V.0.1, your content's views had to implement both `View` and `TabPanel`. Although this will still work, they have been unified in a single interface called `TabView`. Consider using it for simplicity.

### Use GWTP support for dialog boxes ###

If you use dialog boxes in your application, chances are you were forced to do some manual work in order for them to work within GWTP's architecture. You should now consider using the newly available support for popup `PresenterWidgets`.  From the containing presenter simply call `addPopupContent()`, passing your dialog's PresenterWidget. The dialog's view should also implement `PopupView` and inherit from `PopupViewImpl`.


# V0.3 #

This section describes the changes needed to go from V0.2 to V0.3.

## Package name change to gwtplatform ##

The package of all the classes has been changed so as not to refer to `philbeaudoin` anymore. The following global search & replace should take care of it:
```
  Replace: "com.philbeaudoin.gwtp."
  With:    "com.gwtplatform."
```

## MVP Module ##

The MVP module has a few breaking changes which should have limited impact on your code. The following describes how to modify your code to overcome these problems.


### Bind your RootPresenter asEagerSingleton() ###
Details at http://groups.google.com/group/gwt-platform/browse_thread/thread/e941661e8149ffa8.

```
//Before
public class MyClientModule extends AbstractPresenterModule {

  protected void configure() {

    ...
    //in V0.2 we bind the RootPresenter as standard singleton
    bind(RootPresenter.class).in(Singleton.class);
    ...

    }
}

```

```
//After
public class MyClientModule extends AbstractPresenterModule {

  protected void configure() {

    ...
   //in V0.3 we have to bind the RootPresenter asEagerSingleton()
   bind(RootPresenter.class).asEagerSingleton();
    ...

   }

}
```


### `@PlaceInstance` has been deprecated ###

The `@PlaceInstance` annotation has been deprecated in favor of a new and cleaned mechanism that doesn't require you to write any code within a string. The new annotation to use is `@UseGatekeeper` and the way to use it is described in its javadoc and that of `Gatekeeper`. Also see [Issue 101](http://code.google.com/p/gwt-platform/issues/detail?id=101) for details.

Basically, you will have to create a `Gatekeeper` class instead of the custom `Place`-derived classes you were using before. You will also need a `getMyGatekeeper` method in your custom `ginjector`. It's typically a good idea to bind your gatekeepers as singletons. Then, for each proxy that you want to protect you use the `@UseGatekeeper` annotation with your desired custom `Gatekeeper` class. Here is an example taken from PuzzleBazar:

```
@Singleton
public class AdminGatekeeper implements Gatekeeper {

  private final CurrentUser currentUser;

  @Inject
  public AdminGatekeeper( CurrentUser currentUser ) {
    this.currentUser = currentUser;
  }

  @Override
  public boolean canReveal() {
    return currentUser.isAdministrator();
  }
}
```

And a proxy that uses it:
```
  @ProxyCodeSplit
  @NameToken( NameTokens.adminUsers )
  @UseGatekeeper( AdminGatekeeper.class )
  @TabInfo(
      container = AdminTabPresenter.class, 
      priority = 1, 
      getLabel="ginjector.getTranslations().tabUsers()")
  public interface MyProxy extends TabContentProxyPlace<AdminUsersPresenter> {}
```

The old `@PlaceInstance` will still work for now, but will probably be taken out in a future version.

### Do not reveal presenters through their proxy or by firing a `PlaceRequestEvent` ###

In previous versions of GWTP it was acceptable to reveal a presenter through a call to the proxy's `reveal` method of by firing a `PlaceRequestEvent`. None of these techniques is supported anymore (the `reveal` method has been taken out of `Presenter` and `Proxy`). Instead, you should always use the `PlaceManager` to reveal a presenter, including in your place manager's `revealDefaultPlace()` method (and similar). Simply build a `PlaceRequest` and pass it to one of the following method of `PlaceManager`:
  * `revealPlace(PlaceRequest)`
  * `revealRelativePlace(PlaceRequest)`
  * `revealRelativePlace(PlaceRequest, int)`
Here is an example of an application-specific `PlaceManager` using that mechanism:
```

  @Inject
  public MyPlaceManager(
      final EventBus eventBus, 
      final TokenFormatter tokenFormatter,
      @DefaultPlace String defaultPlaceNameToken ) {
    super(eventBus, tokenFormatter);
    this.defaultPlaceRequest = new PlaceRequest( defaultPlaceNameToken );
  }

  @Override
  public void revealDefaultPlace() {
    revealPlace( defaultPlaceRequest );
  }
```
Assuming "`!Main`" is the name token of your default place, you would use the following binding in your gin module:
```
    bindConstant().annotatedWith(DefaultPlace.class).to( "!Main" );
```
These changes were made so simplify GWTP, and they were needed to support hierarchical places.

### Slashes in the history token are now escaped ###

In the rare situation where your application used slashes (`/`) in its name tokens or in the parameters, your old bookmarks will no longer works. This is because slashes are now automatically escaped (doubled) by GWTP token formatter. This should not cause any other problem to your application.

### `TokenFormatter` should no longer be used directly ###

If you had to build URLs from complex `PlaceRequest` before, it is possible that you were using directly the `TokenFormatter`. Now, the `PlaceManager` provides methods for building URLs out of place requests, such as:
  * `buildUrl(PlaceRequest)`
  * `buildRelativeUrl(PlaceRequest)`
  * `buildRelativeUrl(PlaceRequest, int)`
  * `buildRelativeUrl(int)`
You should use these. (Note that their has been some interface changes in `TokenFormatter` so your previous calls may no longer work.)

### Your `PopupView` classes must now be injected with the `EventBus` ###

If you have use any popup or dialog box and have view classes inheriting from `PopupViewImpl` then you will have to inject the `EventBus` into their constructor and pass that up. For example, in `gwtptabsample`, the class `InfoPopupView` has now the following constructor:
```
  @Inject
  public InfoPopupView(EventBus eventBus) {
    super(eventBus);
    widget = uiBinder.createAndBindUi(this);  
  }
```

### Do not use `autoHideOnHistoryEventsEnabled="true"` in your popup views ###

A problem was identified with popup view that were using GWT's automatic hiding on history event, which can be set either by writing `autoHideOnHistoryEventsEnabled="true"` in the UiBinder file or by calling `setAutoHideOnHistoryEventsEnabled(true);`. It is now highly recommended that you do not use this mechanism but instead call `setAutoHideOnNavigationEventEnabled(true)` in your view's constructor. For an example, see the class `InfoPopupView` in `gwtptabsample`.




# V0.4 #

This section describes the changes needed to go from V0.3 to V0.4.


## MVP Module ##

The MVP module has a few breaking changes. The following describes how to modify your code to overcome these problems.

To enforce better encapsulation, the visibility of some members of presenter-related classes will be changed to private and new interfaces have been provided to access them. If you get deprecation warnings and want to get rid of them, follow the steps described below.

### Protected eventBus has been deprecated ###

The `eventBus` fields in `PresenterWidgetImpl`, `PlaceManager` and `ProxyPlaceAbstract` will soon be made private and has been deprecated in [revision 0](https://code.google.com/p/gwt-platform/source/detail?r=0).4. If you want to get rid of these warnings you have two options.

1) The simple (but slightly dirty) fix is to replace all use of `eventBus` by a call to `getEventBus()`

2) There is a cleaner fix, however, that will ensure the `source` field in your events are correctly set. This `source` is not used frequently but we still recommend you follow this pattern to ensure you don't run into problems in the future. Moreover, it aligns well with the standard GWT usage of events:
  * Never call `getEventBus().fireEvent( new MyEvent(...) )`, instead call `fireEvent()` directly. Better yet, provide a static factory method in your event and call `MyEvent.fire(this, ...)`. Note that if the call is in an inner class you may have to qualify `this`, for example `MyEvent.fire(MyPresenter.this, ...);`
  * Instead of `MyEvent.fire(getEventBus(), ...);` call `MyEvent.fire(this, ...);`.
  * Instead of `getEventBus().fireEvent(...);` call `fireEvent(...);`
  * Instead of `getEventBus().addHandler(...);` call `addRegisteredHandler();`

The same thing hold for your custom `PlaceManager` or for any custom proxy you have.

### Static fire methods in custom events ###

If you use pattern 2 make sure your static fire methods in your event classes have a signature of the form `public static void fire( HasEventBus source, ... )`. Note the use of `HasEventBus` instead of `EventBus`. This is important, but may cause problems if you have custom classes firing event on the bus. In that case, make sure the class that is firing implements `HasEventBus` and passes itself as the source. Here is an example:
```
public abstract class MyCustomCallback<T> implements AsyncCallback<T>, HasEventBus {

  @Inject
  private static EventBus eventBus;

  @Override
  public void onFailure(Throwable caught) {
    DisplayErrorMessageEvent.fire(this, "Oops! Something went wrong!");
  }

  public void fireEvent(GwtEvent<?> event) {
      eventBus.fireEvent(event);
  }
}
```

### Protected view has been deprecated ###

The `view` field in `PresenterWidgetImpl` has been deprecated, use the `getView()` method.

### Minor change to `PlaceManager` interfaces for hierarchical places ###

  * The `getCurrentTitle(int index, SetPlaceTitleHandler handler)` method has been modified to `getTitle(int index, SetPlaceTitleHandler handler)`.
  * The `getCurrentTitle(SetPlaceTitleHandler handler)` now returns the title of the currently visible place, instead of that of `getCurrentTitle(0, handler)`.

### Presenter methods `xxxContent` have been deprecated ###

Presenter methods that allowed you to set, add or remove content from slots have been deprecated and given more meaningful names. To get rid of deprecation warnings rename them as follow:
  * `addContent` to `addToSlot`
  * `addPopupContent` to `addToPopupSlot`
  * `clearContent` to `clearSlot`
  * `removeContent` to `removeFromSlot`
  * `setContent` to `setInSlot`

### View methods `xxxContent` have been deprecated ###

Viewmethods that allowed you to set, add or remove content from slots have been deprecated and given more meaningful names. To get rid of deprecation warnings rename them as follow:
  * `addContent` to `addToSlot`
  * `removeContent` to `removeFromSlot`
  * `setContent` to `setInSlot`

### `PresenterWidget` and family are now abstract classes ###

In previous versions `PresenterWidget`, `Presenter` and `TabContainerPresenter` were interfaces. This caused a number of problems with testing and turned out to be a cumbersome design choice. We modified the entire hierarchy to abstract classes instead.

As a result, `PresenterWidgetImpl`, `PresenterImpl` and `TabContainerPresenterImpl` have now been deprecated. To get rid of the warning, simply remove the `Impl` suffix.

### Possible breaking change with `fireEvent` in unit test ###

This can be breaking for UnitTest where you'll have to change your test to use fireEvent(source, event).

Example of a mockito verify that would have to change:
```
verify( eventBus ).fireEvent( isA(UpdateObjectEvent.class) );
```
Must become:
```
verify( eventBus ).fireEvent( isA(UpdateObjectEvent.class), eq(presenter) );
```

### The `prepareRequest` request method has been removed ###

In previous versions you had to override the `prepareRequest` method in your presenter in order for history to behave correctly. This mechanism would sometimes cause a new token to be inserted in the browser history, messing up with the desired behavior of the _back_ and _forward_ button. (See [Issue 134](http://code.google.com/p/gwt-platform/issues/detail?id=134) and [135](http://code.google.com/p/gwt-platform/issues/detail?id=135)).

This mechanism is no longer required and the method will no longer work automatically. Chances are you simply want to entirely delete all your `prepareRequest` methods. In the rare case where you would like to reproduce the previous behavior, simply add the following code to your presenter's `onReveal()`:
```
  @Override
  protected void onReveal() {
    super.onReveal();
    PlaceRequest request = new PlaceRequest(getProxy().getNameToken());
    placeManager.updateHistory(presenter.prepareRequest(request));
  }
```
Where `prepareRequest` is your old method without the `@Override` and in which you've removed the call to `super.prepareRequest`. Also note that you can now make this method  `private`.

Please note that the rarely used methods `Presenter.notifyChange()` and `PlaceManager.onPlaceChanged(PlaceRequest)` have been replaced by `PlaceManager.updateHistory(PlaceRequest)`.

### Small behavior change in `PopupViewImpl.center` ###

The method `PopupViewImpl.center()` uses a deferred command to work around an IE bug (read the source for details). If you position your popup anywhere else but in the center of the screen then you might want to overwrite `center()` to an empty method otherwise your popup might show up in the center rather than your designated position.

## Dispatch Module ##

Some minor changes has been done to the dispatch module and one is a breaking change.

### Package rename to lower case ###

Some packages were not following the typical convention of all lowercase package names. You will have to rename them accordingly:

  * `com.gwtplatform.dispatch.server.actionHandler` to `... .actionhandler`;
  * `com.gwtplatform.dispatch.server.actionHandlerValidator` to `... .actionhandlervalidator`;
  * `com.gwtplatform.dispatch.server.actionValidator` to `... .actionvalidator`.


# V0.5 #

This section describes the changes needed to go from V0.4 to V0.5.

## MVP Module ##

### @Deprecated cleanup ###

We had a lot of @Deprecated and decided to do some cleanup. Here's a list of what has been permanently removed:
  * Class `PresenterWidgetImpl`
  * Class `PresenterImpl`
  * Class `TabContainerPresenter`
  * `View.setContent`, use `setInSlot`
  * `View.addContent`, use `addToSlot`
  * `View.addPopupContent`, use `addToPopupSlot`
  * `View.removeContent`, use `removeFromSlot`
  * `View.clearContent`, use `clearSlot`

Also, `eventBus` and `view` in every class using them are now private, you should use `getEventBus()` and `getView()` to access them. The `@PlaceInstance` annotation has been removed, use the `@UseGatekeeper` instead.

### Introducing GWT `EventBus` ###

GWT 2.1 has just released the first RC and we felt that it was stable enough to start making some major change to use more of GWT internals.

Here's the changes concerning our `EventBu`s and GWT `EventBus`:
  * `DefaultEventBus` and `EventBus` has been removed, use `SimpleEventBus` and `com.google.gwt.event.shared.EventBus`.
  * `GwtEventHelper` has been removed. It means calls to `eventBus.fireEvent(source, event)` must be changed to `eventBus.fireEventFromSource(event, source)`. Notice that the parameters are reversed.
  * `HasEventBus` is now deprecated, please use `HasHandlers` directly.
  * Now that we're using GWT's event bust

Now binding your `EventBus` in gin should be done like this:
```
bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
```

Then the last step is to use the right import in your project for `EventBus`.

We also added GWT 2.1 dependent classes inside another Jar for anyone that is not using GWT 2.1.  On [our CI server](http://teamcity.codebetter.com/project.html?projectId=project92), this artifact is named gwteventbus.

### Change to tabbed presenters and `@TabInfo` ###

The `@TabInfo` mechanism has been modified in order to be more flexible and type safe. In particular, the `getLabel` parameters has been removed. To replace it you will need to move the `@TabInfo` annotation to a static method returning a string.

For example, say you annotated your proxy with `@TabInfo(..., getLabel="ginjector.getTranslations().myTabLabel()")`. Then remove the annotation from the proxy and add it to a static method in the following way:
```
@TabInfo(...)
public static String getTabLabel(MyGinjector ginjector) {
  return ginjector.getTranslations().myTabLabel()
}
```

See the javadoc of `@TabInfo` for more details.

### Change to `TabPanel` (also impacting `TabView`) ###

Together with the previous change, `TabPanel` has been modified to allow user-defined custom tabs as described in [Issue 92](http://code.google.com/p/gwt-platform/issues/detail?id=92).

If you wrote your own custom tab panels inheriting `TabPanel`, then you will have to modify the signature of `addTab` from:
```
  public Tab addTab(String text, String historyToken, float priority)
```
To:
```
  public Tab addTab(TabData tabData, String historyToken)
```
You can still recover the text and priority from `tabData` so modifying the method body is easy.

The same modifications must be applied to your `TabView` classes, since they also implement `TabPanel`.

### Minor changes ###

  * `PresenterWidget.isVisible` function has been modified and is no longer final, it should help with unit testing.
  * `com.gwtplatform.mvp.client.ProviderBundle` moves to `com.gwtplatform.common.client.ProviderBundle`.

## Dispatch Module ##

### Change to action path ###
It is possible that you may need to change the path servicing your dispatcher in your server-side Guice module. A line like that:
```
  serve("/puzzlebazar/" + ActionImpl.DEFAULT_SERVICE_NAME).with(DispatchServiceImpl.class);
```
Should become:
```
  serve("/" + ActionImpl.DEFAULT_SERVICE_NAME).with(DispatchServiceImpl.class);
```

### Support for guice or spring ###

These are breaking changes to the dispatch module.

With the new spring support, guice-specific classes have been moved to their own package. You will have to update your dependencies accordingly:
  * `com.gwtplatform.dispatch.server.DispatchServiceImpl`
  * `com.gwtplatform.dispatch.server.HttpSessionSecurityCookieFilter`
  * `com.gwtplatform.dispatch.server.RandomSecurityCookieFilter`
  * `com.gwtplatform.dispatch.server.DispatchServiceImpl`
  * `com.gwtplatform.dispatch.server.SecureRandomSingleton`
  * `com.gwtplatform.dispatch.server.actionhandlervalidator.*`
  * `com.gwtplatform.dispatch.server.actionvalidator.*`
to:
  * `com.gwtplatform.dispatch.server.guice.*`

You'll also need to bind `RequestProvider` with either `DefaultRequestProvider` from Guice or Spring like this:
```
bind(RequestProvider.class).to(DefaultRequestProvider.class).in(Singleton.class);
```

### Deprecated ###

  * The constructors for `DispatchAsyncModule` and `DispatchModule` that took parameters have been replaced by Builders.
  * Use `server.guice.actionvalidator.DefaultActionValidator` instead of `server.actionvalidator.DefaultActionValidator`.
  * Use `server.guice.actionhandlervalidator.ActionHandlerValidatorLinker` instead of `server.actionhandlervalidator.ActionHandlerValidatorLinker`.
  * Use `server.guice.actionhandlervalidator.EagerActionHandlerValidatorRegistryImpl` instead of `server.actionhandlervalidator.EagerActionHandlerValidatorRegistryImpl`.
  * Use `server.guice.actionhandlervalidator.LazyActionHandlerValidatorRegistryImpl` instead of `server.actionhandlervalidator.LazyActionHandlerValidatorRegistryImpl`.

## Tester Module ##

### Deprecated ###

  * `bindMockHandler` has been replaced with `bindMockActionHandler`

# V0.5.1 #

This section describes the changes needed to go from V0.5 to V0.5.1.

## Dependencies ##

This version only impact your dependencies, not the GWTP code itself. If
you use Maven this should all be taken care of. If not here are the details:
  * The minimal required version of GWT is now 2.2. GWTP 0.5.1 and later will not work with versions prior to that.
  * You will also need GIN version 1.5, which can be downloaded from the [google-gin project page](http://code.google.com/p/google-gin/downloads/list).
  * [Guice](http://code.google.com/p/google-guice/) has to be updated to version 3.0. You have to add at least the following jars to your build path:
    * guice-3.0-rc2.jar
    * guice-servlet-3.0-rc2.jar
    * guice-assistedinject-3.0-rc2.jar
    * javax.inject.jar
    * aopalliance.jar

# V0.6 #

This section describes the changes needed to go from V0.5.1 to V0.6.

## Maven dependencies ##

### Client-side dependencies can now use the `provided` scope ###

Maven dependencies that do not need to be deployed on the server can now all use `<scope>provided</scope>` see UsingGwtpWithMaven for details.

### Separate dependencies for Guice and Spring ###

In 0.6 we have separated the dispatcher maven projects in two:
  * `gwtp-dispatch-server-guice`
  * `gwtp-dispatch-server-spring`
Therefore, in your maven POM, you will have to replace `gwtp-dispatch-server` with one of the above. An advantage of this approach is that it will not pull the spring-related dependencies if you use only Guice, and conversely if you use spring.

This does not affect you if you use `gwtp-all` or if you do not use Maven.

### No more need to depend on internal dependencies ###

GWTP has a number of internal dependencies (i.e. `gwtp-clients-common` or `gwtp-dispatch-shared`. These are automatically obtained by Maven using transitive dependencies so you do not need to explicitely refer to them in your pom. For an example, see UsingGwtpWithMaven.

## MVP Module 0.6 ##

### Minor changes to proxies ###

Due to an important internal refactoring, proxies have seen some minor API changes. This will only affect you if you had some manually-written proxies.

### Change to `TokenFormatter` character escaping ###

The previous TokenFormatter escaped problematic characters by doubling them. This mechanism could result in ambiguous cases that could not be resolved uniquely. To solve the problem we now escape all characters using standard url query string encoding (see GWT's [URL.encodeQueryString](http://google-web-toolkit.googlecode.com/svn/javadoc/2.2/com/google/gwt/http/client/URL.html#encodeQueryString(java.lang.String))). As a result, some of your URLs can change slightly after upgrading.

### Added a `DefaultModule` ###

A lot of bindings can be relplaced by a single call to `install(new DefaultModule(MyPlaceManager.class))`, see [Getting started](GettingStarted#Binding_everything_together.md) for more details.

### `PlaceManager.navigateBack()` changes behavior ###

Previously, `PlaceManager.navigateBack()` was doing a complex check to make sure it did not leave the application. This was causing various nasty bugs and was removed. Now, the method simply calls `History.back()`. If the previous mechanism was important to you, you will have to track your own stack of navigated history tokens. This is relatively easy to do using the `NavigationEvent` and can be done in your own `PlaceManager` implementation.

### No more `FailureHandler` ###

The `FailureHandler` interface and its implementation, `DefaultFailureHandler` have both been removed from GWTP. You will have to remove the binding from your module. If you were using it, handle the new and more versatile `AsyncCallFailEvent` instead.

## Dispatch Module 0.6 ##

### @Deprecated cleanup ###

Classes that were deprecated in 0.5 have now been removed:
  * `server.actionvalidator.DefaultActionValidator`.
  * `server.actionhandlervalidator.ActionHandlerValidatorLinker`.
  * `server.actionhandlervalidator.EagerActionHandlerValidatorRegistryImpl`.
  * `server.actionhandlervalidator.LazyActionHandlerValidatorRegistryImpl`.

### Package name change ###

The following classes have moved from the `client` to `shared` package. The most visible one is:
```
  com.gwtplatform.dispatch.client.DispatchAsync
```
Which is now in:
```
  com.gwtplatform.dispatch.shared.DispatchAsync
```
These other classes have moved in a similar way, but they should rarely affect you: `DispatchRequest`, `DispatchService`, `DispatchServiceAsync`, `SecurityCookieAccessor`.

## Crawler Module ##

The experimental crawler module of earlier versions of GWTP has been completely replaced by an approach based on an external service (provided in GWTP) and a filter accessing this service. If you were using the previous version, you will have to make changes. See the [documentation](CrawlerSupport.md) for more details.

# V0.7 #

This section describes the changes needed to go from V0.6 to V0.7.

## MVP Module ##

### Move to bindery ###

We have moved have deprecated classes in `com.google.gwt.event.shared` that had counterparts in `com.google.web.bindery.event.shared`. To update your code replace:
```
  import com.google.gwt.event.shared.EventBus;
  import com.google.gwt.event.shared.HandlerRegistration;
```
by:
```
  import com.google.web.bindery.event.shared.EventBus;
  import com.google.web.bindery.event.shared.HandlerRegistration;
```

After the move, you may notice some calls to the static `fire` method start failing. This is because the new `EventBus` no longer implements `HasHandlers`. This is good, as it forces you into the good pattern of firing events using the _source_ of the event rather than a generic event bus. Concretely, it means you will have to change:
```
  CurrentUserChangedEvent.fire(eventBus);
```
by:
```
  CurrentUserChangedEvent.fire(this);
```
If the object firing the event is a presenter or a presenter widget, the above modification will work out of the box. If it's an arbitrary object just have it implement the `HasHandlers` interface together with its unique method, `fireEvent`. See `CurrentUser` in `gwt-sample-tab` for an example.

### Dynamic control of tab properties ###

We have added the ability to dynamically control the tabs in a tab presenter. Example on how to do this can be found in the tab sample, in `HomePresenter` and `MainPagePresenter`.

This has a couple of change that may impact your code:
  * You will have to define a `changeTab` method in any of your class that extends `TabPanel`. If you're not using dynamic tab, you can leave this method empty.
  * You will have to define a `changeTab` method in your views that extend `TabView`. Usually, you would just forward the call to the wrapped `TabPanel`.
  * If you are using nested tabbed presenter, you will have to change your `TabContentProxy` to `NonLeafTabContentProxy` otherwise you'll get a warning at rebind time.

### Updated token formatter ###

The previous token formatter had problems with email clients that forced the encoding URLs. Our updated token formatter fixes that problem. The generated URLs are very similar to the previous ones, save for the case where the token or the parameters contain "/", ";", "=" or "\". That means if you published URLs they should most likely still work, unless you had these characters in your name tokens or your parameters.

If you encounter broken URLs and really want to go back to the previous formatter, you can always bind `TokenFormatter` to `ParameterTokenFormatterOld`, but be aware that this one is deprecated and will soon be removed.

## Annotation processors, change to optional parameters ##

If you were using `@Optional` parameters with `@GenEvent` or `@GenDispatch` classes, then building instances of these objects has now become slightly more verbose, but much more flexible. For example, given you had:
```
@GenDispatch
public class RetrieveFoo {
  @In(1)
  int fooId;

  @In(2)
  @Optional
  String additionalQuestion;

  // ...
```
Then instead of creating an action this way:
```
new RetrieveFooAction(42, "meaning of life");
```
you should now create it this way:
```
new RetrieveFooAction.Builder(42).additionalQuestion("meaning of life").build();
```
This is only true for classes with optional inputs, for others the old pattern still works.