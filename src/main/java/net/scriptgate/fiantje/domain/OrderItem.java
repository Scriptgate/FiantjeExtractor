package net.scriptgate.fiantje.domain;

public class OrderItem {

    private String description;
    private String remarks;
    private int amount;

    //private constructor jor Jackson
    @SuppressWarnings("unused")
    private OrderItem() {
    }

    public OrderItem(String description, int amount, String remarks) {
        this.description = description;
        this.amount = amount;
        this.remarks = remarks;
    }

    public String getDescription() {
        return description;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getAmount() {
        return amount;
    }
}
