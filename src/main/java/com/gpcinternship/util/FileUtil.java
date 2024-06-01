package com.gpcinternship.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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

        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            Products products = xmlMapper.readValue(file, Products.class);

            if (products == null || products.getProducts() == null) {
                throw new IOException("No valid products found in the file.");
            }

            return products;
        } catch (JsonProcessingException e) {
            throw new IOException("Invalid or empty XML content in file: " + filePath, e);
        }
    }
}
