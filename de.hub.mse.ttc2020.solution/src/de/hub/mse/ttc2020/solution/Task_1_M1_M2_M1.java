package de.hub.mse.ttc2020.solution;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import de.hub.mse.ttc2020.benchmark.AbstractTask;
import de.hub.mse.ttc2020.solution.translationlayer.Task1TranslationLayer;

public class Task_1_M1_M2_M1 extends AbstractTask {
	Task1TranslationLayer translationLayer;
	
	public Task_1_M1_M2_M1(EPackage model1, EPackage model2) {
		super(model1, model2);
		
		this.translationLayer = new Task1TranslationLayer(model1, model2);
	}

	@Override
	public EObject migrate(EObject instance) {
		translationLayer.clearTraces();
		return translationLayer.migrate(instance);
	}

	@Override
	public EObject migrateBack(EObject instance) {
		return translationLayer.migrateBack(instance);
	}

}
