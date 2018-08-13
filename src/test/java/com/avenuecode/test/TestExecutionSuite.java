package com.avenuecode.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses ({
	DistanceServiceTest.class,
	RouteServiceTest.class
})
public class TestExecutionSuite {

	public TestExecutionSuite() {
		
	}

}
