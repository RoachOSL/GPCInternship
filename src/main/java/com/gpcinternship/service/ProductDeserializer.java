package com.gpcinternship.service;

import com.gpcinternship.model.Product;
import com.gpcinternship.model.Products;
import com.gpcinternship.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDeserializer {
    public int countRecordsFromFile(String fileName) throws IOException {
        Products products = FileUtil.checkIfProductsAreValid(fileName);
        return products.getProducts().length;
    }

    public List<String> returnProductsList(String fileName) throws IOException {
        Products products = FileUtil.checkIfProductsAreValid(fileName);

        List<String> explicitProducts = new ArrayList<>();

        for (Product product : products.getProducts()) {
            explicitProducts.add(product.getName());
        }

        return explicitProducts;
    }

    public Product returnProductByGivenName(String fileName, String nameOfProduct) throws IOException {
        Products products = FileUtil.checkIfProductsAreValid(fileName);

        for (Product product : products.getProducts()) {
            if (product.getName().equalsIgnoreCase(nameOfProduct)) {
                return product;
            }
        }

        return new Product();
    }
}
