package de.hub.mse.ttc2020.solution.translationlayer;

import java.util.Calendar;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

public class Task2TranslationLayer {
	// EMF packages for each of the model versions
	EPackage model1;
	EPackage model2;
	
	public Task2TranslationLayer(EPackage model1, EPackage model2) {
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

		// apply transformation for age <-> ybirth
		int age = Integer.parseInt(instance.eGet(class1.getEStructuralFeature("age")).toString());
		instance2.eSet(class2.getEStructuralFeature("ybirth"), ageToYbirth(age));

		return instance2;
	}
	
	// M2 -> M1
	public EObject migrateBack(EObject instance) {
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
	
	static int ybirthToAge(int ybirth) {
		return Calendar.getInstance().get(Calendar.YEAR) - ybirth;
	}
	
	static int ageToYbirth(int age) {
		return Calendar.getInstance().get(Calendar.YEAR) - age;
	}
}
