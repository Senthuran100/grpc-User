package com.senthuran.User;

import com.senthuran.User.Document.Product;
import com.senthuran.User.Repository.ProductRepository;
import com.senthuran.User.Document.User;
import com.senthuran.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@ComponentScan("com.senthuran")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@PostConstruct
	public void initUserRepo() {
		userRepository.saveAll(Stream
				.of( new User(1111, "ABC","NY USA"),
						new User(1112, "XYZ","LA USA"))
				.collect(Collectors.toList()));
	}

	@PostConstruct
	public void initProductRepo(){
		productRepository.saveAll(Stream
				.of( new Product(111, "EarPhone",5),
						new Product(112, "Computer",4))
				.collect(Collectors.toList()));
	}

}
