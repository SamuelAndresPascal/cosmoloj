package com.cosmoloj.util.net;

import com.cosmoloj.util.security.SecurityUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Samuel Andrés
 */
public final class NetUtil {

    private NetUtil() {
    }

    public static String basicAuthorisation(final String username, final String password) {
        return Base64.getEncoder()
                .encodeToString(String.format("%s:%s", username, password).getBytes(StandardCharsets.UTF_8));
    }

    @Deprecated
    public static URL buildUrl(final String base, final Set<Map.Entry<String, String>> params)
            throws MalformedURLException, UnsupportedEncodingException {

        return new URL(base + '?' + params.stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8)
                        + '=' + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .reduce("", (a, b) -> a + '&' + b));
    }

    public static URI buildUri(final String base, final Set<Map.Entry<String, String>> params)
            throws URISyntaxException {

        return new URI(base + '?' + params.stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8)
                        + '=' + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .reduce("", (a, b) -> a + '&' + b));
    }

    public static void submit(final URL url, final String method, final Map<String, String> requestProperties,
            final Consumer<String> certificateInfo, final Consumer<String> responseHeader,
            final Consumer<String> responseBody) throws IOException {
        submit(url, method, requestProperties, null, certificateInfo, responseHeader, responseBody);
    }

    public static void submit(final URL url, final String method, final Map<String, String> requestProperties,
            final Consumer<OutputStream> requestBodyConsumer,
            final Consumer<String> certificateInfo,
            final Consumer<String> responseHeaderConsumer,
            final Consumer<String> responseBodyConsumer) throws IOException {

        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);

        con.setDoOutput(true);

        // headers
        for (final Map.Entry<String, String> requestProperty : requestProperties.entrySet()) {
            con.setRequestProperty(requestProperty.getKey(), requestProperty.getValue());
        }

        if (requestBodyConsumer != null) {
            try (var os = con.getOutputStream()) {
                requestBodyConsumer.accept(os);
            }
        }
        con.connect();

        if (con instanceof HttpsURLConnection && certificateInfo != null) {
            certificateInfo.accept(SecurityUtil.httpsCertificateInfo((HttpsURLConnection) con));
        }
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);


        final int status = con.getResponseCode();

        if (responseHeaderConsumer != null) {
            responseHeaderConsumer.accept(getResponseHeader(status, con.getResponseMessage(), con.getHeaderFields()));
        }

        if (responseBodyConsumer != null) {
            responseBodyConsumer.accept(getResponseBody(con));
        }

        con.disconnect();
    }

    private static String getResponseHeader(final int responseCode, final String responseMessage,
            final Map<String, List<String>> headerFields) {
        final StringBuilder fullResponseBuilder = new StringBuilder();

        // read status and message
        fullResponseBuilder.append(responseCode)
        .append(' ')
        .append(responseMessage)
        .append('\n');

        // read headers
        headerFields.entrySet().stream()
        .filter(entry -> entry.getKey() != null)
        .forEach(entry -> {
            fullResponseBuilder.append(entry.getKey()).append(": ");
            final List<String> headerValues = entry.getValue();
            final Iterator it = headerValues.iterator();
            if (it.hasNext()) {
                fullResponseBuilder.append(it.next());
                while (it.hasNext()) {
                    fullResponseBuilder.append(", ").append(it.next());
                }
            }
            fullResponseBuilder.append('\n');
        });

        return fullResponseBuilder.toString();
    }

    private static String getResponseBody(final HttpURLConnection con) throws IOException {
        final int status = con.getResponseCode();
        if (status > 299) {
            try (var is = con.getErrorStream()) {
                if (is != null) {
                    return getResponseBody(is);
                } else {
                    return String.valueOf(status);
                }
            }
        } else {
            try (var is = con.getInputStream()) {
                return getResponseBody(is);
            }
        }
    }

    private static String getResponseBody(final InputStream is) throws IOException {
        final StringBuilder fullResponseBuilder = new StringBuilder();

        // read response content
        try (var in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                fullResponseBuilder.append(inputLine);
                fullResponseBuilder.append('\n');
            }
        }

        return fullResponseBuilder.toString();
    }
}
