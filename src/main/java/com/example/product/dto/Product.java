package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    private String id;
    @NotNull(message= "product name should not be null")
    private String name;

    @NotNull(message = "product of category message cannot be null ")
    private Category category;

    private String currency;
    @Min(0)
    private double price;
    @Max(100)
    private double discount;

    private String discountDescription;

    private List<String> imageURLs;


}
