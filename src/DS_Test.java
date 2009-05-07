//import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;



public class DS_Test {
  private static final DatastoreService datastoreService =
    DatastoreServiceFactory.getDatastoreService();
  
//  @Test
  public void testds(){
    Ball b = new Ball(new Entity("Ball"));
    b.setColor("red");
    System.out.println("ball key: "+ b.getKey());
    Transaction tx = datastoreService.beginTransaction();
    datastoreService.put(b.getEntity());
    tx.commit();
    System.out.println("ball key: "+ b.getKey());
  }
}

class Ball {

  private final Entity entity;

  public Ball(Entity entity) {
    super();
    // TODO Auto-generated constructor stub
    this.entity = entity;
  }
  
  public String getColor(){
    return (String) entity.getProperty("color");
  }
  
  public void setColor(String color){
    entity.setProperty("color", color);
  }
  
  public Key getKey(){
    return entity.getKey();
  }

  public Entity getEntity() {
    return entity;
  }
}
