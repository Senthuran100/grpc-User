package com.senthuran.User.Controller;

import com.google.protobuf.Empty;
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
        logger.info("View Product method is invoked");
        Optional<Product> product= productRepository.findById(request.getProductId());
        if(product.isPresent()) {
            viewProductResponse productresponse = viewProductResponse.newBuilder().setQuantity(product.get().getQuantity()).setProductID(product.get().getProductId())
                    .setProductName(product.get().getProductName()).build();
            responseObserver.onNext(productresponse);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("There is no User with id "+request.getProductId())
                    .asRuntimeException()
            );
        }
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

    @Override
    public void updateProduct(updateProductRequest request, StreamObserver<updateProductResponse> responseObserver) {
        logger.info("Calling update product");
        updateProductResponse.Builder Response = updateProductResponse.newBuilder();
        request.getProductList().forEach((product) -> {
            Optional<Product> productitem= productRepository.findById(product.getProductID());
            logger.info("productitem "+productitem);
            if(productitem.isPresent()){
                productRepository.save(
                        new Product(product.getProductID(),product.getProductName(),product.getQuantity())
                );
               Response.addMessage("Successfully updated "+product.getProductID());
            } else {
                Response.addMessage("Failed to add "+product.getProductID());
            }
        });
        responseObserver.onNext(Response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void viewAllProducts(Empty request, StreamObserver<viewAllProductsResponse> responseObserver) {
        logger.info("View All Products is getting executed");
        try {
            Iterable<Product> productList = productRepository.findAll();
            viewAllProductsResponse.Builder productResponse = viewAllProductsResponse.newBuilder();
            int counter = 0;
            for (Product product : productList) {
                productResponse.addProduct(counter, com.senthuran.User.Controller.Product.newBuilder().setProductID(product.getProductId()).setProductName(product.getProductName()).setQuantity(product.getQuantity()));
            }
            responseObserver.onNext(productResponse.build());
            responseObserver.onCompleted();
        } catch (Exception e){
            logger.info("Exception ", e);
        }
    }
}
