package com.gpcinternship.controller;

import com.gpcinternship.model.Product;
import com.gpcinternship.service.ProductDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class ApplicationController {
    private final ProductDeserializer productDeserializer;
    private final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    private final String filePath;

    @Autowired
    public ApplicationController(ProductDeserializer productDeserializer,
                                 @Value("${product.file.path}") String filePath) {
        this.productDeserializer = productDeserializer;
        this.filePath = filePath;
    }

    @GetMapping("/")
    public ResponseEntity<String> getCountOfRecordsFromPath() {
        try {
            int count = productDeserializer.countRecordsFromFile(filePath);
            return ResponseEntity.ok("Number of records found in file: " + count);
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file: " + e.getMessage());
        }
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getListOfProducts() {
        try {
            List<Product> products = productDeserializer.returnProductsList(filePath);
            return ResponseEntity.ok(products);
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductByName(@RequestParam String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Product name is required");
        }
        try {
            Optional<Product> product = productDeserializer.returnProductByGivenName(filePath, productName);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
            }
        } catch (IOException e) {
            logger.error("Error reading file: {}", filePath, e);
            return ResponseEntity.internalServerError().body("Error reading file: " + e.getMessage());
        }
    }
}
