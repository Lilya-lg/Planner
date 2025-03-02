package uz.tasks.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tasks.todo.domain.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
