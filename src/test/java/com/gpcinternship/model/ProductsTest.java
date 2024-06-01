package com.gpcinternship.model;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductsTest {

    @Test
    public void whenDeserialize_thenCorrect() throws Exception {
        String xmlContent = """
                <Products>
                    <Product id="1">
                        <Name>apple</Name>
                        <Category>fruit</Category>
                        <PartNumberNR>2303-E1A-G-M-W209B-VM</PartNumberNR>
                        <CompanyName>FruitsAll</CompanyName>
                        <Active>true</Active>
                    </Product>
                    <Product id="2">
                        <Name>orange</Name>
                        <Category>fruit</Category>
                        <PartNumberNR>5603-J1A-G-M-W982F-PO</PartNumberNR>
                        <CompanyName>FruitsAll</CompanyName>
                        <Active>false</Active>
                    </Product>
                </Products>""";

        XmlMapper xmlMapper = new XmlMapper();
        Products products = xmlMapper.readValue(xmlContent, Products.class);

        System.out.println(products);


        assertThat(products).isNotNull();
        assertThat(products.getProducts()).hasSize(2);
        assertThat(products.getProducts()[0].getName()).isEqualTo("apple");
        assertThat(products.getProducts()[0].getCategory()).isEqualTo("fruit");
        assertThat(products.getProducts()[0].isActive()).isTrue();
    }


}
