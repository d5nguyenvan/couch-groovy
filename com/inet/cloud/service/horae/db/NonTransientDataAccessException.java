package com.inet.cloud.service.horae.db;

/**
 * NonTransientDataAccessException.
 *
 * @author: Dzung Nguyen
 * @version: $Id NonTransientDataAccessException 2013-07-18 09:46:30z nguyen_dv $
 * @since 1.0
 */
public abstract class NonTransientDataAccessException extends DataAccessException {
  //~ class properties ========================================================
  private static final long serialVersionUID = -2620570173514711651L;

  //~ class members ===========================================================

  /**
   * Creates {@link NonTransientDataAccessException} from the given exception message.
   *
   * @param msg the exception message.
   */
  protected NonTransientDataAccessException(String msg) {
    super(msg);
  }

  /**
   * Creates {@link NonTransientDataAccessException} from the given exception message
   * and the cause of exception.
   *
   * @param msg the exception message.
   * @param cause the cause of exception.
   */
  protected NonTransientDataAccessException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
