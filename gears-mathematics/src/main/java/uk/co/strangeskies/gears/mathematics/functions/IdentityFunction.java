package uk.co.strangeskies.gears.mathematics.functions;

public class IdentityFunction<T> implements Function<T, T> {
  @Override
  public final T applyTo(T input) {
    return input;
  }
}