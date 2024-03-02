package ie.trentsteel.scratchgame.config.beans;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardSymbolProbability {
    private Integer column;
    private Integer row;
    private Map<String, Integer> symbols;
}
