package org.twgae.client;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.twgae.client.CarryRequest;
import org.twgae.client.Destination;
import org.twgae.client.Origin;
import org.twgae.client.Packet;
import org.twgae.client.TravelNote;
import org.twgae.client.Uzer;
import org.twgae.server.PMF;

import junit.framework.TestCase;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.ApiProxyLocal;
import com.google.appengine.tools.development.ApiProxyLocalFactory;
import com.google.apphosting.api.ApiProxy;

public class StubTest extends TestCase{
public static final ApiProxy.Environment ENV = new ApiProxy.Environment() {
    public String getAppId() {
      return "test";
    }

    public String getVersionId() {
      return "1.0";
    }

    public String getEmail() {
      throw new UnsupportedOperationException(
);
    }

    public boolean isLoggedIn() {
      throw new UnsupportedOperationException();
    }

    public boolean isAdmin() {
      throw new UnsupportedOperationException();
    }

    public String getAuthDomain() {
      throw new UnsupportedOperationException();
    }

    public String getRequestNamespace() {
      throw new UnsupportedOperationException();
    }

    public String getDefaultNamespace() {
      throw new UnsupportedOperationException();
    }

    public void setDefaultNamespace(String s) {
      throw new UnsupportedOperationException();
    }
  };


  public void setUp() throws Exception {
    super.setUp();
    ApiProxyLocalFactory factory = new ApiProxyLocalFactory();
    factory.setApplicationDirectory(new File("."));
    ApiProxyLocal proxy = factory.create();    // run completely in-memory
    proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY, Boolean.TRUE.toString());
    ApiProxy.setDelegate(proxy);
    ApiProxy.setEnvironmentForCurrentThread(ENV);
  }


  public void testA(){
    Origin o = new Origin("mumbai");
    Destination d = new Destination("blore");
    Uzer requester = new Uzer("x@y.com");
    Packet p = new Packet(requester, "a book", "6in", "200g");
    CarryRequest cr = new CarryRequest(o, d, p);
    PersistenceManager pm = PMF.pmf.getPersistenceManager();
    pm.makePersistent(cr);
    System.out.println(requester.getId());
    Origin o2 = new Origin("chennai");
    Destination d2 = new Destination("hyd");
    TravelNote t = new TravelNote(requester, o2, new Date(), d2, new Date());
    pm.makePersistent(t);
    System.out.println(requester.getId());
    List<CarryRequest> requests = requester.getMyRequests();
    List<TravelNote> notes = requester.getMyTravels();
    System.out.println(requests.size());
    System.out.println(notes.size());
  }
  public void tearDown() throws Exception{
    ApiProxy.clearEnvironmentForCurrentThread();
    ApiProxyLocal proxy = (ApiProxyLocal) ApiProxy.getDelegate();
    proxy.stop(); // wipes out all in-memory data
    super.tearDown();
  }
}