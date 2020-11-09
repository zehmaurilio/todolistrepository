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
import com.acme.todolist.model.TaskList;
import com.acme.todolist.service.TaskListService;

@RestController
@RequestMapping("/api/taskList")
@CrossOrigin(origins = "*")
public class TaskListController {
	@Autowired
	private TaskListService taskListService;
	
	@PostMapping
	public ResponseEntity<Response<TaskList>> create(@RequestBody TaskList taskList){

		Response<TaskList> response = new Response<TaskList>();
		
		try {
		
			TaskList taskListPersisted = this.taskListService.createOrUpdate(taskList);
			
			response.setData(taskListPersisted);
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "{id}")
	public ResponseEntity<Response<TaskList>> update(@PathVariable("id") String id, @RequestBody TaskList taskList){

		Response<TaskList> response = new Response<TaskList>();
		
		try {

			TaskList taskListUpdated = this.taskListService.findById(Long.valueOf(id));
			
			if(taskListUpdated == null) {
				response.getErros().add("Id não encontrado");
				return ResponseEntity.badRequest().body(response);
			}
			taskList.setId(Long.valueOf(id));
			this.taskListService.createOrUpdate(taskList);
			
			response.setData(taskList);
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
			
			TaskList taskListDeleted = this.taskListService.findById(Long.valueOf(id));
			
			if(taskListDeleted == null) {
				response.getErros().add("Id não encontrado");
				return ResponseEntity.badRequest().body(response);
			}
			
			this.taskListService.delete(Long.valueOf(id));
			response.setData("TaskList deletado com sucesso!");
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}		
		
		return ResponseEntity.ok(response);
	}
}
