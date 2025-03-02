package uz.tasks.todo.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.tasks.todo.domain.Todo;
import uz.tasks.todo.repository.TodoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo todo1, todo2;

    @BeforeEach
    void setUp() {
        todo1 = new Todo("Buy groceries", "Milk, Bread, Cheese");
        todo2 = new Todo("Finish homework", "Math and Science assignments");
    }

    @Test
    void shouldReturnAllTodos() {
        List<Todo> todos = Arrays.asList(todo1, todo2);
        when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> result = todoService.getAllTodos();
        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnTodoById() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));

        Optional<Todo> result = todoService.getTodoById(1L);
        assertThat(result).isPresent().contains(todo1);
    }

    @Test
    void shouldSaveNewTodo() {
        when(todoRepository.save(todo1)).thenReturn(todo1);

        Todo result = todoService.createTodo(todo1);
        assertThat(result).isEqualTo(todo1);
    }

    @Test
    void shouldUpdateTodo() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo1);

        Todo updatedTodo = new Todo("Buy groceries", "Milk, Bread, Cheese, Eggs");
        Todo result = todoService.updateTodo(1L, updatedTodo);

        assertThat(result.getDescription()).isEqualTo("Milk, Bread, Cheese, Eggs");
    }

    @Test
    void shouldDeleteTodo() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));
        doNothing().when(todoRepository).deleteById(anyLong());

        todoService.deleteTodoById(1L);

        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingTodo() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        Todo updatedTodo = new Todo("Non-existing", "This should not exist");

        assertThatThrownBy(() -> todoService.updateTodo(99L, updatedTodo)).isInstanceOf(RuntimeException.class).hasMessageContaining("TodoItem not found");
    }

    @Test
    void shouldReturnEmptyOptionalForNonExistingTodo() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Todo> result = todoService.getTodoById(99L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTodo() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> todoService.deleteTodoById(99L)).isInstanceOf(RuntimeException.class).hasMessageContaining("TodoItem not found");
    }

    @Test
    void shouldHandleEmptyTodoList() {
        when(todoRepository.findAll()).thenReturn(Arrays.asList());

        List<Todo> result = todoService.getAllTodos();
        assertThat(result).isEmpty();
    }

    @Test
    void shouldHandleCreatingTodoWithEmptyTitle() {
        Todo emptyTitleTodo = new Todo("", "Description");
        when(todoRepository.save(emptyTitleTodo)).thenReturn(emptyTitleTodo);

        Todo result = todoService.createTodo(emptyTitleTodo);
        assertThat(result.getTitle()).isEmpty();
    }
    @Test
    void shouldNotUpdateTodoIfValuesAreSame() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo1);

        Todo result = todoService.updateTodo(1L, todo1);
        assertThat(result.getTitle()).isEqualTo(todo1.getTitle());
        assertThat(result.getDescription()).isEqualTo(todo1.getDescription());
    }
    @Test
    void shouldHandleCreatingTodoWithNullValues() {
        Todo nullTodo = new Todo(null, null);
        when(todoRepository.save(any(Todo.class))).thenReturn(nullTodo);

        Todo result = todoService.createTodo(nullTodo);
        assertThat(result.getTitle()).isNull();
        assertThat(result.getDescription()).isNull();
    }
    @Test
    void shouldReturnEmptyListWhenNoTodosAvailable() {
        when(todoRepository.findAll()).thenReturn(List.of());

        List<Todo> result = todoService.getAllTodos();
        assertThat(result).isEmpty();
    }




}
