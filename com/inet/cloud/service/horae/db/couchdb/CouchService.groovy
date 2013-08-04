package com.inet.cloud.service.horae.db.couchdb

import com.google.common.base.Throwables
import com.inet.cloud.service.horae.db.DataAccessException
import groovy.util.logging.Slf4j
import org.apache.commons.io.IOUtils
import org.ektorp.AttachmentInputStream
import org.ektorp.CouchDbConnector
import org.ektorp.DocumentNotFoundException
import org.ektorp.Options
import org.ektorp.StreamingViewResult
import org.ektorp.ViewQuery

/**
 * CouchService.
 *
 * @author: Dzung Nguyen
 * @version: $Id CouchService 2013-07-22 23:10:30z nguyen_dv $
 *
 * @since 1.0
 */
@Slf4j("LOG")
class CouchService {
  //~ class properties ========================================================
  private final CouchConnectionFactory connectionFactory
  private final String database

  //~ class members ===========================================================
  /**
   * Creates {@link CouchService couch service} object.
   *
   * @param connectionFactory the {@link CouchConnectionFactory connection factory}.
   */
  CouchService(CouchConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory
    this.database = null
  }

  /**
   * Creates {@link CouchService couch service} object.
   *
   * @param connectionFactory {@link CouchConnectionFactory connection factory}.
   */
  private CouchService(CouchConnectionFactory connectionFactory, String database) {
    this.connectionFactory = connectionFactory
    this.database = database
  }

  /**
   * @return the {@link CouchService couch service} object with the new database name.
   */
  CouchService withConnection(String database) {
    new CouchService(connectionFactory, database)
  }

  def methodMissing(String name, args) {
    if (LOG.isInfoEnabled()) {
      LOG.info("Method missing $name")
    }

    try {
      withCouch { CouchDbConnector connector ->
        connector.invokeMethod(name, args)
      }
    } catch (Exception ex) {
      Throwables.propagateIfInstanceOf(ex, DataAccessException.class)
      throw CouchDbExceptions.toDataAccessException(ex)
    }
  }

  def <T> T getOrNull(Class<T> type, String id, String revision) {
    try {
      withCouch { CouchDbConnector connector ->
        try {
          connector.get(type, id, new Options().revision(revision))
        } catch (DocumentNotFoundException ex) {
          null
        }
      }
    } catch (Exception ex) {
      Throwables.propagateIfInstanceOf(ex, DataAccessException.class)
      throw CouchDbExceptions.toDataAccessException(ex)
    }
  }

  def <T> T getOrNull(Class<T> type, String id, Options options) {
    try {
      withCouch { CouchDbConnector connector ->
        try {
          connector.get(type, id, options)
        } catch (DocumentNotFoundException ex) {
          null
        }
      }
    } catch (Exception ex) {
      Throwables.propagateIfInstanceOf(ex, DataAccessException.class)
      throw CouchDbExceptions.toDataAccessException(ex)
    }
  }

  def withCouch(Closure closure) {
    def connection = connectionFactory.getConnection()
    try {
      if (database) closure(connection.create(database))
      else closure(connection.create())
    } finally {
      connection.close()
    }
  }

  def withStream(String id, String rev, Closure closure) {
    withStream(id, [rev: rev], closure)
  }

  def withStream(String id, Map options = [:], Closure closure) {
    // initialize option.
    Options opts = new Options()
    if (options?.rev) {
      opts = opts.revision(options.rev as String)
    }

    if (options?.revs) {
      opts = opts.includeRevisions()
    }

    if (options?.conflicts) {
      opts = opts.includeConflicts()
    }

    InputStream stream = null
    try {
      withCouch { CouchDbConnector connector ->
        stream = (options.isEmpty() ? connector.getAsStream(id) : connector.getAsStream(id, options))
        closure(stream)
      }
    } catch (Exception ex) {
      Throwables.propagateIfInstanceOf(ex, DataAccessException.class)
      throw CouchDbExceptions.toDataAccessException(ex)
    } finally {
      if (stream != null) IOUtils.closeQuietly(stream)
    }
  }

  def withAttachment(String id, String attachmentId, Map options = [:], Closure closure) {
    AttachmentInputStream stream = null
    try {
      withCouch { CouchDbConnector connector ->
        stream = (options?.rev ? connector.getAttachment(id, attachmentId, options.rev as String)
                               : connector.getAttachment(id, attachmentId))

        // process attachment connector.
        closure(stream)
      }
    } catch (Exception ex) {
      Throwables.propagateIfInstanceOf(ex, DataAccessException.class)
      throw CouchDbExceptions.toDataAccessException(ex)
    } finally {
      if (stream != null) IOUtils.closeQuietly(stream)
    }
  }

  def withQueryView(ViewQuery query, Closure closure) {
    try {
      withCouch { CouchDbConnector connector ->
        closure(connector.queryView(query))
      }
    } catch (Exception ex) {
      Throwables.propagateIfInstanceOf(ex, DataAccessException.class)
      throw CouchDbExceptions.toDataAccessException(ex)
    }
  }

  def withQueryStream(ViewQuery query, Closure closure) {
    InputStream stream = null
    try {
      withCouch { CouchDbConnector connector ->
        stream = connector.queryForStream(query)
        closure(stream)
      }
    } catch (Exception ex) {
      Throwables.propagateIfPossible(ex, DataAccessException.class)
      throw CouchDbExceptions.toDataAccessException(ex)
    } finally {
      if (stream != null) IOUtils.closeQuietly(stream)
    }
  }

  def withQueryStreamingView(ViewQuery query, Closure closure) {
    StreamingViewResult viewResult = null

    try {
      withCouch { CouchDbConnector connector ->
        viewResult = connector.queryForStreamingView(query)
        closure(viewResult)
      }
    } catch (Exception ex) {
      Throwables.propagateIfInstanceOf(ex, DataAccessException.class)
      throw CouchDbExceptions.toDataAccessException(ex)
    } finally {
      if (viewResult != null) IOUtils.closeQuietly(viewResult)
    }
  }

  /**
   * @return the current revision; or {@code null}
   */
  String revision(String id) {
    try {
      withCouch { CouchDbConnector connector -> connector.getCurrentRevision(id) }
    } catch (Exception ex) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("An error occurs during getting the revision of the document: ${id}", ex)
      }

      null
    }
  }
}
