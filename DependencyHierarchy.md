

# Introduction #

This document shows the full dependency trees of the jars corresponding to the different modules of GWTP. For a more concise description of internal dependencies, see DescriptionOfIndividualJars.

If you use Maven, the dependencies you need are pulled automatically and deployed to the server only when required. However, if you do not use Maven you will have to manually download the jars listed below and add them to your classpath. Naturally, you only need each dependency once, even if it's listed in multiple trees.

If the dependency is marked as `provided`, you don't need to deploy it to your server, the jar is only needed for GWT-compilation. If it's marked as `compile` you need to deploy it. Finally, it it's marked as `test` you not need the dependency at all.

# Version 0.6 #

Here are the dependency trees for GWTP 0.6, generated via `mvn dependency:tree`.
```
------------------------------------------------------------------------
Building GWTP all (compound jar) 0.6
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-all ---
com.gwtplatform:gwtp-all:jar:0.6
+- com.gwtplatform:gwtp-mvp-client:jar:0.6:compile
+- com.gwtplatform:gwtp-clients-common:jar:0.6:compile
|  \- com.google.inject:guice:jar:3.0:compile
|     \- aopalliance:aopalliance:jar:1.0:compile
+- com.gwtplatform:gwtp-dispatch-client:jar:0.6:compile
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.6:compile
+- com.gwtplatform:gwtp-dispatch-server:jar:0.6:compile
+- com.gwtplatform:gwtp-dispatch-server-guice:jar:0.6:compile
+- com.gwtplatform:gwtp-dispatch-server-spring:jar:0.6:compile
|  +- org.springframework:spring-core:jar:3.0.5.RELEASE:compile
|  |  +- org.springframework:spring-asm:jar:3.0.5.RELEASE:compile
|  |  \- commons-logging:commons-logging:jar:1.1.1:compile
|  +- org.springframework:spring-context:jar:3.0.5.RELEASE:compile
|  |  +- org.springframework:spring-aop:jar:3.0.5.RELEASE:compile
|  |  \- org.springframework:spring-expression:jar:3.0.5.RELEASE:compile
|  +- org.springframework:spring-beans:jar:3.0.5.RELEASE:compile
|  \- org.springframework:spring-web:jar:3.0.5.RELEASE:compile
+- com.gwtplatform:gwtp-tester:jar:0.6:compile
+- com.gwtplatform:gwtp-processors:jar:0.6:compile
+- com.gwtplatform:gwtp-crawler:jar:0.6:compile
|  \- javax.servlet:servlet-api:jar:2.5:compile
+- com.gwtplatform:gwtp-crawler-service:jar:0.6:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  +- net.sourceforge.htmlunit:htmlunit:jar:2.8:compile
|  |  +- xalan:xalan:jar:2.7.1:compile
|  |  |  \- xalan:serializer:jar:2.7.1:compile
|  |  +- commons-collections:commons-collections:jar:3.2.1:compile
|  |  +- commons-lang:commons-lang:jar:2.4:compile
|  |  +- org.apache.httpcomponents:httpclient:jar:4.0.1:compile
|  |  |  \- org.apache.httpcomponents:httpcore:jar:4.0.1:compile
|  |  +- org.apache.httpcomponents:httpmime:jar:4.0.1:compile
|  |  |  \- org.apache.james:apache-mime4j:jar:0.6:compile
|  |  +- commons-codec:commons-codec:jar:1.4:compile
|  |  +- net.sourceforge.htmlunit:htmlunit-core-js:jar:2.8:compile
|  |  +- xerces:xercesImpl:jar:2.9.1:compile
|  |  |  \- xml-apis:xml-apis:jar:1.3.04:compile
|  |  +- net.sourceforge.nekohtml:nekohtml:jar:1.9.14:compile
|  |  +- net.sourceforge.cssparser:cssparser:jar:0.9.5:compile
|  |  |  \- org.w3c.css:sac:jar:1.3:compile
|  |  \- commons-io:commons-io:jar:1.4:compile
|  +- com.google.appengine:appengine-api-1.0-sdk:jar:1.5.0:compile
|  +- com.google.inject.extensions:guice-servlet:jar:3.0:compile
|  +- com.googlecode.objectify:objectify:jar:2.2.3:compile
|  \- javax.persistence:persistence-api:jar:1.0:compile
+- junit:junit:jar:4.5:test
+- javax.validation:validation-api:jar:1.0.0.GA:provided
\- javax.validation:validation-api:jar:sources:1.0.0.GA:provided

------------------------------------------------------------------------
Building GWTP MVP client 0.6
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-mvp-client ---
com.gwtplatform:gwtp-mvp-client:jar:0.6
+- com.gwtplatform:gwtp-clients-common:jar:0.6:compile
+- com.google.gwt:gwt-user:jar:2.3.0:provided
+- com.google.gwt:gwt-dev:jar:2.3.0:provided
+- com.google.gwt.inject:gin:jar:1.5.0:provided
|  +- com.google.inject.extensions:guice-assistedinject:jar:3.0:provided (version managed from 3.0-rc2)
|  \- com.google.gwt:gwt-servlet:jar:2.3.0:runtime (version managed from 2.2.0; scope managed from provided)
+- com.google.inject:guice:jar:3.0:provided (scope not updated to compile)
|  \- aopalliance:aopalliance:jar:1.0:provided
+- javax.inject:javax.inject:jar:1:provided
+- com.gwtplatform:gwtp-tester:jar:0.6:test
|  +- com.gwtplatform:gwtp-dispatch-shared:jar:0.6:test
|  \- com.gwtplatform:gwtp-dispatch-server:jar:0.6:test
+- org.jukito:jukito:jar:1.1:test
+- org.mockito:mockito-core:jar:1.8.5:test
|  +- org.hamcrest:hamcrest-core:jar:1.1:test
|  \- org.objenesis:objenesis:jar:1.0:test
+- junit:junit:jar:4.5:test
+- javax.validation:validation-api:jar:1.0.0.GA:provided
\- javax.validation:validation-api:jar:sources:1.0.0.GA:provided

------------------------------------------------------------------------
Building GWTP Dispatch client 0.6
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-dispatch-client ---
com.gwtplatform:gwtp-dispatch-client:jar:0.6
+- com.gwtplatform:gwtp-clients-common:jar:0.6:compile
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.6:compile
+- com.google.gwt:gwt-user:jar:2.3.0:provided
+- com.google.gwt.inject:gin:jar:1.5.0:provided
|  +- com.google.inject.extensions:guice-assistedinject:jar:3.0:provided (version managed from 3.0-rc2)
|  \- com.google.gwt:gwt-servlet:jar:2.3.0:runtime (version managed from 2.2.0; scope managed from provided)
+- com.google.inject:guice:jar:3.0:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- junit:junit:jar:4.5:test
+- javax.validation:validation-api:jar:1.0.0.GA:provided
\- javax.validation:validation-api:jar:sources:1.0.0.GA:provided

------------------------------------------------------------------------
Building GWTP Dispatch Server, Guice implementation 0.6
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-dispatch-server-guice ---
com.gwtplatform:gwtp-dispatch-server-guice:jar:0.6
+- com.gwtplatform:gwtp-dispatch-server:jar:0.6:compile
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.6:compile
+- com.google.gwt:gwt-user:jar:2.3.0:provided
+- com.google.inject:guice:jar:3.0:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- com.gwtplatform:gwtp-dispatch-test:jar:0.6:test
+- junit:junit:jar:4.5:test
+- javax.validation:validation-api:jar:1.0.0.GA:provided
\- javax.validation:validation-api:jar:sources:1.0.0.GA:provided

------------------------------------------------------------------------
Building GWTP Dispatch Server, Spring implementation 0.6
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-dispatch-server-spring ---
com.gwtplatform:gwtp-dispatch-server-spring:jar:0.6
+- com.gwtplatform:gwtp-dispatch-server:jar:0.6:compile
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.6:compile
+- com.google.gwt:gwt-user:jar:2.3.0:provided
+- org.springframework:spring-core:jar:3.0.5.RELEASE:compile
|  +- org.springframework:spring-asm:jar:3.0.5.RELEASE:compile
|  \- commons-logging:commons-logging:jar:1.1.1:compile
+- org.springframework:spring-context:jar:3.0.5.RELEASE:compile
|  +- org.springframework:spring-aop:jar:3.0.5.RELEASE:compile
|  \- org.springframework:spring-expression:jar:3.0.5.RELEASE:compile
+- org.springframework:spring-beans:jar:3.0.5.RELEASE:compile
+- org.springframework:spring-web:jar:3.0.5.RELEASE:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- org.springframework:spring-test:jar:3.0.5.RELEASE:test
+- cglib:cglib-nodep:jar:2.2:test
+- com.gwtplatform:gwtp-dispatch-test:jar:0.6:test
+- junit:junit:jar:4.5:test
+- javax.validation:validation-api:jar:1.0.0.GA:provided
\- javax.validation:validation-api:jar:sources:1.0.0.GA:provided

------------------------------------------------------------------------
Building GWTP Crawler 0.6
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-crawler ---
com.gwtplatform:gwtp-crawler:jar:0.6
+- javax.servlet:servlet-api:jar:2.5:compile
+- com.google.inject:guice:jar:3.0:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- junit:junit:jar:4.5:test
+- javax.validation:validation-api:jar:1.0.0.GA:provided
\- javax.validation:validation-api:jar:sources:1.0.0.GA:provided
                                                                        
------------------------------------------------------------------------
Building GWTP Crawler Service 0.6
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-crawler-service ---
com.gwtplatform:gwtp-crawler-service:jar:0.6
+- javax.servlet:servlet-api:jar:2.5:compile
+- javax.inject:javax.inject:jar:1:compile
+- net.sourceforge.htmlunit:htmlunit:jar:2.8:compile
|  +- xalan:xalan:jar:2.7.1:compile
|  |  \- xalan:serializer:jar:2.7.1:compile
|  +- commons-collections:commons-collections:jar:3.2.1:compile
|  +- commons-lang:commons-lang:jar:2.4:compile
|  +- org.apache.httpcomponents:httpclient:jar:4.0.1:compile
|  |  \- org.apache.httpcomponents:httpcore:jar:4.0.1:compile
|  +- org.apache.httpcomponents:httpmime:jar:4.0.1:compile
|  |  \- org.apache.james:apache-mime4j:jar:0.6:compile
|  +- commons-codec:commons-codec:jar:1.4:compile
|  +- net.sourceforge.htmlunit:htmlunit-core-js:jar:2.8:compile
|  +- xerces:xercesImpl:jar:2.9.1:compile
|  |  \- xml-apis:xml-apis:jar:1.3.04:compile
|  +- net.sourceforge.nekohtml:nekohtml:jar:1.9.14:compile
|  +- net.sourceforge.cssparser:cssparser:jar:0.9.5:compile
|  |  \- org.w3c.css:sac:jar:1.3:compile
|  +- commons-io:commons-io:jar:1.4:compile
|  \- commons-logging:commons-logging:jar:1.1.1:compile
+- com.google.appengine:appengine-api-1.0-sdk:jar:1.5.0:compile
+- com.google.inject:guice:jar:3.0:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- com.google.inject.extensions:guice-servlet:jar:3.0:compile
+- com.googlecode.objectify:objectify:jar:2.2.3:compile
+- javax.persistence:persistence-api:jar:1.0:compile
+- junit:junit:jar:4.5:test
+- javax.validation:validation-api:jar:1.0.0.GA:provided
\- javax.validation:validation-api:jar:sources:1.0.0.GA:provided

------------------------------------------------------------------------
Building GWTP Annotation Processors 0.6
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-processors ---
com.gwtplatform:gwtp-processors:jar:0.6
+- junit:junit:jar:4.5:test
+- javax.validation:validation-api:jar:1.0.0.GA:provided
\- javax.validation:validation-api:jar:sources:1.0.0.GA:provided

------------------------------------------------------------------------
Building GWTP Tester 0.6
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-tester ---
com.gwtplatform:gwtp-tester:jar:0.6
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.6:compile
+- com.gwtplatform:gwtp-dispatch-client:jar:0.6:provided
|  \- com.gwtplatform:gwtp-clients-common:jar:0.6:provided
+- com.gwtplatform:gwtp-dispatch-server:jar:0.6:compile
+- com.google.gwt:gwt-user:jar:2.3.0:provided
+- com.google.inject:guice:jar:3.0:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- com.google.gwt.inject:gin:jar:1.5.0:provided
|  +- com.google.inject.extensions:guice-assistedinject:jar:3.0:provided (version managed from 3.0-rc2)
|  \- com.google.gwt:gwt-servlet:jar:2.3.0:runtime (version managed from 2.2.0; scope managed from provided)
+- org.jukito:jukito:jar:1.1:test
+- org.mockito:mockito-core:jar:1.8.5:test
|  +- org.hamcrest:hamcrest-core:jar:1.1:test
|  \- org.objenesis:objenesis:jar:1.0:test
+- com.google.gwt:gwt-dev:jar:2.3.0:test
+- junit:junit:jar:4.5:test
+- javax.validation:validation-api:jar:1.0.0.GA:provided
\- javax.validation:validation-api:jar:sources:1.0.0.GA:provided
```

# Version 0.5.1 #

Here are the dependency trees for GWTP 0.5.1.
```
------------------------------------------------------------------------
Building GWTP all (compound jar) 0.5.1
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-all ---
com.gwtplatform:gwtp-all:jar:0.5.1
+- com.gwtplatform:gwtp-mvp-client:jar:0.5.1:compile
|  +- com.google.inject:guice:jar:3.0-rc2:compile
|  |  +- javax.inject:javax.inject:jar:1:compile
|  |  \- aopalliance:aopalliance:jar:1.0:compile
|  +- com.google.gwt:gwt-user:jar:2.2.0:compile
|  \- com.google.gwt.inject:gin:jar:1.5.0:compile
|     +- com.google.inject.extensions:guice-assistedinject:jar:3.0-rc2:compile
|     \- com.google.gwt:gwt-servlet:jar:2.2.0:runtime
+- com.gwtplatform:gwtp-clients-common:jar:0.5.1:compile
+- com.gwtplatform:gwtp-dispatch-client:jar:0.5.1:compile
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.5.1:compile
+- com.gwtplatform:gwtp-dispatch-server:jar:0.5.1:compile
|  +- org.springframework:spring-core:jar:3.0.5.RELEASE:compile
|  |  +- org.springframework:spring-asm:jar:3.0.5.RELEASE:compile
|  |  \- commons-logging:commons-logging:jar:1.1.1:compile
|  +- org.springframework:spring-context:jar:3.0.5.RELEASE:compile
|  |  +- org.springframework:spring-aop:jar:3.0.5.RELEASE:compile
|  |  \- org.springframework:spring-expression:jar:3.0.5.RELEASE:compile
|  +- org.springframework:spring-beans:jar:3.0.5.RELEASE:compile
|  \- org.springframework:spring-web:jar:3.0.5.RELEASE:compile
+- com.gwtplatform:gwtp-tester:jar:0.5.1:compile
+- com.gwtplatform:gwtp-processors:jar:0.5.1:compile
+- com.gwtplatform:gwtp-crawler:jar:0.5.1:compile
|  +- net.sourceforge.htmlunit:htmlunit:jar:2.8:compile
|  |  +- xalan:xalan:jar:2.7.1:compile
|  |  |  \- xalan:serializer:jar:2.7.1:compile
|  |  +- commons-collections:commons-collections:jar:3.2.1:compile
|  |  +- commons-lang:commons-lang:jar:2.4:compile
|  |  +- org.apache.httpcomponents:httpclient:jar:4.0.1:compile
|  |  |  \- org.apache.httpcomponents:httpcore:jar:4.0.1:compile
|  |  +- org.apache.httpcomponents:httpmime:jar:4.0.1:compile
|  |  |  \- org.apache.james:apache-mime4j:jar:0.6:compile
|  |  +- commons-codec:commons-codec:jar:1.4:compile
|  |  +- net.sourceforge.htmlunit:htmlunit-core-js:jar:2.8:compile
|  |  +- xerces:xercesImpl:jar:2.9.1:compile
|  |  |  \- xml-apis:xml-apis:jar:1.3.04:compile
|  |  +- net.sourceforge.nekohtml:nekohtml:jar:1.9.14:compile
|  |  +- net.sourceforge.cssparser:cssparser:jar:0.9.5:compile
|  |  |  \- org.w3c.css:sac:jar:1.3:compile
|  |  \- commons-io:commons-io:jar:1.4:compile
|  \- com.google.appengine:appengine-api-1.0-sdk:jar:1.4.2:compile
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP MVP client 0.5.1
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-mvp-client ---
com.gwtplatform:gwtp-mvp-client:jar:0.5.1
+- com.gwtplatform:gwtp-clients-common:jar:0.5.1:compile
+- com.google.inject:guice:jar:3.0-rc2:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- com.google.gwt:gwt-user:jar:2.2.0:compile
+- com.google.gwt:gwt-dev:jar:2.2.0:provided
+- com.google.gwt.inject:gin:jar:1.5.0:compile
|  +- com.google.inject.extensions:guice-assistedinject:jar:3.0-rc2:compile
|  \- com.google.gwt:gwt-servlet:jar:2.2.0:runtime
+- com.gwtplatform:gwtp-tester:jar:0.5.1:test
|  +- com.gwtplatform:gwtp-dispatch-shared:jar:0.5.1:test
|  +- com.gwtplatform:gwtp-dispatch-client:jar:0.5.1:test
|  \- com.gwtplatform:gwtp-dispatch-server:jar:0.5.1:test
|     +- org.springframework:spring-core:jar:3.0.5.RELEASE:test
|     |  +- org.springframework:spring-asm:jar:3.0.5.RELEASE:test
|     |  \- commons-logging:commons-logging:jar:1.1.1:test
|     +- org.springframework:spring-context:jar:3.0.5.RELEASE:test
|     |  +- org.springframework:spring-aop:jar:3.0.5.RELEASE:test
|     |  \- org.springframework:spring-expression:jar:3.0.5.RELEASE:test
|     +- org.springframework:spring-beans:jar:3.0.5.RELEASE:test
|     \- org.springframework:spring-web:jar:3.0.5.RELEASE:test
+- org.jukito:jukito:jar:1.0:test
|  +- org.mockito:mockito-core:jar:1.8.5:test
|  |  +- org.hamcrest:hamcrest-core:jar:1.1:test
|  |  \- org.objenesis:objenesis:jar:1.0:test
|  \- com.google.inject.extensions:guice-assisted-inject:jar:2.0:test
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP Dispatch client 0.5.1
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-dispatch-client ---
com.gwtplatform:gwtp-dispatch-client:jar:0.5.1
+- com.gwtplatform:gwtp-clients-common:jar:0.5.1:compile
|  \- com.google.inject:guice:jar:3.0-rc2:compile
|     +- javax.inject:javax.inject:jar:1:compile
|     \- aopalliance:aopalliance:jar:1.0:compile
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.5.1:compile
+- com.google.gwt:gwt-user:jar:2.2.0:compile
+- com.google.gwt.inject:gin:jar:1.5.0:compile
|  +- com.google.inject.extensions:guice-assistedinject:jar:3.0-rc2:compile
|  \- com.google.gwt:gwt-servlet:jar:2.2.0:runtime
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP Dispatch Server 0.5.1
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-dispatch-server ---
com.gwtplatform:gwtp-dispatch-server:jar:0.5.1
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.5.1:compile
|  \- com.google.inject:guice:jar:3.0-rc2:compile
|     \- javax.inject:javax.inject:jar:1:compile
+- com.gwtplatform:gwtp-dispatch-client:jar:0.5.1:compile
|  +- com.gwtplatform:gwtp-clients-common:jar:0.5.1:compile
|  \- com.google.gwt.inject:gin:jar:1.5.0:compile
|     +- com.google.inject.extensions:guice-assistedinject:jar:3.0-rc2:compile
|     \- com.google.gwt:gwt-servlet:jar:2.2.0:runtime
+- com.google.gwt:gwt-user:jar:2.2.0:compile
+- org.springframework:spring-core:jar:3.0.5.RELEASE:compile
|  +- org.springframework:spring-asm:jar:3.0.5.RELEASE:compile
|  \- commons-logging:commons-logging:jar:1.1.1:compile
+- org.springframework:spring-context:jar:3.0.5.RELEASE:compile
|  +- org.springframework:spring-aop:jar:3.0.5.RELEASE:compile
|  \- org.springframework:spring-expression:jar:3.0.5.RELEASE:compile
+- org.springframework:spring-beans:jar:3.0.5.RELEASE:compile
+- org.springframework:spring-web:jar:3.0.5.RELEASE:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- org.springframework:spring-test:jar:3.0.5.RELEASE:test
+- cglib:cglib-nodep:jar:2.2:test
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP Crawler 0.5.1
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-crawler ---
com.gwtplatform:gwtp-crawler:jar:0.5.1
+- net.sourceforge.htmlunit:htmlunit:jar:2.8:compile
|  +- xalan:xalan:jar:2.7.1:compile
|  |  \- xalan:serializer:jar:2.7.1:compile
|  +- commons-collections:commons-collections:jar:3.2.1:compile
|  +- commons-lang:commons-lang:jar:2.4:compile
|  +- org.apache.httpcomponents:httpclient:jar:4.0.1:compile
|  |  \- org.apache.httpcomponents:httpcore:jar:4.0.1:compile
|  +- org.apache.httpcomponents:httpmime:jar:4.0.1:compile
|  |  \- org.apache.james:apache-mime4j:jar:0.6:compile
|  +- commons-codec:commons-codec:jar:1.4:compile
|  +- net.sourceforge.htmlunit:htmlunit-core-js:jar:2.8:compile
|  +- xerces:xercesImpl:jar:2.9.1:compile
|  |  \- xml-apis:xml-apis:jar:1.3.04:compile
|  +- net.sourceforge.nekohtml:nekohtml:jar:1.9.14:compile
|  +- net.sourceforge.cssparser:cssparser:jar:0.9.5:compile
|  |  \- org.w3c.css:sac:jar:1.3:compile
|  +- commons-io:commons-io:jar:1.4:compile
|  \- commons-logging:commons-logging:jar:1.1.1:compile
+- com.google.appengine:appengine-api-1.0-sdk:jar:1.4.2:compile
+- com.gwtplatform:gwtp-dispatch-server:jar:0.5.1:compile
|  +- com.gwtplatform:gwtp-dispatch-shared:jar:0.5.1:compile
|  +- com.gwtplatform:gwtp-dispatch-client:jar:0.5.1:compile
|  |  +- com.gwtplatform:gwtp-clients-common:jar:0.5.1:compile
|  |  \- com.google.gwt.inject:gin:jar:1.5.0:compile
|  |     +- com.google.inject.extensions:guice-assistedinject:jar:3.0-rc2:compile
|  |     \- com.google.gwt:gwt-servlet:jar:2.2.0:runtime
|  +- org.springframework:spring-core:jar:3.0.5.RELEASE:compile
|  |  \- org.springframework:spring-asm:jar:3.0.5.RELEASE:compile
|  +- org.springframework:spring-context:jar:3.0.5.RELEASE:compile
|  |  +- org.springframework:spring-aop:jar:3.0.5.RELEASE:compile
|  |  \- org.springframework:spring-expression:jar:3.0.5.RELEASE:compile
|  +- org.springframework:spring-beans:jar:3.0.5.RELEASE:compile
|  \- org.springframework:spring-web:jar:3.0.5.RELEASE:compile
+- com.google.gwt:gwt-user:jar:2.2.0:compile
+- com.google.gwt:gwt-dev:jar:2.2.0:provided
+- com.google.inject:guice:jar:3.0-rc2:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP Annotation Processors 0.5.1
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-processors ---
com.gwtplatform:gwtp-processors:jar:0.5.1
+- com.google.gwt:gwt-user:jar:2.2.0:compile
+- com.google.inject:guice:jar:3.0-rc2:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP Tester 0.5.1
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-tester ---
com.gwtplatform:gwtp-tester:jar:0.5.1
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.5.1:compile
+- com.gwtplatform:gwtp-dispatch-client:jar:0.5.1:compile
|  \- com.gwtplatform:gwtp-clients-common:jar:0.5.1:compile
+- com.gwtplatform:gwtp-dispatch-server:jar:0.5.1:compile
|  +- org.springframework:spring-core:jar:3.0.5.RELEASE:compile
|  |  +- org.springframework:spring-asm:jar:3.0.5.RELEASE:compile
|  |  \- commons-logging:commons-logging:jar:1.1.1:compile
|  +- org.springframework:spring-context:jar:3.0.5.RELEASE:compile
|  |  +- org.springframework:spring-aop:jar:3.0.5.RELEASE:compile
|  |  \- org.springframework:spring-expression:jar:3.0.5.RELEASE:compile
|  +- org.springframework:spring-beans:jar:3.0.5.RELEASE:compile
|  \- org.springframework:spring-web:jar:3.0.5.RELEASE:compile
+- com.google.gwt:gwt-user:jar:2.2.0:compile
+- com.google.inject:guice:jar:3.0-rc2:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- com.google.gwt.inject:gin:jar:1.5.0:compile
|  +- com.google.inject.extensions:guice-assistedinject:jar:3.0-rc2:compile
|  \- com.google.gwt:gwt-servlet:jar:2.2.0:runtime
+- org.jukito:jukito:jar:1.0:test
|  +- org.mockito:mockito-core:jar:1.8.5:test
|  |  +- org.hamcrest:hamcrest-core:jar:1.1:test
|  |  \- org.objenesis:objenesis:jar:1.0:test
|  \- com.google.inject.extensions:guice-assisted-inject:jar:2.0:test
+- com.google.gwt:gwt-dev:jar:2.2.0:test
\- junit:junit:jar:4.5:test
```

# Version 0.5 #

Here are the dependency trees for GWTP 0.5.
```
------------------------------------------------------------------------
Building GWTP all (compound jar) 0.5
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-all ---
com.gwtplatform:gwtp-all:jar:0.5
+- com.gwtplatform:gwtp-mvp-client:jar:0.5:compile
|  +- com.google.inject:guice:jar:3.0-rc2:compile
|  |  +- javax.inject:javax.inject:jar:1:compile
|  |  \- aopalliance:aopalliance:jar:1.0:compile
|  +- com.google.gwt:gwt-user:jar:2.1.0:compile
|  \- com.google.gwt:gwt-gin:jar:1.0-r137:compile
+- com.gwtplatform:gwtp-clients-common:jar:0.5:compile
+- com.gwtplatform:gwtp-dispatch-client:jar:0.5:compile
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.5:compile
+- com.gwtplatform:gwtp-dispatch-server:jar:0.5:compile
|  +- org.springframework:spring-core:jar:3.0.3.RELEASE:compile
|  |  +- org.springframework:spring-asm:jar:3.0.3.RELEASE:compile
|  |  \- commons-logging:commons-logging:jar:1.1.1:compile
|  +- org.springframework:spring-context:jar:3.0.3.RELEASE:compile
|  |  +- org.springframework:spring-aop:jar:3.0.3.RELEASE:compile
|  |  \- org.springframework:spring-expression:jar:3.0.3.RELEASE:compile
|  +- org.springframework:spring-beans:jar:3.0.3.RELEASE:compile
|  \- org.springframework:spring-web:jar:3.0.3.RELEASE:compile
+- com.gwtplatform:gwtp-tester:jar:0.5:compile
+- com.gwtplatform:gwtp-processors:jar:0.5:compile
+- com.gwtplatform:gwtp-crawler:jar:0.5:compile
|  +- net.sourceforge.htmlunit:crawl_htmlunit:jar:r5662-gae:compile
|  \- com.google.appengine:appengine-api-1.0-sdk:jar:1.3.1:compile
\- junit:junit:jar:4.5:test
------------------------------------------------------------------------

------------------------------------------------------------------------
Building GWTP MVP client 0.5
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-mvp-client ---
com.gwtplatform:gwtp-mvp-client:jar:0.5
+- com.gwtplatform:gwtp-clients-common:jar:0.5:compile
+- com.google.inject:guice:jar:3.0-rc2:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- com.google.gwt:gwt-user:jar:2.1.0:compile
+- com.google.gwt:gwt-dev:jar:2.1.0:provided
+- com.google.gwt:gwt-gin:jar:1.0-r137:compile
+- com.gwtplatform:gwtp-tester:jar:0.5:test
|  +- com.gwtplatform:gwtp-dispatch-shared:jar:0.5:test
|  +- com.gwtplatform:gwtp-dispatch-client:jar:0.5:test
|  \- com.gwtplatform:gwtp-dispatch-server:jar:0.5:test
|     +- org.springframework:spring-core:jar:3.0.3.RELEASE:test
|     |  +- org.springframework:spring-asm:jar:3.0.3.RELEASE:test
|     |  \- commons-logging:commons-logging:jar:1.1.1:test
|     +- org.springframework:spring-context:jar:3.0.3.RELEASE:test
|     |  +- org.springframework:spring-aop:jar:3.0.3.RELEASE:test
|     |  \- org.springframework:spring-expression:jar:3.0.3.RELEASE:test
|     +- org.springframework:spring-beans:jar:3.0.3.RELEASE:test
|     \- org.springframework:spring-web:jar:3.0.3.RELEASE:test
+- org.jukito:jukito:jar:1.0:test
|  +- org.mockito:mockito-core:jar:1.8.5:test
|  |  +- org.hamcrest:hamcrest-core:jar:1.1:test
|  |  \- org.objenesis:objenesis:jar:1.0:test
|  \- com.google.inject.extensions:guice-assisted-inject:jar:2.0:test
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP Dispatch client 0.5
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-dispatch-client ---
com.gwtplatform:gwtp-dispatch-client:jar:0.5
+- com.gwtplatform:gwtp-clients-common:jar:0.5:compile
|  \- com.google.inject:guice:jar:3.0-rc2:compile
|     +- javax.inject:javax.inject:jar:1:compile
|     \- aopalliance:aopalliance:jar:1.0:compile
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.5:compile
+- com.google.gwt:gwt-user:jar:2.1.0:compile
+- com.google.gwt:gwt-gin:jar:1.0-r137:compile
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP Dispatch Server 0.5
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-dispatch-server ---
com.gwtplatform:gwtp-dispatch-server:jar:0.5
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.5:compile
|  \- com.google.inject:guice:jar:3.0-rc2:compile
|     \- javax.inject:javax.inject:jar:1:compile
+- com.gwtplatform:gwtp-dispatch-client:jar:0.5:compile
|  +- com.gwtplatform:gwtp-clients-common:jar:0.5:compile
|  \- com.google.gwt:gwt-gin:jar:1.0-r137:compile
+- com.google.gwt:gwt-user:jar:2.1.0:compile
+- org.springframework:spring-core:jar:3.0.3.RELEASE:compile
|  +- org.springframework:spring-asm:jar:3.0.3.RELEASE:compile
|  \- commons-logging:commons-logging:jar:1.1.1:compile
+- org.springframework:spring-context:jar:3.0.3.RELEASE:compile
|  +- org.springframework:spring-aop:jar:3.0.3.RELEASE:compile
|  \- org.springframework:spring-expression:jar:3.0.3.RELEASE:compile
+- org.springframework:spring-beans:jar:3.0.3.RELEASE:compile
+- org.springframework:spring-web:jar:3.0.3.RELEASE:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- org.springframework:spring-test:jar:3.0.3.RELEASE:test
+- cglib:cglib-nodep:jar:2.2:test
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP Crawler 0.5
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-crawler ---
com.gwtplatform:gwtp-crawler:jar:0.5
+- net.sourceforge.htmlunit:crawl_htmlunit:jar:r5662-gae:compile
+- com.google.appengine:appengine-api-1.0-sdk:jar:1.3.1:compile
+- com.gwtplatform:gwtp-dispatch-server:jar:0.5:compile
|  +- com.gwtplatform:gwtp-dispatch-shared:jar:0.5:compile
|  +- com.gwtplatform:gwtp-dispatch-client:jar:0.5:compile
|  |  +- com.gwtplatform:gwtp-clients-common:jar:0.5:compile
|  |  \- com.google.gwt:gwt-gin:jar:1.0-r137:compile
|  +- org.springframework:spring-core:jar:3.0.3.RELEASE:compile
|  |  +- org.springframework:spring-asm:jar:3.0.3.RELEASE:compile
|  |  \- commons-logging:commons-logging:jar:1.1.1:compile
|  +- org.springframework:spring-context:jar:3.0.3.RELEASE:compile
|  |  +- org.springframework:spring-aop:jar:3.0.3.RELEASE:compile
|  |  \- org.springframework:spring-expression:jar:3.0.3.RELEASE:compile
|  +- org.springframework:spring-beans:jar:3.0.3.RELEASE:compile
|  \- org.springframework:spring-web:jar:3.0.3.RELEASE:compile
+- com.google.gwt:gwt-user:jar:2.1.0:compile
+- com.google.gwt:gwt-dev:jar:2.1.0:provided
+- com.google.inject:guice:jar:3.0-rc2:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP AnnotatioProcessors 0.5
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-processors ---
com.gwtplatform:gwtp-processors:jar:0.5
+- com.google.gwt:gwt-user:jar:2.1.0:compile
+- com.google.inject:guice:jar:3.0-rc2:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
\- junit:junit:jar:4.5:test

------------------------------------------------------------------------
Building GWTP Tester 0.5
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ gwtp-tester ---
com.gwtplatform:gwtp-tester:jar:0.5
+- com.gwtplatform:gwtp-dispatch-shared:jar:0.5:compile
+- com.gwtplatform:gwtp-dispatch-client:jar:0.5:compile
|  \- com.gwtplatform:gwtp-clients-common:jar:0.5:compile
+- com.gwtplatform:gwtp-dispatch-server:jar:0.5:compile
|  +- org.springframework:spring-core:jar:3.0.3.RELEASE:compile
|  |  +- org.springframework:spring-asm:jar:3.0.3.RELEASE:compile
|  |  \- commons-logging:commons-logging:jar:1.1.1:compile
|  +- org.springframework:spring-context:jar:3.0.3.RELEASE:compile
|  |  +- org.springframework:spring-aop:jar:3.0.3.RELEASE:compile
|  |  \- org.springframework:spring-expression:jar:3.0.3.RELEASE:compile
|  +- org.springframework:spring-beans:jar:3.0.3.RELEASE:compile
|  \- org.springframework:spring-web:jar:3.0.3.RELEASE:compile
+- com.google.gwt:gwt-user:jar:2.1.0:compile
+- com.google.inject:guice:jar:3.0-rc2:compile
|  +- javax.inject:javax.inject:jar:1:compile
|  \- aopalliance:aopalliance:jar:1.0:compile
+- com.google.gwt:gwt-gin:jar:1.0-r137:compile
+- org.jukito:jukito:jar:1.0:test
|  +- org.mockito:mockito-core:jar:1.8.5:test
|  |  +- org.hamcrest:hamcrest-core:jar:1.1:test
|  |  \- org.objenesis:objenesis:jar:1.0:test
|  \- com.google.inject.extensions:guice-assisted-inject:jar:2.0:test
+- com.google.gwt:gwt-dev:jar:2.1.0:test
\- junit:junit:jar:4.5:test
```