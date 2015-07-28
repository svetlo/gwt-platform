# Version 0.7 Beta (02/01/2012) #
  * Support for dynamic tabbed presenters with user data
  * More robust token formatter, tokens now survive in email
  * Support for GWT 2.4
  * Migrated the event system to com.google.web.bindery
  * Improved error reporting during code generation and for dispatcher
  * Improved documentation
  * Many bug fixes to the eclipse plugin
  * Numerous bug fixes and enhancements. See the [complete list of closed issues](http://code.google.com/p/gwt-platform/issues/list?can=1&q=label:milestone-V1.0-7), and the [migration details](PortingV1#V0.7.md).

# Version 0.6 Beta (06/06/2011) #
  * Introduction of the [GWTP Eclipse Plugin](EclipsePlugin.md).
  * Support for [search engine crawling](CrawlerSupport.md).
  * Integration with [Google Analytics](GoogleAnalytics.md).
  * Major refactoring of GWT generators, resulting in better error messages and more robustness. (See [Issue 6](http://code.google.com/p/gwt-platform/issues/detail?id=6).)
  * Fixed a bug with the TokenFormatter. (See [Issue 286](http://code.google.com/p/gwt-platform/issues/detail?id=286).)
  * Fixed a bug with navigation confirmation mechanism. (See [Issue 291](http://code.google.com/p/gwt-platform/issues/detail?id=291).)
  * Made it possible to reveal a place without changing the URL token, so the browser _back_ button works when revealing an error place. (See [Issue 274](http://code.google.com/p/gwt-platform/issues/detail?id=273).)
  * More powerful notification system for internal asynchronous calls. (See [Issue 308](http://code.google.com/p/gwt-platform/issues/detail?id=308).)
  * Cleaned-up projects and Maven dependencies, resulting in a cleaner dependency graph and total separation of Guice and Spring dependent code.
  * Increased the quality of errors reported by annotation processors. (See [Issue 318](http://code.google.com/p/gwt-platform/issues/detail?id=318).)
  * Numerous bug fixes and enhancements. See the [complete list of closed issues](http://code.google.com/p/gwt-platform/issues/list?can=1&q=label:milestone-V1.0-6), and the [migration details](PortingV1#V0.6.md).

# Version 0.5.1 Beta (03/11/2011) #
  * Compatible with GWT 2.2, no longer compatible with GWT 2.1
  * Uses official GIN release 1.5.
  * See the [migration details](PortingV1#V0.5.1.md).

# Version 0.5 Beta (01/31/2011) #
  * Full support for Spring on the backend for the dispatcher.
  * Dispatcher now supports client-side queuing, caching and handling of actions.
  * More flexible tabs with support for user-defined properties.
  * Various improvements to the annotation processors for automatic generation of events, actions and DTOs.
  * The project is now entirely built with Maven.
  * Separate jars for the different components of GWTP.
  * Numerous bug fixes and enhancements. See the [complete list of closed issues](http://code.google.com/p/gwt-platform/issues/list?can=1&q=label:milestone-V1.0-5), and the [migration details](PortingV1#V0.5.md).

# Version 0.4 Beta (09/10/2010) #

  * Automatic generation of boilerplate code via annotation processors. See the [documentation](http://code.google.com/p/gwt-platform/wiki/BoilerplateGeneration) or the `hplacesample` for more information.
  * New manual reveal makes it possible to reveal pages only when the data they need has been fetched.
  * Dispatched actions can now be interrupted.
  * Major overhaul of the `Presenter` class hierarchy that gets rid of many problems when UnitTesting, improved naming convention.
  * The coding style is now clearly documented and the project uses checkstyle to enforce it.
  * The `HasEventBus` interface makes it possible to track the source of events when calling `fireEvent`.
  * Setup of a ContinuousIntegration server, and improved build process for releasing key versions and snapshots.
  * Other bug fixes and enhancements. See the [complete list of closed issues](http://code.google.com/p/gwt-platform/issues/list?can=1&q=label:milestone-V1.0-4), and the [migration details](PortingV1#V0.4.md).

# Version 0.3 Beta (07/21/2010) #

  * Renamed package from `com.philbeaudoin.gwtp` to `com.gwtplatform`
  * Support for hierarchical places and breadcrumbs (including a small sample app)
  * Support for registering custom events in the proxy using `@ProxyEvent`, taking care of most cases that required custom proxys
  * Support for centralized and simplified internationalization via the `mergelocales` script
  * Replaced `@PlaceInstance` by `@UseGatekeeper` with a type-safe syntax.
  * Support for individually removing `PresenterWidget` from their parent.
  * Other bug fixes and enhancements. See the [complete list of closed issues](http://code.google.com/p/gwt-platform/issues/list?can=1&q=label:milestone-V1.0-3), and the [migration details](PortingV1#V0.3.md).

# Version 0.2 Beta (05/28/2010) #

  * Major overhaul of the command pattern
    * Renamed packages and classes and simplified the hierarchy, see PortingV1 for migration details
    * Support for simple server-side action validation, see IntroductionActionValidator
    * Support for per-action dispatch URLs
    * Integrated and simple protection against XSRF attacks
    * Support for undo operations
  * Support for nested tab presenters
  * Support for MVP dialog boxes and popup panels
  * Sample applications for nested presenters, tab presenters, dialog boxes and popups
  * Support for binding the same View class to multiple presenters
  * GWTP is now published on a [Maven repository](http://code.google.com/p/gwt-platform/source/browse?repo=maven)
  * Other bug fixes and enhancements. See the [complete list of closed issues](http://code.google.com/p/gwt-platform/issues/list?can=1&q=label:milestone-V1.0-2) and the [migration details](PortingV1#V0.2.md).

# Version 0.1 Beta (04/16/2010) #

  * ~~Stable interfaces, these shouldn't change for V1.0.~~ Changes will be documented in PortingV1.
  * Now using the standard Gin library ([r137](https://code.google.com/p/gwt-platform/source/detail?r=137) with AsyncProvider).
  * Supports lifecycle hook `PresenterWidgetImpl#onReset()`.
  * Presenter lifecycle hooks (`onReveal`, `onHide`, etc.) no longer exposed in the interface.
  * Released sample application available on `https://samples.gwt-platform.googlecode.com/hg/`.
  * Other bug fixes, see the [complete list of closed issues](http://code.google.com/p/gwt-platform/issues/list?can=1&q=label:Milestone-V1.0-1).

# Version 0.0.1 Alpha (04/01/2010) #

  * First library available outside PuzzleBazar.