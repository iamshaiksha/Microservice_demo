package com.example.product.service;

import com.example.product.dto.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    List<Product> products=new ArrayList<>();
    public String addProduct(Product product) {
        productRepository.save(product);
    return "success";
    }

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> producCategorytList(String category) {
       return productRepository.finProductByCategory(category);
    }

    public Product productIdList(Integer id) {
       return productRepository.findById(id).get();
    }

    public String updateProduct(Product product) {

                productRepository.save(product);
                return "product updated successfully";

    }

    public String deleteProductById(Integer id) {
        productRepository.deleteById(id);
        return "product deleted successfully";
    }
}
