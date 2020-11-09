package com.acme.todolist.service;

import com.acme.todolist.model.TaskList;

public interface TaskListService {
	TaskList createOrUpdate(TaskList pTaskList);
	
	TaskList findById(Long id);
	
	void delete(Long id);

	Iterable<TaskList> findAll();
}
