package org.twgae.client;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
 public class Uzer implements Serializable {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  String id;
  
  @Persistent
  String email;
  
  @Persistent(dependentElement = "true", mappedBy = "traveller")
  @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="id asc"))
  List<TravelNote> myTravels;

  public List<TravelNote> getMyTravels() {
    return myTravels;
  }

  public List<CarryRequest> getMyRequests() {
    return myRequests;
  }

  @Persistent(dependentElement = "true", mappedBy = "requester")
  @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="id asc"))
  List<CarryRequest> myRequests;
  
  public Uzer(String email) {
    this.email = email;
  }

  public Uzer() {
    super();
  }

  public String getEmail() {
    return email;
  }
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return email;
  }
  
  
}
