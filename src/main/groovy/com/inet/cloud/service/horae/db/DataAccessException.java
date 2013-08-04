package com.inet.cloud.service.horae.db;

import com.inet.cloud.service.horae.HoraeRuntimeException;

/**
 * DataAccessException.
 *
 * @author: Dzung Nguyen
 * @version: $Id DataAccessException 2013-07-18 09:34:30z nguyen_dv $
 * @since 1.0
 */
public abstract class DataAccessException extends HoraeRuntimeException {
  //~ class properties ========================================================
  private static final long serialVersionUID = -7040612086916058311L;

  //~ class members ===========================================================
  /**
   * Creates {@link DataAccessException} from the given exception message.
   *
   * @param msg the given exception message.
   */
  protected DataAccessException(String msg) {
    super(msg);
  }

  /**
   * Creates {@link DataAccessException} from the given exception message and the
   * cause of exception.
   *
   * @param msg the given exception message.
   * @param cause the cause of exception.
   */
  protected DataAccessException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
