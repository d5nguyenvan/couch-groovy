package com.inet.cloud.service.horae.db.couchdb

import groovy.util.logging.Slf4j
import org.ektorp.CouchDbConnector
import org.ektorp.CouchDbInstance
import org.ektorp.http.HttpClient
import org.ektorp.impl.StdCouchDbConnector
import org.ektorp.impl.StdCouchDbInstance

/**
 * CouchConnection.
 *
 * @author: Dzung Nguyen
 * @version: $Id CouchConnection 2013-07-22 17:24:30z nguyen_dv $
 *
 * @since 1.0
 */
@Slf4j("LOG")
class CouchConnection {
  //~ class properties ========================================================
  final CouchDbInstance dbInstance
  final String database

  //~ class members ===========================================================
  /**
   * Creates {@link CouchConnection couch connection} from the client.
   *
   * @param client the client to make instance.
   */
  CouchConnection(HttpClient client, String database) {
    this.dbInstance = new StdCouchDbInstance(client)
    this.database = database
  }


  /**
   * @return the {@link CouchDbConnector couch database connector} connect to
   *         database.
   */
  CouchDbConnector create(String database, boolean createIfNotExists) {
    if (LOG.isInfoEnabled()) {
      LOG.info("Trying getting database: ${database} from server.")
    }

    try {
      CouchDbConnector connector = new StdCouchDbConnector(database, this.dbInstance)

      if (createIfNotExists) {
        connector.createDatabaseIfNotExists()
      }

      connector
    } catch (Exception ex) {
      throw CouchDbExceptions.toDataAccessException(ex)
    }
  }

  /**
   * @return the {@link CouchDbConnector couch database connector} connect
   *         to database.
   */
  CouchDbConnector create(String database) {
    create(database, false)
  }

  /**
   * @return the {@link CouchDbConnector couch database connector} connect
   *         to database.
   */
  CouchDbConnector create() {
    create(database, false)
  }

  /**
   * Delete the database.
   */
  def deleteDb(String database) {
    try {
      this.dbInstance.deleteDatabase(database)
    } catch (Exception ex) {
      throw CouchDbExceptions.toDataAccessException(ex)
    }
  }

  /**
   * Creates the database from the given database name.
   */
  def createDb(String database) {
    try {
      this.dbInstance.createDatabase(database)
    } catch (Exception ex) {
      throw CouchDbExceptions.toDataAccessException(ex)
    }
  }

  /**
   * @return {@code true} if the given database is existing, otherwise {@code false}.
   */
  boolean checkIfDbExists(String database) {
    try {
      this.dbInstance.checkIfDbExists(database)
    } catch (Exception ex) {
      throw CouchDbExceptions.toDataAccessException(ex)
    }
  }

  /**
   * Close the connection.
   */
  def close() {
    if (LOG.isInfoEnabled()) {
      LOG.info("Close the connection to couch database.")
    }
  }
}
