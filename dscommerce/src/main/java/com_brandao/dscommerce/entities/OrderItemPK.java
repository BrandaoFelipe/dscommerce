package com_brandao.dscommerce.entities;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class OrderItemPK {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItemPK() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        if (order == null || product == null)
            return super.hashCode();
        return Objects.hash(
                order.getId(), 
                product.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderItemPK that = (OrderItemPK) o;
        return Objects.equals(order != null ? order.getId() : null,
                that.order != null ? that.order.getId() : null) &&
                Objects.equals(product != null ? product.getId() : null,
                        that.product != null ? that.product.getId() : null);
    }
}
