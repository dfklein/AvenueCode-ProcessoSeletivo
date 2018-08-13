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
import com.avenuecode.model.GraphModel;
import com.avenuecode.repository.GraphRepository;
import com.avenuecode.service.RouteService;
import com.avenuecode.vo.RouteVO;
import com.avenuecode.vo.RoutesVO;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteServiceTest {

	public static final Long MOCK_ID = 01L;
	
	@TestConfiguration
    static class RouteServiceTestContextConfiguration {
  
        @Bean
        public RouteService routeService() {
            return new RouteService();
        }
    }
	
	@Autowired
	private RouteService routeService;
	
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
	public void testRoutesBetweenTwoPoints() {
		RoutesVO r1 = routeService.getRoutesBetweenTwoPointsByGraphId(MOCK_ID, 'C', 'C', 3);
		RoutesVO r2 = routeService.getRoutesBetweenTwoPointsByGraphId(MOCK_ID, 'A', 'C', 4);
		
		Assert.assertTrue(r1.getRoutes().contains(new RouteVO("CDC", 2)));
		Assert.assertTrue(r1.getRoutes().contains(new RouteVO("CEBC", 3)));
		
		Assert.assertTrue(r2.getRoutes().contains(new RouteVO("ABC", 2)));
		Assert.assertTrue(r2.getRoutes().contains(new RouteVO("ADC", 2)));
		Assert.assertTrue(r2.getRoutes().contains(new RouteVO("AEBC", 3)));
		Assert.assertTrue(r2.getRoutes().contains(new RouteVO("ADEBC", 4)));
	}
}
