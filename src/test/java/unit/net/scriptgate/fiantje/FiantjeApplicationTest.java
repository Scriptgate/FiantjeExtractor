package net.scriptgate.fiantje;

import net.scriptgate.fiantje.http.FiantjeHttpService;
import net.scriptgate.fiantje.domain.Order;
import net.scriptgate.fiantje.domain.OrderEntry;
import net.scriptgate.fiantje.domain.OrderItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static java.util.Arrays.asList;
import static net.scriptgate.util.TestResourceUtil.getFileContent;
import static net.scriptgate.fiantje.orders.FiantjeOrderService.DELIVERY_DATE_FORMAT;
import static net.scriptgate.fiantje.orders.FiantjeOrderService.ORDER_DATE_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FiantjeApplicationTest {

    @Mock
    private FiantjeHttpService httpService;

    private FiantjeApplication fiantjeApplication;

    @Before
    public void setUp() throws Exception {
        fiantjeApplication = FiantjeApplication.createFiantje(httpService);
    }

    @Test
    public void getOrder() throws Exception {
        String orderPage = getFileContent("orderPageExtract.html");
        OrderEntry entry = new OrderEntry("123");
        when(httpService.getOrderPage(entry)).thenReturn(orderPage);

        Order order = fiantjeApplication.getOrder(entry);

        assertThat(order.getId()).isEqualTo("123");
        assertThat(order.getOrderDate()).isEqualTo(ORDER_DATE_FORMAT.parse("08:57 05.01.2015"));
        assertThat(order.getDeliveryDate()).isEqualTo(DELIVERY_DATE_FORMAT.parse("05.01.2015"));
        assertThat(order.getTotal()).isEqualTo(4.2);
        assertThat(order.getStatus()).isEqualTo("Nieuwe bestelling");
        assertThat(order.getItems())
                .extracting(OrderItem::getDescription)
                .containsOnly(
                        "Broodjes Diversen - Bickino ()",
                        "Dranken - Coca Cola (Flesje 50 cl)"
                );
    }

    @Test
    public void getOrders() throws Exception {
        String orderPage = getFileContent("orderPageExtract.html");
        OrderEntry entryA = new OrderEntry("123");
        OrderEntry entryB = new OrderEntry("124");
        when(httpService.getOrderPage(entryA)).thenReturn(orderPage);
        when(httpService.getOrderPage(entryB)).thenReturn(orderPage);

        Collection<Order> orders = fiantjeApplication.getOrders(asList(entryA, entryB));

        assertThat(orders).extracting(Order::getId).containsOnly("123", "124");
    }
}