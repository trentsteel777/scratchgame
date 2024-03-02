package ie.trentsteel.scratchgame.config.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProbabilitiesConfig {
	@JsonAlias("standard_symbols")
    private List<StandardSymbolProbability> standardSymbols;
	@JsonAlias("bonus_symbols")
    private BonusSymbolProbability bonusSymbols;
}
