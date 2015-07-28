# Introduction #

This page describes the procedure to follow when releasing GWTP. It is targetted to project owners and contributors.

# Starting point #

The starting point is the [Sonatype OSS Maven Repository User Guide](https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide). Follow all the steps in there. Among other things you will have to install [GPG](http://www.gnupg.org/) and generate keys. The process is explained [in this guide](http://www.sonatype.com/people/2010/01/how-to-generate-pgp-signatures-with-maven/).

# Specifying your Google Code credentials #

The various deployment steps need Maven to know your Google Code credentials. You specify them on your local machine by adding the following to Maven's `settings.xml`:
```
<servers>
  ...
  <server>
    <id>gwt-platform.googlecode.com</id>
    <username>philippe.beaudoin</username>
    <password>0123456789ABCD</password>
  </server>
  ...
</servers>
```


# Deploying a snapshot #

**Important!** Make sure you quit Eclipse before doing that, so that there is no interference between Eclipse automatic building and Maven.

This is explained in section 7a.2 of the previous document.

It's accomplished simply via:
```
mvn clean deploy
```
To see the snapshot, go to Sonatype's Nexus at http://oss.sonatype.org/ and navigate to `Repositories > Snapshots > com > gwtplatform`.

# Releasing a new version #

**Important!** Make sure you quit Eclipse before doing that, so that there is no interference between Eclipse automatic building and Maven.

Releasing is done in two steps: first staging a release, the performing the actual release.

## Staging the release ##

This is explained in section 7a.2 of the previous document.

Before you deploy a release you should make sure all the source is committed and pushed to the Google Code repository. You do not have to tag the code, it will be done automatically by Maven in the following steps. This is done via:
```
mvn release:clean
mvn release:prepare -Dgpg.passphrase=yourpassphrase
```
<a href='Hidden comment: 
On sonatype: philbeaudoin (s and a number)
Hint for my passphrase: LSDLEL
'></a>
Answer all the questions. Following that, Maven will automatically modify the `POM` and commit, tag and push the code a couple of times. If things go wrong at that point, you should `hg revert --all --no-backup` or even `hg strip` any automatic commits by Maven (if they have not yet been pushed), you may have to clean up any remaining `.orig` or `.backup` files. Then start back at `release:clean`.

The next step is performing staging the actual release through the following command:
```
mvn release:perform -Dgpg.passphrase=yourpassphrase
```
To see the staging release, go to Sonatype's Nexus at http://oss.sonatype.org/ and navigate to `Staging Repositories`. You must be logged in to see this.
<a href='Hidden comment: 
Username:PhilBeaudoin
Password:Short with the number
'></a>

## Releasing ##

This is explained in section 8 of the previous document.

Once you see the staging release on Nexus, simply select it, click `Close`, and enter a brief description.

If something goes wrong and you can't `Close` the release, you will have to backout any change pushed by maven to the repository, including tags. To do this, update to the version before the automatic maven commit, and update with the tip indicating that you want to ignore all the changes for the branch with which you're merging.

Next, the artifacts should be downloaded and tested.

When everything works, click the `Release` button.

To see the release, go to Sonatype's Nexus at http://oss.sonatype.org/ and navigate to `Repositories > Releases > com > gwtplatform`.

## Publishing zip files ##

Every release comes with three zip files. To generate them, after you've done the above go to your main `gwtp` repository and enter the following commands, using the current version number on the first line:
```
> export GWTPVER=0.7
> ./release_zip.sh
```
This will create three zip files, one directory up: `gwtp-samples-0.7.zip`, `gwtp-separate-0.7.zip` and `gwtp-all-0.7.zip`, which simply have to be uploaded to the `Downloads` section.

## Manual release to our maven repository ##

To manually release to our own maven repository, clone it first, beside gwtp:
```
> hg clone https://maven.gwt-platform.googlecode.com/hg/ gwt-platform-maven
```

Then, from the `gwtp` root, type:
```
> mvn clean deploy -Prelease -DaltDeploymentRepository=release::default::file:///Users/beaudoin/gwtp/gwt-platform-maven
```