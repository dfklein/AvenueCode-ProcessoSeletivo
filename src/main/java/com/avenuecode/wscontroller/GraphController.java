package com.avenuecode.wscontroller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.model.GraphModel;
import com.avenuecode.model.RouteModel;
import com.avenuecode.service.GraphService;


@RestController
@RequestMapping("/graph")
public class GraphController {
	
	@Autowired
	private GraphService graphService;
	
	@RequestMapping(value="/", method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_VALUE, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GraphModel> saveRoute(@RequestBody GraphModel graphs){
		System.out.println(graphs.getData());
		graphs = graphService.save(graphs);
		
		return new ResponseEntity<GraphModel>(graphs, HttpStatus.CREATED);
		
	}

	@RequestMapping(method = RequestMethod.GET, value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GraphModel> retrieveRouteById(@PathVariable Long id) {
		
		GraphModel graphModel = graphService.findGraphsById(id);
		
		return new ResponseEntity<GraphModel>(graphModel, HttpStatus.OK);

	}

}
