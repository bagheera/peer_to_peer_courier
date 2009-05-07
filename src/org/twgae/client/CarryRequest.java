package org.twgae.client;

import static org.twgae.client.Str.s;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CarryRequest implements Serializable {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  String id;
  @Persistent
  private Packet packet;
  @Persistent
  private Origin carryFrom;
  @Persistent
  private Destination carryTo;
  @Persistent
  private Uzer requester;

  public CarryRequest(Origin carryFrom, Destination carryTo, Packet packet) {
    super();
    this.carryFrom = carryFrom;
    this.carryTo = carryTo;
    this.packet = packet;
  }

  public CarryRequest() {
    super();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(s(packet)).append(s(" from ", carryFrom)).append(s(" to ", carryTo));
    return sb.toString();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setRequester(Uzer requester) {
    this.requester = requester;
  }

  public Uzer getRequester() {
    return requester;
  }
}
