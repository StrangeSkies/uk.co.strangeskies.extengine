package uk.co.strangeskies.gears.entity.processing.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

import uk.co.strangeskies.gears.entity.management.EntityManager;
import uk.co.strangeskies.gears.entity.processing.Processor;
import uk.co.strangeskies.gears.entity.scheduling.Scheduler;
import uk.co.strangeskies.gears.entity.state.StateComponent;
import uk.co.strangeskies.gears.utilities.flowcontrol.HashingStripedReadWriteLock;
import uk.co.strangeskies.gears.utilities.flowcontrol.StripedReadWriteLock;

@Component(service = { Processor.class })
public class ProcessorImpl implements Processor {
	private final StripedReadWriteLock<StateComponent<?>> locks;
	private final Set<Scheduler> schedulers;

	public ProcessorImpl() {
		this.locks = new HashingStripedReadWriteLock<>();
		schedulers = new HashSet<>();
	}

	@Override
	public boolean startProcessing(EntityManager entityManager) {
		return startProcessing(entityManager, entityManager.behaviour()
				.getAllSchedulers());
	}

	@Override
	public boolean startProcessing(final EntityManager entityManager,
			Collection<Scheduler> schedulers) {
		synchronized (this.schedulers) {
			if (!this.schedulers.isEmpty()) {
				return false;
			}

			boolean addedProcesses = this.schedulers.addAll(schedulers);

			for (final Scheduler scheduler : this.schedulers) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						scheduler.process(new ScheduleProcessingContextImpl(scheduler,
								entityManager, locks));
					}
				}).start();
			}

			return addedProcesses;
		}
	}

	@Override
	public boolean stopProcessing() {
		synchronized (this.schedulers) {
			if (schedulers.isEmpty()) {
				return false;
			}

			for (final Scheduler scheduler : schedulers) {
				scheduler.stopProcessing();
			}
			schedulers.clear();

			return true;
		}
	}
}