package org.twgae.server;

import static org.twgae.server.PMF.pmf;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

public class Repository {
  private static final Logger logger = Logger.getLogger(Repository.class.getName());
  private static final Repository singleton = new Repository();

  private Repository() {
  }

  public static Repository instance() {
    return singleton;
  }

  void perform(Operation operation) {
    logger.fine("starting operation "+ operation.getClass().getSimpleName());
    PersistenceManager pm = pmf.getPersistenceManager();
    try {
      operation.execute(pm);
      logger.fine("ending operation "+ operation.getClass().getSimpleName());
    } catch (Throwable e) { // required for ORM 0.8.1 only
      logger.severe("error in operation "+ operation.getClass().getSimpleName());
      System.out.print(e.getMessage());
      throw new RuntimeException(e);
    } finally {
      pm.close();
    }
  }
}

