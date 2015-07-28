**GWTP Project Has Moved** <br />
https://github.com/ArcBees/GWTP

-<br />
-<br />
-<br />



# Introduction #

The goal of this page is to help you get your first project started using GWTP's Model-View-Presenter architecture. Although it focuses on how to use GWTP in Eclipse, it should be easy to adapt within your own development environment.

After reading this guide you should have a better idea whether or not GWTP is the tool you need for your next project. If you used gwt-presenter before, you might first want to take a look at how it [compares to GWTP](http://code.google.com/p/gwt-platform/wiki/ComparisonWithGwtPresenter). Andreas Borglin has also written about his experience switching from gwt-presenter to GWTP, you might want to [read it](http://borglin.net/gwt-project/?page_id=817).

For more information about the Model-View-Presenter architecture, check out Ray Ryan's [Google IO talk](http://code.google.com/events/io/2009/sessions/GoogleWebToolkitBestPractices.html) or [this video](http://development.lombardi.com/?p=1397) from Lombardi's Alex Moffat.

## Starting with the GWTP Eclipse Plugin ##

The rest of this document describes how to create a GWTP project manually step-by-step and will give you a deep knowledge of GWTP's fundamental principles. To get started quickly however, head over to the [GWTP Eclipse Plugin](EclipsePlugin.md) and follow the screencasts presented there.

## Getting the sample applications ##

A good way to learn advanced GWTP features is to look at the included sample applications. Getting and compiling them, either from the command line or from Eclipse, is described in details [here](RunningGwtpSamples.md). These samples are partly meant to test GWTP so they are a bit hard to setup to run comfortably within Eclipse and their configuration files are a bit complex. Your first GWTP projects can be much simpler, and this is what this page guides you though.

### External examples ###

The [PuzzleBazar project](http://code.google.com/p/puzzlebazar/wiki/GettingStarted) is a more complete example that can be worth a look. This project relies on some advanced GWTP features and can be an invaluable source of information, although it tends to trail behind and sometimes use an earlier release of GWTP.

Another example of a web-page-like application built with GWTP is ArcBees website, which is [open-sourced here](http://code.google.com/p/arcbees-hive/).

# Set-up #

This section explains how to setup your first GWTP project within Eclipse. If you're only interested in browsing the sample source code, [skip right to the next section](http://code.google.com/p/gwt-platform/wiki/GettingStarted#Using_GWTP) where we take you on a step-by-step journey through the basic features of GWTP.

## Creating a GWT Eclipse project ##

The first step is to start a new Eclipse project from scratch by following the steps given [here](http://code.google.com/webtoolkit/usingeclipse.html). This will load your project with the sample GWT project, which you can then start migrating to GWTP. This section will give you all the information you need to do this.

## Getting GWTP ##

You can get the current release of GWTP from the [download section](http://code.google.com/p/gwt-platform/downloads/list), the latest snapshot is available on our [continuous integration server](http://teamcity.codebetter.com/viewType.html?buildTypeId=bt225&tab=buildTypeStatusDiv), simply create an account and get it from the `artifact` column. You can also get the latest release or snapshot with [Maven](UsingGwtpWithMaven.md),  or you can elect to [clone out mercurial repository](RunningGwtpSamples#Downloading_GWTP_sources.md).

## Browsing the Javadoc ##

The javadoc of GWTP is fairly complete. You can browse the one for [release 0.5](http://javadoc.gwt-platform.googlecode.com/hg/0.5/index.html), or if you're using GWTP from trunk you can browse it from the [continuous integration server](http://teamcity.codebetter.com/repository/download/bt225/.lastSuccessful/gwtp-core/gwtp-all/target/apidocs/index.html).

## Required libraries ##

Any GWTP project depends on a couple of libraries. The rest of this section lists these and explains how to obtain them. If your project is using Maven then all the dependencies will be taken care of automatically. See [Using Gwtp With Maven](UsingGwtpWithMaven.md) for more details. For the complete dependency hierarchy take a look at DependencyHierarchy.

### Gin and Guice ###

GWTP makes heavy use of dependency injection through [google-guice](http://code.google.com/p/google-guice/) and [google-gin](http://code.google.com/p/google-gin/). You will therefore need to add these libraries to your project. You will need release 137 or above of gin, which you can get from gwt-platform's [download section](http://code.google.com/p/gwt-platform/downloads/list).

Add the jars to your build path by doing the following:
  * First, copy these jars to the `war/WEB-INF/lib` directory of your project ;
  * Then, from Eclipse, right-click on each of these files and select `Build Path > Add to Build Path`.

### GWTP ###

Naturally, you will need GWTP itself. Simply copy GWTP's compound jar `gwtp-all-0.5.jar` into your project's `war/WEB-INF/lib` directory and add it to your build path, as explained above.

If you want to minimize the size of your jars it's possible to cherry-pick the components you want from GWTP. See the DescriptionOfIndividualJars for details. This comes in handy when using AppEngine, to keep cold start-up time to a minimum.

# Using GWTP #

At this point, your application should compile without build errors and you should be able to run it. It's time to start writing code!

## Your first View ##

In a GWTP application, all pages correspond to a Presenter-Proxy-View triplet. Your first step should therefore be to build a View. Create a new `MainPageView` class and have it inherit from `ViewImpl`. Make your class injectable by google-gin by marking its constructor with the `@Inject` annotation.

If you take a look at PuzzleBazar views (i.e. `UserSettingsGeneralView`) you will see that all the widgets are created in the view's constructor (through `UiBinder` in this case). This is a good way of creating your view's layout, and it will not slow your application since this constructor is only invoked when the view is first displayed.

Once you have created your widgets, you will need to place them in a panel so that there is a single widget containing your entire view. Your `MainPageView` class needs to implement the method `asWidget`, which is responsible for returning the widget corresponding to that view. Here is a sample view class:

```
public class MainPageView extends ViewImpl
implements MainPagePresenter.MyView {

  private static String html =
    "<h1>Web Application Starter Project</h1>\n" +
    "<table align=\"center\">\n" +
    "  <tr>\n" +
    "    <td colspan=\"2\" style=\"font-weight:bold;\">Please enter your name:</td>\n" +
    "  </tr>\n" +
    "  <tr>\n" +
    "    <td id=\"nameFieldContainer\"></td>\n" +
    "    <td id=\"sendButtonContainer\"></td>\n" +
    "  </tr>\n" +
    "  <tr>\n" +
    "    <td colspan=\"2\" style=\"color:red;\" id=\"errorLabelContainer\"></td>\n" +
    "  </tr>\n" +
    "</table>\n";
    
  HTMLPanel panel = new HTMLPanel(html);
  
  @Inject
  public MainPageView() {
    final Button sendButton = new Button("Send");
    final TextBox nameField = new TextBox();
    nameField.setText("GWT User");
    final Label errorLabel = new Label();

    // We can add style names to widgets
    sendButton.addStyleName("sendButton");

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    panel.add(nameField, "nameFieldContainer");
    panel.add(sendButton, "sendButtonContainer");
    panel.add(errorLabel, "errorLabelContainer");
  }
  
  @Override
  public Widget asWidget() {
    return panel;
  }
}
```

## Your first Presenter ##

Now that you have a View, you can start working on an associated Presenter. Create `MainPagePresenter` with `Presenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy>` as a superclass. This means you have to define the inner interfaces `MyView` and `MyProxy`. We'll look at the proxy in more details later, but for now just add this to your class:
```
  public interface MyView extends View {}
  public interface MyProxy extends ProxyPlace<MainPagePresenter> {}
```

Again, add an injectable constructor (with the `@Inject` annotation) that simply forward its parameters to the superclass.

You will also need to define the `revealInParent()` method. This is where a presenter performs the operations required to become visible. The reason this is called revealIn _Parent_ is due to the hierarchical nature of presenters, which is [explained below](http://code.google.com/p/gwt-platform/wiki/GettingStarted#Nested_presenters). For now, our presenter will simply notify the top-level parent (a special Presenter built in GWTP) that it wants to be revealed. This is done by firing a `RevealRootContentEvent`.

The presenter class should now look like this:
```
public class MainPagePresenter extends 
Presenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

  public interface MyView extends View {}

  public interface MyProxy extends ProxyPlace<MainPagePresenter> {}
  
  @Inject
  public MainPagePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
    super(eventBus, view, proxy);
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire( this, this );
  }
}
```

## Your first Proxy ##

At that point you're probably thinking "Do I really have to create three classes for every page in my application?" Fear not! Chances are you will never have to write a proxy class. That's because most of the time proxys can be created automatically by GWT's generators. All you have to do is use special annotations above your `MyProxy` interface.

The first annotation you will need is either `@ProxyStandard` or `@ProxyCodeSplit`, depending whether or not you want your presenter and view to sit behind a split point (see [here](http://code.google.com/webtoolkit/doc/latest/DevGuideCodeSplitting.html) for details on GWT's code splitting feature). That's right, using code splitting is that simple! (Although there are some gotchas, see for example [Provider bundles](http://code.google.com/p/gwt-platform/wiki/GettingStarted#Provider_bundles) below.)

The second annotation you might want to use is the `@NameToken( "MyPlaceName" )`, so that this page can use the browser history. This will let you navigate to this presenter by entering the name token in the URL, or via an `Hyperlink` widget. This will also let you use the back and forward button of your browser to navigate in your application.

In the end, this is how the `MyProxy` interface should be defined in your presenter:

```
...
  @ProxyCodeSplit
  @NameToken("main")
  public interface MyProxy extends ProxyPlace<MainPagePresenter> {}
...
```
Make sure your name token doesn't contain the following characters: "`;`", "`=`" or "`/`".

Congratulations! You've written your first Presenter-Proxy-View triplet! If the available annotations and automatic proxies are not enough for your needs, read the CustomProxyClass page.

## Default page ##

Your application needs to know which page to show when the URL has no name token. This is done by creating your own `PlaceManager`:
```
public class MyPlaceManager extends PlaceManagerImpl {

  @Inject
  public MyPlaceManager(
      EventBus eventBus, 
      TokenFormatter tokenFormatter ) {
    super(eventBus, tokenFormatter);
  }

  @Override
  public void revealDefaultPlace() {
    // Using false as a second parameter ensures that the URL in the browser bar
    // is not updated, so the user is able to leave the application using the
    // browser's back navigation button.
    revealPlace( new PlaceRequest("main"), false );
  }
}
```

To explain that last comment a bit more, a situation that could occur if `revealDefaultPlace` redirected the user and changed the URL would be to make it impossible for the user to go back to where he was before using the _back_ button of his browser. That is, if you're at `#!invalid` and get redirected to `#!valid` then hitting back gets you back to `#!invalid` which redirects you again to `#!valid`. To make sure this doesn't happen we use the two parameter variant of revealPlace and pass `false` as a second parameter so that you get redirected to the _page_ corresponding to `#!valid` but the browser's history is not updated and the URL stays at `#!invalid`.

## Binding everything together ##

Notice how the presenter knows nothing about its view's implementation. This is deliberate as it promotes a loosely coupled architecture. It also means that you have to bind things together. This is achieved via dependency injection using google-gin.

Your bindings need to appear in a class inheriting from `AbstractPresenterModule`. Here are the bindings you will need for our example:
```
public class MyModule extends AbstractPresenterModule {

  @Override
  protected void configure() {  
    install(new DefaultModule(MyPlaceManager.class));
    
    // Presenters
    bindPresenter(MainPagePresenter.class, 
        MainPagePresenter.MyView.class, 
        MainPageView.class, 
        MainPagePresenter.MyProxy.class);

  }
}
```

Installing `DefaultModule` saves you from having to perform all the following bindings:
```
    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
    bind(RootPresenter.class).asEagerSingleton();
    bind(PlaceManager.class).to(MyPlaceManager.class).in(Singleton.class);
    bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);
```

However, if you want to replace some of the above by your own custom implementations, feel free to remove the call to `install` and bind everything manually.

You also need a ginjector:
```
@GinModules({ MyModule.class })
public interface MyGinjector extends Ginjector {
  PlaceManager getPlaceManager();
  EventBus getEventBus();
  AsyncProvider<MainPagePresenter> getMainPagePresenter();
}
```

Notice that you will have to add any future presenters in your ginjector with the right return value: `Provider<>` if you're using `@ProxyStandard` or `AsyncProvider<>` if you're using `@ProxyCodeSplit`. This is due to some limitation when using google-gin with GWT generators (feel free to star [this issue](http://code.google.com/p/google-gin/issues/detail?id=95) if you'd like the process to be simplified). You could also start [this issue](http://code.google.com/p/gwt-platform/issues/detail?id=207) unrelated to Gin that will simplify greatly your Ginjector class.

## Setting the entry point ##

You now need to configure your project's entry point. In it you simply need to create your ginjector, ensure everything is bound correctly, and reveal the place from the URL. Say your entry point class is `Gwtpsample`, it should look like that:
```
public class Gwtpsample implements EntryPoint {
  public final MyGinjector ginjector = GWT.create(MyGinjector.class);

  public void onModuleLoad() {

    DelayedBindRegistry.bind(ginjector);
    
    ginjector.getPlaceManager().revealCurrentPlace();    
  }
}
```


## Configuring your GWT module ##

The final step is to have your GWT module inherit the GWTP module. You also have to work around the google-gin issue mentioned above by defining the location of your ginjector. To do so, simply add these lines to your project's `.gwt.xml` file:
```
  <inherits name='com.gwtplatform.mvp.Mvp' />

  <define-configuration-property name="gin.ginjector" is-multi-valued="false" />
  <set-configuration-property name="gin.ginjector"
       value="com.gwtplatform.samples.basic.client.MyGinjector" />
```
Make sure you replace that last string with the fully qualified name of your ginjector class.

## Going further ##

At this point, your application should execute and you should see your default page no matter what you type in the URL bar. Your next step should be to write a second presenter with a different name and to see that you can navigate from one to the other by editing the URL. Once you're done, read the rest of this document and look at PuzzleBazar.

# Advanced features #

This section presents a number of advanced features of GWTP. You will need many of these when building a complete GWTP application.

## Presenter lifecycle ##

Sometimes you will need to do various maintenance operations at key moments of a Presenter's lifecycle. To this end, GWTP offers a number of hooks in the form of virtual methods you can override. The following are available to you:
  * `onBind()` is called right after the Presenter is constructed. This is the right place to add handlers to the view. If you add a handler, register it by calling `registerHandler()` so that it is automatically removed later.
  * `onUnbind()` is called if the Presenter needs to release its resources. You should usually undo any operations you performed in `onBind()`, although you don't have to worry about the handlers you registered with `registerHandler()`, these will be taken care of automatically.
  * `onReveal()` is called whenever the Presenter was not visible on screen and becomes visible.
  * `onHide()` is called whenever the Presenter was visible on screen and is being hidden.
  * `onReset()` is called whenever the user navigates to a page that shows the presenter, whether it was visible or not.

**IMPORTANT!** Whenever you override one of these methods, make sure the first thing you do is call your parent's corresponding method. For example, the first line of your `onBind()` method should be `super.onBind()`.

## Using URL parameters ##

It's often useful to register part of the state of a presenter in the URL, so that a bookmark or navigation can return to the presenter in that given state. GWTP offers native support for such parameters which, by default, will lead to URLs looking like this:
```
http://phone.com#!search;q=iphone
```

To support such URL parameters, simply override the `prepareFromRequest()` method.

Here is an example that should give you a better idea on how to do this:
```
  @Override
  public void prepareFromRequest(PlaceRequest placeRequest) {
    super.prepareFromRequest(placeRequest);

    // In the next call, "view" is the default value,
    // returned if "action" is not found on the URL.
    String actionString = placeRequest.getParameter("action", "view");
    action = INVALID_ACTION;
    if( actionString.equals("view") )
      action = ACTION_VIEW;
    else if( actionString.equals("edit") )
      action = ACTION_EDIT;
    else if( actionString.equals("new") )
      action = ACTION_NEW;

    try {
      id = Long.valueOf( placeRequest.getParameter("id", null) );
    } catch( NumberFormatException e ) {
      id = INVALID_ID;
    }

    if( action == INVALID_ACTION || 
        id == INVALID_ID && action != ACTION_NEW ) {
      placeManager.revealErrorPlace( placeRequest.getNameToken() );
      return;
    }

  }
```

The first thing that method do is to call its parent's equivalent method. Make sure you do this, since your parent class might be expecting some parameters.

## Revealing a presenter ##

In GWTP, navigating to a new page of your application is done by revealing a new presenter. There are many ways to do this.

**1) By manually modifying the URL in the user's browser**

This is what happens when the user navigates to a bookmark or uses the back/forward button of his browser. In this case, the request is internally handled by the place manager and the correct presenter will be revealed, you don't have to do anything.

**2) By using a GWT `Hyperlink`**

This is a nice way to implement navigation as it lets the user bookmark the link, open it in a new tab, etc. It also makes your application easier to discover by a search engine (when this is supported, see [Issue 1](https://code.google.com/p/gwt-platform/issues/detail?id=1)). For this to work, simply add an `Hyperlink` widget to your page with the correct history token.

If you need to build a complex history token, for example when you use parameters or hierarchical places, then you may want to rely on the `buildHistoryToken` or one of the `buildRelativeHistoryToken` methods found in `PlaceManager`.

**3) By manually building a request and revealing the presenter**

Sometimes, however, using an `Hyperlink` is not possible and you will need to reveal a proxy manually, within your code. For example, this is what you need to do if you want to reveal a presenter when the user clicks a button.

You do this by calling the `revealPlace` or one of the `revealRelativePlace` method of `PlaceManager`. To do this you need to build a `PlaceRequest` with the desired name token:
```
PlaceRequest myRequest = new PlaceRequest("desiredNameToken");
// If needed, add URL parameters in this way:
myRequest = myRequest.with( "key1", "param1" ).with( "key2", "param2" );
placeManager.revealPlace( myRequest ); 
```
For an example of this, see `MainPagePresenter.sendNameToServer()` in the sample application.

Revealing a place in this way will update the browser's URL, inserting a new element in its history which lets the user click "back" to go back to the previous page, or bookmark the current page. In cases where you don't want such a behavior you can pass `false` as the second parameter:
```
placeManager.revealPlace( myRequest, false ); 
```

## Nested presenters ##

A web app will often have a consistent layout across its pages. For example, it could have a header, a left-hand column containing links, etc. Duplicating these areas in each of your presenters makes little sense.

To solve this problem, GWTP uses nested presenters. To keep a loosely-coupled architecture, nested presenters communicate with each other through the event bus. Using nested presenters will require you to use the annotation `@ContentSlot` and to override the `setInSlot()` method in your views. You can look at SimpleNestedSample for more details on how to do that.

Note that it often doesn't make sense to display an empty presenter from the middle of this hierarchy. For this reason, you will usually want to use the `@NameToken` annotation only on your lowest-level presenters.

## Tabbed presenters ##

A particularly useful type of nested presenter is one in which the parent presenter display a list of links and clicking on any link causes a child presenter to appear.

GWTP makes it easy to implement such presenters while maintaining a loosely coupled architecture. To use this feature the parent presenter needs to inherit from `TabContainerPresenter` and you will have to use the `@RequestTabs` and `@TabInfo` annotations. For an example, look at the [tabsample](http://code.google.com/p/gwt-platform/source/browse#hg%2Fgwtp-samples%2Fgwtp-sample-tab) in the `samples` repository.

## Presenter widgets ##

Sometimes you will need a graphical object that can be instantiated multiple times (like a Widget) but that contain a lot of logic (like a Presenter). Implementing such an object as a Widget would force you to mix logic and UI code in the same class. Implementing it as a Presenter-Proxy-View triplet is not possible because a Presenter is a singleton: it's instantiated only once.

In this case, what you need is PresenterWidget-View pair. PresenterWidgets are simplified Presenters: they have no proxy and they don't need to implement the `revealInParent()` method. As a result, the parent presenter of a PresenterWidget is responsible for creating it and adding it to its view.

You can also use a PresenterWidget instead for a graphical object you do not plan to instantiate multiple times, but that it complex enough to justify a new class. For example, in a IDE-like application, you might want to have a main presenter responsible of the entire screen, but fill the different panes with various PresenterWidgets.

Instantiating a PresenterWidget is the responsibility of its parent. One way to proceed is simple to inject the desired PresenterWidget in its parent presenter's constructor. Then, when this parent is revealed (see [onReveal](#Hooking_up_to_your_Presenter.md)), you can call `setInSlot` passing this PresenterWidget and the slot you want to place it in. Calling `setInSlot` will make sure that this presenter widget's lifecycle methods are correctly called.

## Dialog box and popup panels ##

GWTP has native support for dialog boxes and popup panels that follows an MVP architecture. Simply create a PresenterWidget and a View for your dialog box then you can use the `addToPopupSlot` method of PresenterWidget to create a local popup, or fire the `RevealRootPopupContentEvent` to create an application wide popup. The first version will be hidden and redisplayed whenever it's parent presenter is. The second one will be displayed even after navigation. For more examples, look at `tabsample`.

Popup PresenterWidgets receive all the standard lifecycle calls (although they usually won't receive the call to `onReset()` when they are first added, as documented in `addToPopupSlot`). Moreover, dialog boxes are automatically hidden if the user navigates away from the page. You can decide to close them permanently or show them automatically when the user comes back.

## Navigation confirmation ##

Nothing is more frustrating than loosing the contents of a complex form after accidentally closing a tab or navigating out of a page. To make this less likely to happen, nice web applications will often pop-up a confirmation dialog whenever the user tries to leave a page without saving its changes. GWTP comes built-in with a mechanism to allow exactly that.

Enabling a confirmation message is as simple as calling `placeManager.setOnLeaveConfirmation("Do you really want to leave?")`. For example, you could call this method whenever the user edits something on the page. When the user saves his changes you probably want to disable this confirmation, which you do by calling `placeManager.setOnLeaveConfirmation(null)`.

When a navigation confirmation message is set, a pop-up dialog will be shown whenever the user tries to leave the page. If he decides not to leave, then the current page will stay visible and nothing will be lost.

## Blocking some presenters ##

Although security should always be enforced server-side, you probably don't want regular users to take a peek at your administration pages. In GWTP, you can prevent some presenters from revealing themselves by creating your custom `Gatekeeper` classes. For example, if you want some presenters to be accessible only when the user is logged in you could write the following class:
```
@Singleton
public class LoggedInGatekeeper implements Gatekeeper {  
  
  private final CurrentUser currentUser;

  @Inject
  public LoggedInGatekeeper (
      final CurrentUser currentUser ) {
    this.currentUser = currentUser;
  }

  @Override
  public boolean canReveal() {
    return currentUser.isLoggedIn();
  }
}
```

Then you simply need to add the `@UseGatekeeper` annotation to the proxy of each presenter you want to protect:
```
  @ProxyCodeSplit
  @NameToken("userSettings")
  @UseGatekeeper( LoggedInGatekeeper.class )
  public interface MyProxy extends ProxyPlace<MainPagePresenter> {}
```

You must make sure that your custom `ginjector` has a `getLoggedInGatekeeper` method. In your `ginjector` you can also use the `@DefaultGatekeeper` method to annotate the `get` method returning the `Gatekeeper` class you want to use for any proxy that is not annotated with `@UseGatekeeper`. If you use a `@DefaultGatekeeper` and would like to specify that a proxy shouldn't use any gatekeeper, then use the `@NoGatekeeper` annotation on that proxy.

You must make sure that the presenter handling errors is not using a `Gatekeeper` otherwise you risk running in an error. For this reason, it's good practice to annotate this presenter's proxy with `@NoGatekeeper`. The presenter handling errors is the one revealed by your custom `PlaceManager`'s `revealErrorPlace` method. If you do not override that method, then it's the one revealed by your `revealDefaultPlace` method.

## Using layout panels ##

GWT 2.0 offers new and very interesting [layout panels](http://code.google.com/webtoolkit/doc/latest/DevGuideUiPanels.html#LayoutPanels) that makes it easy to design web applications using the entire browser window, just like desktop applications. If you have a top-level presenter that behaves as a layout panel, then its `revealInParent()` method should look like this:
```
@Override
protected void revealInParent() {
  RevealRootLayoutContentEvent.fire( eventBus, this );
}
```
Better yet, your application can contain both pages that behave as layout panels and others that behave like standard web pages (i.e. [basic panels](http://code.google.com/webtoolkit/doc/latest/DevGuideUiPanels.html#BasicPanels)). The latter simply have to fire a `RevealRootContentEvent`.

## Attaching events to proxies ##

It is often useful to let a presenter respond to custom events even before it has been initialized. To do this it is necessary for the proxy to listen to the events. Then, whenever the proxy receives the event, it should initialize its presenter and forward the call. To make this entire process simple, GWTP provides the `@ProxyEvent` annotation. To use this feature, first define your custom `GwtEvent` class, for example:
```
public class RevealDefaultLinkColumnEvent extends GwtEvent<RevealDefaultLinkColumnHandler> {

  private static final Type<RevealDefaultLinkColumnHandler> TYPE = new Type<RevealDefaultLinkColumnHandler>();
  
  public static Type<RevealDefaultLinkColumnHandler> getType() {
      return TYPE;
  }

  public static void fire(HasEventBus source) {
    source.fire(new RevealDefaultLinkColumnEvent());  
  }  

  public RevealDefaultLinkColumnEvent() {
  }

  @Override
  protected void dispatch( RevealDefaultLinkColumnHandler handler ) {
    handler.onRevealDefaultLinkColumn( this );
  }

  @Override
  public Type<RevealDefaultLinkColumnHandler> getAssociatedType() {
    return getType();
  }
}
```
You will need to provide a static `getType` method in order for the `@ProxyEvent` to work. Once you have the event class, you should provide the handler interface:
```
public interface RevealDefaultLinkColumnHandler extends EventHandler {
  void onRevealDefaultLinkColumn( RevealDefaultLinkColumnEvent event );
}
```
Make sure that this interface has a single method and that the method accepts only one parameter: the event. Armed with these classes, you can have your presenter handle the event simply by having it implement the `RevealDefaultLinkColumnEvent` interface and by defining the handler method in this way:
```
  @ProxyEvent
  @Override
  public void onRevealDefaultLinkColumn(RevealDefaultLinkColumnEvent event) {
    // Do anything you want in there. If you want to reveal the presenter:
    forceReveal();
  }
```
Calling `forceReveal()` in this way should only be done for leaf presenters that do not have a name token. In the case where the presenter is associated to a place, use a method from the `PlaceManager` instead.

## Using manual reveal ##

It is frequent that a presenter requires to fetch information from the server before it can be used efficiently. The standard behavior of GWTP is to display the presenter right away, which will cause information received via RPC to appear with a delay. Sometimes, however, this does not lead to a pleasant user experience. For example, it is not natural to see an empty user information form being filled after some delay. In such situations you might want to use GWTP's manual reveal feature.

Manual reveal gives you greater control over the precise moment at which your presenter is revealed. It can be enabled on any `Presenter` that uses a `ProxyPlace`. Simply override the `useManualReveal()` method to return `true`. Once enabled you will need to make sure you manually reveal your presenter withing it's `prepareFromRequest` method. There are two ways to do this. First, using `ManualRevealCallback`:
```

  @Override
  public void prepareFromRequest(PlaceRequest request) {
    super.prepareFromRequest(request);
    dispatcher.execute( new DelayAction(), ManualRevealCallback.create(this,
        new AsyncCallback<NoResult>(){
          @Override
          public void onSuccess(NoResult result) {
            // Do something with the data
          }
          @Override
          public void onFailure(Throwable caught) {
            // Display an error message
          }
        } ) );
  }
```
Alternatively, you can directly call `ProxyPlace.manualReveal()` or `ProxyPlace.manualRevealFailed`. Remember that you **must** call one of these two methods, otherwise your application will remain locked and become unusable. An example of using these methods:
```
  @Override
  public void prepareFromRequest(PlaceRequest request) {
    super.prepareFromRequest(request);
    dispatcher.execute( new DelayAction(),
        new AsyncCallback<NoResult>(){
          @Override
          public void onSuccess(NoResult result) {
            // Do something with the data
            getProxy.manualReveal(ThePresenter.this);
          }
          @Override
          public void onFailure(Throwable caught) {
            // Display an error message
            getProxy.manualRevealFailed();
          }
        } );
  }
```

## Setting the source on fireEvent with your own objects ##
Since we already implemented that way of working with our presenter, place manger and proxy, here's how to implement this feature on any other objects.

```
public abstract class MyCustomCallback<T> implements AsyncCallback<T>, HasEventBus {
  @Inject
  private static EventBus eventBus;

  @Override
  public void onFailure(Throwable caught) {
    ShowMessageEvent.fire(this, "Oops! Something went wrong!");
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
      eventBus.fireEvent(this, event);
  }
}
```

`HasEventBus` here is important and also the static injection of the eventBus on your custom object.

## Delegate some actions from the view to the presenter ##
It's often useful for the view to delegate some of its actions to the presenter using a pattern sometimes referred to as a "supervising controller":

![http://4.bp.blogspot.com/_lUDLhPj-dyg/SYsogEymUWI/AAAAAAAAAB0/CSSLkHiRTMo/s1600/mvp.png](http://4.bp.blogspot.com/_lUDLhPj-dyg/SYsogEymUWI/AAAAAAAAAB0/CSSLkHiRTMo/s1600/mvp.png)

This has also been [advocated by Google](http://code.google.com/webtoolkit/articles/mvp-architecture-2.html#complex_ui) as a good way to benefit from the nice `@UiHandler` annotation at your disposal when you use UiBinder. The main difference with the original presenter pattern is that the view keeps a link back to the presenter in order to invoke some of its methods, instead of the presenter registering callbacks towards the view.

The "supervising controller" pattern can easily be implemented using the tools provided by GWTP. First you need to create an interface that extends `UiHandlers` and add all the methods your view needs to call. For example:
```
public interface UserProfileUiHandlers extends UiHandlers{
  void onSave();
}
```
It would be nice to define this class directly within `UserProfileView` and call it `MyUiHandlers`. Unfortunately, this introduces a dependency cycle according to the [Java Language Specification 8.1.4](http://java.sun.com/docs/books/jls/third_edition/html/classes.html). (We have advocated such an inner declaration in the past as it often works, but it seems due to a bug in Eclipse batch builder and [javac](http://bugs.sun.com/view_bug.do?bug_id=6695838). It is therefore better to avoid the cycle.)

Your presenter then needs to implement this interface:
```
public class UserProfilePresenter extends Presenter<UserProfilePresenter.MyView, UserProfilePresenter.MyProxy>
    implements UserProfileUiHandlers {
  ...
  @Override
  public void onSave() {
    doSomething();
  }
  ...
}
```

Then you have to connect these methods to your view. This is done by letting `MyView` extend `HasUiHandlers` and by calling `setUiHandlers()` within your presenter’s constructor to finalize the connection:
```
public class UserProfilePresenter extends Presenter<UserProfilePresenter.MyView, UserProfilePresenter.MyProxy>
    implements UserProfileUiHandlers{

  public interface MyView extends View, HasUiHandlers<UserProfileUiHandlers>{
  }

  @Inject
  ExamplePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
    super(eventBus, view, proxy);
    getView().setUiHandlers(this);
  }
  ...
}
```

Be careful: since the view is instantiated before the presenter, the `setUiHandlers()` method will be called after the view’s constructor has executed. This means you cannot refer to the presenter within your view’s constructor. Also, it’s important to call `setUiHandlers()` early, otherwise you might run into situations where your view needs to access a ui handler method before it even has a reference to it. Just to be on the safe side, you should probably check for `null` before invoking any ui handler method.

The last step is to let your view extends `ViewWithUiHandlers` or `PopupViewWithUiHandlers` instead of `ViewImpl` or `PopupViewImpl`. Then you’re ready to use your controls via `getUiHandlers()`. As a result, using the great `@UiHandler` annotation is now very easy:
```
public class ExampleView extends ViewWithUiHandlers<UserProfileUiHandlers> implements MyView {
  ...

  @UiHandler("saveButton")
  void onSaveButtonClicked(ClickEvent event) {
    if (getUiHandlers() != null) {
      getUiHandlers().onSave();
    }
  }
```

For more details, an article can be found on our blog: [Reversing the MVP pattern and using @UiHandler](http://arcbees.wordpress.com/2010/09/03/reversing-the-mvp-pattern-and-using-uihandler/)

## Embedding a GWTP app in a webpage ##

Say you have a standard HTML page and want the GWTP to appear within the `mainContent` element on that page. To do this, simply write your custom `RootPresenter` in this way:
```
public class MyRootPresenter extends RootPresenter {

  public static final class MyRootView extends RootView {
    @Override
    public void setInSlot(Object slot, Widget widget) {
      RootPanel.get("mainContent").add(widget);
    }
  }

  @Inject
  public MyRootPresenter( EventBus eventBus, MyRootView myRootView ) {
    super( eventBus, myRootView );
  }
}
```

Then, in your gin module, replace the following line:
```
    bind(RootPresenter.class).asEagerSingleton();
```
by:
```
    bind(RootPresenter.class).to(MyRootPresenter.class).asEagerSingleton();
```

## Notifying the user of code split requests ##

If you'd like to notify the user when an asynchronous request is performed by GWTP as a result of code splitting, you can do so easily simply by listening to the following events:
```
  AsyncCallStartEvent
  AsyncCallSuccessEvent
  AsyncCallFailEvent
```
For example, you can display a `Loading...` message when the first event is handled, and clear it when one of the other two is received. Check out the events javadoc for details.

## Why Proxies? ##

Proxies are light-weight classes whose size doesn't depend on the complexity of the underlying presenter and view. They are instantiated as soon as the application loads and are responsible for listening to any event that would require their associated presenter and view to be created. Proxies are the key to a fast MVP web application, they enable code splitting and lazy instantiation of the largest part of your code.

## Provider bundles ##

Provider bundles are an advanced way to optimize code splitting in your application. It is used by PuzzleBazar in `AdminTabPresenter` and `UserSettingsTabPresenter`.

## Automatic boilerplate generation ##

GWTP offers some annotation processors to reduce the burden of creating simple classes, like events. Check out the BoilerplateGeneration page for more details.