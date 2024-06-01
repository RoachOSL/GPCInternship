package com.gpcinternship.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gpcinternship.model.Products;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static Products checkIfProductsAreValid(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found " + filePath);
        }

        XmlMapper xmlMapper = new XmlMapper();
        Products products = xmlMapper.readValue(file, Products.class);

        if (products == null || products.getProducts() == null) {
            throw new IOException("No valid products found in the file.");
        }

        return products;
    }
}
