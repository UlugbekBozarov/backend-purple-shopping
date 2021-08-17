package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.Categories;
import com.example.online_shopping.database.repository.CategoriesRepo;
import com.example.online_shopping.database.repository.UsersRepository;
import com.example.online_shopping.erroe.NotAcceptable;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.SystemError;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.request.category.CategoryRequest;
import com.example.online_shopping.response.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoriesServices {

    private final CategoriesRepo categoryRepo;

    private final UsersRepository usersRepository;

    @Autowired
    public CategoriesServices(CategoriesRepo categoryRepo, UsersRepository usersRepository) {
        this.categoryRepo = categoryRepo;
        this.usersRepository = usersRepository;
    }


    public List<Categories> getCategories() throws NotFound {
        try {
            return categoryRepo.findAll();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(404, "No information was found in the database.");
            throw new NotFound("Find All - Category", -1, error);
        }
    }


    public List<Categories> getCategoriesAsc() throws NotFound {
        try {
            return categoryRepo.findAllAsc();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(404, "No information was found in the database.");
            throw new NotFound("Find All Order By ASC - Category", -1, error);
        }
    }


    public Categories getCategoryById(Long id) throws NotFound {
        try {
            return categoryRepo.findById(id).get();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(404, "No such information was found in the database.");
            throw new NotFound("Find By Id - Category", -1, error);
        }
    }


    public List<Categories> saveCategoriy(CategoryRequest request) throws NotAcceptable {

        try {
            Categories category = new Categories(request.getType(), request.getIcon());
            categoryRepo.save(category);

            log.info("Save Category: {}", category.toString());
            return categoryRepo.findAll();

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "Could not save category type.");
            throw new NotAcceptable("Save - Category", -2, error);
        }
    }


    public List<Categories> updateCategoriy(Long category_id, CategoryRequest request) throws NotAcceptable {

        try {
            Categories category = categoryRepo.findById(category_id).get();
            category.setType(request.getType());
            category.setIcon(request.getIcon());
            categoryRepo.save(category);

            log.info("Update Category: {}", category.toString());

//            Message message = new Message(202, "The category has been uptdated!");
            return categoryRepo.findAll();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "Could not update category type.");
            throw new NotAcceptable("Update - Category", -1, error);
        }
    }


    public Message deleteCategoriy(Long id, String token) throws NotAcceptable {

        try {
            Categories categories = categoryRepo.findById(id).get();
            categoryRepo.deleteById(id);

            log.error("Delete  Category: category_id : {}", categories.toString());

            Message message = new Message(200, "The category has been deleted!");
            return message;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The category has not been deleted!");
            throw new NotAcceptable("Delete - Category", id, error);
        }

    }


}
