package com.inet.cloud.service.horae.db.couchdb;

import com.inet.cloud.service.horae.db.DataAccessException;
import com.inet.cloud.service.horae.db.DataAccessResourceFailureException;
import com.inet.cloud.service.horae.db.InvalidDataAccessApiUsageException;
import org.ektorp.*;

import java.io.IOException;

/**
 * CouchDbExceptions.
 *
 * @author: Dzung Nguyen
 * @version: $Id CouchDbExceptions 2013-07-22 21:14:30z nguyen_dv $
 * @since 1.0
 */
public final class CouchDbExceptions {
  //~ class members ===========================================================
  private CouchDbExceptions(){
    throw new AssertionError("The CouchDbExceptions utility must not be instantiated.");
  }

  /**
   * Converts the exception thrown by the CouchDB API to {@link DataAccessException} exception.
   *
   * @param ex the exception thrown by CouchDB API.
   * @return the {@link DataAccessException} exception.
   */
  public static DataAccessException toDataAccessException(Exception ex) {
    if (ex instanceof ViewResultException) {
      throw new DataAccessResourceFailureException(ex.getMessage(), ex);
    }

    if (ex instanceof UpdateConflictException) {
      throw new DataAccessResourceFailureException(ex.getMessage(), ex);
    }

    if (ex instanceof DocumentNotFoundException) {
      throw new DataAccessResourceFailureException(ex.getMessage(), ex);
    }

    if (ex instanceof InvalidDocumentException) {
      throw new InvalidDataAccessApiUsageException(ex.getMessage(), ex);
    }

    if (ex instanceof DbAccessException) {
      throw new InvalidDataAccessApiUsageException(ex.getMessage(), ex);
    }

    if (ex instanceof IOException) {
      throw new CouchConnectionFailureException("Could not connect to CouchDB server.", ex);
    }

    throw new CouchSystemException("Unknown CouchDB exception", ex);
  }
}
