package uz.tasks.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.tasks.todo.domain.Todo;
import uz.tasks.todo.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        return todoRepository.findById(id).map(todo -> {
            todo.setTitle(updatedTodo.getTitle());
            todo.setDescription(updatedTodo.getDescription());
            return todoRepository.save(todo);
        }).orElseThrow(() -> new RuntimeException("TodoItem not found"));
    }

    public void deleteTodoById(Long id) {
        todoRepository.findById(id).orElseThrow(() -> new RuntimeException("TodoItem not found"));
        todoRepository.deleteById(id);

    }
}
