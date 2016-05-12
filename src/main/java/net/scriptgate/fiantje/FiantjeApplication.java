package net.scriptgate.fiantje;


import net.scriptgate.fiantje.http.FiantjeHttpService;
import net.scriptgate.fiantje.orders.FiantjeOrderService;
import net.scriptgate.fiantje.domain.Order;
import net.scriptgate.fiantje.domain.OrderEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.scriptgate.fiantje.http.FiantjeHttpService.createDelayedService;

public class FiantjeApplication {

    private Logger log = LoggerFactory.getLogger(FiantjeApplication.class);

    private FiantjeHttpService httpService;
    private FiantjeOrderService orderService;

    public static FiantjeApplication createFiantje() {
        FiantjeHttpService httpService = createDelayedService();
        return createFiantje(httpService);
    }

    public static FiantjeApplication createFiantje(FiantjeHttpService httpService) {
        FiantjeOrderService orderService = new FiantjeOrderService();

        return new FiantjeApplication(httpService, orderService);
    }

    private FiantjeApplication(FiantjeHttpService httpService, FiantjeOrderService orderService) {
        this.httpService = httpService;
        this.orderService = orderService;
    }

    private List<OrderEntry> getOrderEntries() throws FiantjeException {
        String ordersPage;
        try {
            ordersPage = httpService.getOrdersPage();
        } catch (IOException ex) {
            throw new FiantjeException(ex);
        }
        List<OrderEntry> entries = orderService.extractOrderEntries(ordersPage);
        log.info("Fetched " + entries.size() + " order entries");
        return entries;
    }

    Order getOrder(OrderEntry entry) throws IOException, ParseException {
        String orderPage = httpService.getOrderPage(entry);
        return orderService.extractOrder(entry.getId(), orderPage);
    }

    List<Order> getOrders(Collection<OrderEntry> entries) throws FiantjeException {
        try {
            List<Order> orders = new ArrayList<>();
            for (OrderEntry entry : entries) {
                int index = orders.size() + 1;
                log.info("Fetching order " + entry.getId() + " (" + index + " of " + entries.size() + ")");
                orders.add(getOrder(entry));
            }
            return orders;
        } catch (IOException | ParseException ex) {
            throw new FiantjeException(ex);
        }
    }

    public List<Order> getOrders() throws FiantjeException {
        List<OrderEntry> entries = getOrderEntries();
        return getOrders(entries);
    }

    public void login(String username, String password) throws FiantjeException {
        try {
            httpService.login(username, password);
        } catch (IOException ex) {
            throw new FiantjeException(ex);
        }
    }
}
