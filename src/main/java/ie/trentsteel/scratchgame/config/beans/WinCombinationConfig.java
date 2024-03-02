package ie.trentsteel.scratchgame.config.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class WinCombinationConfig {
	
	private String desc;

	@JsonAlias("reward_multiplier")
    private Double rewardMultiplier;
    private String when;
    private Integer count;
    private String group;
    @EqualsAndHashCode.Exclude
    @JsonAlias("covered_areas")
    private List<List<String>> coveredAreas;
    
}
