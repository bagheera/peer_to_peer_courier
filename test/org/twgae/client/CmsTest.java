package org.twgae.client;

import org.twgae.client.CarryRequest;
import org.twgae.client.Peer_to_peer_courier;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class CmsTest extends GWTTestCase {

  /**
   * Must refer to a valid module that sources this class.
   */
  public String getModuleName() {
    return "org.twgae.peer_to_peer_courier";
  }

  /**
   * Add as many tests as you like.
   */
  public void testSimple() {
    assertTrue(true);
  }
  
  public void testCall(){
    Peer_to_peer_courier cms = new Peer_to_peer_courier();
    cms.carryService.listRequests(new AsyncCallback<CarryRequest[]>(){

      public void onFailure(Throwable caught) {
        throw new RuntimeException(caught);
      }

      public void onSuccess(CarryRequest[] result) {
        assertTrue(result.length == 0);
        finishTest();
      }
      
    });
    delayTestFinish(2000);
  }
}
