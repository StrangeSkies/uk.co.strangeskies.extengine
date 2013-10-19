package uk.co.strangeskies.gears.rendering.buffering;

import uk.co.strangeskies.gears.mathematics.Interpolate;
import uk.co.strangeskies.gears.mathematics.InterpolationOperation;
import uk.co.strangeskies.gears.mathematics.expressions.DecouplingExpression;
import uk.co.strangeskies.gears.mathematics.expressions.collections.DoubleBuffer;
import uk.co.strangeskies.gears.mathematics.functions.Function;
import uk.co.strangeskies.gears.mathematics.values.DoubleValue;
import uk.co.strangeskies.gears.utilities.Self;

public interface SceneInterpolator extends SceneBuffer {
	public DoubleValue getDelta();

	public <I, T> Interpolate<I, T> bufferInterpolation(
			DoubleBuffer<? extends T, ? extends T> interpolation,
			InterpolationOperation<? extends I, ? super T> operation);

	public <I, T, F> Interpolate<I, T> bufferInterpolation(
			DoubleBuffer<? extends F, ? extends F> interpolation,
			InterpolationOperation<? extends I, ? super T> operation,
			Function<? extends T, ? super F> function);

	public <I, T extends Self<? extends T>, F> Interpolate<I, T> bufferDecoupledInterpolation(
			DoubleBuffer<T, ? extends DecouplingExpression<? extends T>> interpolation,
			InterpolationOperation<? extends I, ? super T> operation);

	public <I, T, F extends Self<? extends F>> Interpolate<I, T> bufferDecoupledInterpolation(
			DoubleBuffer<F, ? extends DecouplingExpression<? extends F>> interpolation,
			InterpolationOperation<? extends I, ? super T> operation,
			Function<? extends T, ? super F> function);
}