package org.twgae.server;

//Copyright 2008 Google Inc. All Rights Reserved.
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author Max Ross <maxr@google.com>
 */
public final class PMF {
public static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("nontransactional-datasource");

  private PMF() {
  }
}
