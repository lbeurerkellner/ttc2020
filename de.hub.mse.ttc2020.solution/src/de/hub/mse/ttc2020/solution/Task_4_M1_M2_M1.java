package de.hub.mse.ttc2020.solution;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import de.hub.mse.ttc2020.benchmark.AbstractTask;
import de.hub.mse.ttc2020.solution.translationlayer.Task4TranslationLayer;

public class Task_4_M1_M2_M1 extends AbstractTask {

	private Task4TranslationLayer translationLayer;

	public Task_4_M1_M2_M1(EPackage model1, EPackage model2) {
		super(model1, model2);
		
		this.translationLayer = new Task4TranslationLayer(model1, model2);
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
