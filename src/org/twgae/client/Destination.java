package org.twgae.client;

import static org.twgae.client.Str.*;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Destination implements Serializable {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  String id;
  @Persistent
  private String city;

  public Destination(String dest) {
    if(isEmpty(dest)) throw new ValidationException("Please specify destination");
    city = dest;
  }

  public Destination() {
    super();
    // TODO Auto-generated constructor stub
  }

  public String toString() {
    return s(city);
  }
}
