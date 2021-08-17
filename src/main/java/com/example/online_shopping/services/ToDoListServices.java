package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.ToDoList;
import com.example.online_shopping.database.domain.Users;
import com.example.online_shopping.database.repository.ToDoListRepo;
import com.example.online_shopping.database.repository.UsersRepository;
import com.example.online_shopping.erroe.SystemError;
import com.example.online_shopping.erroe.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ToDoListServices {

    private UsersRepository usersRepository;

    private ToDoListRepo toDoListRepo;

    @Autowired
    public ToDoListServices(UsersRepository usersRepository, ToDoListRepo toDoListRepo) {
        this.usersRepository = usersRepository;
        this.toDoListRepo = toDoListRepo;
    }


    public List<ToDoList> getToDoList(String username) {

        try {
            Users user = usersRepository.findByUsername(username);
            return toDoListRepo.findAllByUserId(user.getUser_id());
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(-110, "Could not retrieve data from database");
            throw new SystemError("get to do list", -1, error);
        }
    }


    public List<ToDoList> createToDoListItem(String username, String task) {

        try {
            Users user = usersRepository.findByUsername(username);
            ToDoList toDoList = new ToDoList(user, task);
            toDoListRepo.save(toDoList);

            return toDoListRepo.findAllByUserId(user.getUser_id());
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(-110, "Could not retrieve data from database");
            throw new SystemError("get to do list", -1, error);
        }
    }


    public List<ToDoList> putToDoListItemTrue(String username, long id) {

        try {
            Users user = usersRepository.findByUsername(username);
            ToDoList toDoList = toDoListRepo.findByUserIdAndId(user.getUser_id(), id);
            toDoList.setStatus(true);
            toDoListRepo.save(toDoList);

            return toDoListRepo.findAllByUserId(user.getUser_id());
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(-110, "Could not retrieve data from database");
            throw new SystemError("get to do list", -1, error);
        }
    }


    public List<ToDoList> putToDoListItemFalse(String username, long id) {

        try {
            Users user = usersRepository.findByUsername(username);
            ToDoList toDoList = toDoListRepo.findByUserIdAndId(user.getUser_id(), id);
            toDoList.setStatus(false);
            toDoListRepo.save(toDoList);

            return toDoListRepo.findAllByUserId(user.getUser_id());
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(-110, "Could not retrieve data from database");
            throw new SystemError("get to do list", -1, error);
        }
    }


    public List<ToDoList> deleteToDoListItem(String username, long id) {

        try {
            Users user = usersRepository.findByUsername(username);

            deleteToDoList(user.getUser_id(), id);

            return toDoListRepo.findAllByUserId(user.getUser_id());
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(-110, "Could not retrieve data from database");
            throw new SystemError("delete to do list", -1, error);
        }
    }


    public void deleteToDoList(long user_id, long id) {
        try {

            ToDoList toDoList = toDoListRepo.findByUserIdAndId(user_id, id);
            toDoListRepo.deleteUserIdAndId(user_id, id);
        } catch (Exception e) {
            log.warn("Delete Dodolist item: {}", toDoListRepo.toString());
        }
    }
}
