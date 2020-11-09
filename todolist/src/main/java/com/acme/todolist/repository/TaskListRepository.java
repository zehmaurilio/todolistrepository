package com.acme.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.todolist.model.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}
