package com.senthuran.User.Repository;

import com.senthuran.User.Document.Product;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "user",viewName = "all")
public interface ProductRepository extends CouchbaseRepository<Product,Integer> {
}
