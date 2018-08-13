package com.avenuecode.builder;

import java.util.HashSet;

import com.avenuecode.model.GraphModel;
import com.avenuecode.model.RouteModel;
import com.avenuecode.util.CommonUtils;

public class GraphBuilder {
	
	private GraphModel graphModel;
	
	private Long newRouteId = 0L;
	
	public GraphBuilder() {
		graphModel = new GraphModel();
		graphModel.setData(new HashSet<RouteModel>());
	}
	
	public GraphBuilder setId(Long graphId) {
		this.graphModel.setId(graphId);
		return this;
	}
	
	public GraphBuilder addRoute(String routeString) {
		RouteModel route = CommonUtils.convertRouteStringToRouteModel(routeString);
		route.setGraphModel(this.graphModel);
		route.setId(newRouteId++);
		this.graphModel.getData().add(route);
		return this;
	}
	
	public GraphModel build() {
		return this.graphModel;
	}

}
