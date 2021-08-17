package com.example.online_shopping.controller;

import com.example.online_shopping.database.domain.ToDoList;
import com.example.online_shopping.request.ToDoListReq;
import com.example.online_shopping.services.ToDoListServices;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.example.online_shopping.util.Mappings.FALSE;
import static com.example.online_shopping.util.Mappings.TODOLIST;
import static com.example.online_shopping.util.Mappings.TRUE;

@CrossOrigin
@RestController
@RequestMapping(TODOLIST)
public class TodoListController {

    private final ToDoListServices services;

    public TodoListController(ToDoListServices services) {
        this.services = services;
    }

    @GetMapping()
    public List<ToDoList> getTodoList(Principal principal) {
        return services.getToDoList(principal.getName());
    }


    @PostMapping()
    public List<ToDoList> createTodoList(@RequestBody ToDoListReq req, Principal principal) {
        return services.createToDoListItem(principal.getName(), req.getTask());
    }


    @GetMapping(TRUE + "/{id}")
    public List<ToDoList> putTodoListStatusTrue(@PathVariable Long id, Principal principal) {
        return services.putToDoListItemTrue(principal.getName(), id);
    }

    @GetMapping(FALSE + "/{id}")
    public List<ToDoList> putTodoListStatusFalse(@PathVariable Long id, Principal principal) {
        return services.putToDoListItemFalse(principal.getName(), id);
    }


    @DeleteMapping("/{id}")
    public List<ToDoList> deleteTodoListItem(@PathVariable Long id, Principal principal) {
        return services.deleteToDoListItem(principal.getName(), id);
    }
}
