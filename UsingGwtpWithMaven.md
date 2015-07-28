**GWTP Project Has Moved** <br />
https://github.com/ArcBees/GWTP

-<br />
-<br />
-<br />

# Introduction #

Since version 0.5 GWTP is published to Maven Central. Simply add one or more of the following sections to your POM dependencies:
```
   <!-- MVP component -->
   <dependency>
     <groupId>com.gwtplatform</groupId>
     <artifactId>gwtp-mvp-client</artifactId>
     <version>${gwtp.version}</version>
     <scope>provided</scope>
   </dependency>

   <!-- Dispatch component -->
   <dependency>
     <groupId>com.gwtplatform</groupId>
     <artifactId>gwtp-dispatch-client</artifactId>
     <version>${gwtp.version}</version>
     <scope>provided</scope> <!-- Remove for GWTP 0.5.1 and earlier -->
   </dependency>

   <dependency>
     <groupId>com.gwtplatform</groupId>
     <artifactId>gwtp-dispatch-server-guice</artifactId>
<!-- Or, if you use spring:
     <artifactId>gwtp-dispatch-server-spring</artifactId> -->
<!-- For GWTP 0.5.1 and earlier:
     <artifactId>gwtp-dispatch-server</artifactId> -->
     <version>${gwtp.version}</version>
   </dependency>

   <!-- Crawler component -->
   <dependency>
     <groupId>com.gwtplatform</groupId>
     <artifactId>gwtp-crawler</artifactId>
     <version>${gwtp.version}</version>
   </dependency>

   <!-- Annotation component -->
   <dependency>
     <groupId>com.gwtplatform</groupId>
     <artifactId>gwtp-processors</artifactId>
     <version>${gwtp.version}</version>
     <scope>provided</scope>
   </dependency>

   <!-- Tester component -->
   <dependency>
     <groupId>com.gwtplatform</groupId>
     <artifactId>gwtp-tester</artifactId>
     <version>${gwtp.version}</version>
     <scope>test</scope>
   </dependency>
```

You can remove any GWTP component you don't use, for more information on the different components see LibraryOverview.

You can define `${gwtp.version}` in your POM `<properties>` or replace it directly with the version number you want to use. You should use the latest version (see the home page). In particular, you need version 0.5.1 or later if you're using GWT 2.2+.

# Snapshots #

Snapshots of the current version are regularly deployed. If you want to use it then add the sonatype snapshot repository to your project:
```
    <repositories>
        <repository>
            <id>sonatype.snapshots</id>
            <name>Sonatype snapshot repository</name>
            <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
            <layout>default</layout>
        </repository>
    </repositories>
```
and make sure you depend on the snapshot:
```
  <properties>
      <gwtp.version>0.6-SNAPSHOT</gwtp.version>
  </properties>
```
You can also visit the [snapshot repository](https://oss.sonatype.org/content/repositories/snapshots/com/gwtplatform/) from your browser.

# Earlier versions #

You can get versions prior to 0.5 from our own [Maven repository](http://code.google.com/p/gwt-platform/source/browse?repo=maven).

# Excluding Spring dependencies #

If you're using `gwtp-all` (or `gwtp-dispatch-server` in GWTP 0.5.1 or earlier) you will get all the spring transitive dependencies automatically. If you don't need them (because, say, you're using Guice) then you can modify your POM as follows:
```
<dependency>
  <groupId>com.gwtplatform</groupId>
  <artifactId>gwtp-all</artifactId>
  <version>0.5</version>
  <exclusions>
    <exclusion>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
    </exclusion>
    <exclusion>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
    </exclusion>
    <exclusion>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
    </exclusion>
    <exclusion>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
    </exclusion>
  </exclusions> 
</dependency>
```