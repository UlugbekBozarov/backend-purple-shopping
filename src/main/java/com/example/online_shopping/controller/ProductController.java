package com.example.online_shopping.controller;

import com.example.online_shopping.database.domain.Products;
import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.request.product.ProductRequest;
import com.example.online_shopping.services.ProductServices;
import com.example.online_shopping.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.online_shopping.util.Mappings.IMAGE;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(Mappings.PRODUCT)
public class ProductController {

    private final ProductServices services;

    public ProductController(ProductServices services) {
        this.services = services;
    }


    @GetMapping()
    public List<Products> getProduct() {
        return services.getProduct();
    }


    @GetMapping(Mappings.CATEGORY + "/{category_id}")
    public List<Products> getProductByCategoryId(@PathVariable Long category_id) {
        return services.getProductAllByCategoryId(category_id);
    }


    @GetMapping("{product_id}")
    public Products getProduct(@PathVariable Long product_id) {
        return services.getProductById(product_id);
    }


    @GetMapping(IMAGE + "/{product_id}")
    public String getProductImageByCategoryId(@PathVariable Long product_id) {
        return services.getProductImageById(product_id);
    }


    @PostMapping()
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductRequest product, BindingResult result) {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services.saveProduct(product), HttpStatus.CREATED);
    }


    @PutMapping("/{product_id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long product_id, @Valid @RequestBody ProductRequest product, BindingResult result) {
        return new ResponseEntity<>(services.updateProduct(product_id, product), HttpStatus.OK);
    }


    @DeleteMapping("/{product_id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long product_id) {
        return new ResponseEntity<>(services.deleteProduct(product_id), HttpStatus.OK);
    }
}
