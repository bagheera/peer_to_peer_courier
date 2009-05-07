package org.twgae.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CarryServiceAsync {
  void create(CarryRequest carryRequest, AsyncCallback<Void> callback);

  void listRequests(AsyncCallback<CarryRequest[]> callback);

  void createTravelNote(TravelNote travelNote, AsyncCallback<TravelNote> callback);

  void listTravelNotes(AsyncCallback<TravelNote[]> callback);
  
  void getCurrentUser(AsyncCallback<Uzer> callback);
  
  void createLoginUrl(String urlAfterLogin, AsyncCallback<String> callback);

  void createLogoutUrl(String urlAfterLogout, AsyncCallback<String> callback);

}
