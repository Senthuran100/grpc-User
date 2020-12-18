package com.senthuran.User.Controller;

import com.senthuran.User.Document.User;
import com.senthuran.User.Repository.UserRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@GRpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void viewUser(viewUserRequest request, StreamObserver<viewUserResponse> response) {

        logger.info("View User method is invoked");
        Optional<User> user= userRepository.findById(request.getId());
        if(user.isPresent()) {
            viewUserResponse userresponse = viewUserResponse.newBuilder().setAdddress(user.get().getAddress()).setUserid(user.get().getId())
                    .setName(user.get().getName()).build();
            response.onNext(userresponse);
            response.onCompleted();
        } else {
            response.onError(Status.NOT_FOUND
            .withDescription("There is no User with id "+request.getId())
            .asRuntimeException()
            );
        }
    }

    @Override
    public void addUser(userRequest request, StreamObserver<userResponse> responseObserver) {

        logger.info("Add User method is invoked");
        Optional<User> user= userRepository.findById(request.getUserid());
        if(user.isEmpty()) {
            userRepository.save(new User(request.getUserid(), request.getName(), request.getAdddress()));
            userResponse Response = userResponse.newBuilder().setMessage("Success").build();
            responseObserver.onNext(Response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.ALREADY_EXISTS
            .withDescription("Already a user exist with id "+request.getUserid()+".")
            .asRuntimeException());
        }
    }

    @Override
    public void viewAllUser(Empty emptyRequest, StreamObserver<viewAllUserResponse> response) {

        logger.info("View All User method is invoked");
        Iterable<User> userList = userRepository.findAll();
        viewAllUserResponse.Builder userResponse = viewAllUserResponse.newBuilder();
        int counter =0;
        for(User user : userList) {
           userResponse.addUser(counter,com.senthuran.User.Controller.User.newBuilder().setId(user.getId()).setName(user.getName()).setAddress(user.getAddress()));
        }
        response.onNext(userResponse.build());
        response.onCompleted();

    }

}
