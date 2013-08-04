package com.inet.cloud.service.horae.db.couchdb

import com.google.common.base.Strings
import groovy.util.logging.Slf4j
import org.apache.http.conn.ssl.SSLSocketFactory
import org.ektorp.CouchDbConnector
import org.ektorp.http.HttpClient
import org.ektorp.http.StdHttpClient
import org.ektorp.support.CouchDbRepositorySupport
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean

/**
 * CouchConnectionFactory.
 *
 * @author: Dzung Nguyen
 * @version: $Id CouchConnectionFactory 2013-07-22 16:33:30z nguyen_dv $
 *
 * @since 1.0
 */
@Slf4j("LOG")
class CouchConnectionFactory implements InitializingBean, DisposableBean {
  //~ class properties ========================================================
  private HttpClient client

  String url
  String host = "localhost"
  int port = 5984
  int maxConnections = 20
  int connectionTimeout = 1000
  int socketTimeout = 10000
  boolean autoUpdateViewOnChange
  String username
  String password
  String database
  boolean cleanupIdleConnections = true
  boolean enableSsl = false
  boolean relaxedSslSettings
  boolean caching = true
  int maxCacheEntries = 1000
  int maxObjectSizeBytes = 8192 // 8KBs
  SSLSocketFactory sslSocketFactory

  //~ class members ===========================================================
  /**
   * @return the {@link CouchConnection couch connection} connect to database.
   */
  CouchConnection getConnection() {
    new CouchConnection(client, database)
  }

  @Override
  void destroy() throws Exception {
    if (client != null) {
      client.shutdown()
    }
  }

  @Override
  void afterPropertiesSet() throws Exception {
    if (LOG.isInfoEnabled()) {
      LOG.info("Initializing CouchDB connection on ${host}:${port} ...")
      LOG.info("URL: ${url}")
      LOG.info("Max Connections: ${maxConnections}")
      LOG.info("Connection Timeout: ${connectionTimeout}")
      LOG.info("Socket Timeout: ${socketTimeout}")
      LOG.info("Auto Update View On Change: ${autoUpdateViewOnChange}")
      LOG.info("Cleanup Idle Connections: ${cleanupIdleConnections}")
      LOG.info("Enable SSL: ${enableSsl}")
      LOG.info("Relaxed SSL Settings: ${relaxedSslSettings}")
      LOG.info("Caching: ${caching}")
      LOG.info("Max Cache Entries: ${maxCacheEntries}")
      LOG.info("Max Object Size Bytes: ${maxObjectSizeBytes}")
    }

    // setting username/password.
    if (Strings.isNullOrEmpty(username)) {
      username = null
      password = null
    }

    // build the client.
    client = new StdHttpClient.Builder().host(host)
                                        .port(port)
                                        .maxConnections(maxConnections)
                                        .connectionTimeout(connectionTimeout)
                                        .socketTimeout(socketTimeout)
                                        .username(username)
                                        .password(password)
                                        .cleanupIdleConnections(cleanupIdleConnections)
                                        .enableSSL(enableSsl)
                                        .relaxedSSLSettings(relaxedSslSettings)
                                        .sslSocketFactory(sslSocketFactory)
                                        .caching(caching)
                                        .maxCacheEntries(maxCacheEntries)
                                        .maxObjectSizeBytes(maxObjectSizeBytes)
                                        .url(url)
                                        .build()

    // configure the auto update view on change.
    configureAutoUpdateViewOnChange()
  }

  // update the view.
  private def configureAutoUpdateViewOnChange() {
    if (autoUpdateViewOnChange && !Boolean.getBoolean(System.getProperty(CouchDbRepositorySupport.AUTO_UPDATE_VIEW_ON_CHANGE))) {
      System.setProperty(CouchDbRepositorySupport.AUTO_UPDATE_VIEW_ON_CHANGE, Boolean.TRUE.toString())
    }
  }
}
