package net.scriptgate.http;

import java.io.IOException;

public class HttpService extends HttpServiceBase {

    HttpService(FormService formService, RequestService requestService) {
        super(formService, requestService);
    }

    public void login(String loginUrl, String username, String password) throws IOException {
        String postParameters = getLoginParameters(loginUrl, username, password);
        sendPOST(loginUrl, postParameters);
    }

    public String getPage(String url) throws IOException {
        return sendGET(url);
    }
}
