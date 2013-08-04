package com.inet.cloud.service.horae.db;

/**
 * UncategorizedDataAccessException.
 *
 * @author: Dzung Nguyen
 * @version: $Id UncategorizedDataAccessException 2013-07-18 10:05:30z nguyen_dv $
 * @since 1.0
 */
public abstract class UncategorizedDataAccessException extends NonTransientDataAccessException {
  //~ class properties ========================================================
  private static final long serialVersionUID = -3553106243743243634L;

  //~ class members ===========================================================
  /**
   * Creates {@link UncategorizedDataAccessException} from the given exception message
   * and the cause of exception.
   *
   * @param msg the given exception message.
   * @param cause the exception thrown by underlying data access API.
   */
  protected UncategorizedDataAccessException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
