package com.gpcinternship.util;

import com.gpcinternship.model.Product;
import com.gpcinternship.model.Products;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilTest {

    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("test", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void shouldReturnProductsWhenValidFile() throws IOException {
        String xmlContent = "<Products><Product id=\"1\"><Name>Apple</Name><Category>Fruit</Category></Product></Products>";
        Files.writeString(tempFile, xmlContent);
        Products products = FileUtil.checkIfProductsAreValid(tempFile.toString());

        assertNotNull(products.getProducts());
        assertEquals(1, products.getProducts().length);
        Product product = products.getProducts()[0];
        assertEquals(1, product.getId());
        assertEquals("Apple", product.getName());
        assertEquals("Fruit", product.getCategory());
    }

    @Test
    void shouldThrowIOExceptionWhenFileDoesNotExist() {
        Exception exception = assertThrows(IOException.class, () -> FileUtil.checkIfProductsAreValid("lostFilePath"));

        assertTrue(exception.getMessage().contains("File not found"));
    }

    @Test
    void shouldThrowIOExceptionWhenNoValidProductsFound() throws IOException {
        Files.writeString(tempFile, "<Products></Products>");
        Exception exception = assertThrows(IOException.class, () -> FileUtil.checkIfProductsAreValid(tempFile.toString()));

        assertTrue(exception.getMessage().contains("No valid products found in the file."));
    }

    @Test
    void shouldHandleIncorrectOrEmptyXMLStructure() throws IOException {
        String xmlContent = "<Products><Product><Name></Product></Products>";
        Files.writeString(tempFile, xmlContent);
        Exception exception = assertThrows(IOException.class, () -> FileUtil.checkIfProductsAreValid(tempFile.toString()));

        assertTrue(exception.getMessage().contains("Invalid or empty XML content in file"));
    }

    @Test
    void shouldIgnoreUnmappedFields() throws IOException {
        String xmlContent = "<Products><Product><Name>Apple</Name><NewField>Test</NewField></Product></Products>";
        Files.writeString(tempFile, xmlContent);
        Products products = FileUtil.checkIfProductsAreValid(tempFile.toString());

        assertNotNull(products);
        assertEquals("Apple", products.getProducts()[0].getName());
    }

}
