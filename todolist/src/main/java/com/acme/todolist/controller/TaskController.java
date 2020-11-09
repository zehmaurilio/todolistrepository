package com.acme.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.response.Response;
import com.acme.todolist.model.Task;
import com.acme.todolist.service.TaskService;

@RestController
@RequestMapping("/api/task")
@CrossOrigin(origins = "*")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	@PostMapping
	public ResponseEntity<Response<Task>> create(@RequestBody Task task){
		Response<Task> response = new Response<Task>();
		
		try {
			Task taskPersisted = this.taskService.createOrUpdate(task);
			
			response.setData(taskPersisted);
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "{id}")
	public ResponseEntity<Response<Task>> update(@PathVariable("id") String id, @RequestBody Task task){
		Response<Task> response = new Response<Task>();
		
		try {
			Task taskUpdated = this.taskService.findById(Long.valueOf(id));
			
			if(taskUpdated == null) {
				response.getErros().add("Id não encontrado");
				return ResponseEntity.badRequest().body(response);
			}
			task.setId(Long.valueOf(id));
			this.taskService.createOrUpdate(task);
			
			response.setData(task);
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		
		try {			
			Task taskDeleted = this.taskService.findById(Long.valueOf(id));
			
			if(taskDeleted == null) {
				response.getErros().add("Id não encontrado");
				return ResponseEntity.badRequest().body(response);
			}
			
			this.taskService.delete(Long.valueOf(id));
			response.setData("Task deletado com sucesso!");
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}		
		
		return ResponseEntity.ok(response);
	}
}
