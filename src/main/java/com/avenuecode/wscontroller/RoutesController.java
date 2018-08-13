package com.avenuecode.wscontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.service.RouteService;
import com.avenuecode.vo.RoutesVO;

@RestController
@RequestMapping("/routes")
public class RoutesController {

	@Autowired
	private RouteService routeService;
	
	@RequestMapping(value = "/{graphId}/from/{source}/to/{target}", 
			method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoutesVO> findAvailableRoutesMaxStops(@PathVariable Long graphId,
			@PathVariable Character source, @PathVariable Character target, @RequestParam(value = "maxStops", required=false) Integer maxStops) {

		
		RoutesVO routes = routeService.getRoutesBetweenTwoPointsByGraphId(graphId, source, target, maxStops);
		
		return new ResponseEntity<RoutesVO>(routes, HttpStatus.OK);
	}
	

}
