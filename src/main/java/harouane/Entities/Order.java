package harouane.Entities;

import java.time.LocalDate;
import java.util.List;

public class Order {
    static Long id= 0L;
    String status;
    LocalDate orderDate;
    LocalDate deliveryDate;
    List<Product> products;
    Costumer costumer;

    public Order(String status, LocalDate orderDate, LocalDate deliveryDate, List<Product> products, Costumer costumer) {
        Order.id++;
        this.status = status;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.products = products;
        this.costumer = costumer;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "status='" + status + '\'' +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", products=" + products +
                ", costumer=" + costumer +
                '}';
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Costumer getCostumer() {
        return costumer;
    }
}
