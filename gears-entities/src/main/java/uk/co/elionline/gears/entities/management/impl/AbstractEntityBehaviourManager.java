package uk.co.elionline.gears.entities.management.impl;

import java.util.Collection;
import java.util.UUID;

import uk.co.elionline.gears.entities.behaviour.BehaviourComponent;
import uk.co.elionline.gears.entities.management.EntityBehaviourManager;

public abstract class AbstractEntityBehaviourManager implements
		EntityBehaviourManager {
	@Override
	public final boolean attachAll(UUID entity,
			Collection<BehaviourComponent> behaviourComponents) {
		boolean changed = false;

		for (BehaviourComponent behaviourComponent : behaviourComponents) {
			changed = changed || attach(entity, behaviourComponent);
		}

		return changed;
	}

	@Override
	public final boolean has(UUID entity, BehaviourComponent behaviourComponent) {
		return isUniversal(behaviourComponent)
				|| hasExplicitly(entity, behaviourComponent);
	}
}
