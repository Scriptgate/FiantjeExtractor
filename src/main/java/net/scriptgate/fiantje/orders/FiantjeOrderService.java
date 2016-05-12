package net.scriptgate.fiantje.orders;

import net.scriptgate.fiantje.domain.Order;
import net.scriptgate.fiantje.domain.OrderEntry;
import net.scriptgate.fiantje.domain.OrderItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FiantjeOrderService {

    public static final SimpleDateFormat ORDER_DATE_FORMAT = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    public static final SimpleDateFormat DELIVERY_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final DecimalFormat CURRENCY_FORMAT;

    static {
        CURRENCY_FORMAT = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        CURRENCY_FORMAT.setDecimalFormatSymbols(symbols);
    }

    private static final String ENTRY_REGEX = "/nl/accountien/vieworder\\.html\\?order_id=(\\d+)";

    public List<OrderEntry> extractOrderEntries(String ordersPage) {
        Set<String> entryIds = new HashSet<>();

        Matcher m = Pattern.compile(ENTRY_REGEX).matcher(ordersPage);
        while (m.find()) {
            entryIds.add(m.group(1));
        }

        return entryIds.stream().map(OrderEntry::new).collect(Collectors.toList());
    }

    public Order extractOrder(String orderId, String orderPage) throws ParseException {
        Order order = new Order(orderId);

        Document document = Jsoup.parse(orderPage);

        Element orderElement = document.getElementsByClass("acount").first();

        Elements orderTerms = orderElement.getElementsByTag("dt");
        fillOrderTerms(order, orderTerms);

        Element itemsElement = document.getElementsByClass("last").first();
        fillOrderItems(order, itemsElement);

        return order;
    }

    private void fillOrderTerms(Order order, Elements orderTerms) throws ParseException {
        order.withOrderDate(ORDER_DATE_FORMAT.parse(orderTerms.get(0).text()));
        order.withDeliveryDate(DELIVERY_DATE_FORMAT.parse(orderTerms.get(1).text()));
        order.withTotal(CURRENCY_FORMAT.parse(orderTerms.get(2).text()).doubleValue());
        order.withState(orderTerms.get(3).text());
    }

    private void fillOrderItems(Order order, Element itemElement) {
        Collection<OrderItem> items = new ArrayList<>();

        Elements itemDescriptions = itemElement.getElementsByTag("dd");
        Elements itemTerms = itemElement.getElementsByTag("dt");

        for (int i = 0; i < itemDescriptions.size(); i++) {
            String[] itemDescriptionAndAmount = itemDescriptions.get(i).text().split("x ");

            String itemDescription = itemDescriptionAndAmount[0];
            int itemAmount = Integer.valueOf(itemDescriptionAndAmount[1]);
            String itemRemarksPrepended = itemTerms.size() > i ? itemTerms.get(i).text() : "";

            //remove "Opmerkingen:"
            String itemRemarks = itemRemarksPrepended.substring(12);


            items.add(new OrderItem(itemDescription, itemAmount, itemRemarks));
        }

        order.withItems(items);
    }
}
