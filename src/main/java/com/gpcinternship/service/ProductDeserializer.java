package com.gpcinternship.service;

import com.gpcinternship.model.Product;
import com.gpcinternship.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDeserializer {
    private final FileValidation fileValidation;

    @Autowired
    public ProductDeserializer(FileValidation fileValidation) {
        this.fileValidation = fileValidation;
    }

    public int countRecordsFromFile(String fileName) throws IOException {
        Products products = validateAndLoadProducts(fileName);
        return products.getProducts().length;
    }

    public List<Product> returnProductsList(String fileName) throws IOException {
        Products products = validateAndLoadProducts(fileName);

        return new ArrayList<>(Arrays.asList(products.getProducts()));
    }

    public Optional<Product> returnProductByGivenName(String fileName, String nameOfProduct) throws IOException {
        Products products = validateAndLoadProducts(fileName);

        for (Product product : products.getProducts()) {
            if (product.getName().equalsIgnoreCase(nameOfProduct)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    private Products validateAndLoadProducts(String fileName) throws IOException {
        return fileValidation.validateAndParseProductsFile(fileName);
    }
}
