package com.example.product.contoller;

import com.example.product.dto.Product;
import com.example.product.exception.OfferNotValidException;
import com.example.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService=productService;
    }

    @PostMapping("/addproduct")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product){
        if(product.getPrice()==0 && product.getDiscount()>0){
            throw new OfferNotValidException("No discount is allowed at 0 product price");
        }
        String status=productService.addProduct(product);
        log.info("Product added status", status);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/getallproducts")
    public List<Product> productList()
    {
        return productService.listAllProducts();
    }
    @GetMapping("/productList/{category}")
    public List<Product> producCategorytList(@PathVariable String category)
    {
        return productService.producCategorytList(category);
    }
    @GetMapping("/product/{id}")
    public Product producCategorytList(@PathVariable Integer id)
    {
        return productService.productIdList(id);
    }
    @PutMapping("updateproduct")
    public String updateProduct(@RequestBody Product product)
    {
       return productService.updateProduct(product);
    }
    @DeleteMapping("/product/{id}")
    public String deleteProductById(@PathVariable Integer id)
    {
        return productService.deleteProductById(id);
    }
}
