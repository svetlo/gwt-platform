

# Introduction #

GWTP originally started by forking the excellent MVP framework [gwt-presenter](http://code.google.com/p/gwt-presenter/). Through time, though, the two approaches have diverged significantly. To better help you decide which framework suits you best, this page discusses some the major differences.

# Good support for nested presenters #

Most web applications use a consistent layout across all their pages, sharing elements such as the header or a menu bar. In an MVP architecture this is achieved by nesting presenters into one another.

In gwt-presenter, `WidgetContainerPresenter` is the base class for nested presenters. However, its implementation requires you to pass all the subpresenters within the constructor. This goes against the principle of weak coupling, and practically defeats the purpose of using an event bus.

In contrast, GWTP is entirely built around the idea of nested presenters. Revealing a presenter triggers a precise string of events that ripples up to the parent presenters, lazily instantiating any presenter that is needed in the process. GWTP's presenters also have a clearly defined lifecycle on which you can easily hook custom behaviors.

# Different use of the event bus #

Using an event bus is a nice way to decouple objects that are weakly related. For example, the different presenters are clearly independent and they shouldn't know one another.

However, relying on the event bus for communication between strongly coupled components (i.e. a presenter and its place, as gwt-presenter does) brings little benefit and can contribute to obscure the code or lead to performance issues.

GWTP uses the event bus every time two presenters need to communicate. However, a presenter communicates with its proxy (GWTP's equivalent of a place) through method invocation, which makes it easier to follow the control flow.

Also, the approach of gwt-presenter of using a single event type to reveal any presenter has a performance drawback: each `Place` needs to handle and analyze the content of any `PlaceRequestEvent` or `PlaceRevealedEvent` that travels on the bus. This is OK for small applications, but could slow down the event bus when many presenters are instantiated.

GWTP relies on the efficient dispatching mechanism of GWT's event bus to send events only to presenters interested in it. To make sure this doesn't snowball into a menagerie of event classes and handler interfaces, GWTP relies on _type-injected events_ compatible with GWT's standard events.

# Support for fine-grained code splitting #

The PuzzleBazar project was built before code splitting was introduced in GWT, and as its  size increased, so did the loading time ― to the point where it was nearly unusable! Code splitting has the potential to dramatically reduce the initial loading time. However, using code splitting with an MVP architecture requires special care. This is because presenters can be invoked at any time. If you're not careful, you'll need to instantiate all your presenters as soon as the application loads, which will offset most of the benefits of code splitting. This is hard to avoid in gwt-presenter, especially due to the nature of [nested presenters](#Good_support_for_nested_presenters.md).

GWTP's principle of associating each presenter with a proxy solves this problem: only the
lightweight proxy needs to be instantiated when the application starts.

# Support for non-singleton presenters #

In gwt-presenter, all presenters are singletons. This is good practice for a large part of your application, but it can sometimes be too limiting.

GWTP introduces `PresenterWidget`s. These behave like regular presenters with the difference that:
  * They're not associated with a proxy (consequently, they don't have a `Place`), so their lifecycle is handled by their parent presenter ;
  * They are non-singleton and can be instantiated multiple times.

`PresenterWidget`s are useful for heavier elements that need to access your application's model. A typical use case would be a news item in a news stream. You need to have many such items, and each need to setup it's view according to a news element in your model.

# Automatic Proxy classes #

These advantages and extra features of GWTP come at the price of a slightly larger codebase. From a user's point of view, however, the biggest difference with gwt-presenter is the need to provide a `Proxy` class for every `Presenter`.

At first, this might seem like an important drawback. After all, these proxy classes are all quite similar and nobody likes to write extra boilerplate! Fortunately, GWT provides a very nice mechanism for automatically generating implementation classes called "generators". GWTP uses these generators to create the proxy classes automatically, so you never have to write them yourself. Only add a couple of annotations, provide the desired name token, et voilà!

# Conclusion #

The simplicity of gwt-presenter will definitely help you get to grip with the MVP pattern. However, as your application size grows, you might find the need for the extra features provided by GWTP.