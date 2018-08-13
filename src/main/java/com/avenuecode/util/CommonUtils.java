package com.avenuecode.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.avenuecode.exception.PathNotFoundException;
import com.avenuecode.model.RouteModel;
import com.avenuecode.vo.RouteVO;
import com.avenuecode.vo.RoutesVO;

public final class CommonUtils {
	
	private CommonUtils() {
		
	}
	
	public static Set<String> convertRouteModelListToStringList(Set<RouteModel> listRouteModel) {
		Set<String> listRouteModelString = new HashSet<String>();
		listRouteModel.forEach(routeModel -> {
			listRouteModelString.add(routeModel.toString());
		});
		return listRouteModelString;
	}
	
	public static Set<RouteModel> findRoutesBySourceInList(Set<RouteModel> routeList, Character source) {
		Set<RouteModel> routesFromSource = new HashSet<RouteModel>();
		routeList.forEach(route -> {
			if(route.getSource() == source) {
				routesFromSource.add(route);
			}
		});
		return routesFromSource;
	}
	
	public static Set<RouteModel> findRoutesByTargetInList(Set<RouteModel> routeList, Character target) {
		Set<RouteModel> routesFromSource = new HashSet<RouteModel>();
		routeList.forEach(route -> {
			if(route.getTarget() == target) {
				routesFromSource.add(route);
			}
		});
		return routesFromSource;
	}
	
	public static List<Character> convertRouteStringToCharacterList(String routeString) {
		List<Character> routeSet = new ArrayList<>();
		for (int i = 0; i < routeString.length(); i++) {
			routeSet.add(new Character(routeString.charAt(i)));
		}
		return routeSet;
	}
	
	/**
	 * String format should be two characters and a number like AB5, CD2, etc... 
	 */
	public static RouteModel convertRouteStringToRouteModel(String routeString) { 
		RouteModel rm = new RouteModel();
		rm.setSource(routeString.charAt(0));
		rm.setTarget(routeString.charAt(1));
		rm.setDistance(Character.getNumericValue(routeString.charAt(2)));
		return rm;
	}
	
	public static RoutesVO createRoutesVOFromStringListOfRoutes(Set<String> stringfiedRoutes) {
		Set<RouteVO> routes = new HashSet<RouteVO>();
		stringfiedRoutes.forEach(routeString -> {
			RouteVO routeVO = new RouteVO();
			routeVO.setRoute(routeString);
			routeVO.setStops(routeString.length()-1);
			routes.add(routeVO);
		});
		return new RoutesVO(routes);
	}

	public static Set<RouteModel> removeFromSetByTarget(Set<RouteModel> possibleNextRoutes, Character target) {
		Set<RouteModel> routeListWithoutTarget = new HashSet<RouteModel>();
		possibleNextRoutes.forEach(route -> {
			if(route.getTarget() == target) {
				routeListWithoutTarget.add(route);
			}
		});
		return routeListWithoutTarget;
	}
	

	public static Set<String> createNewListOfRoutesWithNextTarget(String route, Set<RouteModel> possibleNextRoutes) {
		Set<String> newRoutes = new HashSet<String>();
			possibleNextRoutes.forEach(nextRoute -> {
				if(route.length() < 3 || nextRoute.getTarget() != route.charAt(route.length()-2)) {// ypu should only add it if it's not going back to where it came from in the previous route.
					newRoutes.add(route + String.valueOf(nextRoute.getTarget()));
				}
			});
		return newRoutes;

	}
	
	public static String getGraphNotFoundExceptionMessage(final Long parameterGraphId) {
		return "NOT FOUND: Graph of ID " + parameterGraphId + " was not found";
	}
	
	
	
	public static RouteModel findRouteBySourceAndTargetInGraph(Set<RouteModel> data, Character source, Character target) {
		RouteModel routeWithSourceAndTargetRequested = null;

		// Look for route with informed source
		OUTTER: for (RouteModel routeSource : data) {
			if(routeSource.getSource().equals(source)) {
				// Look four route with informed source and destination. When found, can set return value and break outter loop (won't use lambda due to break need)
				for (RouteModel routeTarget : data) {
					if(routeTarget.getSource().equals(source) && routeTarget.getTarget().equals(target)) {
						routeWithSourceAndTargetRequested = routeTarget;
						break OUTTER;
					}

				}
			}
		}

		return routeWithSourceAndTargetRequested;

	}
	
	public static int calculatePathDistance(List<Character> path, Set<RouteModel> data, boolean thorwsNotFoundException) {
		int distance = 0;

		for(Character town : path) { // used old style iteration to be able to break the loop.

			if(path.indexOf(town) != path.size()-1) { // checks if there's a next stop
				Character nextStop = path.get(path.indexOf(town)+1);
				RouteModel route = findRouteBySourceAndTargetInGraph(data, town, nextStop); // thought of querying database, but all the information is already loaded and if the input is big it might be expensive
				if(route != null) {
					distance += route.getDistance();
				} else {
					if (thorwsNotFoundException) { // implemented because of test case number 5, but message is not described in requirements. Shouldn't it return -1 in this case? 
						throw new PathNotFoundException("NO SUCH ROUTE: Target point can't be reached from source in informed graph");
					} else {
						distance = -1;
						break;
					}
				}

			} else { // this means you are checking the final stop point, so you reached your destination
				break;
			}

		}
		return distance;
	}
	
}
