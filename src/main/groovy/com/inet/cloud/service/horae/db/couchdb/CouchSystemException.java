package com.inet.cloud.service.horae.db.couchdb;

import com.inet.cloud.service.horae.db.UncategorizedDataAccessException;

/**
 * CouchSystemException.
 *
 * @author: Dzung Nguyen
 * @version: $Id CouchSystemException 2013-07-22 22:36:30z nguyen_dv $
 * @since 1.0
 */
public class CouchSystemException extends UncategorizedDataAccessException {
  //~ class properties ========================================================
  private static final long serialVersionUID = -8103387439301908992L;

  //~ class members ===========================================================
  /**
   * Creates {@link CouchSystemException} from the given exception message and the
   * cause of exception.
   *
   * @param msg the exception message.
   * @param cause the exception thrown by underlying data access API.
   */
  public CouchSystemException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
