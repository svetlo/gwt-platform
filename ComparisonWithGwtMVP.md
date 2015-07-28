One good place to start are Thomas Broyer's articles on 2.1 MVP classes:
  * [Places](http://tbroyer.posterous.com/gwt-21-places) ;
  * [Places part II](http://tbroyer.posterous.com/gwt-21-places-part-ii) ;
  * [Activities](http://tbroyer.posterous.com/gwt-21-activities) ;
  * [Activities, nesting? YAGNI!](http://tbroyer.posterous.com/gwt-21-activities-nesting-yagni)

I have never tried writing a complete app using 2.1 MVP classes, so my analysis here is mostly based on the above articles and the javadoc. As a result, I'm pretty sure there are some mistakes and I'd be more than happy to listen to corrections from people who have used the 2.1 MVP clases.

If you don't have the time to read what follows my conclusion is that:
  * 2.1 MVP is closer to the metal and will need you to write more code ;
  * 2.1 MVP takes the stance that presenter hierarchies are not desirable, GWTP takes the opposite stance ;
  * GWTP MVP's have more features out-of-the-box ;
  * 2.1 MVP are still meant to sit behind a 3rd party tool, i.e. SpringRoo or (eventually) GWTP.

# Place mechanism #

GWTP tries to make places really easy. A simple annotation is often all you need to get your presenter to respond to a place. If you need parameters your parse them in your presenter's `prepareFromRequest()` method. This handles the 95% use case in a few lines of code. For the exceptional situations where you want to do something else than reveal a presenter on a place request (reveal non-nested presenters, trigger an action, ...), you will have to go through the event bus.

GWTP shadows each of your presenter with an automatically generated proxy (hurray for GWT's generators!). This is a light weight class that listens on the event bus even before the presenter has been instantiated. As a result GWTP's will always lazily load your presenter. An annotation also makes it trivial to have your presenter sit behind a GWT code splitting point. As a bonus, this proxy is the perfect place for your presenter to listen to events that may be of interest to him before it's instantiated.

In GWT 2.1, places are a subsystem entirely decoupled from the MVP layer. You connect them via the `ActivityManager` and `ActivityMapper`, which let you lazily load or code split your presenters. The `ActivityManager` and `ActivityMapper` are not generated for you however, you have to maintain them manually. If you want your presenter to listen to other events before it's instantiated, then you will probably also have to create a proxy manually (or accept that your presenter is not lazily instantiated).

Some features are offered by both framework (i.e. user confirmation before navigation), but GWTP places offers more features out-of-the-box:
  * `Gatekeeper`s makes it very easy to implement a right management mechanism client-side. For example, you can make a presenter accessible only to the admin, another one only to logged in users, etc.
  * Hierarchical places make it easy to implement breadcrumbs or a _back to what I was doing before_ link

# Presenters (Called activities in 2.1) #

GWTP offers two different concepts: `Presenters`, which are singletons and `PresenterWidget` which can be non-singleton. These can be nested in one another, offering a powerful set of cascading lifecycle hooks that you can override: `onBind`, `onReveal`, `onReset`, `onHide` and `onUnbind`. These are cascading in the sense that a call to `onHide` on a parent presenter will cause `onHide` to be called on the children presenters. It makes it easy to organize your app logically.

This ability to nest presenters doesn't imply higher coupling since the nesting is entirely done via the event bus. A parent presenter does not know who his children are until they are connected and vice-versa. This makes it very easy to reorganize your application layout, either dynamically or based on the configuration. For example, you can make a different hierarchical layout for your mobile version.

Activities in GWT 2.1 take the stance that presenter nesting is not required. This means, for one thing, that you will have to think in advance as to how you want to split your screen in display regions. Modifying the layout within one presenter means you have to modify the global screen layout. This also means that you cannot get cascading effects: you will have to make sure `onStop` is called on all the presenters that need to go away when you navigate.

This last point makes it harder to think of your presenters as widgets with a nice separation between logic and view. On the contrary, GWTP's `PresenterWidget`s makes it very simple to build reusable components that behave like presenters. In GWT 2.1 you would have to handle the separation yourself, and to hook-up the resulting class to the lifecycle methods of its presenter when needed (`mayStop`, `onStart`, `onStop`).

# In conclusion #

It looks like GWT 2.1 MVP classes are not meant to replace 3rd party tools like SpringRoo of GWTP (see Thomas Broyer's comments in [this thread](http://groups.google.com/group/gwt-platform/browse_thread/thread/4c00e59dc139ccdf)). In their current form they offer a very nice decoupled abstraction between the various components of a well-written application: MVP, places, and event bus. But in order to write such an application effectively you have to bring these components together in a way that helps the developer gets more things done with less code. This has been GWTP's goal from day one and I think its large user base is an indication that we succeeded.