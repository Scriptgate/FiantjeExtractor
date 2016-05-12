package net.scriptgate.http;

import net.scriptgate.util.InputStreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.util.List;

abstract class HttpServiceBase {

    private final Logger log = LoggerFactory.getLogger(HttpServiceBase.class);

    private final RequestService requestService;
    private final FormService formService;
    private List<String> cookies;

    HttpServiceBase(FormService formService, RequestService requestService) {
        this.formService = formService;
        this.requestService = requestService;
        enableCookies();
    }

    private void enableCookies() {
        CookieHandler.setDefault(new CookieManager());
    }

    String getLoginParameters(String loginUrl, String username, String password) throws IOException {
        String page = sendGET(loginUrl);
        return getFormService().getFormParameters(page, username, password);
    }

    protected String sendPOST(String url, String postParameters) throws IOException {
        URL _url = new URL(url);


        HttpURLConnection connection = (HttpURLConnection) _url.openConnection();

        getRequestService().setPOSTProperties(connection, postParameters, getCookies());

        int responseCode = connection.getResponseCode();

        log.debug("Sending 'POST' request to URL : " + url);
        log.debug("Post parameters : " + postParameters);
        log.debug("Response Code : " + responseCode);

        return readPage(connection);
    }

    protected String sendGET(String url) throws IOException {
        URL _url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) _url.openConnection();

        getRequestService().setGETProperties(connection, getCookies());

        int responseCode = connection.getResponseCode();

        log.debug("Sending 'GET' request to URL : " + url);
        log.debug("Response Code : " + responseCode);

        extractCookies(connection);

        return readPage(connection);
    }

    private String readPage(HttpURLConnection connection) throws IOException {
        return InputStreamUtil.getContent(connection.getInputStream());
    }

    private void extractCookies(HttpURLConnection connection) {
        setCookies(connection.getHeaderFields().get("Set-Cookie"));
    }

    private RequestService getRequestService() {
        return this.requestService;
    }

    private FormService getFormService() {
        return this.formService;
    }

    private List<String> getCookies() {
        return cookies;
    }

    private void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }
}
