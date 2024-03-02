package ie.trentsteel.scratchgame.config.beans;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SymbolConfig {
	@JsonAlias("reward_multiplier")
    private Double rewardMultiplier;
    private String type;
    private Double extra;
    private String impact;
}
