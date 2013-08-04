package com.inet.cloud.service.horae.db;

/**
 * DataAccessResourceFailureException.
 *
 * @author: Dzung Nguyen
 * @version: $Id DataAccessResourceFailureException 2013-07-18 10:24:30z nguyen_dv $
 * @since 1.0
 */
public class DataAccessResourceFailureException extends NonTransientDataAccessException {
  //~ class properties ========================================================
  private static final long serialVersionUID = -182290599334293093L;

  //~ class members ===========================================================
  /**
   * Creates {@link DataAccessResourceFailureException} from the given exception message.
   *
   * @param msg the exception message.
   */
  public DataAccessResourceFailureException(String msg) {
    super(msg);
  }

  /**
   * Creates {@link DataAccessResourceFailureException} from the given exception message
   * and the cause of exception.
   *
   * @param msg the given exception message.
   * @param cause the exception thrown by the underlying data access API.
   */
  public DataAccessResourceFailureException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
