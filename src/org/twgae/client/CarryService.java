package org.twgae.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface CarryService extends RemoteService {
  void create(CarryRequest carryRequest);

  CarryRequest[] listRequests();

  TravelNote createTravelNote(TravelNote travelNote);

  TravelNote[] listTravelNotes();

  Uzer getCurrentUser();
  
  String createLoginUrl(String urlAfterLogin);

  String createLogoutUrl(String urlAfterLogout);
}
