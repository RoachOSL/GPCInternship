package com.gpcinternship.controller;

import com.gpcinternship.model.Product;
import com.gpcinternship.service.ProductDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {
    @Mock
    private ProductDeserializer productDeserializer;
    @InjectMocks
    private ApplicationController applicationController;
    private final String testFilePath = "test.xml";

    @BeforeEach
    public void setup() {
        applicationController = new ApplicationController(productDeserializer, testFilePath);
    }

    @Test
    public void getCountOfRecordsFromPathShouldReturnProperCount() throws IOException {
        when(productDeserializer.countRecordsFromFile(testFilePath)).thenReturn(2);

        ResponseEntity<String> response = applicationController.getCountOfRecordsFromPath();

        assertEquals("Number of records found in file: 2", response.getBody());
        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    public void getListOfProductsShouldReturnCorrectProducts() throws IOException {
        Product apple = new Product();
        apple.setName("Apple");
        Product banana = new Product();
        banana.setName("Banana");
        when(productDeserializer.returnProductsList(testFilePath)).thenReturn(Arrays.asList(apple, banana));

        ResponseEntity<List<Product>> response = applicationController.getListOfProducts();

        assertEquals(Arrays.asList(apple, banana), response.getBody());
        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    public void getProductByNameShouldReturnProductDetails() throws IOException {
        Product product = new Product();
        product.setName("Apple");

        when(productDeserializer.returnProductByGivenName(testFilePath, "Apple")).thenReturn(Optional.of(product));

        ResponseEntity<?> response = applicationController.getProductByName("Apple");

        assertInstanceOf(Product.class, response.getBody());
        assertEquals("Apple", ((Product) response.getBody()).getName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getProductByNameShouldReturnNotFoundMessage() throws IOException {
        when(productDeserializer.returnProductByGivenName(testFilePath, "Orange")).thenReturn(Optional.empty());

        ResponseEntity<?> response = applicationController.getProductByName("Orange");

        assertEquals("Product not found.", response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getProductByNameShouldReturnBadRequestIfNameIsNull() {
        ResponseEntity<?> response = applicationController.getProductByName(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Product name is required", response.getBody());
    }

    @Test
    public void getProductByNameShouldReturnBadRequestIfNameIsEmpty() {
        ResponseEntity<?> response = applicationController.getProductByName("   ");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Product name is required", response.getBody());
    }
}
