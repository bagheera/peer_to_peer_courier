package org.twgae.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.twgae.client.CarryRequest;
import org.twgae.client.Origin;
import org.twgae.client.TravelNote;
import org.twgae.client.Uzer;

interface Operation {
  void execute(PersistenceManager pm);
}

abstract class BaseOperation implements Operation {
  public abstract void execute(PersistenceManager pm);
  protected static Logger logger;
  
  public BaseOperation(){
    logger = Logger.getLogger(this.getClass().getName());
  }
}

class CreateCarryRequest extends BaseOperation {
  private Object carryRequest;

  public CreateCarryRequest(CarryRequest carryRequest) {
    this.carryRequest = carryRequest;
  }

  @Override
  public void execute(PersistenceManager pm) {
    pm.makePersistent(carryRequest);
    pm.refresh(carryRequest);
  }
}

class ListCarryRequests extends BaseOperation {
  CarryRequest[] result;

  @Override
  public void execute(PersistenceManager pm) {
    String query = "select from " + CarryRequest.class.getName();
    List<CarryRequest> requests = (List<CarryRequest>) pm.newQuery(query).execute();
    if (null == requests)
      setResult(new CarryRequest[0]);
    List<CarryRequest> fetched_list = new ArrayList<CarryRequest>();
    for (CarryRequest note : requests)
      fetched_list.add(note);
    setResult(fetched_list.toArray(new CarryRequest[fetched_list.size()]));
  }

  private void setResult(CarryRequest[] carryRequests) {
    this.result = carryRequests;
  }
}

class ListTravelNotes extends BaseOperation {
  TravelNote[] result;

  @Override
  public void execute(PersistenceManager pm) {
    Extent<TravelNote> extent = pm.getExtent(TravelNote.class, false);
    List<TravelNote> fetched_list = new ArrayList<TravelNote>();
    for (TravelNote note : extent)
      fetched_list.add(note);
    if (fetched_list.isEmpty())
      setResult(new TravelNote[0]);
    setResult(fetched_list.toArray(new TravelNote[fetched_list.size()]));
  }

  private void setResult(TravelNote[] travelNotes) {
    this.result = travelNotes;
  }
}

class CreateTravelNote extends BaseOperation {
  private final TravelNote travelNote;
  TravelNote result;

  public CreateTravelNote(TravelNote travelNote) {
    this.travelNote = travelNote;
  }

  @Override
  public void execute(PersistenceManager pm) {
    //    FindOrigin findOrigin = new FindOrigin(travelNote.getOriginCity());
    //    findOrigin.execute(pm);
    //    if(null != findOrigin.result) travelNote.setOrigin(findOrigin.result);
    FindOrCreateUzer findOrCreateUzer = new FindOrCreateUzer(travelNote.getTravellerEmail());
    findOrCreateUzer.execute(pm);
    travelNote.setTraveller(findOrCreateUzer.result);
    pm.makePersistent(travelNote);
    pm.refresh(travelNote);
    setResult(travelNote);
  }

  private void setResult(TravelNote travelNote) {
    this.result = travelNote;
  }
}

class FindOrigin extends BaseOperation {
  private final String city;

  public FindOrigin(String city) {
    this.city = city;
  }

  Origin result = null;

  @Override
  public void execute(PersistenceManager pm) {
    Query findOrigin = pm.newQuery(Origin.class, "fromCity == city");
    findOrigin.declareParameters("String city");
    List resultSet = (List) findOrigin.execute(city);
    if (null == resultSet || resultSet.isEmpty())
      return;
    if (resultSet.size() != 1)
      logger.warning("Duplicate origin found");
    result = (Origin) resultSet.get(0);
  }
}

class FindOrCreateUzer extends BaseOperation {
  private String userEmail;
  Uzer result;

  public FindOrCreateUzer(String email) {
    super();
    this.userEmail = email;
  }

  @Override
  public void execute(PersistenceManager pm) {
    Query findUzer = pm.newQuery(Uzer.class, "email == userEmail");
    findUzer.declareParameters("String userEmail");
    List resultSet = (List) findUzer.execute(userEmail);
    if (null == resultSet || resultSet.isEmpty()) {
      result = new Uzer(userEmail);
      pm.makePersistent(result);
    } else {
      if (resultSet.size() != 1)
        logger.warning("Duplicate Uzer found");
      result = (Uzer) resultSet.get(0);
    }
  }
}