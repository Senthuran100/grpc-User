package com.senthuran.User.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import com.couchbase.client.java.repository.annotation.Id;
import com.couchbase.client.java.repository.annotation.Field;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document
public class User {
    @Id
    private Integer id;

    @Field
    private String name;

    @Field
    private String address;

    @NotNull
    @Field
    private List<Product> Products = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(Integer id, String name, String address, @NotNull List<Product> products) {
        this.id = id;
        this.name = name;
        this.address = address;
        Products = products;
    }
}