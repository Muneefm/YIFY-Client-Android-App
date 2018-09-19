package yts.mnf.torrent.Tools;

import android.util.Log;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class NullX509TrustManager implements X509TrustManager {
    /**
     * Does nothing.
     *
     * @param chain
     *            certificate chain
     * @param authType
     *            authentication type
     */
    @Override
    public void checkClientTrusted(final X509Certificate[] chain,
                                   final String authType) throws CertificateException {
        // Does nothing
        Log.e("ssl","ssl catch 3 ");

    }

    /**
     * Does nothing.
     *
     * @param chain
     *            certificate chain
     * @param authType
     *            authentication type
     */
    @Override
    public void checkServerTrusted(final X509Certificate[] chain,
                                   final String authType) throws CertificateException {
        // Does nothing
        Log.e("ssl","ssl catch 4 ");

    }

    /**
     * Gets a list of accepted issuers.
     *
     * @return empty array
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        Log.e("ssl","ssl catch 5 ");

        return new X509Certificate[0];
        // Does nothing
    }
}
