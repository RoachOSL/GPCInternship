package com.gpcinternship.controller;

import com.gpcinternship.model.Product;
import com.gpcinternship.service.ProductDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    private final ProductDeserializer productDeserializer;

    @Value("${product.file.path}")
    private String filePath;


    @Value("${product.nameToFind}")
    private String productNameToFind;

    @Autowired
    public ApplicationController(ProductDeserializer productDeserializer) {
        this.productDeserializer = productDeserializer;
    }

    @GetMapping("/")
    public String getCountOfRecordsFromPath() {
        try {
            int count = productDeserializer.countRecordsFromFile(filePath);
            return "Number of records found in file: " + count;
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
            return "Error reading file: " + e.getMessage();
        }
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getListOfProducts() {
        try {
            return productDeserializer.returnProductsList(filePath);
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
            return Collections.singletonList("Error reading file: " + e.getMessage());
        }
    }

    @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProductByName() {
        try {
            return productDeserializer.returnProductByGivenName(filePath, productNameToFind);
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
            return new Product();
        }
    }


}
