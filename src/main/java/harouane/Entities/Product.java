package harouane.Entities;

public class Product {
    static Long id= 0L;
    String name;
    String category;
    Double price;

    public Product(String name, String category, Double price) {
        Product.id++;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void specialPrice(Double perCent) {
        this.price -= (price/100)*perCent;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
