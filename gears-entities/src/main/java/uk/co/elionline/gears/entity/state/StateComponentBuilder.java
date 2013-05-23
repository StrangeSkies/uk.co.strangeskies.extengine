package uk.co.elionline.gears.entity.state;

import uk.co.elionline.gears.utilities.Factory;

public interface StateComponentBuilder {
	public <D> StateComponentConfigurator<D> dataFactory(
			Factory<? extends D> dataFactory);
}
