package com.senthuran.User.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import com.couchbase.client.java.repository.annotation.Id;
import com.couchbase.client.java.repository.annotation.Field;

@Data
@NoArgsConstructor
@Document
public class User {
    @Id
    private int id;

    @Field
    private String name;

    @Field
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setAddress(String address) {
        this.address = address;
    }

    public User(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}