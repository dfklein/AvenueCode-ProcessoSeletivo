package com.avenuecode.wscontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.service.DistanceService;
import com.avenuecode.vo.DistanceVO;
import com.avenuecode.vo.RouteVO;

@RestController
@RequestMapping("/distance")
public class DistanceController {
	
	@Autowired
	private DistanceService distanceService;

	@RequestMapping(value = "/{graphId}", 
			method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DistanceVO> findDistanceForPathByGraph(@PathVariable Long graphId, @RequestBody(required = false) DistanceVO path) {
		
		DistanceVO distanceVO  = distanceService.findDistanceForPathByGraph(graphId, path);
		
		return new ResponseEntity<DistanceVO>(distanceVO, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/{graphId}/from/{origin}/to/{destination}", 
			method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DistanceVO> findShortestDistanceBetweenTownsByGraph(@PathVariable Long graphId,
			@PathVariable Character origin, @PathVariable Character destination) {

		DistanceVO distanceVO  = distanceService.findShortestDistanceBetweenTownsByGraph(graphId, origin, destination);
		
		return new ResponseEntity<DistanceVO>(distanceVO, HttpStatus.OK);
	}
	
	
}
