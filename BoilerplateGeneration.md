

# Introduction #

GWTP provides annotation processors that reduce the effort of creating classes that primarily consist of [boilerplate code](http://en.wikipedia.org/wiki/Boilerplate_(text)#Boilerplate_code).  Annotation processors enable compiler-/IDE-driven generated-as-you-type code generation based on Java files in your project.

# Using annotations to generate classes #

To generate the boilerplate code, configure your build environment and then create simple classes with specific annotations that specify the metadata.

For full information refer to the javadoc of your `@GenEvent`, `@GenDispatch` or `@GenDTO`.

## Generate Event and Event Handler ##

The `@GenEvent` will generate an `Event` class and an `EventHandler` interface.
```
@GenEvent 
public class FooChanged {
  @Order(1) Foo foo;
  @Order(2) boolean originator;
}
```

The above example will generate `FooChangedEvent` and `FooChangedEventHandler`.

## Generate Action and Result ##

The `@GenDispatch` will generate `Action` and `Result` classes.
```
@GenDispatch
public class RetrieveFoo {
  @In(1) Key<Foo> fooKey;
  @Out(1) Foo foo;
  @Out(2) boolean bar;
}
```

The above example will generate `RetrieveFooAction` and `RetrieveFooResult`.

## Generate Data transfer objects ##

The `@GenDto` annotation will generate simple Data Transfer Object (DTO) classes to be used solely for transferring data between the client and the server.

```
@GenDto
public class LineItem {
  @Order(1) Key<Product> productKey;
  @Order(2) int quantity; 
}
```

The above example will generate a `LineItemDto` class that could be used on the following action when creating an invoice.

```
@GenDispatch
public class CreateInvoice {
  @In Key<Customer> customerKey;
  @In LineItemDto[] lineItems;
  @Out Invoice invoice;
}
```

The alternative to using DTO classes would be to construct complete `LineItem` entites on the client and send them as part of the gwt-rpc call. (i.e. each element of `lineItems` would contain an `id` field, and maybe a `price` field, etc.) With DTO, you can just pass the minimal information required to create the object.

Using DTO classes is a better choice because:
  * The client cannot be trusted. The `LineItem` entity probably has fields that you want to populate on the server (i.e. `id` or `price`).
  * Sending the minimal amount of information lowers the bandwidth required for your operation.

## @Optional ##
You can use the `@Optional` annotation to specify optional fields in all classes that are involved in the generation process.

Normally two constructors are created with either all or only non-optionals. In the second variant the optional annotated fields are not initialized and will contain their default value (i.e. objects will be initialized to null).

```
@GenEvent
public class FooChanged {
  @Order(1) @Optional Foo foo;
  @Order(2) int bar;
  @Order(3) @Optional boolean originator;
}
```

The following constructor and fire methods will be generated:

```
public class FooChangedEvent {
  ...
  protected FooChangedEvent() { ...
  public FooChangedEvent(int bar){ ...
  public FooChangedEvent(Foo foo, int bar, boolean originator) { ...
  ...
  public static void fire(HasEventBus source, int bar) { ...
  public static void fire(HasEventBus source, Foo foo, int bar, boolean originator) { ...
  ...
}
```

## Modifiers and Constants ##

Since GWTP 0.5, the generation process will also consider modifiers and constant fields. If you want to use constants with `@GenDispatch`, do not forget to annotate them as well with `@In` or `@Out`.

## Complex example ##

```
@GenEvent
public class FireAnswerOfLife {
  public static final String ANSWER = "42"; 
  public final boolean REALLY = true; 
  
  @SuppressWarnings("unused")
  private final long TIME_TO_ANSWER = 99999999L;

  @Order(1) @Optional public String myName;
  @Order(2) String myQuestion;
  @Order(3) @Optional public int myAge;
  @Order(4) @Optional HairColor myHairColor;
  @Order(5) protected Boolean doIReallyNeedAnotherField;
  @SuppressWarnings("unused")
  @Order(6) private HashMap<Project, Role> yep;
  @SuppressWarnings("unused")
  @Order(7) private @Optional Boolean questionHasASense;
}
```

# Configuring your build environment #

Annotation processing is an accepted Java standard – the Pluggable Annotation Processing API (JSR 269) – and is part the Java Development Kit.

## Eclipse ##

In Eclipse, the annotation processor kicks in as soon as you save the file you're working on and incrementally changes only the required files.

To enable GWTP annotation processing in eclipse:
  1. Open the properties for your project
  1. Ticking all the boxes on the Annotation Processing page. <br />![http://img138.imageshack.us/img138/6223/eclipseannotationproces.png](http://img138.imageshack.us/img138/6223/eclipseannotationproces.png)
  1. Add the GWTP jar to the factory path. <br />![http://img295.imageshack.us/img295/9355/eclipsefactorypath.png](http://img295.imageshack.us/img295/9355/eclipsefactorypath.png)

## Ant ##

The latest version of ant automatically recognizes annotation processing. A good practice is to put the generated source in an alternate directory. To do this you will have to instruct ant to create that directory:
```
<mkdir dir=".apt_generated" />
```
And then, within your `javac` task, you specify that this directory should be used for generated source via the `-s` flag:
```
<compilerarg value="-s" />
<compilerarg path=".apt_generated" />
```
Don't forget to delete this directory in your `clean` target!

## Maven ##

_Unknown: Can a maven expert please fill this in_

[Instructions and sample pom by Harald](http://groups.google.com/group/gwt-platform/browse_thread/thread/cad03cb1a7b98c9e/d060ebd4c26b4534)