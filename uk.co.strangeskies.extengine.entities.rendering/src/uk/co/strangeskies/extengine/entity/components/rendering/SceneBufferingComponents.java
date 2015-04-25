package uk.co.strangeskies.extengine.entity.components.rendering;

import uk.co.strangeskies.extengine.entity.behaviour.BehaviourComponent;
import uk.co.strangeskies.extengine.entity.state.StateComponent;
import uk.co.strangeskies.extengine.rendering.buffering.SceneBuffer;
import uk.co.strangeskies.extengine.rendering.buffering.SceneInterpolator;

public interface SceneBufferingComponents {
	public StateComponent<SceneBuffer> getSceneBufferState();

	public StateComponent<SceneInterpolator> getInterpolatableSceneBufferState();

	public BehaviourComponent getBufferingBehaviour();

	public BehaviourComponent getBufferingBehaviour(SceneBuffer buffer);
}