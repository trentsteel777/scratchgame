package ie.trentsteel.scratchgame.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import ie.trentsteel.scratchgame.config.beans.SymbolConfig;
import ie.trentsteel.scratchgame.config.beans.WinCombinationConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString 
public class SymbolState {
	
	private static final String TYPE_BONUS = "bonus";
	
	private String symbol;
	private int count;
	private boolean isBonusSymbol;
	
	List<WinCombinationConfig> winningConditions;
	
	public SymbolState(Entry<String, SymbolConfig> entry) {
		String symbol = entry.getKey();
		SymbolConfig symbolConfig = entry.getValue();
		
		this.symbol = symbol;
		this.count = 0;
		this.winningConditions = new ArrayList<>();
		this.isBonusSymbol = TYPE_BONUS.equals(symbolConfig.getType());
	}
	
	public void incrementCount() {
		this.count += 1;
	}
	
	public boolean hasCountGreaterThanOrEqualToThree() {
		return this.count >= 3;
	}
	
	public void addWinCondition(WinCombinationConfig winCombination) {
		winningConditions.add(winCombination);
	}
	
	public boolean isStandardSymbol() {
		return !isBonusSymbol;
	}
	
}
