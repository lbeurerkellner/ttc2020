package de.hub.mse.ttc2020.solution.translationlayer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

public class Task4TranslationLayer {
	// keep track of previous versions for traceability 
	Map<EObject, EObject> traces = new HashMap<>();
	
	EObject getTrace(EObject o) {
		if (traces.containsKey(o)) {
			return traces.get(o);
		}
		return null;
	}
	
	void captureTrace(EObject o, EObject trace) {
		this.traces.put(o, trace);
	}
	
	// EMF packages for each of the model versions
	EPackage model1;
	EPackage model2;
	
	public Task4TranslationLayer(EPackage model1, EPackage model2) {
		this.model1 = model1;
		this.model2 = model2;
	}
	
	// M1 -> M2
	public EObject migrate(EObject container) {
		EObject dog = getDogInContainer(container);
		EObject dog2 = migrateDog(dog);
		return buildContainer2(dog2);
	}
	
	// M2 -> M1
	public EObject migrateBack(EObject container) {
		EObject dog = getDogInContainer(container);
		EObject dog1 = migrateBackDog(dog);
		return buildContainer1(dog1);
	}
	
	// M1 -> M2
	public EObject migratePerson(EObject instance) {
		// create M2 instances
		EClass class1 = instance.eClass();
		EClass class2 = (EClass) model2.getEClassifier("Person");
		EObject instance2 = model2.getEFactoryInstance().create(class2);
		
		// transfer name field
		instance2.eSet(class2.getEStructuralFeature("name"), instance.eGet(class1.getEStructuralFeature("name")));

		// apply transformation for age <-> ybirth
		int age = Integer.parseInt(instance.eGet(class1.getEStructuralFeature("age")).toString());
		instance2.eSet(class2.getEStructuralFeature("ybirth"), ageToYbirth(age));

		return instance2;
	}
	
	// M2 -> M1
	public EObject migrateBackPerson(EObject instance) {
		// create M2 instances
		EClass class1 = (EClass) model1.getEClassifier("Person");
		EClass class2 = instance.eClass();
		EObject instance1 = model1.getEFactoryInstance().create(class1);
		
		// transfer name field
		instance1.eSet(class1.getEStructuralFeature("name"), instance.eGet(class2.getEStructuralFeature("name")));
	
		// apply transformation for age <-> ybirth
		int ybirth = Integer.parseInt(instance.eGet(class2.getEStructuralFeature("ybirth")).toString());
		instance1.eSet(class1.getEStructuralFeature("age"), ybirthToAge(ybirth));
		
		return instance1;
	}
	
	// M1 -> M2
	public EObject migrateDog(EObject instance) {
		// create M1 instances
		EClass class1 = instance.eClass();
		EClass class2 = (EClass) model2.getEClassifier("Dog");
		EObject instance2 = model2.getEFactoryInstance().create(class2);
		
		// transfer name field
		instance2.eSet(class2.getEStructuralFeature("name"), instance.eGet(class1.getEStructuralFeature("name")));
		// store trace for age field
		captureTrace(instance2, instance);
		
		// delegate migration of owner
		EObject owner = (EObject) instance.eGet(class1.getEStructuralFeature("owner"));
		EObject owner2 = migratePerson(owner);
		instance2.eSet(class2.getEStructuralFeature("owner"), owner2);
		
		return instance2;
	}
	
	// M2 -> M1
	public EObject migrateBackDog(EObject instance) {
		// create M2 instances
		EClass class2 = instance.eClass();
		EClass class1 = (EClass) model1.getEClassifier("Dog");
		EObject instance1 = model1.getEFactoryInstance().create(class1);
		
		// transfer name field
		instance1.eSet(class1.getEStructuralFeature("name"), instance.eGet(class2.getEStructuralFeature("name")));
		
		// check for trace for age field
		EObject trace = getTrace(instance);
		if (null != trace) {
			// restore from trace
			instance1.eSet(class1.getEStructuralFeature("age"), trace.eGet(class1.getEStructuralFeature("age")));
		} else {
			// otherwise use default value
			instance1.eSet(class1.getEStructuralFeature("age"), -1);
		}
		
		// delegate migration of owner
		EObject owner = (EObject) instance.eGet(class2.getEStructuralFeature("owner"));
		EObject owner1 = migrateBackPerson(owner);
		instance1.eSet(class1.getEStructuralFeature("owner"), owner1);

		return instance1;
	}
	
	static int ybirthToAge(int ybirth) {
		return Calendar.getInstance().get(Calendar.YEAR) - ybirth;
	}
	
	static int ageToYbirth(int age) {
		return Calendar.getInstance().get(Calendar.YEAR) - age;
	}
	
	
	// boilerplate to construct and deconstruct Container instances 
	EObject getDogInContainer(EObject container) {
		EClass containerClass = container.eClass();
		EObject dog = (EObject) container.eGet(containerClass.getEStructuralFeature("dog"));
		return dog;
	}
	
	EObject buildContainer1(EObject dog) {
		EClass containerClass1 = (EClass) model1.getEClassifier("Container");
		EClass dogClass = dog.eClass();
		
		EObject container1 = model1.getEFactoryInstance().create(containerClass1);
		container1.eSet(containerClass1.getEStructuralFeature("person"), dog.eGet(dogClass.getEStructuralFeature("owner")));
		container1.eSet(containerClass1.getEStructuralFeature("dog"), dog);
		return container1;
	}
	
	EObject buildContainer2(EObject dog) {
		EClass containerClass2 = (EClass) model2.getEClassifier("Container");
		EClass dogClass = dog.eClass();
		
		EObject container2 = model2.getEFactoryInstance().create(containerClass2);
		container2.eSet(containerClass2.getEStructuralFeature("person"), dog.eGet(dogClass.getEStructuralFeature("owner")));
		container2.eSet(containerClass2.getEStructuralFeature("dog"), dog);
		System.out.println(container2);
		return container2;
	}
}
