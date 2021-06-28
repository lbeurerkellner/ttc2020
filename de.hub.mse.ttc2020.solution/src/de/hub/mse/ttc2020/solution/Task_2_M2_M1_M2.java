package de.hub.mse.ttc2020.solution;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import de.hub.mse.ttc2020.benchmark.AbstractTask;
import de.hub.mse.ttc2020.solution.translationlayer.Task2TranslationLayer;

public class Task_2_M2_M1_M2 extends AbstractTask {

	private Task2TranslationLayer translationLayer;
	
	public Task_2_M2_M1_M2(EPackage model1, EPackage model2) {
		super(model1, model2);
		
		this.translationLayer = new Task2TranslationLayer(model1, model2);
	}

	@Override
	public EObject migrate(EObject instance) {
		return translationLayer.migrateBack(instance);
	}

	@Override
	public EObject migrateBack(EObject instance) {
		return translationLayer.migrate(instance);
	}

}
