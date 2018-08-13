package com.avenuecode.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenuecode.exception.GraphNotFoundException;
import com.avenuecode.model.GraphModel;
import com.avenuecode.model.RouteModel;
import com.avenuecode.repository.GraphRepository;
import com.avenuecode.util.CommonUtils;
import com.avenuecode.vo.RoutesVO;

@Service
public class RouteService {

	@Autowired
	private GraphRepository graphRepository;

	public RoutesVO getRoutesBetweenTwoPointsByGraphId(final Long parameterGraphId, final Character source, final Character target,
			Integer maxStops) {

		GraphModel graph = graphRepository.findOne(parameterGraphId);
		validateRoutesBetweenTwoPoints(parameterGraphId, graph);
		
		Set<String> routeBetweenTwoPointsList = CommonUtils.convertRouteModelListToStringList(CommonUtils.findRoutesBySourceInList(graph.getData(), source));
		
		maxStops = maxStops == null ? graph.getData().size() : maxStops <= graph.getData().size() ? maxStops : graph.getData().size(); // if maxStops wasn't informed by user, the max number of stops is the whole data length.

		for (int i = 1; i < maxStops; i++) { // starts at 1 because you'll always have at least one stop
			Set<String> nextRouteBetweenTwoPointsList = new HashSet<String>();
			boolean isLastPossibleStop = i == maxStops-1;
			
			routeBetweenTwoPointsList.forEach(route -> {
				char lastTarget = route.charAt(route.length() - 1);
				
				if(lastTarget != target) {
					Set<RouteModel> possibleNextRoutes = CommonUtils.findRoutesBySourceInList(graph.getData(), lastTarget);
					// if it's the last stop, it shouldn't add any routes not leading to target destination:
					if(isLastPossibleStop) {
						possibleNextRoutes = CommonUtils.removeFromSetByTarget(possibleNextRoutes, target);
					}
					
					nextRouteBetweenTwoPointsList.addAll(CommonUtils.createNewListOfRoutesWithNextTarget(route, possibleNextRoutes));
				} else {
					nextRouteBetweenTwoPointsList.add(route);
				}


			});
			routeBetweenTwoPointsList = nextRouteBetweenTwoPointsList;

		}
		return CommonUtils.createRoutesVOFromStringListOfRoutes(routeBetweenTwoPointsList);

	}
	
	private void validateRoutesBetweenTwoPoints(final Long parameterGraphId, final GraphModel graph) {
		if(graph == null) {
			throw new GraphNotFoundException(CommonUtils.getGraphNotFoundExceptionMessage(parameterGraphId));
		}
	}
}
