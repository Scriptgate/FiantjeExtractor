package net.scriptgate.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DelayedHttpService extends HttpService {

    private final Logger log = LoggerFactory.getLogger(DelayedHttpService.class);

    private static final long DELAY = 500;

    public DelayedHttpService(FormService formService) {
        this(formService, new RequestService());
    }

    private DelayedHttpService(FormService formService, RequestService requestService) {
        super(formService, requestService);
    }

    @Override
    protected String sendPOST(String url, String postParameters) throws IOException {
        String page = super.sendPOST(url, postParameters);
        delayAfterRequest("POST", url);
        return page;
    }

    @Override
    protected String sendGET(String url) throws IOException {
        String page = super.sendGET(url);
        delayAfterRequest("GET", url);
        return page;
    }

    private void delayAfterRequest(String method, String url) {
        log.debug("Sleeping for " + DELAY + " ms after " + method + ":" + url);
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            log.debug("Interrupted when sleeping for " + DELAY + " ms after " + method + ":" + url);
        }
    }
}
