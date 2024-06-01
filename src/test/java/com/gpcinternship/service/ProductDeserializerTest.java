package com.gpcinternship.service;

import com.gpcinternship.model.Product;
import com.gpcinternship.model.Products;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductDeserializerTest {

    @Mock
    private FileValidation fileValidation;

    @InjectMocks
    private ProductDeserializer productDeserializer;

    @Test
    void countRecordsFromFileShouldCorrectlyCountRecordsInFile() throws IOException {
        Products products = new Products();
        products.setProducts(new Product[]{new Product(), new Product(), new Product()});

        when(fileValidation.validateAndParseProductsFile(anyString())).thenReturn(products);

        int count = productDeserializer.countRecordsFromFile("test.xml");
        assertEquals(3, count);
    }

    @Test
    void returnProductsListShouldListAllProductNames() throws IOException {
        Product apple = new Product();
        apple.setName("Apple");
        Product banana = new Product();
        banana.setName("Banana");

        Products products = new Products();
        products.setProducts(new Product[]{apple, banana});

        when(fileValidation.validateAndParseProductsFile("test.xml")).thenReturn(products);

        List<String> productNames = productDeserializer.returnProductsList("test.xml");
        assertArrayEquals(new String[]{"Apple", "Banana"}, productNames.toArray(new String[0]));
    }

    @Test
    void returnProductsListShouldReturnEmptyList() throws IOException {
        Products emptyProducts = new Products();
        emptyProducts.setProducts(new Product[0]);
        when(fileValidation.validateAndParseProductsFile("emptyTest.xml")).thenReturn(emptyProducts);

        List<String> productNames = productDeserializer.returnProductsList("emptyTest.xml");
        assertTrue(productNames.isEmpty());
    }

    @Test
    void returnProductsListShouldThrowIOExceptionWhenFileIsCorrupt() throws IOException {
        when(fileValidation.validateAndParseProductsFile("corruptTest.xml")).thenThrow(new IOException("File access error"));

        Exception exception = assertThrows(IOException.class, () ->
                productDeserializer.returnProductsList("corruptTest.xml")
        );

        assertEquals("File access error", exception.getMessage());
    }


    @Test
    void returnProductByGivenNameShouldRetrieveProductDetailsByName() throws IOException {
        Product product = new Product();
        product.setId(3);
        product.setName("Carrot");
        product.setCategory("Vegetable");
        product.setCompanyName("CarrotCompany");
        product.setPartNumberNR("512-5FG-FA");
        product.setActive(true);

        Products products = new Products();
        products.setProducts(new Product[]{product});

        when(fileValidation.validateAndParseProductsFile("test.xml")).thenReturn(products);

        Optional<Product> foundProduct = productDeserializer.returnProductByGivenName("test.xml", "Carrot");
        assertTrue(foundProduct.isPresent());
        assertEquals(3, foundProduct.get().getId());
        assertEquals("Vegetable", foundProduct.get().getCategory());
        assertEquals("CarrotCompany", foundProduct.get().getCompanyName());
        assertEquals("512-5FG-FA", foundProduct.get().getPartNumberNR());
        assertTrue(foundProduct.get().isActive());
    }

    @Test
    void returnProductByGivenNameReturnsEmptyForUnmatchedName() throws IOException {
        Products products = new Products();
        products.setProducts(new Product[]{new Product()});

        when(fileValidation.validateAndParseProductsFile("test.xml")).thenReturn(products);

        Optional<Product> result = productDeserializer.returnProductByGivenName("test.xml", "Orange");
        assertTrue(result.isEmpty());
    }

    @Test
    void returnProductByGivenNameShouldHandleCaseInsensitiveMatchWhenProductNameDiffersInCase() throws IOException {
        Product apple = new Product();
        apple.setName("apple");

        Products products = new Products();
        products.setProducts(new Product[]{apple});

        when(fileValidation.validateAndParseProductsFile("test.xml")).thenReturn(products);

        Optional<Product> result = productDeserializer.returnProductByGivenName("test.xml", "Apple");
        assertTrue(result.isPresent());
        assertEquals("apple", result.get().getName());
    }


}
