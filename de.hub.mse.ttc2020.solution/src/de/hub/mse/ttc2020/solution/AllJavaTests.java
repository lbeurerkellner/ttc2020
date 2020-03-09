package de.hub.mse.ttc2020.solution;

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;

import de.hub.mse.ttc2020.benchmark.AllTests;

public class AllJavaTests extends AllTests {

	@BeforeClass
	public static void init() throws CoreException {
		AllTests.init();
		
		AllTests.taskFactory = new JavaTaskFactory();
		
		AllTests.pathScenario1 = "../de.hub.mse.ttc2020.benchmark/data/scenario1/";
		AllTests.pathScenario2 = "../de.hub.mse.ttc2020.benchmark/data/scenario2/";
		AllTests.pathScenario3 = "../de.hub.mse.ttc2020.benchmark/data/scenario3/";
		AllTests.pathScenario4 = "../de.hub.mse.ttc2020.benchmark/data/scenario4/";
	}
}
