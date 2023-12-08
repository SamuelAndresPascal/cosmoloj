package com.cosmoloj.util.security;

import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Samuel Andrés
 */
public final class SecurityUtil {

    private SecurityUtil() {
    }

    public static String httpsCertificateInfo(final HttpsURLConnection con) throws IOException {

        final var sb = new StringBuilder()
                .append("Response Code : ").append(con.getResponseCode())
                .append("Cipher Suite : ").append(con.getCipherSuite())
                .append(System.lineSeparator());

        for (final var cert : con.getServerCertificates()) {
           sb.append("Cert Type : ").append(cert.getType())
                   .append("Cert Hash Code : ").append(cert.hashCode())
                   .append("Cert Public Key Algorithm : ").append(cert.getPublicKey().getAlgorithm())
                   .append("Cert Public Key Format : ").append(cert.getPublicKey().getFormat())
                   .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
