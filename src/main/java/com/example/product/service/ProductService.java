package com.example.product.service;

import com.example.product.config.ProductConfiguration;
import com.example.product.dto.Product;
import com.example.product.exception.CurrencyNotValidException;
import com.example.product.exception.OfferNotValidException;
import com.example.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {
   public ProductConfiguration productConfiguration;
    public ProductRepository productRepository;

    public String addProduct(Product product) {
        log.info("Adding product");
        if(product.getPrice()==0 && product.getDiscount()>0){
            throw new OfferNotValidException("No discount is allowed at 0 product price");
        }
        log.info("product currencies"+productConfiguration.getCurrencies());
        if (!productConfiguration.getCurrencies().get(0).contains(product.getCurrency().toUpperCase())){
            throw new CurrencyNotValidException("Invalid Currency.Valid Currencies-"+productConfiguration.getCurrencies());
        }
        productRepository.save(product);
    return "success";
    }

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> producCategorytList(String category) {
       return productRepository.finProductByCategory(category);
    }

    public Product productIdList(String id) {
       return productRepository.findById(id).get();
    }

    public String updateProduct(Product product) {

                productRepository.save(product);
                return "product updated successfully";

    }

    public String deleteProductById(String id) {
        productRepository.deleteById(id);
        return "product deleted successfully";
    }
}
