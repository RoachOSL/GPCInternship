package com.gpcinternship.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Product {
    @JacksonXmlProperty(isAttribute = true)
    private int id = 1;
    @JacksonXmlProperty(localName = "Name")
    private String name = "Unknown name";
    @JacksonXmlProperty(localName = "Category")
    private String category = "Unknown category";
    @JacksonXmlProperty(localName = "PartNumberNR")
    private String partNumberNR = "Unknown PartNumberNR";
    @JacksonXmlProperty(localName = "CompanyName")
    private String companyName = "Unknown company";
    @JacksonXmlProperty(localName = "Active")
    private boolean active;
}
