package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJbdcImplTest {
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>() {{
        add(new Product(1L, "apple", 92.50));
        add(new Product(2L, "avocado", 394));
        add(new Product(3L, "banana", 52.30));
        add(new Product(4L, "kiwi", 128.74));
        add(new Product(5L, "lemon", 76));
        add(new Product(6L, "orange", 178));
        add(new Product(7L, "plum", 280));
    }};
    final Product EXPECTED_FIND_BY_ID = new Product(3l, "banana", 52.30);
    final Product EXPECTED_UPDATE_PRODUCT = new Product(1L, "cherry", 450);
    final Product EXPECTED_SAVED_PRODUCT = new Product(8L, "melon", 150);
    DataSource ds;
    ProductsRepositoryJdbcImpl repository;

    @BeforeEach
    void init() {
        ds = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        repository = new ProductsRepositoryJdbcImpl(ds);
    }

    @Test
    void findAllProduct() throws SQLException {
        List<Product> actual = repository.findAll();
        assertArrayEquals(EXPECTED_FIND_ALL_PRODUCTS.toArray(), actual.toArray());
    }

    @Test
    void findByIdNormal() throws SQLException {
        Product actual = repository.findById(3L).get();
        assertTrue(actual.equals(EXPECTED_FIND_BY_ID));
    }

    @Test
    void findByIdEmpty() throws SQLException {
        Optional<Product> actual = repository.findById(13L);
        assertTrue(actual.isEmpty());
    }

    @Test
    void updateProductNormal() throws SQLException {
        Product newProduct = new Product(1L, "cherry", 450);
        repository.update(newProduct);
        Product after = repository.findById(1L).get();
        assertTrue(after.equals(EXPECTED_UPDATE_PRODUCT));
    }

    @Test
    void updateProductBadName() {
        Product newProduct = new Product(2L, null, 75);
        assertThrows(IllegalArgumentException.class, () -> repository.update(newProduct));
    }

    @Test
    void updateProductBadPrice() {
        Product newProduct = new Product(2L, "tv", -75);
        assertThrows(IllegalArgumentException.class, () -> repository.update(newProduct));
    }

    @Test
    void saveProductNormal() throws SQLException {
        Product newProduct = new Product(8L, "melon", 150);
        repository.save(newProduct);
        Product added = repository.findById(8L).get();
        assertTrue(added.equals(EXPECTED_SAVED_PRODUCT));
    }

    @Test
    void saveProductBadName() {
        Product newProduct = new Product(8L, null, 75);
        assertThrows(IllegalArgumentException.class, () -> repository.save(newProduct));
    }

    @Test
    void saveProductBadPrice() {
        Product newProduct = new Product(8L, "tv", -75);
        assertThrows(IllegalArgumentException.class, () -> repository.save(newProduct));
    }

    @Test
    void saveProductBadId() {
        Product newProduct = new Product(2L, "tv", 75);
        assertThrows(SQLException.class, () -> repository.save(newProduct));
    }

    @Test
    void deleteProductNormal() throws SQLException {
        repository.delete(2L);
        Optional<Product> result = repository.findById(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteProductNotExist() {
        assertThrows(SQLException.class, () -> repository.delete(15L));
    }

    @AfterEach
    void closeDataSource() {
        ((EmbeddedDatabase)ds).shutdown();
    }
}
