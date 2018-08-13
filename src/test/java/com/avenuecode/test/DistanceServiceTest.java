package com.avenuecode.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.builder.GraphBuilder;
import com.avenuecode.exception.PathNotFoundException;
import com.avenuecode.model.GraphModel;
import com.avenuecode.repository.GraphRepository;
import com.avenuecode.service.DistanceService;
import com.avenuecode.vo.DistanceVO;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DistanceServiceTest {
	
	public static final Long MOCK_ID = 01L;
	
	@TestConfiguration
    static class DistanceServiceTestContextConfiguration {
  
        @Bean
        public DistanceService distanceService() {
            return new DistanceService();
        }
    }
	
	@Autowired
	private DistanceService distanceService;
	
	@MockBean
	private GraphRepository graphRepository;
	
	@Before
	public void setUp() {
		GraphModel mockGraphModel = new GraphBuilder().setId(MOCK_ID)
				.addRoute("AB5")
				.addRoute("BC4")
				.addRoute("CD8")
				.addRoute("DC8")
				.addRoute("DE6")
				.addRoute("AD5")
				.addRoute("CE2")
				.addRoute("EB3")
				.addRoute("AE7")
				.build();
		
		Mockito.when(graphRepository.findOne(01L)).thenReturn(mockGraphModel);
	}
	
	@Test
	public void testRouteDistances() {
		DistanceVO d1 = distanceService.findDistanceForPathByGraph(MOCK_ID, new DistanceVO("ABC"));
		DistanceVO d2 = distanceService.findDistanceForPathByGraph(MOCK_ID, new DistanceVO("AD"));
		DistanceVO d3 = distanceService.findDistanceForPathByGraph(MOCK_ID, new DistanceVO("ADC"));
		DistanceVO d4 = distanceService.findDistanceForPathByGraph(MOCK_ID, new DistanceVO("AEBCD"));
		
		Assert.assertEquals(d1.getDistance(), new Integer(9)); 
		Assert.assertEquals(d2.getDistance(), new Integer(5));
		Assert.assertEquals(d3.getDistance(), new Integer(13));
		Assert.assertEquals(d4.getDistance(), new Integer(22));
		
	}
	
	@Test(expected = PathNotFoundException.class)
	public void testRouteDistanceNotFound() {
		DistanceVO d5 = distanceService.findDistanceForPathByGraph(MOCK_ID, new DistanceVO("AED"));
	}
	
	@Test
	public void testShortesDistanceBetweenTowns() {
		DistanceVO d1 = distanceService.findShortestDistanceBetweenTownsByGraph(MOCK_ID, 'A', 'C');
		DistanceVO d2 = distanceService.findShortestDistanceBetweenTownsByGraph(MOCK_ID, 'B', 'B');
		
		Assert.assertEquals(d1.getDistance(), new Integer(9)); 
		Assert.assertEquals(d2.getDistance(), new Integer(0));
		
	}
	
}
