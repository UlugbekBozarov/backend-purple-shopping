package com.example.online_shopping.controller;

import com.example.online_shopping.database.domain.Categories;
import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.request.category.CategoryRequest;
import com.example.online_shopping.services.CategoriesServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static com.example.online_shopping.util.Mappings.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(CATEGORY)
public class CategoryController {

    private final CategoriesServices services;

    public CategoryController(CategoriesServices services) {
        this.services = services;
    }


    @GetMapping()
    public List<Categories> getCategories() {
        return services.getCategories();
    }

    @GetMapping(ASC)
    public List<Categories> getCategoriesAsc() {
        return services.getCategoriesAsc();
    }


    @GetMapping("/{category_id}")
    public Categories getCategoryById(@PathVariable Long category_id) {
        return services.getCategoryById(category_id);
    }


    @PostMapping()
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryRequest request, BindingResult result) {

        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.saveCategoriy(request), HttpStatus.CREATED);
    }


    @PutMapping("/{category_id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long category_id, @Valid @RequestBody CategoryRequest request, BindingResult result) {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(services.updateCategoriy(category_id, request), HttpStatus.OK);
    }


    @DeleteMapping("/{category_id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long category_id, @RequestHeader Map<String, String> headers) {
        return new ResponseEntity<>(services.deleteCategoriy(category_id, headers.get("token")), HttpStatus.CREATED);
    }
}
