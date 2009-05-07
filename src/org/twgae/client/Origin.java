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
public class Origin implements Serializable {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  String id;

  @Persistent
  private String fromCity;

  public Origin() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Origin(String origin) {
    if(isEmpty(origin)) throw new ValidationException("Please specify origin");
    fromCity = origin;
  }

  public String toString() {
    return s(fromCity);
  }

  public String getFromCity() {
    return fromCity;
  }

  public String getId() {
    return id;
  }
}
