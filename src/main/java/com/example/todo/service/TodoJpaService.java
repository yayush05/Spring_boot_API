/*
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.*;
 *
 */

// Write your code here
package com.example.todo.service;

import com.example.todo.model.*;
import com.example.todo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class TodoJpaService implements TodoRepository {
    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @Override
    public ArrayList<Todo> getTodos() {
        List<Todo> todoLists = todoJpaRepository.findAll();
        ArrayList<Todo> todos = new ArrayList<>(todoLists);
        return todos;
    }

    @Override
    public Todo getTodoById(int id) {
        try {
            Todo todo = todoJpaRepository.findById(id).get();
            return todo;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Todo addTodo(Todo todo) {
        todoJpaRepository.save(todo);
        return todo;
    }

    @Override
    public Todo updateTodo(int id, Todo todo) {
        try {
            Todo existingTodo = todoJpaRepository.findById(id).get();
            if (todo.getTodo() != null) {
                existingTodo.setTodo(todo.getTodo());
            }
            if (todo.getPriority() != null) {
                existingTodo.setPriority(todo.getPriority());
            }
            if (todo.getStatus() != null) {
                existingTodo.setStatus(todo.getStatus());
            }
            todoJpaRepository.save(existingTodo);
            return existingTodo;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteTodo(int id) {
        try {
            todoJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
