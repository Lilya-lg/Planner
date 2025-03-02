package uz.tasks.todo.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uz.tasks.todo.domain.Todo;
import uz.tasks.todo.service.TodoService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private Todo todo1, todo2;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
        todo1 = new Todo(1L, "Buy groceries", "Milk, Bread, Cheese");
        todo2 = new Todo(2L, "Finish homework", "Math and Science assignments");
    }

    @Test
    void shouldReturnAllTodos() throws Exception {
        List<Todo> todos = Arrays.asList(todo1, todo2);
        when(todoService.getAllTodos()).thenReturn(todos);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void shouldReturnTodoById() throws Exception {
        when(todoService.getTodoById(1L)).thenReturn(Optional.of(todo1));

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(todo1.getTitle()))
                .andExpect(jsonPath("$.description").value(todo1.getDescription()));
    }

    @Test
    void shouldReturnNotFoundWhenTodoDoesNotExist() throws Exception {
        when(todoService.getTodoById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/todos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewTodo() throws Exception {
        when(todoService.createTodo(any(Todo.class))).thenReturn(todo1);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(todo1.getTitle()))
                .andExpect(jsonPath("$.description").value(todo1.getDescription()));
    }

    @Test
    void shouldUpdateTodo() throws Exception {
        when(todoService.updateTodo(eq(1L), any(Todo.class))).thenReturn(todo1);

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(todo1.getTitle()))
                .andExpect(jsonPath("$.description").value(todo1.getDescription()));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingTodo() throws Exception {
        when(todoService.updateTodo(eq(99L), any(Todo.class))).thenReturn(null);

        mockMvc.perform(put("/api/todos/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo1)))
                .andExpect(status().isNotFound());
    }
}
