package org.twgae.client;

import static org.twgae.client.Str.s;

import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class TravelNote implements Serializable {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  String id;
  private Date departureDate;
  private Date arrivalDate;
  @Persistent
  private Origin travelFrom;
  @Persistent
  private Destination travelTo;
  @Persistent
  private Uzer traveller;//TODO

  public TravelNote(Uzer who, Origin travelFrom, Date departureDate, Destination travelTo, Date arrivalDate) {
    super();
    if(null == who) throw new ValidationException("Unidentified traveller (user)");
    this.traveller = who;
    StringBuilder sb = new StringBuilder();
    if(arrivalDate == null) sb.append("Please specifiy Arrival Date\n");
    if(departureDate == null) sb.append("Please specifiy Departure Date\n");
    if(sb.length()==0 && departureDate.after(arrivalDate)) sb.append("Arrival Date must be after Departure Date\n");
    if(sb.length() > 0) throw new ValidationException(sb.toString());
    this.travelFrom = travelFrom;
    this.travelTo = travelTo;
    this.departureDate = departureDate;
    this.arrivalDate = arrivalDate;
  }

  public TravelNote() {
    super();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(s(traveller)).append(s(" going from ", travelFrom)).append(s(" departing ", departureDate)).append(s(" to ", travelTo)).append(
        s(" arriving ", arrivalDate));
    return sb.toString();
  }

  public String getOriginCity() {
    return travelFrom.getFromCity();
  }

  public void setOrigin(Origin origin) {
    this.travelFrom = origin;
  }

  public String getTravellerEmail() {
    return traveller.email;
  }

  public void setTraveller(Uzer uzer) {
    traveller = uzer;
  }
}
