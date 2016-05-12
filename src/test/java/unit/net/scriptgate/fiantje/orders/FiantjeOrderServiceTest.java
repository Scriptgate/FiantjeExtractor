package net.scriptgate.fiantje.orders;

import net.scriptgate.fiantje.domain.OrderEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static net.scriptgate.util.TestResourceUtil.getFileContent;
import static org.assertj.core.api.Assertions.assertThat;

public class FiantjeOrderServiceTest {

    private FiantjeOrderService service;

    @Before
    public void setUp() throws Exception {
        service = new FiantjeOrderService();
    }

    @Test
    public void getOrderEntries() throws Exception {

        String orderExtract = getFileContent("ordersPageExtract.html");

        Collection<OrderEntry> orderEntries = service.extractOrderEntries(orderExtract);
        assertThat(orderEntries)
                .extracting(OrderEntry::getId)
                .containsOnly("74495", "74601", "74652");
    }
}