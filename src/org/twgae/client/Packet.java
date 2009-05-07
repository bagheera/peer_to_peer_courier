package org.twgae.client;

import static org.twgae.client.Str.*;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Packet implements Serializable {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  private String id;
  @Persistent
  private String description;
  @Persistent
  private String dimensions;
  @Persistent
  private String weight;
  @Persistent
  private Uzer requester;

  public Packet(Uzer requester, String description, String dimensions, String weight) {
    if(null == requester) throw new ValidationException("Unidentified requester (user)");
    this.requester = requester;
    if(isEmpty(description)) throw new ValidationException("Please provide a description.");
    this.description = description;
    this.dimensions = dimensions;
    this.weight = weight;
  }

  public Packet() {
    super();
    // TODO Auto-generated constructor stub
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(s(requester)).append(s(" requests courier of ",description)).append(s(" dimensions ", dimensions)).append(s(" weight ", weight));
    return sb.toString();
  }
}
