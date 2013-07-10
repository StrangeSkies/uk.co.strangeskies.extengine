package uk.co.elionline.gears.entity.components.rendering.impl;

import uk.co.elionline.gears.entity.Entity;
import uk.co.elionline.gears.entity.behaviour.BehaviourComponent;
import uk.co.elionline.gears.entity.behaviour.BehaviourComponentBuilder;
import uk.co.elionline.gears.entity.behaviour.BehaviourProcess;
import uk.co.elionline.gears.entity.behaviour.BehaviourProcessingContext;
import uk.co.elionline.gears.entity.state.StateComponent;
import uk.co.elionline.gears.entity.state.StateComponentBuilder;
import uk.co.elionline.gears.rendering.Renderable;
import uk.co.elionline.gears.rendering.Scene;
import uk.co.elionline.gears.rendering.rendering2d.Camera2D;
import uk.co.elionline.gears.rendering.rendering2d.Data2D;
import uk.co.elionline.gears.rendering.rendering2d.Renderer2D;
import uk.co.elionline.gears.rendering.rendering2d.SceneFactory2D;
import uk.co.elionline.gears.utilities.Factory;

public class RenderingComponents2D {
	private final StateComponent<Renderable<Data2D>> renderableState;

	private final BehaviourComponent renderingBehaviour;

	private final StateComponent<Camera2D> cameraState;

	private final StateComponent<Scene<Data2D>> sceneState;

	public RenderingComponents2D(final Renderer2D renderer,
			final SceneFactory2D sceneFactory,
			BehaviourComponentBuilder behaviourComponentBuilder,
			StateComponentBuilder stateComponentBuilder) {
		renderableState = stateComponentBuilder
				.data(new Factory<Renderable<Data2D>>() {
					@Override
					public Renderable<Data2D> create() {
						return sceneFactory.createRenderable();
					}
				}).name("Renderable in 2D")
				.description("Data for 2D rendering of entities").create();

		cameraState = stateComponentBuilder.data(new Factory<Camera2D>() {
			@Override
			public Camera2D create() {
				return sceneFactory.createCamera();
			}
		}).name("Camera in 2D").description("Camera for 2D scenes").create();

		sceneState = stateComponentBuilder.data(new Factory<Scene<Data2D>>() {
			@Override
			public Scene<Data2D> create() {
				return sceneFactory.createScene();
			}
		}).name("Scene in 2D").description("Scene for 2D renderables and cameras")
				.create();

		renderingBehaviour = behaviourComponentBuilder
				.process(new BehaviourProcess() {
					@Override
					public void process(BehaviourProcessingContext processingContext) {
						for (Entity entity : processingContext.getEntities()) {
							Camera2D data = processingContext.entities().state()
									.getData(entity, cameraState);
							renderer.render(data);
						}
					}
				}).name("Rendering").description("Behaviour for rendering entities")
				.readDependencies(cameraState).create();
	}

	public BehaviourComponent getRenderingBehaviour() {
		return renderingBehaviour;
	}

	public StateComponent<Renderable<Data2D>> getRenderableState() {
		return renderableState;
	}

	public StateComponent<Camera2D> getCameraState() {
		return cameraState;
	}

	public StateComponent<Scene<Data2D>> getSceneState() {
		return sceneState;
	}
}