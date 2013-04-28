package uk.co.elionline.gears.entities.management;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import uk.co.elionline.gears.entities.behaviour.BehaviourComponent;
import uk.co.elionline.gears.entities.scheduling.Scheduler;
import uk.co.elionline.gears.utilities.collections.SetMultiMap;

public interface EntityBehaviourManager {
	public boolean attach(UUID entity, BehaviourComponent behaviourComponent);

	public boolean attachAll(UUID entity,
			Collection<BehaviourComponent> behaviourComponents);

	public boolean detach(UUID entity, BehaviourComponent behaviourComponent);

	public void detachAll(UUID entity);

	public boolean has(UUID entity, BehaviourComponent behaviourComponent);

	public boolean hasExplicitly(UUID entity,
			BehaviourComponent behaviourComponent);

	/**
	 * Get the set of all entities which currently have the given behaviour
	 * attached (either explicitly or universally).
	 * 
	 * @param behaviourComponent
	 *          The behaviour component for which we want to find all applicable
	 *          entities.
	 * @return The set of entities which are attached to this behaviour.
	 */
	public Set<UUID> getEntitiesExplicitlyWith(
			BehaviourComponent behaviourComponent);

	public boolean addUniversal(BehaviourComponent behaviourComponent);

	public boolean removeUniversal(BehaviourComponent behaviourComponent);

	public Set<BehaviourComponent> getUniversal();

	public boolean isUniversal(BehaviourComponent behaviourComponent);

	public Set<BehaviourComponent> getAll();

	public Set<BehaviourComponent> getDependencies(BehaviourComponent behaviour);

	public Set<BehaviourComponent> getDependents(BehaviourComponent behaviour);

	public SetMultiMap<BehaviourComponent, BehaviourComponent> getDependencies();

	public SetMultiMap<BehaviourComponent, BehaviourComponent> getDependents();

	public void setDefaultScheduler(Scheduler scheduler);

	public void setBehaviourScheduler(Scheduler scheduler,
			BehaviourComponent behaviourComponent);

	public void setBehaviourSchedulerToDefault(
			BehaviourComponent behaviourComponent);

	public Set<BehaviourComponent> getBehavioursForScheduler(Scheduler scheduler);

	public Set<Scheduler> getAllSchedulers();
}
