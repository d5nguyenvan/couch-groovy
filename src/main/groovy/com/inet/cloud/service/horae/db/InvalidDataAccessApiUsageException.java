package com.inet.cloud.service.horae.db;

/**
 * InvalidDataAccessApiUsageException.
 *
 * @author: Dzung Nguyen
 * @version: $Id InvalidDataAccessApiUsageException 2013-07-18 09:58:30z nguyen_dv $
 * @since 1.0
 */
public class InvalidDataAccessApiUsageException extends NonTransientDataAccessException {
  //~ class properties ========================================================
  private static final long serialVersionUID = -8519630046857791734L;

  //~ class members ===========================================================

  /**
   * Creates {@link InvalidDataAccessApiUsageException} object from the exception message.
   *
   * @param msg the exception message.
   */
  public InvalidDataAccessApiUsageException(String msg) {
    super(msg);
  }

  /**
   * Creates {@link InvalidDataAccessApiUsageException} object from the exception message.
   *
   * @param msg the exception message.
   * @param cause the cause from the data access API in use.
   */
  public InvalidDataAccessApiUsageException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
