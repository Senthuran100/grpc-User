package com.senthuran.User.Repository;

import com.senthuran.User.Document.User;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "user",viewName = "all")
public interface UserRepository  extends CouchbaseRepository<User,Integer> {

}
