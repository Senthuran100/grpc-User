package com.senthuran.User.Controller;

import com.senthuran.Product.Controller.*;
import com.senthuran.Product.Controller.Empty;
import com.senthuran.User.Document.Product;
import com.senthuran.User.Repository.ProductRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GRpcService
public class ProductService extends ProductServiceGrpc.ProductServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductRepository productRepository;

    @Override
    public void viewProduct(viewProductRequest request, StreamObserver<viewProductResponse> responseObserver) {
        super.viewProduct(request, responseObserver);
    }

    @Override
    public void addProduct(addProductRequest request, StreamObserver<addProductResponse> responseObserver) {
        logger.info("Add Product is getting executed");
        Optional<Product> product= productRepository.findById(request.getProductID());
        if(product.isEmpty()) {
            productRepository.save(new Product(request.getProductID(), request.getProductName(), request.getQuantity()));
            addProductResponse Response = addProductResponse.newBuilder().setMessage("Success").build();
            responseObserver.onNext(Response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.ALREADY_EXISTS
                    .withDescription("Already a product exist with id "+request.getProductID()+".")
                    .asRuntimeException());
        }
    }

//    @Override
//    public void viewAllProducts(Empty request, StreamObserver<viewAllProductsResponse> responseObserver) {
//        super.viewAllProducts(request, responseObserver);
//    }

    @Override
    public void updateProduct(updateProductRequest request, StreamObserver<updateProductResponse> responseObserver) {
        super.updateProduct(request, responseObserver);
    }
}
