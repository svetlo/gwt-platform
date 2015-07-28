# Introduction #

GWTP gives you the choice between a compound jar, `gwtp-all` and a series of smaller jars containing each of the components.

If you are not using Maven and don't care about the size of your server-side artifacts, simply use the compound jar. It will not make your client-side javascript code bigger since GWT compiles only what it needs.

However, if you want to minimize the size of your server-side classes (for example to reduce cold start-up time on AppEngine), then you will probably want to use only the jars you need. This page describes the jars needed for every component listed in LibraryOverview:

**MVP component**
  * `gwtp-mvp-client`
  * `gwtp-clients-common`
**Dispatch component**
  * `gwtp-dispatch-client`
  * `gwtp-clients-common`
  * `gwtp-dispatch-shared` (server-side)
  * `gwtp-dispatch-server` (server-side)
  * `gwtp-dispatch-server-guice` (server-side, for Guice, GWTP 0.6+)
  * `gwtp-dispatch-server-spring` (server-side, for Spring, GWTP 0.6+)
**Crawler component**
  * `gwtp-crawler` (server-side)
  * `gwtp-crawler-service` (server-side, on the crawler service app)
  * TODO (See [Issue 298](http://code.google.com/p/gwt-platform/issues/detail?id=298))
**Annotation component**
  * `gwtp-processors`
**Tester component**
  * `gwtp-tester`
  * TODO (See [Issue 299](http://code.google.com/p/gwt-platform/issues/detail?id=299))

The jars noted as _server-side_ need to be deployed on your server, the others are only needed for compilation or testing.

## Dependencies ##

For a list of all dependencies, see DependencyHierarchy.