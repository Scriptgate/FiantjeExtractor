package net.scriptgate.fiantje.http;

import net.scriptgate.http.DelayedHttpService;
import net.scriptgate.http.HttpService;
import net.scriptgate.fiantje.domain.OrderEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Function;

public class FiantjeHttpService {

    private final Logger log = LoggerFactory.getLogger(FiantjeHttpService.class);

    private static final String LOGIN_URL = "http://fiantje.be/nl/login.php";
    private static final String HOME_URL = "http://fiantje.be/nl/accountien/overview.html";
    private static final String ORDERS_URL = "http://fiantje.be/nl/accountien/orders.html";
    private static final Function<String, String> ID_TO_ORDER_URL = id -> "http://fiantje.be/nl/accountien/vieworder.html?order_id=" + id;

    private HttpService httpService;

    public static FiantjeHttpService createDelayedService() {
        DelayedHttpService httpService = new DelayedHttpService(new FiantjeFormService());
        return new FiantjeHttpService(httpService);
    }

    private FiantjeHttpService(HttpService httpService) {
        this.httpService = httpService;
    }

    public void login(String username, String password) throws IOException {
        httpService.login(LOGIN_URL, username, password);
        log.info("Logged in");
    }

    public String getHomePage() throws IOException {
        return httpService.getPage(HOME_URL);
    }

    public String getOrderPage(OrderEntry entry) throws IOException {
        String orderUrl = ID_TO_ORDER_URL.apply(entry.getId());
        return httpService.getPage(orderUrl);
    }

    public String getOrdersPage() throws IOException {
        return httpService.getPage(ORDERS_URL);
    }
}
