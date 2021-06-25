package de.hub.mse.ttc2020.solution.translationlayer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

public class Task3TranslationLayer {
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
	
	public Task3TranslationLayer(EPackage model1, EPackage model2) {
		this.model1 = model1;
		this.model2 = model2;
	}
	
	// M1 -> M2
	public EObject migrate(EObject instance) {
		// create M2 instances
		EClass class1 = instance.eClass();
		EClass class2 = (EClass) model2.getEClassifier("Person");
		EObject instance2 = model2.getEFactoryInstance().create(class2);
		
		Object name = instance.eGet(class1.getEStructuralFeature("name"));
		
		// special handling if a potential default value is detected
		if (name.toString().isEmpty()) {
			EObject trace = getTrace(instance);
			if (null != trace) {
				// check trace for null and assume default value if so
				Object migratedName = trace.eGet(class2.getEStructuralFeature("name"));
				if (null == migratedName) migratedName = "";
				
				// check whether name has been modified
				if (name.equals(migratedName)) {
					// restore null value if not modified
					instance2.eSet(class2.getEStructuralFeature("name"), null);
				} else {
					// use empty string if set explicitly (not just the default value)
					instance2.eSet(class2.getEStructuralFeature("name"), "");
				}
			} else {
				instance2.eSet(class2.getEStructuralFeature("name"), name);
			}
		} else {
			instance2.eSet(class2.getEStructuralFeature("name"), name);
		}

		// transfer age
		int age = Integer.parseInt(instance.eGet(class1.getEStructuralFeature("age")).toString());
		instance2.eSet(class2.getEStructuralFeature("age"), age);

		return instance2;
	}
	
	// M2 -> M1
	public EObject migrateBack(EObject instance) {
		// create M2 instances
		EClass class1 = (EClass) model1.getEClassifier("Person");
		EClass class2 = instance.eClass();
		EObject instance1 = model1.getEFactoryInstance().create(class1);
		
		captureTrace(instance1, instance);
		
		// transfer name field
		Object name = instance.eGet(class2.getEStructuralFeature("name"));
		if (null == name) {
			// use default value to replace null
			instance1.eSet(class1.getEStructuralFeature("name"), "");
		} else {
			instance1.eSet(class1.getEStructuralFeature("name"), name);
		}
	
		// transfer age
		int age = Integer.parseInt(instance.eGet(class2.getEStructuralFeature("age")).toString());
		instance1.eSet(class1.getEStructuralFeature("age"), age);
		
		return instance1;
	}
	
	static int ybirthToAge(int ybirth) {
		return Calendar.getInstance().get(Calendar.YEAR) - ybirth;
	}
	
	static int ageToYbirth(int age) {
		return Calendar.getInstance().get(Calendar.YEAR) - age;
	}
}
