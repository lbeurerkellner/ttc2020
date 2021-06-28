package de.hub.mse.ttc2020.solution.translationlayer;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

public class Task1TranslationLayer {
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
	
	public void clearTraces() {
		this.traces.clear();
	}
	
	// EMF packages for each of the model versions
	EPackage model1;
	EPackage model2;
	
	public Task1TranslationLayer(EPackage model1, EPackage model2) {
		this.model1 = model1;
		this.model2 = model2;
	}
	
	// M1 -> M2
	public EObject migrate(EObject instance) {
		// create M2 instances
		EClass class1 = instance.eClass();
		EClass class2 = (EClass) model2.getEClassifier("Person");
		EObject instance2 = model2.getEFactoryInstance().create(class2);
		
		// transfer name field
		instance2.eSet(class2.getEStructuralFeature("name"), instance.eGet(class1.getEStructuralFeature("name")));
		
		// check for trace for age field
		EObject trace = getTrace(instance);
		if (null != trace) {
			// restore from trace
			instance2.eSet(class2.getEStructuralFeature("age"), trace.eGet(class2.getEStructuralFeature("age")));
		} else {
			// otherwise use default value
			instance2.eSet(class2.getEStructuralFeature("age"), -1);
		}

		return instance2;
	}
	
	// M2 -> M1
	public EObject migrateBack(EObject instance) {
		// create M1 instances
		EClass class1 = (EClass) model1.getEClassifier("Person");
		EClass class2 = instance.eClass();
		EObject instance1 = model1.getEFactoryInstance().create(class1);
		
		// transfer name field
		instance1.eSet(class1.getEStructuralFeature("name"), instance.eGet(class2.getEStructuralFeature("name")));
		// store trace for age field
		captureTrace(instance1, instance);
		
		return instance1;
	}
}
