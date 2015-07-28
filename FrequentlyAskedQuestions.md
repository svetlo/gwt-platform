**This is no longer being maintained and is getting a bit out of sync, deprecating it for now... If someone wants to take over you're welcome to.**



# Introduction #
Frequently asked questions related to the GWTP project.  Code examples apply to the latest current official release: **Version 0.3 (22nd July 2010).**

Since answers are more easily understandable when they are associated with code the answers, were applicable, relate to the demo projects associated with this framework.  This document currently uses these samples:

[SimpleNestedSample](http://code.google.com/p/gwt-platform/wiki/SimpleNestedSample)

## Submitting F.A.Q. questions ##
Leave them in the comments below or in the [GWTP discussion group](http://groups.google.com/group/gwt-platform/browse_thread/thread/844ab9f400c14e78/d08feb34e57bcb05#d08feb34e57bcb05).

# The Questions #
## When running an application how do you specify the default page? ##
Each application instantiates its own version of the `PlaceManager`.  This functionality is to link up the GWT history with the application proxies.

When the application first loads code similar to the following is run:
```
	@Override
	public void onModuleLoad() {
	  //This is required for Gwt-Platform proxy's generator.
		DelayedBindRegistry.bind(ginjector);
		
		ginjector.getPlaceManager().revealCurrentPlace();
	}
```

In the `ClientGinjector` class a `PlaceManager` instance is made available:
```
PlaceManager getPlaceManager();
```
The Gin module configures the `PlaceManager` to this applications version of the `PlaceManager`:
```
bind(PlaceManager.class).to(MyApplicationPlaceManager.class).in(Singleton.class);
```

This translates to meaning whenever a `PlaceManager` is instantiated then get this applications version of it `MyApplicationPlaceManager` and make it a `Singleton` as there should only be one per application.

In the `onModuleLoad` above the `MyApplicationPlaceManager.revealCurrentPlace` method is called this calls a function which looks like this:
```
	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest);
	}
```

In order to create `defaultPlaceRequest` we need to Gin inject a default token into the constructor for `MyApplicationPlaceManager`.

```
	private final PlaceRequest defaultPlaceRequest;

	@Inject
	public MyApplicationPlaceManager(
			final EventBus eventBus,
			final TokenFormatter tokenFormatter,
			@DefaultPlace final String defaultPlaceNameToken) {
		super(eventBus, tokenFormatter);
		
		this.defaultPlaceRequest = new PlaceRequest(defaultPlaceNameToken);
	}
```

The `@DefaultPlace` annotation is setup in the GIN `ClientModule` class.

```
	    //Constants
	    bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.welcome);
```

This translates to:  A constructor variable of the type `Constant`, which is, annotated with `@DefaultPlace` then pass the name token "Welcome".

Then when the revealDefaultPlace is called the 'Welcome' presenter is shown.



## How does the GWTP framework "know" which pages to display? ##
When you create a proxy in your presenter you may annotate it with the `@NameToken`:
```
  @ProxyCodeSplit
  @NameToken(NameTokens.contactPage)
  public interface MyProxy extends ProxyPlace<ContactPresenter> {}
```

The `NestedSimple` example uses a `NameTokens` class to centralise the available tokens.  `NameTokens.contactPage` corresponds to:
```
  public static final String contactPage = "!contactPage";
  public static String getContactPage() {
    return contactPage;
  }
```
There are two public static declarations of every token, `contactPage` and `getContactPage`.   UiBinder can only use the method (`getContactPage`) and GWT Generator can only use a parameter (`@NameToken(NameTokens.contactPage)`.  The example given above would lead to a url similar to:
```
http://www.myapplication.com/!contactPage
```

The exclamation point before the token is important for making the application crawlable.  It doesn't work on AppEngine yet (see [issue](http://code.google.com/p/gwt-platform/issues/detail?id=1)), but should work fine on normal tomcat servers.

After you have bound your application in the Gin Client Modules these proxy mappings are mapped to potential GWT History Tokens.  Therefore when you create a change in history, this event is fired and the appropriate presenter is revealed.


## How do I show a presenter from code? ##
This is covered [here](http://code.google.com/p/gwt-platform/wiki/GettingStarted?tm=6#Revealing_a_presenter) but the quick answer is something like the following:
```
PlaceRequest myRequest = new PlaceRequest("desiredNameToken");
// If needed, add URL parameters in this way:
myRequest = myRequest.with( "key1", "param1" ).with( "key2", "param2" );
placeManager.revealPlace( myRequest ); 
```
The `PlaceManager` is a Singleton which hold all the information for mapping tokens with the appropriate presenter.

## How do I pass complex parameters to a presenter? ##
This is answered above for simple key-value type parameters.  However take the typical wizard type UI where at each stage of the wizard complex information needs to be communicated to the next wizard.  Of course it is easy to store this in the database, loading it each time, using a reference parameter, and utilising caching to speed up the process.  But lets say you wanted to pass complex information from one presenter to the next.

**(GWTP V0.3 only)**
One solution is to use the new GWTP annotation `@ProxyEvent`. This will reveal your place and allow code splitting while being able to customize a normal event. Downside, no parameters.

Step 1 - Define `Event` and `Handler`: See the [Custom Events](http://code.google.com/p/gwt-platform/wiki/GettingStarted#Using_custom_events) section in getting started and create your handler and event classes.  Lets assume we have created a `CurrentUserChangedEvent` and its associated `CurrentUserChangedHandler`.

Step 2 - Expand on the example event by adding the complex information you need to pass in the Event object.  For instance adding the variable `private final User currentUser;` and its associated getter and setter.

Step 3 - In the presenter you want to reveal because of this event add this sort of code:
```
	@ProxyEvent
	@Override
	  public void onCurrentUserChangedEvent(CurrentUserChangedEvent event) {
	    // Do anything you want in there. If you want to reveal the presenter:
	    // Use the information in event.getCurrentUser(); to do something.
	    forceReveal();
	  }
```

Step 4 - Fire the event where necessary:
```
CurrentUserChangedEvent.fire(this, currentUser);
```


## How do I display multiple versions of the same `PresenterWidget` on the screen at once? ##
I tried to keep this question as generic as possible.  A typical scenario here might be a `SearchResultsContainerPresenter` which contains many `SearchResultPresenter` rows.  We have used a `PresenterWidget` for each row because they need to respond and listen to application events and contain more than rudimentary application logic.

**Step 1 :  Inject a factory in `SearchResultsContainerPresenter`.**
Using GIN injection add a constructor argument in the `SearchResultsContainerPresenter`.  This will be the factory for creating `SearchResultPresenter` instances.
```
private final Provider<SearchResultPresenter> searchResultFactory;

@Inject
public SearchResultsContainerPresenter( ...
    Provider<SearchResultPresenter> searchResultFactory )
{
    this.searchResultFactory = searchResultFactory ;
    ...
}
```

**Step 2 : Create a slot in `SearchResultsContainerPresenter` in which to add rows.**
Adding a `PresenterWidget` to a parent presenter requires a slot. In our case this slot is not used to display `Presenter`s, only `PresenterWidget`s. Therefore, there is no need to use the `@ContentSlot` annotation and the slot can be represented by any object we like, in this case an `Object`.

```
// The slot
public static final Object TYPE_RevealSearchContent = new Object();
```

**Step 3 : Create a result row using the factory.** You will usually create these inside a loop and fill the row content as you iterate.
```
   SearchResultPresenter resultRow = searchResultFactory.get();
   // Call methods on resultRow to fill its content
```
Once the row is filled you want to add it to `SearchResultsContainerPresenter`:
```
   addToSlot(TYPE_RevealSearchContent, resultRow);
```

**Step 4 : Make sure the view is aware of the slot**
Then we need to alter the `SearchResultsContainerView` to ensure that the content is displayed wherever we need it in the view:
```
@Override
public void addToSlot(Object slot, Widget content) {
  if (slot == SearchResultsContainerPresenter.TYPE_RevealSearchContent)
    searchPanel.insert(content, 0);
  else
    super.addToSlot(slot, content); // Good practice, supports inheritance
}
```
You also need to override `setInSlot` so that the slot can be cleared:
```
@Override
public void setInSlot(Object slot, Widget content) {
  if (slot == SearchResultsContainerPresenter.TYPE_RevealSearchContent) {
    searchPanel.clear();
    if( content != null )  // Important! Content will be null when clearing the slot
      addToSlot(slot, content);
  } else
    super.addToSlot(slot, content); // Good practice, supports inheritance
}
```


## How do I secure my application? ##
With respect to specific application actions [Action Validators](http://code.google.com/p/gwt-platform/wiki/IntroductionActionValidator) provide a server side solution.

## How do I choose between onReveal() and onReset()? ##
`onReset` is called whenever a new presenter is requested, even if the current presenter is visible.  `onReveal` is called whenever the presenter is revealed.

## When should I use a Presenter, PresenterWidget or a regular plain old UI widget? ##
First lets differentiate between whether to use a Presenter object and when to use a UI widget.  This is down to personal preference but boils down to complexity.  The idea is to keep the application modular and easy to understand.  If the UI Control is a small single unit of functionality such as the visuals for a paging control, then place it in a UI and raise a custom event to indicate the type of thing it is initiating and let its parent presenter handle what should happen when a paging event occurs.  However if you want the paging control to be in control of the server requests and passing of data to be displayed then you should really use a PresenterWidget.

The choice between Presenter and PresenterWidget is down to how its instantiated.  If you have a re-usable functionality where many of the same type are to be displayed at once, then the PresenterWidget is the solution.  If there is only one instance per application then the Presenter is the solution.  A good example here might be a TabContainer panel and the individual tabs.  Your application may only have one TabContainer (Presenter) but may display many individual tabs (PresenterWidget).

## In what order are the PresenterImpl methods called? ##
**On First load:**
  * Constructor
  * onBind
  * prepareFromRequest
  * revealInParent
  * onReveal
  * onReset
  * prepareRequest

**On Hiding:**
  * onHide

**On Subsequent Reveals:**
  * prepareFromRequest
  * revealInParent
  * onReveal
  * onReset
  * prepareRequest


## How can I use two leaf Presenter on the same page? (i.e. the link column bar and the main content) ##
ToDo

## Should my ginjector method to return an `Provider<MyPresenter>` or an `AsyncProvider<MyPresenter>`? ##
ToDo

## What is a good way of disabling browser navigation when a modal dialog is displayed? ##
ToDo

## How to I hide presenters from users depending on the authentication level? ##
Todo

## How do I implement a multi view - nested presenter sceanrio? ##
Todo