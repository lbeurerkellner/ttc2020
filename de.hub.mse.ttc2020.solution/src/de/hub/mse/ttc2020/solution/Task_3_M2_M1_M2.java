package de.hub.mse.ttc2020.solution;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import de.hub.mse.ttc2020.benchmark.AbstractTask;
import de.hub.mse.ttc2020.solution.translationlayer.Task3TranslationLayer;

public class Task_3_M2_M1_M2 extends AbstractTask {

	private Task3TranslationLayer migrationService;

	public Task_3_M2_M1_M2(EPackage model1, EPackage model2) {
		super(model1, model2);
		
		this.migrationService = new Task3TranslationLayer(model1, model2);
	}

	@Override
	public EObject migrate(EObject instance) {
		return migrationService.migrateBack(instance);
	}

	@Override
	public EObject migrateBack(EObject instance) {
		return migrationService.migrate(instance);
	}

}
