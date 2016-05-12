package net.scriptgate.fiantje.domain;

import java.util.Collection;
import java.util.Date;

import static java.lang.Math.abs;

public class Order {

    private String id;
    private Date orderDate;
    private Date deliveryDate;
    private double total;
    private String status;

    private Collection<OrderItem> items;

    //private constructor jor Jackson
    @SuppressWarnings("unused")
    private Order() {
    }

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public double getTotal() {
        return (status.equals("Annulatie") ? -1 : 1) * abs(total);
    }

    public String getStatus() {
        return status;
    }

    public Collection<OrderItem> getItems() {
        return items;
    }

    public Order withOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public Order withDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public Order withTotal(double total) {
        this.total = total;
        return this;
    }

    public Order withState(String status) {
        this.status = status;
        return this;
    }

    public Order withItems(Collection<OrderItem> items) {
        this.items = items;
        return this;
    }
}
