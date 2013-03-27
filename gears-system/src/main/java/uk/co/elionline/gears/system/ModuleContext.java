package uk.co.elionline.gears.system;

import java.util.Map;

import uk.co.elionline.gears.utilities.Self;

public class ModuleContext<M extends ModuleContext<M>> implements Self<M> {
	Map<Class<? extends Module<M>>, Module<M>> modules;

	@Override
	public M copy() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public M getThis() {
		return (M) this;
	}
}