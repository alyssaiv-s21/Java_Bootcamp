package edu.school21.models;

import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private double price;

    public Product() { }

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() { return price; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.getId() && name.equals(product.getName()) && price == product.getPrice();
    }
    public int hashCode() {
        return Objects.hashCode(id);
    }
    public String toString() {
        return "ID " + id + ", name " + name + ", price " + price;
    }
}


