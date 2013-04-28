package uk.co.elionline.gears.entities.assemblages;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.co.elionline.gears.entities.management.EntityManager;

public class EntityAssemblageCollection {
	private final List<EntityAssemblage> entityAssemblages;

	// private final Map<EntityAssemblage, ?> map; // TODO wat

	public EntityAssemblageCollection() {
		entityAssemblages = new ArrayList<>();
	}

	public void assembleInto(EntityManager entityManager) {
		for (EntityAssemblage entityAssemblage : entityAssemblages) {
			UUID entity = entityManager.create();

			entityAssemblage.assembleOnto(entity, entityManager);
		}
	}
}
