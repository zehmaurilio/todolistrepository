package com.acme.todolist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.todolist.model.Task;
import com.acme.todolist.repository.TaskRepository;
import com.acme.todolist.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public Task createOrUpdate(Task pTask) {
		return this.taskRepository.save(pTask);
	}

	@Override
	public Task findById(Long id) {
		return this.taskRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		this.taskRepository.deleteById(id);
	}

	@Override
	public Iterable<Task> findAll() {
		return this.taskRepository.findAll();
	}
}
