package com.dwd.model;

public class PriceTrend {
    private String price;
    private String trend;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    @Override
    public String toString() {
        return "PriceTrend{" +
                "price=" + price +
                ", trend=" + trend +
                '}';
    }
}
