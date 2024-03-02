package ie.trentsteel.scratchgame.config.beans;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScratchGameConfig {
    private Integer columns;
    private Integer rows;
    private Map<String, SymbolConfig> symbols;
    private ProbabilitiesConfig probabilities;
    @JsonAlias("win_combinations")
    private WinCombinationsConfig winCombinations;
}
