package com.gpcinternship.service;

import ch.qos.logback.classic.Level;
import com.gpcinternship.model.Product;
import com.gpcinternship.model.Products;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileValidationTest {

    private Path tempFile;
    private FileValidation fileValidation;

    @BeforeAll
    static void setup() {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(FileValidation.class))
                .setLevel(Level.OFF);
    }

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("test", ".txt");
        fileValidation = new FileValidation();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void shouldReturnProductsWhenValidFile() throws IOException {
        String xmlContent = "<Products><Product id=\"1\"><Name>Apple</Name><Category>Fruit</Category></Product></Products>";
        Files.writeString(tempFile, xmlContent);
        Products products = fileValidation.validateAndParseProductsFile(tempFile.toString());

        assertNotNull(products.getProducts());
        assertEquals(1, products.getProducts().length);
        Product product = products.getProducts()[0];
        assertEquals(1, product.getId());
        assertEquals("Apple", product.getName());
        assertEquals("Fruit", product.getCategory());
    }

    @Test
    void shouldThrowIOExceptionWhenFileDoesNotExist() {
        Exception exception = assertThrows(IOException.class, () -> fileValidation.validateAndParseProductsFile("lostFilePath"));

        assertTrue(exception.getMessage().contains("File not found"));
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsFound() throws IOException {
        Files.writeString(tempFile, "<Products></Products>");

        Products result = fileValidation.validateAndParseProductsFile(tempFile.toString());

        assertNotNull(result, "The result should not be null.");
        assertNotNull(result.getProducts(), "The products list should not be null.");
        assertEquals(0, result.getProducts().length, "The products list should be empty.");
    }

    @Test
    void shouldHandleIncorrectOrEmptyXMLStructure() throws IOException {
        String xmlContent = "<Products><Product><Name></Product></Products>";
        Files.writeString(tempFile, xmlContent);
        Exception exception = assertThrows(IOException.class, () -> fileValidation.validateAndParseProductsFile(tempFile.toString()));

        assertTrue(exception.getMessage().contains("Invalid or empty XML content in file"));
    }

    @Test
    void shouldIgnoreUnmappedFields() throws IOException {
        String xmlContent = "<Products><Product><Name>Apple</Name><NewField>Test</NewField></Product></Products>";
        Files.writeString(tempFile, xmlContent);
        Products products = fileValidation.validateAndParseProductsFile(tempFile.toString());

        assertNotNull(products);
        assertEquals("Apple", products.getProducts()[0].getName());
    }

}
