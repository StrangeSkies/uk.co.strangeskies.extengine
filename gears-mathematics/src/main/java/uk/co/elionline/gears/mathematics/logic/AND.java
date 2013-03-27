package uk.co.elionline.gears.mathematics.logic;

import uk.co.elionline.gears.mathematics.expressions.BinaryOperationExpression;
import uk.co.elionline.gears.mathematics.expressions.Expression;

public class AND
    extends
    BinaryOperationExpression</*@ReadOnly*/BooleanValue, /*@ReadOnly*/BooleanValue, /*@ReadOnly*/BooleanValue> {
  public AND(Expression<? extends /*@ReadOnly*/BooleanValue> firstOperand,
      Expression<? extends /*@ReadOnly*/BooleanValue> secondOperand) {
    super(firstOperand, secondOperand, new ANDOperation());
  }
}