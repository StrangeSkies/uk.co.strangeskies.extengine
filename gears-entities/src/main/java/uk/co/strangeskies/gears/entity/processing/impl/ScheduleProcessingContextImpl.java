package uk.co.strangeskies.gears.entity.processing.impl;

import java.util.Set;

import uk.co.strangeskies.gears.entity.behaviour.BehaviourComponent;
import uk.co.strangeskies.gears.entity.management.EntityManager;
import uk.co.strangeskies.gears.entity.scheduling.ScheduleProcessingContext;
import uk.co.strangeskies.gears.entity.scheduling.Scheduler;
import uk.co.strangeskies.gears.entity.state.StateComponent;
import uk.co.strangeskies.gears.utilities.flowcontrol.LimitedReadWriteLockReleaseWrapper;
import uk.co.strangeskies.gears.utilities.flowcontrol.StripedReadWriteLock;

public class ScheduleProcessingContextImpl implements ScheduleProcessingContext {
	private final EntityManager entityManager;
	private final StripedReadWriteLock<StateComponent<?>> locks;
	private final Set<BehaviourComponent> behaviours;

	public ScheduleProcessingContextImpl(Scheduler scheduler,
			EntityManager entityManager, StripedReadWriteLock<StateComponent<?>> locks) {
		this.entityManager = entityManager;
		this.locks = locks;
		behaviours = entityManager.behaviour().getBehavioursForScheduler(scheduler);
	}

	@Override
	public void processBehaviour(final BehaviourComponent behaviour)
			throws InterruptedException {
		LimitedReadWriteLockReleaseWrapper<StateComponent<?>> locks = new LimitedReadWriteLockReleaseWrapper<>(
				this.locks, behaviour.getIndirectStateReadDependencies(),
				behaviour.getIndirectStateWriteDependencies());
		locks.obtain();
		behaviour.getProcess().process(
				new BehaviourProcessingContextImpl(behaviour, entityManager, locks));
		locks.release();
	}

	@Override
	public Set<BehaviourComponent> getBehaviours() {
		return behaviours;
	}
}