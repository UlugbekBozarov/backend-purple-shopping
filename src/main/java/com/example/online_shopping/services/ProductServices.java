package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.Products;
import com.example.online_shopping.database.repository.ProductsRepo;
import com.example.online_shopping.erroe.NotAcceptable;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.request.product.ProductRequest;
import com.example.online_shopping.response.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServices {

    private final ProductsRepo productRepo;

    @Autowired
    public ProductServices(ProductsRepo productRepo) {
        this.productRepo = productRepo;
    }


    public List<Products> getProduct() throws NotFound {
        try {
            return productRepo.findAll();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(404, "No information was found in the database.");
            throw new NotFound("Find All - Product", -1, error);
        }
    }


    public Products getProductById(Long id) throws NotFound {
        try {
            return productRepo.findById(id).get();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(404, "No such information was found in the database.");
            throw new NotFound("Find By Id - Product", -1, error);
        }
    }

    public String getProductImageById(Long id) throws NotFound {
        try {
            return productRepo.findById(id).get().getImage_source();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(404, "No such information was found in the database.");
            throw new NotFound("Find By Id - Product", -1, error);
        }
    }


    public List<Products> getProductAllByCategoryId(Long category_id) throws NotFound {
        try {
            return productRepo.findAllByCategoryId(category_id);
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(404, "No information was found in the database.");
            throw new NotFound("Find All By Category Id - Product", -1, error);
        }
    }


    public Message saveProduct(ProductRequest p) throws NotAcceptable {
        try {
            Products product = new Products(p.getProduct_name(), p.getCategory_id(), p.getStock(), p.getDescription(), p.getPrice(), p.getImage_source());
            productRepo.save(product);

            log.info("Save Product: {}", product.toString());

            Message message = new Message(201, "The new product has been saved!");
            return message;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The product was not saved!");
            throw new NotAcceptable("Save - Product", -1, error);
        }
    }


    public List<Products> updateProduct(Long product_id, ProductRequest p) throws NotAcceptable {

        try {
            System.out.println("Product_id: " + product_id);
            Products product = productRepo.findById(product_id).get();

            product.setCategory_id(p.getCategory_id());
            product.setDescription(p.getDescription());
            product.setProduct_name(p.getProduct_name());
            product.setPrice(p.getPrice());
            product.setStock(p.getStock());
            product.setImage_source(p.getImage_source());

            productRepo.save(product);

            log.info("Update Product: {}", product.toString());

            return productRepo.findAllByCategoryId(product.getCategory_id());
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The product has not been modified");
            throw new NotAcceptable("Update - Product", -1, error);
        }
    }


    public List<Products> deleteProduct(Long id) throws NotAcceptable {

        try {
            Products product = productRepo.findById(id).get();
            productRepo.deleteById(id);

            log.info("Delete Product: product_id : {}", product.toString());

            return productRepo.findAllByCategoryId(product.getCategory_id());
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The product has not been deleted!");
            throw new NotAcceptable("Delete - Product", id, error);
        }

    }
}
