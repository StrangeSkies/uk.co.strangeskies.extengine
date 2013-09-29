package uk.co.elionline.gears.entity.components.rendering.impl;

import uk.co.elionline.gears.entity.behaviour.BehaviourComponent;
import uk.co.elionline.gears.entity.behaviour.BehaviourComponentBuilder;
import uk.co.elionline.gears.entity.behaviour.BehaviourProcess;
import uk.co.elionline.gears.entity.behaviour.BehaviourProcessingContext;
import uk.co.elionline.gears.entity.state.StateComponent;
import uk.co.elionline.gears.entity.state.StateComponentBuilder;
import uk.co.elionline.gears.rendering.buffering.SceneBuffer;
import uk.co.elionline.gears.rendering.buffering.SceneInterpolator;
import uk.co.elionline.gears.rendering.buffering.impl.SceneBufferImpl;
import uk.co.elionline.gears.rendering.buffering.impl.SceneInterpolatorImpl;
import uk.co.elionline.gears.utilities.Factory;

public class SceneBufferingComponents {
	private final StateComponent<SceneBuffer> sceneBufferState;

	private final StateComponent<SceneInterpolator> interpolatableSceneBufferState;

	private final BehaviourComponent bufferingBehaviour;

	private final BehaviourComponentBuilder behaviourComponentBuilder;

	public SceneBufferingComponents(
			BehaviourComponentBuilder behaviourComponentBuilder,
			StateComponentBuilder stateComponentBuilder) {
		sceneBufferState = stateComponentBuilder.data(new Factory<SceneBuffer>() {
			@Override
			public SceneBuffer create() {
				return new SceneBufferImpl();
			}
		}).name("Renderable in 2D")
				.description("Data for 2D rendering of entities").create();

		interpolatableSceneBufferState = stateComponentBuilder
				.data(new Factory<SceneInterpolator>() {
					@Override
					public SceneInterpolator create() {
						return new SceneInterpolatorImpl();
					}
				}).name("Buffer for interpolatable rendering data")
				.description("Data for 2D rendering of entities").create();

		bufferingBehaviour = behaviourComponentBuilder
				.process(new BehaviourProcess() {
					@Override
					public void process(BehaviourProcessingContext context) {
						for (SceneBuffer buffer : context.entities().state()
								.getAllData(sceneBufferState)) {
							buffer.push();
						}
						for (SceneBuffer buffer : context.entities().state()
								.getAllData(interpolatableSceneBufferState)) {
							buffer.push();
						}
					}
				})
				.name("Buffering")
				.description("Behaviour for buffering renderable data")
				.indirectWriteDependencies(sceneBufferState,
						interpolatableSceneBufferState).create();

		this.behaviourComponentBuilder = behaviourComponentBuilder;
	}

	public StateComponent<SceneBuffer> getSceneBufferState() {
		return sceneBufferState;
	}

	public StateComponent<SceneInterpolator> getInterpolatableSceneBufferState() {
		return interpolatableSceneBufferState;
	}

	public BehaviourComponent getBufferingBehaviour() {
		return bufferingBehaviour;
	}

	public BehaviourComponent getBufferingBehaviour(final SceneBuffer buffer) {
		return behaviourComponentBuilder
				.process(new BehaviourProcess() {
					@Override
					public void process(BehaviourProcessingContext context) {
						buffer.push();
					}
				})
				.name("Buffering Single")
				.description(
						"Behaviour for buffering renderable data prepared using a specific SceneBuffer")
				.create();
	}
}