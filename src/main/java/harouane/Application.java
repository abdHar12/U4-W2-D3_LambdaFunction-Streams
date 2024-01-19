package harouane;

import com.github.javafaker.Faker;
import harouane.Entities.Costumer;
import harouane.Entities.Order;
import harouane.Entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Application {

    public static void main(String[] args) {

        String boys="Boys";
        String baby="Baby";
        /*

        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton

        String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449

        System.out.printf(name+firstName+lastName+streetAddress);*/

        Supplier<Double> doubleSupplier = ()->{
            Random random = new Random();
            return Math.floor((random.nextDouble(1, 200)) * 100) / 100;
        };

        Supplier<Integer> tierSupplier=()->{
            Random random = new Random();
            return random.nextInt(1, 5);
        };
        Faker faker = new Faker();

        Supplier<Product> createNewProductBook= ()->{
            String books="Books";
            return new Product(faker.book().title(), books, doubleSupplier.get());
        };

        ArrayList<Product> allProducts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            allProducts.add(createNewProductBook.get());
        }

        Product prodotto5 = new Product("Peluche di " + faker.cat().name(), baby, doubleSupplier.get());
        Product prodotto6 = new Product("Giocattolo: " + faker.dog().name(), baby, doubleSupplier.get());
        Product prodotto7 = new Product("Peluche di " + faker.animal().name(), baby, doubleSupplier.get());
        Product prodotto8 = new Product("Action figure di: " + faker.dragonBall().character(), boys, doubleSupplier.get());
        Product prodotto9 = new Product("Action figure del personaggio di Ritorno al futuro: " + faker.backToTheFuture().character(), boys, doubleSupplier.get());
        allProducts.add(prodotto5);
        allProducts.add(prodotto6);
        allProducts.add(prodotto7);
        allProducts.add(prodotto8);
        allProducts.add(prodotto9);

        allProducts.forEach(product -> System.out.println(product));

        Predicate<Double> isMoreThan100 = num -> num > 100;
        Predicate<Product> isBaby = pr -> pr.getCategory() == baby;
        Predicate<Product> isBoys = pr -> pr.getCategory() == boys;

        allProducts.stream().map(product -> product.getPrice()).filter(isMoreThan100).forEach(pr -> System.out.println(pr));
        System.out.println("-------------------libri di piu di 100€--------------");
        allProducts.stream().filter(pr -> isMoreThan100.test(pr.getPrice())).forEach(pr -> System.out.println(pr));

        Supplier<Costumer> createCostumer=()->{
          return new Costumer(faker.name().firstName()+" "+faker.name().lastName(), tierSupplier.get());
        };

        ArrayList<Product> order1= new ArrayList<>();
        order1.add(createNewProductBook.get());
        order1.add(createNewProductBook.get());
        ArrayList<Product> order2= new ArrayList<>();
        order2.add(createNewProductBook.get());
        order2.add(prodotto5);
        ArrayList<Product> order3= new ArrayList<>();
        order3.add(prodotto9);
        ArrayList<Product> order4= new ArrayList<>();
        order4.add(prodotto6);
        order4.add(prodotto7);
        ArrayList<Product> order5= new ArrayList<>();
        order5.add(createNewProductBook.get());

        ArrayList<Order> allOrders = new ArrayList<>();

        allOrders.add(new Order("In arrivo", LocalDate.parse("2021-02-10"), LocalDate.parse("2024-01-10"), order1,createCostumer.get()));
        allOrders.add(new Order("In Elaborazione", LocalDate.parse("2021-03-10"),LocalDate.parse("2023-12-31"), order2,createCostumer.get()));
        allOrders.add(new Order("Spedito", LocalDate.parse("2024-01-10"),LocalDate.parse("2024-01-17"), order3,createCostumer.get()));
        allOrders.add(new Order("In arrivo", LocalDate.parse("2023-05-10"), LocalDate.parse("2024-01-11"), order4,createCostumer.get()));
        allOrders.add(new Order("In arrivo", LocalDate.parse("2024-03-20"),LocalDate.parse("2024-01-11"), order4,createCostumer.get()));
        allOrders.add(new Order("In arrivo", LocalDate.parse("2023-05-10"), LocalDate.parse("2024-01-11"), order5, createCostumer.get()));

        System.out.println("-------------------Tutti gli ordini--------------");
        allOrders.forEach(o->System.out.println(o));

        System.out.println("-------------------ordini dei prodotti della categoria Baby--------------");

        Predicate<List<Product>> containBabyProduct= arr->{
          List<Product> hasBabyProduct = arr.stream().filter(pr->isBaby.test(pr)).toList();
          if (hasBabyProduct.size()>0) return true;
          return false;
        };

        Predicate<List<Product>> containBoysProduct= arr->{
          List<Product> hasBoysProduct = arr.stream().filter(pr->isBoys.test(pr)).toList();
          if (hasBoysProduct.size()>0) return true;
          return false;
        };
        Predicate<Order>insideDate010221and010421=order -> {
            if(order.getOrderDate().isAfter(LocalDate.of(2021, 2, 1)) && order.getOrderDate().isBefore(LocalDate.of(2021, 4, 1))) return true;
            return false;
        };
        Predicate<Order> moreThanTier2=order -> {
            Costumer costumer= order.getCostumer();
            if (costumer.getTier() > 2) return true;
            return false;
        };

        allOrders.stream().map(order -> order.getProducts()).filter(products -> containBabyProduct.test(products)).forEach(pr->System.out.println(pr));
        System.out.println("-------------------ordini dei prodotti della categoria boys senza sconto--------------");
        allProducts.stream().filter(pr -> isBoys.test(pr)).forEach(pr->System.out.println(pr));
        System.out.println("-------------------lista dei prodotti della categoria boys con sconto del 10%--------------");
        allProducts.stream().filter(pr -> isBoys.test(pr)).forEach(singlePr-> {
            singlePr.specialPrice(10.00);
            System.out.println(singlePr);
        });
        System.out.println("-------------------ordini dei clienti con tier > di 2 ordinati tra l’01-Feb-2021 e l’01-Apr-2021--------------");
        allOrders.stream().filter(order -> moreThanTier2.test(order) && insideDate010221and010421.test(order)).forEach(order -> System.out.println(order));
    }



}
