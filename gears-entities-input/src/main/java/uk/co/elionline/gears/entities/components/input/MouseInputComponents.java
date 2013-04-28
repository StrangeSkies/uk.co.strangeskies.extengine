package uk.co.elionline.gears.entities.components.input;

import java.util.UUID;

import uk.co.elionline.gears.entities.behaviour.BehaviourComponent;
import uk.co.elionline.gears.entities.behaviour.BehaviourComponentBuilderFactory;
import uk.co.elionline.gears.entities.behaviour.BehaviourProcess;
import uk.co.elionline.gears.entities.behaviour.BehaviourProcessingContext;
import uk.co.elionline.gears.entities.state.StateComponent;
import uk.co.elionline.gears.entities.state.StateComponentBuilderFactory;
import uk.co.elionline.gears.input.MouseInputController;
import uk.co.elionline.gears.input.MouseMovementAdapter;
import uk.co.elionline.gears.input.MouseMovementAdapter.MovementType;
import uk.co.elionline.gears.input.WindowManagerInputController;
import uk.co.elionline.gears.mathematics.geometry.matrices.Vector2;
import uk.co.elionline.gears.mathematics.values.IntValue;
import uk.co.elionline.gears.utilities.CopyFactory;

public class MouseInputComponents {
	private final StateComponent<CursorStateData> cursorState;

	private final BehaviourComponent cursorBehaviour;

	public MouseInputComponents(MouseInputController mouseInputController,
			WindowManagerInputController windowManagerInputController,
			BehaviourComponentBuilderFactory behaviourComponentBuilderFactory,
			StateComponentBuilderFactory stateComponentBuilderFactory) {
		final MouseMovementAdapter mouseMovementAdapter = new MouseMovementAdapter(
				mouseInputController, windowManagerInputController);
		mouseMovementAdapter.setMovementType(MovementType.Relative);

		cursorState = stateComponentBuilderFactory.<CursorStateData> begin()
				.name("Cursor State")
				.description("The state of an entity which behaves like a cursor")
				.dataFactory(new CopyFactory<>(new CursorStateData())).create();

		cursorBehaviour = behaviourComponentBuilderFactory.begin()
				.name("Cursor Behaviour").description("The behaviour of a cursor")
				.writeDependencies(cursorState).process(new BehaviourProcess() {
					@Override
					public void process(BehaviourProcessingContext processingContext) {
						for (UUID entity : processingContext.getEntities()) {
							CursorStateData cursorStateData = processingContext
									.getEntityManager().getStateManager()
									.getData(entity, getCursorState());

							Vector2<IntValue> nextPosition = mouseMovementAdapter
									.getPosition().getBack();
							Vector2<IntValue> currentPosition = cursorStateData.getPosition();

							cursorStateData.getVelocity().set(
									currentPosition.subtract(nextPosition).negate());
							currentPosition.set(nextPosition);
						}
					}
				}).create();
	}

	public final StateComponent<CursorStateData> getCursorState() {
		return cursorState;
	}

	public final BehaviourComponent getCursorBehaviour() {
		return cursorBehaviour;
	}
}
