package ie.trentsteel.scratchgame.config.beans;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusSymbolProbability {
	private Map<String, Integer> symbols;
}
