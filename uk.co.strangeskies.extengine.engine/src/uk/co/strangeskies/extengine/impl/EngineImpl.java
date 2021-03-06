package uk.co.strangeskies.extengine.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import uk.co.strangeskies.extengine.Engine;
import uk.co.strangeskies.extengine.Game;
import uk.co.strangeskies.extengine.sections.SectionProcessor;

@Component(immediate = true)
public class EngineImpl implements Engine {
	private Game game;
	private SectionProcessor sectionProcessor;

	@Override
	@Activate
	public void launch() {
		getSectionProcessor().process(getGame().getEntranceSection());
	}

	public Game getGame() {
		return game;
	}

	@Override
	@Reference(service = Game.class)
	public void setGame(Game game) {
		this.game = game;
	}

	public void unsetGame(Game game) {
		this.game = null;
	}

	public SectionProcessor getSectionProcessor() {
		return sectionProcessor;
	}

	@Override
	@Reference(service = SectionProcessor.class)
	public void setSectionProcessor(SectionProcessor sectionProcessor) {
		this.sectionProcessor = sectionProcessor;
	}

	public void unsetSectionProcessor(SectionProcessor sectionProcessor) {
		this.sectionProcessor = null;
	}
}
