**GWTP Project Has Moved** <br />
https://github.com/ArcBees/GWTP

-<br />
-<br />
-<br />



# Introduction #

This page is meant for people who want to run the GWTP samples locally to get a taste of GWTP-based development. If you want to go further and contibute to the project you should be [reading this page instead](CompilingAndDebuggingGwtp.md).

# Obtaining GWTP samples #

You have two options to build the GWTP Maven samples, either get the latest release from the download section, or the latest and greatest version from the mercurial repository.

## From the download section ##

If you want to compile and run the latest release of the sources head to the [Downloads Section](http://code.google.com/p/gwt-platform/downloads/list) and get `gwtp-samples-VER.zip`. The procedure describe here will only work with `VER` greater or equal to 0.6.

## From mercurial ##

If you prefer to build the latest version of the samples you can get them from the mercurial repository. You will therefore need to install Mercurial on your platform. On windows, installing [TortoiseHg](http://tortoisehg.bitbucket.org/) does the trick.

To clone the GWTP repository on your machine, first open a command line prompt. Then go to the directory where you want to download GWTP (if you plan on using eclipse, go to your eclipse workspace directory). Finally, issue the following command:
```
hg clone http://gwt-platform.googlecode.com/hg/ gwt-platform 
```

# Compiling and running using the command line #

This is the simplest way to run the GWTP samples, but you lose the convenience of running them from within a nice IDE.

## Getting Maven ##

GWTP and the samples are built using Maven, so you'll need it from there:
  * http://maven.apache.org/download.html

## Running the examples ##

To run any sample. Go to the directory for the sample of your choice, for example `gwtp-samples/gwtp-sample-basic`, and type:
```
> mvn gwt:run 
```
After some compilation, the GWT development mode window should spring to life. In there, simply click the `Launch Default Browser` button. That's it! You're running your first GWTP application!

# Compiling and running within Eclipse #

Running the samples from Eclipse Indigo is more comfortable, but slightly more involved than running them from the command line. The reason is essentially because you need to install a couple of plugins before you can get started.

The information presented in this section is valid for the project's trunk only and may not work with a zipped version of the samples. Also, it is meant specifically for Eclipse Indigo and has been tested with the Eclipse IDE for Java Developer and may not work the Java EE version.

## Google Plugin for Eclipse ##

If you don't have it already, install the Google Plugin for Eclipse by going to `Help > Install New Software...` and using this update site:
  * `http://dl.google.com/eclipse/plugin/3.7`
The `Google Plugin for Eclipse 3.7` is all you need.

## Install the Eclipse-Maven mappers ##

Under the `Window` or `Eclipse` menu select `> Preferences > Maven > Discovery > open catalog`, then check:
  * `buildhelper`
  * `m2e WTP`
  * `Checkstyle m2e`

## Building the project for the first time ##

Before the project and samples run in Eclipse you will need to build them outside of eclipse once. From a command prompt, go to the top-level directory where you placed GWTP, from there run
```
mvn install
```
and let the process terminate.

## Importing the sample projects in Eclipse ##

Now that all the plugins are installed, launch Eclipse and import GWTP as a maven project into Eclipse. To import, do:
```
File > Import > Maven > Existing Maven Project
```
Browse for files and select the top level directory. This will list many POMs: `/pom.xml`, `gwtp-build-tools/pom.xml`, `gwtp-core/pom.xml`, etc. Make sure all the POMs are checked.

## Solving errors about GWT and App Engine SDKs ##

If you get errors in the samples about missing App Engine or GWT SDKs, these can usually be solved easily by pointing Eclips to the right SDK. First make sure you have the SDK currently used by GWTP. Open the main `gwt/pom.xml` and look for `<gae.version>` and `<gwt.version>`. Then right-click on the sample project in error and select `Google > Web Toolkit Settings...` or `Google > App Engine Settings...`. Select `Use Specific SDK` and from the drop down choose the correct version. If it does not appear, install it first (which you can usually do by visiting the `Configure SDKs...` link in that dialog).

## Getting rid of bogus warnings ##

This step is optional, but read on if you want to get rid of the warnings Eclipse wrongfully generates.

You will get many warnings of the form `No grammar constraints (DTD or XML schema) detected for the document.`. To disable these go to `Window > Preferences > XML > XML Files Validation` and in the field `No grammar specified` select `Ignore`.

You will still have some warnings. If someone knows how to get rid of them, please tell me! :)

## Running and debugging the samples ##

To run any of the samples simply right-click on it in the Package Explorer and select `Run As > Web Application`. To debug it select `Debug As > Web Application`.