package com.avenuecode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avenuecode.exception.GraphNotFoundException;
import com.avenuecode.model.GraphModel;
import com.avenuecode.repository.GraphRepository;
import com.avenuecode.util.CommonUtils;

@Service
public class GraphService {

	@Autowired
	private GraphRepository graphRepository;
	
	@Transactional
	public GraphModel save(GraphModel gr) {
		return graphRepository.save(gr);
	}
	
	public GraphModel findGraphsById(Long id) {
		GraphModel gm = graphRepository.findOne(id);
		if(gm == null) {
			throw new GraphNotFoundException(CommonUtils.getGraphNotFoundExceptionMessage(id));
		} else {
			return gm;
		}
	}
	
}
