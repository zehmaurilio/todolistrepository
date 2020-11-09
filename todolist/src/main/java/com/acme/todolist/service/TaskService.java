package com.acme.todolist.service;

import com.acme.todolist.model.Task;

public interface TaskService {
	Task createOrUpdate(Task pTask);
	
	Task findById(Long id);
	
	void delete(Long id);

	Iterable<Task> findAll();
}
