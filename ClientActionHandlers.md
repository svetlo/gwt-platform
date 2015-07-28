<h1>DRAFT DRAFT DRAFT DRAFT DRAFT DRAFT</h1>



# Introduction #

Introduced in GWTP 0.5, client action handlers are a powerful way to extend the command pattern.

Unlike "normal" action handlers which are run on the server, and receive requests over gwt-rpc, client action handlers are run on the client before the request is sent over the wire, and can provide a number of benefits :
  * The action can be modified before sending the action over gwt-rpc to the server.
  * A result can be returned without contacting the server.
  * The result can be modified or processed after it is returned from the server.
  * The client action handler can take over and communicate directly with the server, possibly using a different mechanism than gwt-rpc.

# Client action handlers #

### Example Client Action Handler ###
```
public class FooRetrieveClientActionHandler
    extends
    AbstractClientActionHandler<FooRetrieveAction, FooRetrieveResult> {

  private Cache cache;

  @Inject
  FooRetrieveClientActionHandler(Cache cache) {
    super(FooRetrieveAction.class);
    this.cache = cache;
  }

  @Override
  public void execute(
      FooRetrieveAction action,
      final AsyncCallback<FooRetrieveResult> resultCallback,
      AsyncExecute<FooRetrieveAction, FooRetrieveResult> dispatch) {
    FooRetrieveResult result = this.cache.get(getActionType(), action);
    if(result != null) {
      resultCallback.onSuccess(result);
    } else {
      dispatch.execute(action, new AsyncCallback<FooRetrieveResult>() {
        void onSuccess(FooRetrieveResult result) {
          this.cache.put(getActionType(), action, result);
          resultCallback.onSuccess(result);
        }
        void onFailure(Throwable caught) {
          resultCallback.onFailure(caught);
        }
      });
    }
  }

  @Override
  public void undo(FooRetrieveAction action,
      FooRetrieveResult result, AsyncCallback<Void> callback,
      AsyncUndo<FooRetrieveAction, FooRetrieveResult> dispatch) {
    dispatch.undo(action, result, callback);    
  }
}
```

# Registering client action handlers #

To register a client action handler, extend the DefaultClientActionHandlerRegistry and call register in the constructor.   You handlers should be injected into the constructor of your registry.
  * Pass a handler object.  The code for the client action handler will be part of the initial download, and handler construction overhead will occur at application startup.
  * Pass a handler Provider.  The code for the client action handler will be part of the initial download, and but the object constructor overhead will be delayed until the first time the client action handler is used.
  * pass a handler AsyncProvider. The code for this client action handler will be in a separate split point. This code wont be retrieved and the handler wont be constructed until the first time the client action handler is used.

You need to specify that gwtp should use your registry by:
  * Creating a new module that extends `AbstractGinModule`.
  * Installing a new `DispatchAsyncModule` object, passing the name of your registry class to the `DispatchAsyncModule`.

### Source code to register client action handlers ###

```
@GinModules({..., ClientDispatchModule.class})
public interface ClientGinjector extends Ginjector {
  ...
}


public class ClientDispatchModule extends AbstractGinModule {
	@Override
	protected void configure() {
		install(new DispatchAsyncModule(MyClientActionHandlerRegistry.class));		
	}
}

public class MyClientActionHandlerRegistry extends
    DefaultClientActionHandlerRegistry {

  @Inject
  public ClientActionHandlerRegistry(
      MyFirstClientActionHandler handler
      Provider<MySecondClientActionHandler> handlerProvider,
      AsyncProvider<MyThirdClientActionHandler> asyncHandlerProvider) {

    register(handler);
    register(MySecondAction.class, handlerProvider);
    register(MyThirdAction.class, asyncHandlerProvider);
  }
}
```

# Testing Presenters that use client action handlers #

```
@RunWith(MockitoJUnitRunner.class)
public class TestMyPresenter {

  @Mock
  private MyNonGwtRpcClientActionHandler myNonGwtRpcClientActionHandler;

  private MyGwtRpcClientActionHandler myGwtRpcClientActionHandler;

  ...

  @Before
  public void setUp() throws Exception {

    helper.setUp();
    
    myGwtRpcClientActionHandler = new MyGwtRpcClientActionHandler(); 

    Injector injector =
        Guice.createInjector(new DispatchHandlerModule(),
            new MockHandlerModule() {
              @Override
              protected void configureMockHandlers() {

                bindMockActionHandler(
                    MyGwtRpcAction.class,
                    myGwtRpcClientActionHandler);

                bindMockClientActionHandler(
                    MyNonGwtRpcAction.class,
                    myNonGwtRpcClientActionHandler);

                bindMockClientActionHandler(MyGwtRpcAction.class, myGwtRpcClientActionHandler);
              }
            });

    DispatchAsync dispatcher = injector.getInstance(DispatchAsync.class);    

    presenter =
        new MyPresenterImpl(null, wizardView, proxy,
            null, placeManager, null, dispatcher);

  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testPresenter() throws ActionException, ServiceException {

    // fake result for client action handler that doesn't call the server.

    final MyNonGwtRpcResult result = new MyNonGwtRpcResult(...);
    doAnswer(new Answer<Object>() {
      @SuppressWarnings("unchecked")
      public Object answer(InvocationOnMock invocation) {
        AsyncCallback<MyNonGwtRpcResult> callback = (AsyncCallback<MyNonGwtRpcResult>) invocation.getArguments()[1];
        callback.onSuccess(result);
        return null;
      }
    }).when(myNonGwtRpcClientActionHandler)
      .execute(
          eq(new MyNonGwtRpcAction(...)), any(AsyncCallback.class),
          any(ClientDispatchRequest.class), any(ExecuteCommand.class));

    final MyGwtRpcResult result2 =
        new MyGwtRpcResult(...);
    when(
        MyGwtRpcActionHandler.execute(
            eq(new MyGwtRpcAction(..)),
            any(ExecutionContext.class))).thenReturn(result2);
  }
}
```

# Examples #

## Caching Example ##
_coming soon_

## Geocoding Example ##
The following code demonstrates a client action handler that communicate directly with a server using a different mechanism than gwt-rpc.
```
@GenDispatch
public class GeocodeAddress {
  @In(1) String address;
  @In(2) String region;
  @Optional @Out(1) PlacemarkDto[] placemarks; 
}


@GenDto
public class Placemark {
  String address;
  PointDto point;
}

@GenDto
public class Point {
  double latitude;
  double longitude;
}

/**
 * Uses the Google Maps API v3 to geocode an address.
 * 
 * @see <a href="http://code.google.com/apis/maps/documentation/javascript/services.html#Geocoding">http://code.google.com/apis/maps/documentation/javascript/services.html#Geocoding</a>
 */
public class GeocodeAddressClientActionHandler extends
    AbstractClientActionHandler<GeocodeAddressAction, GeocodeAddressResult> {

  private final AsyncProvider<Geocoder> geocoderProvider;

  @Inject
  protected GeocodeAddressClientActionHandler(
      AsyncProvider<Geocoder> geocoderProvider) {
    super(GeocodeAddressAction.class);
    this.geocoderProvider = geocoderProvider;
  }

  @Override
  public void execute(GeocodeAddressAction action,
      final AsyncCallback<GeocodeAddressResult> resultCallback,
      ClientDispatchRequest request,
      ExecuteCommand<GeocodeAddressAction, GeocodeAddressResult> dispatch) {

    final GeocoderRequest geocodeRequest = new GeocoderRequest();
    geocodeRequest.setAddress(action.getAddress());
    geocodeRequest.setRegion(action.getRegion());

    this.geocoderProvider.get(new AsyncCallback<Geocoder>() {

      @Override
      public void onSuccess(Geocoder geocoder) {
        geocoder.geocode(geocodeRequest, new GeocoderCallback() {

          @SuppressWarnings("deprecation")
          @Override
          public void callback(List<HasGeocoderResult> responses,
              String status) {
            if (status.equals("OK")) {

              PlacemarkDto[] placemarks =
                  new PlacemarkDto[responses.size()];
              for (int i = 0; i < responses.size(); i++) {
                HasGeocoderResult response = responses.get(i);
                HasLatLng location =
                    response.getGeometry().getLocation();
                placemarks[i] =
                    new PlacemarkDto(
                        response.getFormattedAddress(),
                        new PointDto(
                            location.getLatitude(),
                            location.getLongitude()));
              }

              resultCallback.onSuccess(new GeocodeAddressResult(
                  placemarks));
            } else if (status.equals("ZERO_RESULTS")) {
              resultCallback.onSuccess(new GeocodeAddressResult());
            } else {
              resultCallback.onFailure(new Exception("Geocoder: "
                  + status));
            }
          }
        });
      }

      @Override
      public void onFailure(Throwable caught) {
        resultCallback.onFailure(caught);
      }
    });
  }

  @Override
  public void undo(GeocodeAddressAction action, GeocodeAddressResult result,
      AsyncCallback<Void> callback, ClientDispatchRequest request,
      UndoCommand<GeocodeAddressAction, GeocodeAddressResult> dispatch) {
    // do nothing
  }
}
```

## Testing the Geocoding Example ##
```
@RunWith(MockitoJUnitRunner.class)
public class MyTest {

  @Mock
  private CreateEmployeeView view;
  ...

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper();

  ...
  private CreateEmployeePresenterImpl;

  @Mock
  private GeocodeAddressClientActionHandler geocodeAddressClientActionHandler;

  @Before
  public void setUp() throws Exception {

    helper.setUp();
    
    Injector injector =
        Guice.createInjector(new DispatchHandlerModule(),
            new MockHandlerModule() {
              @Override
              protected void configureMockHandlers() {
    
                bindMockClientActionHandler(
                    GeocodeAddressAction.class,
                    geocodeAddressClientActionHandler);
              }
            });

    DispatchAsync dispatcher = injector.getInstance(DispatchAsync.class);

    // ... pass dispatcher to wizard
    presenter = new CreateEmployeePresenterImpl(null, view,
        proxy, null, null, null, dispatcher);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGeocode() throws ActionException, ServiceException {

    doAnswer(new Answer<Object>() {
      @SuppressWarnings("unchecked")
      public Object answer(InvocationOnMock invocation) {
        AsyncCallback<GeocodeAddressResult> callback = (AsyncCallback<GeocodeAddressResult>) invocation.getArguments()[1];
        callback.onSuccess(new GeocodeAddressResult(null));
        return null;
      }
    }).when(geocodeAddressClientActionHandler)
      .execute(
          eq(new GeocodeAddressAction("1 Google Way, New Zealand",
              "nz")), any(AsyncCallback.class),
          any(ClientDispatchRequest.class), any(ExecuteCommand.class));

    wizard.getAddresses("1 Google Way, New Zealand");
    verify(view).setError("Not found.");

    doAnswer(new Answer<Object>() {
      @SuppressWarnings("unchecked")
      public Object answer(InvocationOnMock invocation) {
        AsyncCallback<GeocodeAddressResult> callback = (AsyncCallback<GeocodeAddressResult>) invocation.getArguments()[1];
        PlacemarkDto[] placemarks = new PlacemarkDto[2];
        placemarks[0] =
            new PlacemarkDto("2 Google Way, Auckland, New Zealand",
                new PointDto(-36.84, 174.73));
        placemarks[1] =
            new PlacemarkDto("2 Google Way, Wellington, New Zealand",
                new PointDto(-41.29, 174.78));
        callback.onSuccess(new GeocodeAddressResult(placemarks));
        return null;
      }
    }).when(geocodeAddressClientActionHandler)
      .execute(
          eq(new GeocodeAddressAction("2 Google Way, New Zealand",
              "nz")), any(AsyncCallback.class),
          any(ClientDispatchRequest.class), any(ExecuteCommand.class));
    
    wizard.getAddresses("2 Google Way, New Zealand");
    
    String[] addresses =
        new String[] {
            "2 Google Way, Auckland, New Zealand",
            "2 Google Way, Wellington, New Zealand"};
    verify(view).setSearchResults(addresses);
    verify(view).setMapLocation(-36.84, 174.73);    
  }
}

```