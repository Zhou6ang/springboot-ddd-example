package com.example.hexagon.albummgt.user.driven.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class SkipSslClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
  SkipSslClientHttpRequestFactory() {}

  protected void prepareConnection(HttpURLConnection connection, String httpMethod)
      throws IOException {
    if (connection instanceof HttpsURLConnection httpsURLConnection) {
      this.prepareHttpsConnection(httpsURLConnection);
    }

    super.prepareConnection(connection, httpMethod);
  }

  private void prepareHttpsConnection(HttpsURLConnection connection) {
    connection.setHostnameVerifier(new SkipHostnameVerifier());

    try {
      connection.setSSLSocketFactory(this.createSslSocketFactory());
    } catch (Exception var3) {
    }
  }

  private SSLSocketFactory createSslSocketFactory() throws Exception {
    return createSslContext().getSocketFactory();
  }

  public static SSLContext createSslContext() throws Exception {
    SSLContext context = SSLContext.getInstance("TLS");
    context.init(null, new TrustManager[] {new SkipX509TrustManager()}, new SecureRandom());
    return context;
  }

  private static final class SkipHostnameVerifier implements HostnameVerifier {
    private SkipHostnameVerifier() {}

    public boolean verify(String s, SSLSession sslSession) {
      return true;
    }
  }

  private static final class SkipX509TrustManager extends X509ExtendedTrustManager {
    private SkipX509TrustManager() {}

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket)
        throws CertificateException {}

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket)
        throws CertificateException {}

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine)
        throws CertificateException {}

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine)
        throws CertificateException {}

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {}

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {}

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[0];
    }
  }
}
