package com.avenuecode.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenuecode.exception.GraphNotFoundException;
import com.avenuecode.model.GraphModel;
import com.avenuecode.model.RouteModel;
import com.avenuecode.repository.GraphRepository;
import com.avenuecode.util.CommonUtils;
import com.avenuecode.vo.DistanceVO;

@Service
public class DistanceService {

		@Autowired
		private GraphRepository graphRepository;

		public DistanceVO findDistanceForPathByGraph(final Long graphId, final DistanceVO distanceVO) {
			
			GraphModel graph = graphRepository.findOne(graphId);
			DistanceVO distanceResponse = new DistanceVO();
			
			final Integer invalidParametersCode = validateFindDistanceForPath(graphId, distanceVO, distanceResponse, graph);
			
			if (invalidParametersCode == null) {
				Set<RouteModel> data = graph.getData();
				int distance = CommonUtils.calculatePathDistance(distanceVO.getPath(), data, true); // a "NO SUCH ROUTE" error message is described in test cases but not in requirements(requirements says it should return -1). I didn't know what to do here.
				distanceResponse.setDistance(distance);
			} else {
				distanceResponse.setDistance(invalidParametersCode);
			}
			return distanceResponse;
			
		}

		public DistanceVO findShortestDistanceBetweenTownsByGraph(final Long graphId, final Character origin, final Character destination) {
			
			GraphModel graph = graphRepository.findOne(graphId);
			DistanceVO distanceVO = new DistanceVO();
			
			final Integer invalidParametersCode = validateFindShortestDistanceBetweenTowns(graphId, origin, destination, graph);
			
			if (invalidParametersCode == null) {
				Map<List<Character>, Integer> destinationPathMap = new HashMap<List<Character>, Integer>(); // this map will pair a list of characters representing a path and an Integer with its distance
				
				Set<RouteModel> startingRoutes = CommonUtils.findRoutesBySourceInList(graph.getData(), origin);
				Set<String> actualSetOfPath = CommonUtils.convertRouteModelListToStringList(startingRoutes);
				
				SHORTESPATHFIND: while (true) {
					Set<String> nextSetOfPaths = new HashSet<String>();
					boolean destinationFound = false;
					for (String pathFromActualList : actualSetOfPath) {
						char lastTarget = pathFromActualList.charAt(pathFromActualList.length() - 1);
						if (lastTarget != destination) {
							Set<RouteModel> possibleNextRoutesList = CommonUtils
									.findRoutesBySourceInList(graph.getData(), lastTarget);
							nextSetOfPaths.addAll(CommonUtils.createNewListOfRoutesWithNextTarget(pathFromActualList,
									possibleNextRoutesList));
						} else {
							destinationFound = true;
							List<Character> path = CommonUtils.convertRouteStringToCharacterList(pathFromActualList);
							Integer distance = CommonUtils.calculatePathDistance(path, graph.getData(), false); // false because it should return -1 according to requirements 
							destinationPathMap.put(path, distance);

						}

					}
					
					if(destinationFound) {
						break SHORTESPATHFIND;
					}
					
					actualSetOfPath = nextSetOfPaths;

				}
				
				Entry<List<Character>, Integer> shortestPath = Collections.min(destinationPathMap.entrySet(), Comparator.comparing(Entry::getValue));
				
				distanceVO.setPath(shortestPath.getKey());
				distanceVO.setDistance(shortestPath.getValue());
				
			} else {
				distanceVO.setDistance(invalidParametersCode);
			}
			return distanceVO;
		}
		
		
		private Integer validateFindDistanceForPath(final Long parameterGraphId, final DistanceVO distanceVO, final DistanceVO distanceResponse,
				final GraphModel graph) {
			if(graph == null) {
				throw new GraphNotFoundException(CommonUtils.getGraphNotFoundExceptionMessage(parameterGraphId));
			}
			
			if(distanceVO == null || distanceVO.getPath() == null) {
				return -1;
			}
			
			if(distanceVO.getPath().isEmpty() || distanceVO.getPath().size() ==1) {
				return 0;
			}
			return null;
		}
		
		private Integer validateFindShortestDistanceBetweenTowns(final Long parameterGraphId, final Character origin, final Character destination, final GraphModel graph) {
			if(graph == null) {
				throw new GraphNotFoundException(CommonUtils.getGraphNotFoundExceptionMessage(parameterGraphId));
			}
			
			if(origin.equals(destination)) {
				return 0;
			}
			
			if(CommonUtils.findRoutesBySourceInList(graph.getData(), origin).isEmpty() || CommonUtils.findRoutesByTargetInList(graph.getData(), destination).isEmpty()) {
				return -1;
			}
			
			return null;
			
		}
		
}
