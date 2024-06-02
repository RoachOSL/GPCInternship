package com.gpcinternship.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gpcinternship.model.Product;
import com.gpcinternship.model.Products;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileValidation {

    private static final Logger logger = LoggerFactory.getLogger(FileValidation.class);
    private final XmlMapper xmlMapper;

    public FileValidation() {
        this.xmlMapper = createXmlMapper();
    }

    private static XmlMapper createXmlMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public Products validateAndParseProductsFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            String errorMessage = "File not found: " + filePath;
            logger.error(errorMessage);
            throw new IOException(errorMessage);
        }

        if (!filePath.toLowerCase().endsWith(".xml")) {
            String errorMessage = "Invalid file type: " + filePath + ". Expected an XML file.";
            logger.error(errorMessage);
            throw new IOException(errorMessage);
        }

        try {
            Products products = xmlMapper.readValue(file, Products.class);

            if (products == null) {
                products = new Products();
            }
            if (products.getProducts() == null) {
                products.setProducts(new Product[0]);
            }
            return products;

        } catch (JsonProcessingException e) {
            String errorMessage = "Invalid or empty XML content in file: " + filePath;
            logger.error(errorMessage, e);
            throw new IOException(errorMessage, e);
        }
    }
}
