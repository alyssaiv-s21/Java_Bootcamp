package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository{
    private DataSource ds;
    public ProductsRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }
    @Override
    public List<Product> findAll() throws SQLException {
        String queryFindAll = "select * from product";
        List<Product> listProduct = new ArrayList<>();
        try(Connection conn = ds.getConnection()) {
            try(Statement stmt = conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(queryFindAll);
                while(resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getDouble("price"));
                    listProduct.add(product);
                }

            }
        }
        return listProduct;
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        String queryFindById = "select * from product where id = " + id;
        try(Connection conn = ds.getConnection()) {
            try(Statement stmt = conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(queryFindById);
                if(resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getDouble("price"));
                    return Optional.of(product);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        if (product.getName() == null) {
            throw new IllegalArgumentException("The name can't be null");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("The price should be positive");
        }
        String queryUpdate = "update product set name = ?, price = ? where id = ?";
        try(Connection conn = ds.getConnection()) {
            try(PreparedStatement ps = conn.prepareStatement(queryUpdate)) {
                ps.setString(1, product.getName());
                ps.setDouble(2, product.getPrice());
                ps.setLong(3, product.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void save(Product product) throws SQLException {
        if (product.getName() == null) {
            throw new IllegalArgumentException("The name can't be null");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("The price should be positive");
        }
        String querySave = "insert into product values(?, ?, ?)";
        try(Connection conn = ds.getConnection()) {
            try(PreparedStatement ps = conn.prepareStatement(querySave)) {
                ps.setLong(1, product.getId());
                ps.setString(2, product.getName());
                ps.setDouble(3, product.getPrice());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String queryDelete = "delete from product where id = " + id;
        try(Connection conn = ds.getConnection()) {
            try(Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(queryDelete);
                if(res == 0) {
                    throw new SQLException("there is no such index in DB");
                }
            }
        }
    }
}

