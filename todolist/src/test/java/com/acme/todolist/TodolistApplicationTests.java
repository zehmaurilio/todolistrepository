package com.acme.todolist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.todolist.model.Task;
import com.acme.todolist.model.TaskList;
import com.acme.todolist.repository.TaskListRepository;
import com.acme.todolist.repository.TaskRepository;
import com.acme.todolist.service.TaskListService;
import com.acme.todolist.service.TaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
class TodolistApplicationTests {
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskListService taskListService;

	@MockBean
	private TaskRepository taskRepository;
	@MockBean
	private TaskListRepository taskListRepository;

	@Test
	public void getTasksTest() {
		when(taskRepository.findAll()).thenReturn(Stream.of(new Task(), new Task()).collect(Collectors.toList()));

		assertEquals(true, taskService.findAll().iterator().hasNext());
	}

	@Test
	public void getTaskListsTest() {
		when(taskListRepository.findAll()).thenReturn(Stream.of(new TaskList(), new TaskList()).collect(Collectors.toList()));

		assertEquals(true, taskListService.findAll().iterator().hasNext());
	}

	@Test
	public void getTaskByIdTest() {
		Task task = generateTaskObject();

		when(taskRepository.findById(task.getId())).thenReturn(java.util.Optional.of(task));

		assertNotNull(taskService.findById(task.getId()));
	}

	@Test
	public void getTaskListByIdTest() {
		TaskList taskList = generateTaskListObject();

		when(taskListRepository.findById(taskList.getId())).thenReturn(java.util.Optional.of(taskList));

		assertNotNull(taskListService.findById(taskList.getId()));
	}

	@Test
	public void createOrUpdateTaskTest() {
		Task task = generateTaskObject();

		when(taskRepository.save(task)).thenReturn(task);

		assertEquals(task, taskService.createOrUpdate(task));
	}

	@Test
	public void createOrUpdateTaskListTest() {
		TaskList taskList = generateTaskListObject();

		when(taskListRepository.save(taskList)).thenReturn(taskList);

		assertEquals(taskList, taskListService.createOrUpdate(taskList));
	}

	@Test
	public void deleteTaskTest() {
		Task task = generateTaskObject();

		taskService.delete(task.getId());
		verify(taskRepository, times(1)).deleteById(task.getId());
	}

	@Test
	public void deleteTaskListTest() {
		TaskList taskList = generateTaskListObject();

		taskListService.delete(taskList.getId());
		verify(taskListRepository, times(1)).deleteById(taskList.getId());
	}

	private Task generateTaskObject() {
		TaskList taskList = new TaskList();
		taskList.setId(Long.valueOf("5"));

		Task task = new Task();
		task.setDescription("Description");
		task.setDone(true);
		task.setId(Long.valueOf("1"));
		task.setTaskList(taskList);

		return task;
	}

	private TaskList generateTaskListObject() {
		TaskList taskList = new TaskList();
		taskList.setId(Long.valueOf("10"));
		taskList.setTitle("Title");

		return taskList;
	}
}