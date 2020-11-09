package com.acme.todolist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.todolist.model.TaskList;
import com.acme.todolist.repository.TaskListRepository;
import com.acme.todolist.service.TaskListService;

@Service
public class TaskListServiceImpl implements TaskListService{
	@Autowired
	private TaskListRepository taskListRepository;
	
	@Override
	public TaskList createOrUpdate(TaskList pTaskList) {
		return this.taskListRepository.save(pTaskList);
	}

	@Override
	public TaskList findById(Long id) {
		return this.taskListRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		this.taskListRepository.deleteById(id);
	}

	@Override
	public Iterable<TaskList> findAll() {
		return this.taskListRepository.findAll();
	}
}
