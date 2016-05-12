package net.scriptgate.http;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.List;

class RequestService {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String REQUEST_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String REQUEST_ACCEPT_LANGUAGE = "en-US,en;q=0.5";

    void setGETProperties(HttpURLConnection conn, List<String> cookies) throws ProtocolException {
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        setGeneralRequestProperties(conn);
        setCookiesRequestParameters(conn, cookies);
    }

    void setPOSTProperties(HttpURLConnection connection, String postParameters, List<String> cookies) throws IOException {
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Host", "fiantje.be");
        setGeneralRequestProperties(connection);
        setCookiesRequestParameters(connection, cookies);
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Referer", "http://fiantje.be/nl/index.html");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(postParameters.length()));
        connection.setDoOutput(true);
        connection.setDoInput(true);
        try (DataOutputStream postParametersWriter = new DataOutputStream(connection.getOutputStream())) {
            postParametersWriter.writeBytes(postParameters);
            postParametersWriter.flush();
        }
    }

    private void setGeneralRequestProperties(HttpURLConnection conn) {
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept", REQUEST_ACCEPT);
        conn.setRequestProperty("Accept-Language", REQUEST_ACCEPT_LANGUAGE);
    }

    private void setCookiesRequestParameters(HttpURLConnection conn, List<String> cookies) {
        if (cookies != null) {
            for (String cookie : cookies) {
                conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }
        }
    }
}
