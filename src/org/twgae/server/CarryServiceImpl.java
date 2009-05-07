package org.twgae.server;

import java.util.logging.Logger;

import org.twgae.client.CarryRequest;
import org.twgae.client.CarryService;
import org.twgae.client.TravelNote;
import org.twgae.client.Uzer;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;



public class CarryServiceImpl extends RemoteServiceServlet implements CarryService {

  private static final Logger logger = Logger.getLogger(CarryServiceImpl.class.getName());
  public CarryServiceImpl() {
    super();
  }

  public void create(CarryRequest carryRequest) {
    checkLogin();
    Repository.instance().perform(new CreateCarryRequest(carryRequest));
  }

  public CarryRequest[] listRequests() {
    checkLogin();
    ListCarryRequests operation = new ListCarryRequests();
    Repository.instance().perform(operation);
    return operation.result;
  }

  public TravelNote createTravelNote(TravelNote travelNote) {
    checkLogin();
    CreateTravelNote operation = new CreateTravelNote(travelNote);
    Repository.instance().perform(operation);
    return operation.result;//failing here?
  }

  public TravelNote[] listTravelNotes() {
    checkLogin();
    ListTravelNotes operation = new ListTravelNotes();
    Repository.instance().perform(operation);
    return operation.result;
  }
  
  public Uzer getCurrentUser(){
    checkLogin();
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    logger.fine("getCurrentUser "+ user.getEmail());
    FindOrCreateUzer findOrCreateUzer = new FindOrCreateUzer(user.getEmail());
    Repository.instance().perform(findOrCreateUzer);
    return findOrCreateUzer.result;
  }

  private void checkLogin() {
    UserService userService = UserServiceFactory.getUserService();
    if(!userService.isUserLoggedIn()) throw new RuntimeException("Not logged in");
  }

  public String createLoginUrl(String urlAfterLogin) {
    UserService userService = UserServiceFactory.getUserService(); 
    return userService.createLoginURL(urlAfterLogin);
  }

  public String createLogoutUrl(String urlAfterLogout) {
    checkLogin();
    UserService userService = UserServiceFactory.getUserService(); 
    return userService.createLogoutURL(urlAfterLogout);
  }
}
