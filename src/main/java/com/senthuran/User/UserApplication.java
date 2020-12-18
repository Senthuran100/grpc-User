package com.senthuran.User;

import com.senthuran.User.Document.User;
import com.senthuran.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void initUserRepo() {
		userRepository.saveAll(Stream
				.of( new User(111, "ABC","NY USA"),
						new User(112, "XYZ","LA USA"))
				.collect(Collectors.toList()));
	}

}
