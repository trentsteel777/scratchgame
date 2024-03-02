package ie.trentsteel.scratchgame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ie.trentsteel.scratchgame.beans.SymbolState;
import ie.trentsteel.scratchgame.config.beans.ScratchGameConfig;
import ie.trentsteel.scratchgame.config.beans.SymbolConfig;
import ie.trentsteel.scratchgame.config.beans.WinCombinationConfig;
import ie.trentsteel.scratchgame.engine.ScratchGameRulesEngine;
import ie.trentsteel.scratchgame.utils.JsonHelper;
import lombok.Getter;

@Getter
public class ScratchGame {

	private static final String BS_MISS = "miss";
	private static final String BS_MULTIPLY_REWARD = "multiply_reward";
	private static final String BS_EXTRA_BONUS = "extra_bonus";

	@JsonIgnore
	private final ScratchGameConfig config;

	@JsonIgnore
	private ScratchGameRulesEngine rulesEngine;
	
	private String[][] matrix;

	@JsonIgnore
	private Double bettingAmount;
	
	private Double reward = 0.0;
	
	@JsonProperty("applied_winning_combinations")
	private Map<String, List<String>> appliedWinningCombinations;
	
	@JsonProperty("applied_bonus_symbol")
	private String appliedBonusSymbol;

	public ScratchGame(ScratchGameConfig config) {
		this.config = config;
		this.rulesEngine = new ScratchGameRulesEngine(config);
	}
	
	public void run(Double bettingAmount) {
		this.run(bettingAmount, generateGameMatrix());
	}
	
	protected void run(Double bettingAmount, String[][] mx) {
		this.bettingAmount = bettingAmount;
		
		matrix = mx;
		
		Map<String, SymbolState> winningSymbols = rulesEngine.calculateFinalGameState(matrix);
		
		buildEndGameResult(winningSymbols);
	}


	private String[][] generateGameMatrix() {
		ProbabilityDistribution pb = new ProbabilityDistribution(config);
		
		int c = config.getColumns();
		int r = config.getRows();
		
		String[][] mx = new String[c][r];
		
		for(int i = 0; i < c; i++) {
			for(int j = 0; j < r; j++) {
				mx[i][j] = pb.getNextSymbol();
			}
		}
		
		return mx;
	}
	
	private void buildEndGameResult(Map<String, SymbolState> winningSymbols) {
		appliedWinningCombinations = new HashMap<>(winningSymbols.size());
		if(winningSymbols.isEmpty()) {
			// User has lost
			return;
		}
		
		Function<Double, Double> fnApplyBonus = null;
		List<Double> rewardPerSymbol = new ArrayList<>(winningSymbols.size());
		for(SymbolState symbolState : winningSymbols.values()) {
			SymbolConfig symbolConfig = config.getSymbols().get(symbolState.getSymbol());
			
			if(symbolState.isBonusSymbol()) { 
				appliedBonusSymbol = symbolState.getSymbol();
				
				if(BS_EXTRA_BONUS.equals(symbolConfig.getImpact())) {
					fnApplyBonus = (x) -> x + symbolConfig.getExtra();
				}
				else if(BS_MULTIPLY_REWARD.equals(symbolConfig.getImpact())) {
					fnApplyBonus = (x) -> x * symbolConfig.getRewardMultiplier();
				}
				else if(BS_MISS.equals(symbolConfig.getImpact())) {
					appliedWinningCombinations = Collections.emptyMap();
					return;
				}
			}
			else {
				Double symbolReward = bettingAmount * symbolConfig.getRewardMultiplier();
				
				List<String> winningCombinations = new ArrayList<>(symbolState.getWinningConditions().size());
				for(WinCombinationConfig wc : symbolState.getWinningConditions()) {
					winningCombinations.add(wc.getDesc());
					symbolReward*=wc.getRewardMultiplier();
				}
				rewardPerSymbol.add(symbolReward);
				appliedWinningCombinations.put(symbolState.getSymbol(), winningCombinations);
			}
		};
		
		reward = rewardPerSymbol.stream().mapToDouble(Double::doubleValue).sum();
		if(fnApplyBonus != null) {
			reward = fnApplyBonus.apply(reward);
		}
		
	}
	
	public String toJsonString() {
		return JsonHelper.prettyJsonString(this);
	}
	
}
