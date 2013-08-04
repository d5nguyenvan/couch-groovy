package com.inet.cloud.service.horae.db.couchdb;

import com.inet.cloud.service.horae.db.DataAccessResourceFailureException;

/**
 * CouchConnectionFailureException.
 *
 * @author: Dzung Nguyen
 * @version: $Id CouchConnectionFailureException 2013-07-22 22:43:30z nguyen_dv $
 * @since 1.0
 */
public class CouchConnectionFailureException extends DataAccessResourceFailureException {
  //~ class properties ========================================================
  private static final long serialVersionUID = 6193183144899230152L;

  //~ class members ===========================================================
  /**
   * Creates {@link CouchConnectionFailureException} from the exception message and
   * the cause of exception.
   *
   * @param msg the exception message.
   * @param cause the exception thrown by underlying data access API.
   */
  public CouchConnectionFailureException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
