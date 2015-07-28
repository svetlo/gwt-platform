

# Concepts #

This article is like a cookbook, it provides a number of "recipes" (or patterns) that you can use for unit testing.

_"The driving reason to use Passive View is to enhance testability. With the view reduced to a dumb slave of the controller, you run little risk by not testing the view."_ - Martin Fowler, [Passive Screen](http://martinfowler.com/eaaDev/PassiveScreen.html) (a.k.a View)

_"One of the prime reasons to use Supervising Controller is for testability. Assuming the view is hard to test, by moving any complex logic into the controller, we put the logic in a place that's easier to test."_ - Martin Fowler, [Supervising Controller](http://martinfowler.com/eaaDev/SupervisingPresenter.html) (a.k.a Presenter)

![http://4.bp.blogspot.com/_lUDLhPj-dyg/SYsogEymUWI/AAAAAAAAAB0/CSSLkHiRTMo/s1600/mvp.png](http://4.bp.blogspot.com/_lUDLhPj-dyg/SYsogEymUWI/AAAAAAAAAB0/CSSLkHiRTMo/s1600/mvp.png)

# Presenter Unit Testing #

The samples in this article refer to requires gwt-0.4 or later.

When unit testing a presenter, you want to test just the presenter and mock the view interface and the action handler interfaces that the presenter interacts with.

This pattern has the following characteristics:
  * Has an interface for both the presenter and the view.  The real presenter only knows about the view interface, and the real view only knows about the presenter interface.
  * The presenter class defines it's dependencies using [constructor injection](http://code.google.com/p/google-guice/wiki/Injections).  The unit test provides mocks of the dependencies and uses mockito to stub some methods and verify calls to other methods.

## View and Presenter Interfaces ##
### CreateEmployeeView.java ###
The file defines the interfaces for the view and the presenter's UiHandlers.
The view only has `set`... methods and the UiActionHandlers only has `on`... methods.  This results in a view that only does what it is told, and if something happens, lets the presenter decide what to do.
```
public interface CreateEmployeeView extends View {

  void setCompanyName(String name)
    
  public interface CreateEmployeeUiHandlers extends UiHandlers {
    void onNameChanged(String name);
    void onCreateButtonClicked();
  }
}
```

## Presenter Unit Test ##
### TestCreateEmployeeWizard.java ###
This test case uses [Mockito](http://code.google.com/p/mockito/).

CreateEmployeePresenterImpl makes calls to CompanyRetrieveActionHandler and CreateEmployeeActionHandler, and also interacts with CreateEmployeeView.

To limit the scope of the test to just the presenter, a guice injector is created using the production class that extends HandlerModule which defines the ActionHandlers (in this case, DispatchHandlerModule) and a MockHandlerModule that binds the action handlers to mocks.  The DispatchAsync that is retrieved from the injector and passed the CreateEmployeePresenterImpl will result in calls to mock action handlers.

The view interface that is passed to CreateEmployeePresenterImpl is also a mock.

```
@RunWith(MockitoJUnitRunner.class)
public class TestCreateEmployeeWizard {

  @Mock
  private CreateEmployeeView view;
  @Mock
  private CreateEmployeeProxy proxy;

  @Mock
  private RetrieveCompanyActionHandler retrieveCompanyActionHandler;
  @Mock
  private CreateEmployeeActionHandler createEmployeeActionHandler;

  private CreateEmployeePresenterImpl presenter;

  @Before
  public void setUp() throws Exception {

    helper.setUp();

    Injector injector = Guice.createInjector(new DispatchHandlerModule(),
        new MockHandlerModule() {
          @Override
          protected void configureMockHandlers() {
            bindMockHandler(RetrieveCompanyActionHandler.class,
                retrieveCompanyActionHandler);
            bindMockHandler(CreateEmployeeActionHandler.class,
                createEmployeeActionHandler);
          }
        });

    DispatchAsync dispatcher = injector.getInstance(DispatchAsync.class);

    // ... pass dispatcher to wizard
    presenter = new CreateEmployeePresenterImpl(null, view,
        proxy, null, null, null, dispatcher);

  }

  @Test
  public void testCreate() throws ActionException, ServiceException {

    Company company = spy(new Company());
    when(company.getId()).thenReturn(1L);
    company.setName("Test Company");

    Key<Company> companyKey = new Key<Company>(Company.class, 1);

    // test that on presenter reset, retrieves company and sets name in view
    PlaceRequest request1 = new PlaceRequest("").with("company", "1");
    presenter.prepareFromRequest(request1);
 
    final RetrieveCompanyResult result = new RetrieveCompanyResult(company);

    when(RetrieveCompanyActionHandler.execute(
         eq(new RetrieveCompanyAction(companyKey)),
         any(ExecutionContext.class))).thenReturn(result);

    presenter.onReset();
    verify(view).setCompanyName("Test Company");

    // test that setting employee name and then trigger create button
    // causes the presenter to submit an action with the correct parameters

    presenter.onNameChanged("John Doe");
    presenter.onCreateButtonClicked();

    verify(CreateEmployeeActionHandler).execute(
         eq(new CreateEmployeeAction(companyKey, "John Doe")),
         any(ExecutionContext.class));		
    }
}
```

## Avoid GWT.create() and other UI operations in your presenters ##
When you are unit testing presenters, the goal is to test complex logic that is in the presenter.  If your presenter unit test is calling `GWT.create()` or other UI code (like displaying messages boxes), it is an indication of [code smell](http://en.wikipedia.org/wiki/Code_smell).  Your test is no longer a presenter unit test, as either the scope of the test is probably too large or your presenter is doing the view's job.  If you are getting the following error, try refactoring your code. You should usually not try to follow the suggestion of using `GWTTestCase` - `GWTTestCase` should only be needed when testing views.
```
java.lang.RuntimeException: java.lang.UnsupportedOperationException:
ERROR: GWT.create() is only usable in client code!  It cannot be
called, for example, from server code.  If you are running a unit
test, check that your test case extends GWTTestCase and that
GWT.create() is not called from within an initializer, constructor, or
setUp()/tearDown()
```
If you are getting the above message, double check you code to make sure you are not calling `GWT.create()`. If you really can't find the problem, don't hesitate to ask for help on the forum.