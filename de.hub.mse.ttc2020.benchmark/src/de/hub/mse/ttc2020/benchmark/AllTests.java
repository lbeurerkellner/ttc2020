package de.hub.mse.ttc2020.benchmark;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class AllTests {

	protected static AbstractTaskFactory taskFactory;

	protected static String pathScenario1 = "data/scenario1/";
	protected static String pathScenario2 = "data/scenario2/";
	protected static String pathScenario3 = "data/scenario3/";
	protected static String pathScenario4 = "data/scenario4/";

	private static ResourceSet resSet;

	@BeforeClass
	public static void init() throws CoreException {
		System.out.println("Start running tests...");

		// IExtensionRegistry reg = Platform.getExtensionRegistry();
		if (Platform.getExtensionRegistry() != null) {
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor("de.hub.mse.ttc2020.benchmark.concretetaskfactory");
			if (elements.length > 0) {
				taskFactory = (AbstractTaskFactory) elements[0].createExecutableExtension("concrete_task_factory");
			}
		}

		// taskFactory = (AbstractTaskFactory)
		// Class.forName("de.hub.mse.ttc2020.solution.JavaTaskFactory").newInstance();
		resSet = new ResourceSetImpl();

		// Initialize EMF resource factories
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("ecore", new EcoreResourceFactoryImpl());
		m.put("xmi", new XMIResourceFactoryImpl());

		// Suppress Log4J warnings (used by EMFCompare)
		Logger.getRootLogger().setLevel(Level.OFF);
	}

	 @Test
	 public void task_1_M1_M2_M1_a() throws IOException {
	 System.out.println("\n= Task 1 M1->M2->M1 (a)");
	
	 EPackage model1 = getModel(pathScenario1 + "models/V1.ecore");
	 EPackage model2 = getModel(pathScenario1 + "models/V2.ecore");
	 EObject input = getInstance(pathScenario1 + "instances/input/V1a.xmi");
	 System.out.println(PrettyPrinter.printModel(model1));
	 System.out.println(PrettyPrinter.printModel(model2));
	 System.out.println("Input " + PrettyPrinter.printInstance(input));
	
	 AbstractTask task = taskFactory.createTask(TaskInfo.TASK_1_M1_M2_M1, model1,
	 model2);
	
	 EObject migrated = task.migrate(input);
	 EObject migratedBack = task.migrateBack(migrated);
	 System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
	 System.out.println("Migrated Back " +
	 PrettyPrinter.printInstance(migratedBack));
	
	 EObject expout = getInstance(pathScenario1 + "instances/expout/V1a.xmi");
	 System.out.println("Expected " + PrettyPrinter.printInstance(expout));
	
	 assertTrue(isEqual(migratedBack, expout));
	 }
	
	 @Test
	 public void task_1_M1_M2_M1_b() throws IOException {
	 System.out.println("\n= Task 1 M1->M2->M1 (b)");
	
	 EPackage model1 = getModel(pathScenario1 + "models/V1.ecore");
	 EPackage model2 = getModel(pathScenario1 + "models/V2.ecore");
	 EObject input = getInstance(pathScenario1 + "instances/input/V1b.xmi");
	 System.out.println(PrettyPrinter.printModel(model1));
	 System.out.println(PrettyPrinter.printModel(model2));
	 System.out.println("Input " + PrettyPrinter.printInstance(input));
	
	 AbstractTask task = taskFactory.createTask(TaskInfo.TASK_1_M1_M2_M1, model1,
	 model2);
	
	 EObject migrated = task.migrate(input);
	 EObject migratedBack = task.migrateBack(migrated);
	 System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
	 System.out.println("Migrated Back " +
	 PrettyPrinter.printInstance(migratedBack));
	
	 EObject expout = getInstance(pathScenario1 + "instances/expout/V1b.xmi");
	 System.out.println("Expected " + PrettyPrinter.printInstance(expout));
	
	 assertTrue(isEqual(migratedBack, expout));
	 }
	
	 @Test
	 public void task_1_M2_M1_M2_a() throws IOException {
	 System.out.println("\n= Task 1 M2->M1->M2 (a)");
	
	 EPackage model1 = getModel(pathScenario1 + "models/V1.ecore");
	 EPackage model2 = getModel(pathScenario1 + "models/V2.ecore");
	 EObject input = getInstance(pathScenario1 + "instances/input/V2a.xmi");
	 System.out.println(PrettyPrinter.printModel(model1));
	 System.out.println(PrettyPrinter.printModel(model2));
	 System.out.println("Input " + PrettyPrinter.printInstance(input));
	
	 AbstractTask task = taskFactory.createTask(TaskInfo.TASK_1_M2_M1_M2, model1,
	 model2);
	
	 EObject migrated = task.migrate(input);
	 EObject migratedBack = task.migrateBack(migrated);
	 System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
	 System.out.println("Migrated Back " +
	 PrettyPrinter.printInstance(migratedBack));
	
	 EObject expout = getInstance(pathScenario1 + "instances/expout/V2a.xmi");
	 System.out.println("Expected " + PrettyPrinter.printInstance(expout));
	
	 assertTrue(isEqual(migratedBack, expout));
	 }
	
	 @Test
	 public void task_1_M2_M1_M2_b() throws IOException {
	 System.out.println("\n= Task 1 M2->M1->M2 (b)");
	
	 EPackage model1 = getModel(pathScenario1 + "models/V1.ecore");
	 EPackage model2 = getModel(pathScenario1 + "models/V2.ecore");
	 EObject input = getInstance(pathScenario1 + "instances/input/V2b.xmi");
	 System.out.println(PrettyPrinter.printModel(model1));
	 System.out.println(PrettyPrinter.printModel(model2));
	 System.out.println("Input " + PrettyPrinter.printInstance(input));
	
	 AbstractTask task = taskFactory.createTask(TaskInfo.TASK_1_M2_M1_M2, model1,
	 model2);
	
	 EObject migrated = task.migrate(input);
	 EObject migratedBack = task.migrateBack(migrated);
	 System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
	 System.out.println("Migrated Back " +
	 PrettyPrinter.printInstance(migratedBack));
	
	 EObject expout = getInstance(pathScenario1 + "instances/expout/V2b.xmi");
	 System.out.println("Expected " + PrettyPrinter.printInstance(expout));
	
	 assertTrue(isEqual(migratedBack, expout));
	 }
	
	 @Test
	 public void task_2_M1_M2_M1_a() throws IOException {
	 System.out.println("\n= Task 2 M1->M2->M1 (a)");
	
	 EPackage model1 = getModel(pathScenario2 + "models/V1.ecore");
	 EPackage model2 = getModel(pathScenario2 + "models/V2.ecore");
	 EObject input = getInstance(pathScenario2 + "instances/input/V1a.xmi");
	 System.out.println(PrettyPrinter.printModel(model1));
	 System.out.println(PrettyPrinter.printModel(model2));
	 System.out.println("Input " + PrettyPrinter.printInstance(input));
	
	 AbstractTask task = taskFactory.createTask(TaskInfo.TASK_2_M1_M2_M1, model1,
	 model2);
	
	 EObject migrated = task.migrate(input);
	 EObject migratedBack = task.migrateBack(migrated);
	 System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
	 System.out.println("Migrated Back " +
	 PrettyPrinter.printInstance(migratedBack));
	
	 EObject expout = getInstance(pathScenario2 + "instances/expout/V1a.xmi");
	 System.out.println("Expected " + PrettyPrinter.printInstance(expout));
	
	 assertTrue(isEqual(migratedBack, expout));
	 }
	
	 @Test
	 public void task_2_M1_M2_M1_b() throws IOException {
	 System.out.println("\n= Task 2 M1->M2->M1 (b)");
	
	 EPackage model1 = getModel(pathScenario2 + "models/V1.ecore");
	 EPackage model2 = getModel(pathScenario2 + "models/V2.ecore");
	 EObject input = getInstance(pathScenario2 + "instances/input/V1b.xmi");
	 System.out.println(PrettyPrinter.printModel(model1));
	 System.out.println(PrettyPrinter.printModel(model2));
	 System.out.println("Input " + PrettyPrinter.printInstance(input));
	
	 AbstractTask task = taskFactory.createTask(TaskInfo.TASK_2_M1_M2_M1, model1,
	 model2);
	
	 EObject migrated = task.migrate(input);
	 EObject migratedBack = task.migrateBack(migrated);
	 System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
	 System.out.println("Migrated Back " +
	 PrettyPrinter.printInstance(migratedBack));
	
	 EObject expout = getInstance(pathScenario2 + "instances/expout/V1b.xmi");
	 System.out.println("Expected " + PrettyPrinter.printInstance(expout));
	
	 assertTrue(isEqual(migratedBack, expout));
	 }
	
	 @Test
	 public void task_2_M2_M1_M2_a() throws IOException {
	 System.out.println("\n= Task 2 M2->M1->M2 (a)");
	
	 EPackage model1 = getModel(pathScenario2 + "models/V1.ecore");
	 EPackage model2 = getModel(pathScenario2 + "models/V2.ecore");
	 EObject input = getInstance(pathScenario2 + "instances/input/V2a.xmi");
	 System.out.println(PrettyPrinter.printModel(model1));
	 System.out.println(PrettyPrinter.printModel(model2));
	 System.out.println("Input " + PrettyPrinter.printInstance(input));
	
	 AbstractTask task = taskFactory.createTask(TaskInfo.TASK_2_M2_M1_M2, model1,
	 model2);
	
	 EObject migrated = task.migrate(input);
	 EObject migratedBack = task.migrateBack(migrated);
	 System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
	 System.out.println("Migrated Back " +
	 PrettyPrinter.printInstance(migratedBack));
	
	 EObject expout = getInstance(pathScenario2 + "instances/expout/V2a.xmi");
	 System.out.println("Expected " + PrettyPrinter.printInstance(expout));
	
	 assertTrue(isEqual(migratedBack, expout));
	 }
	
	 @Test
	 public void task_2_M2_M1_M2_b() throws IOException {
	 System.out.println("\n= Task 2 M2->M1->M2 (b)");
	
	 EPackage model1 = getModel(pathScenario2 + "models/V1.ecore");
	 EPackage model2 = getModel(pathScenario2 + "models/V2.ecore");
	 EObject input = getInstance(pathScenario2 + "instances/input/V2b.xmi");
	 System.out.println(PrettyPrinter.printModel(model1));
	 System.out.println(PrettyPrinter.printModel(model2));
	 System.out.println("Input " + PrettyPrinter.printInstance(input));
	
	 AbstractTask task = taskFactory.createTask(TaskInfo.TASK_2_M2_M1_M2, model1,
	 model2);
	
	 EObject migrated = task.migrate(input);
	 EObject migratedBack = task.migrateBack(migrated);
	 System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
	 System.out.println("Migrated Back " +
	 PrettyPrinter.printInstance(migratedBack));
	
	 EObject expout = getInstance(pathScenario2 + "instances/expout/V2b.xmi");
	 System.out.println("Expected " + PrettyPrinter.printInstance(expout));
	
	 assertTrue(isEqual(migratedBack, expout));
	 }

	@Test
	public void task_3_M1_M2_M1_a() throws IOException {
		System.out.println("\n= Task 3 M1->M2->M1 (a)");

		EPackage model1 = getModel(pathScenario3 + "models/V1.ecore");
		EPackage model2 = getModel(pathScenario3 + "models/V2.ecore");
		EObject input = getInstance(pathScenario3 + "instances/input/V1a.xmi");
		System.out.println(PrettyPrinter.printModel(model1));
		System.out.println(PrettyPrinter.printModel(model2));
		System.out.println("Input " + PrettyPrinter.printInstance(input));

		AbstractTask task = taskFactory.createTask(TaskInfo.TASK_3_M1_M2_M1, model1, model2);

		EObject migrated = task.migrate(input);
		EObject migratedBack = task.migrateBack(migrated);
		System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
		System.out.println("Migrated Back " + PrettyPrinter.printInstance(migratedBack));

		EObject expout = getInstance(pathScenario3 + "instances/expout/V1a.xmi");
		System.out.println("Expected " + PrettyPrinter.printInstance(expout));

		assertTrue(isEqual(migratedBack, expout));
	}

	@Test
	public void task_3_M1_M2_M1_b() throws IOException {
		System.out.println("\n= Task 3 M1->M2->M1 (b)");

		EPackage model1 = getModel(pathScenario3 + "models/V1.ecore");
		EPackage model2 = getModel(pathScenario3 + "models/V2.ecore");
		EObject input = getInstance(pathScenario3 + "instances/input/V1b.xmi");
		System.out.println(PrettyPrinter.printModel(model1));
		System.out.println(PrettyPrinter.printModel(model2));
		System.out.println("Input " + PrettyPrinter.printInstance(input));

		AbstractTask task = taskFactory.createTask(TaskInfo.TASK_3_M1_M2_M1, model1, model2);

		// Migrate
		EObject migrated = task.migrate(input);
		System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));

		// Modify
		migrated.eSet(migrated.eClass().getEStructuralFeature("name"), null);
		System.out.println("Modified " + PrettyPrinter.printInstance(migrated));

		// Migrate Back
		EObject migratedBack = task.migrateBack(migrated);
		System.out.println("Migrated Back " + PrettyPrinter.printInstance(migratedBack));

		EObject expout = getInstance(pathScenario3 + "instances/expout/V1b.xmi");
		System.out.println("Expected " + PrettyPrinter.printInstance(expout));

		assertTrue(isEqual(migratedBack, expout));
	}

	@Test
	public void task_3_M2_M1_M2_a() throws IOException {
		System.out.println("\n= Task 3 M2->M1->M2 (a)");

		EPackage model1 = getModel(pathScenario3 + "models/V1.ecore");
		EPackage model2 = getModel(pathScenario3 + "models/V2.ecore");
		EObject input = getInstance(pathScenario3 + "instances/input/V2a.xmi");
		System.out.println(PrettyPrinter.printModel(model1));
		System.out.println(PrettyPrinter.printModel(model2));
		System.out.println("Input " + PrettyPrinter.printInstance(input));

		AbstractTask task = taskFactory.createTask(TaskInfo.TASK_3_M2_M1_M2, model1, model2);

		EObject migrated = task.migrate(input);
		EObject migratedBack = task.migrateBack(migrated);
		System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));
		System.out.println("Migrated Back " + PrettyPrinter.printInstance(migratedBack));

		EObject expout = getInstance(pathScenario3 + "instances/expout/V2a.xmi");
		System.out.println("Expected " + PrettyPrinter.printInstance(expout));

		assertTrue(isEqual(migratedBack, expout));
	}

	@Test
	public void task_3_M2_M1_M2_b() throws IOException {
		System.out.println("\n= Task 3 M2->M1->M2 (b)");

		EPackage model1 = getModel(pathScenario3 + "models/V1.ecore");
		EPackage model2 = getModel(pathScenario3 + "models/V2.ecore");
		EObject input = getInstance(pathScenario3 + "instances/input/V2b.xmi");
		System.out.println(PrettyPrinter.printModel(model1));
		System.out.println(PrettyPrinter.printModel(model2));
		System.out.println("Input " + PrettyPrinter.printInstance(input));

		AbstractTask task = taskFactory.createTask(TaskInfo.TASK_3_M2_M1_M2, model1, model2);

		// Migrate
		EObject migrated = task.migrate(input);
		System.out.println("Migrated " + PrettyPrinter.printInstance(migrated));

		// Modify
		migrated.eSet(migrated.eClass().getEStructuralFeature("name"), "");
		System.out.println("Modified " + PrettyPrinter.printInstance(migrated));

		// Migrate back
		EObject migratedBack = task.migrateBack(migrated);
		System.out.println("Migrated Back " + PrettyPrinter.printInstance(migratedBack));

		EObject expout = getInstance(pathScenario3 + "instances/expout/V2b.xmi");
		System.out.println("Expected " + PrettyPrinter.printInstance(expout));

		assertTrue(isEqual(migratedBack, expout));
	}

	@Test
	public void task_4_M1_M2_M1_a() throws IOException {
		System.out.println("\n= Task 4 M1->M2->M1 (a)");

		EPackage model1 = getModel(pathScenario4 + "models/V1.ecore");
		EPackage model2 = getModel(pathScenario4 + "models/V2.ecore");
		EObject input = getInstance(pathScenario4 + "instances/input/V1a.xmi");
		System.out.println(PrettyPrinter.printModel(model1));
		System.out.println(PrettyPrinter.printModel(model2));
		System.out.println("Input " + PrettyPrinter.printContainer(input));

		AbstractTask task = taskFactory.createTask(TaskInfo.TASK_4_M1_M2_M1, model1, model2);

		EObject migrated = task.migrate(input);
		System.out.println("Migrated " + PrettyPrinter.printContainer(migrated));

		EObject migratedBack = task.migrateBack(migrated);
		System.out.println("Migrated Back " + PrettyPrinter.printContainer(migratedBack));

		EObject expout = getInstance(pathScenario4 + "instances/expout/V1a.xmi");
		System.out.println("Expected " + PrettyPrinter.printContainer(expout));

		assertTrue(isEqual(migratedBack, expout));
	}

	@Test
	public void task_4_M1_M2_M1_b() throws IOException {
		System.out.println("\n= Task 4 M1->M2->M1 (b)");

		EPackage model1 = getModel(pathScenario4 + "models/V1.ecore");
		EPackage model2 = getModel(pathScenario4 + "models/V2.ecore");
		EObject input = getInstance(pathScenario4 + "instances/input/V1b.xmi");
		System.out.println(PrettyPrinter.printModel(model1));
		System.out.println(PrettyPrinter.printModel(model2));
		System.out.println("Input " + PrettyPrinter.printContainer(input));

		AbstractTask task = taskFactory.createTask(TaskInfo.TASK_4_M1_M2_M1, model1, model2);

		EObject migrated = task.migrate(input);
		System.out.println("Migrated " + PrettyPrinter.printContainer(migrated));

		EObject migratedBack = task.migrateBack(migrated);
		System.out.println("Migrated Back " + PrettyPrinter.printContainer(migratedBack));

		EObject expout = getInstance(pathScenario4 + "instances/expout/V1b.xmi");
		System.out.println("Expected " + PrettyPrinter.printContainer(expout));

		assertTrue(isEqual(migratedBack, expout));
	}

	@Test
	public void task_4_M2_M1_M2_a() throws IOException {
		System.out.println("\n= Task 4 M2->M1->M2 (a)");

		EPackage model1 = getModel(pathScenario4 + "models/V1.ecore");
		EPackage model2 = getModel(pathScenario4 + "models/V2.ecore");
		EObject input = getInstance(pathScenario4 + "instances/input/V2a.xmi");
		System.out.println(PrettyPrinter.printModel(model1));
		System.out.println(PrettyPrinter.printModel(model2));
		System.out.println("Input " + PrettyPrinter.printContainer(input));

		AbstractTask task = taskFactory.createTask(TaskInfo.TASK_4_M2_M1_M2, model1, model2);

		// Migrate
		EObject migrated = task.migrate(input);
		System.out.println("Migrated " + PrettyPrinter.printContainer(migrated));

		// Migrate back
		EObject migratedBack = task.migrateBack(migrated);
		System.out.println("Migrated Back " + PrettyPrinter.printContainer(migratedBack));

		EObject expout = getInstance(pathScenario4 + "instances/expout/V2a.xmi");
		System.out.println("Expected " + PrettyPrinter.printContainer(expout));

		assertTrue(isEqual(migratedBack, expout));
	}

	@Test
	public void task_4_M2_M1_M2_b() throws IOException {
		System.out.println("\n= Task 4 M2->M1->M2 (b)");

		EPackage model1 = getModel(pathScenario4 + "models/V1.ecore");
		EPackage model2 = getModel(pathScenario4 + "models/V2.ecore");
		EObject input = getInstance(pathScenario4 + "instances/input/V2b.xmi");
		System.out.println(PrettyPrinter.printModel(model1));
		System.out.println(PrettyPrinter.printModel(model2));
		System.out.println("Input " + PrettyPrinter.printContainer(input));

		AbstractTask task = taskFactory.createTask(TaskInfo.TASK_4_M2_M1_M2, model1, model2);

		// Migrate
		EObject migrated = task.migrate(input);
		System.out.println("Migrated " + PrettyPrinter.printContainer(migrated));

		// Migrate back
		EObject migratedBack = task.migrateBack(migrated);
		System.out.println("Migrated Back " + PrettyPrinter.printContainer(migratedBack));

		EObject expout = getInstance(pathScenario4 + "instances/expout/V2b.xmi");
		System.out.println("Expected " + PrettyPrinter.printContainer(expout));

		assertTrue(isEqual(migratedBack, expout));
	}

	private EPackage getModel(String path) throws IOException {
		Resource resource = resSet.getResource(URI.createURI(path), true);
		resource.load(Collections.EMPTY_MAP);
		EPackage model = (EPackage) resource.getContents().get(0);
		resSet.getPackageRegistry().put(model.getNsURI(), model);
		return model;
	}

	private EObject getInstance(String path) throws IOException {
		Resource resource = resSet.getResource(URI.createURI(path), true);
		resource.load(Collections.EMPTY_MAP);
		return resource.getContents().get(0);
	}

	private boolean isEqual(EObject out, EObject expout) {
		// Configure EMF Compare
		IEObjectMatcher matcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.NEVER);
		IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
		IMatchEngine.Factory matchEngineFactory = new MatchEngineFactoryImpl(matcher, comparisonFactory);
		matchEngineFactory.setRanking(20);
		IMatchEngine.Factory.Registry matchEngineRegistry = new MatchEngineFactoryRegistryImpl();
		matchEngineRegistry.add(matchEngineFactory);
		EMFCompare comparator = EMFCompare.builder().setMatchEngineFactoryRegistry(matchEngineRegistry).build();

		// Perform comparison
		IComparisonScope scope = EMFCompare.createDefaultScope(out, expout);
		Comparison comparison = comparator.compare(scope);

		// Equal if diff list is empty
		return comparison.getDifferences().isEmpty();
	}
}
