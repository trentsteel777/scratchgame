package ie.trentsteel.scratchgame.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import ie.trentsteel.scratchgame.config.beans.ScratchGameConfig;

public class ProbabilityDistribution {

	private Map<Double, String> cumulativeProbabilitiesWithBonus = new TreeMap<>();
	private Map<Double, String> cumulativeProbabilitiesExcludeBonus = new TreeMap<>();
	private boolean hasBonusSymbolBeenGenerated = false;
	private Set<String> bonusSymbols;
	
	public ProbabilityDistribution(ScratchGameConfig config) {
		bonusSymbols = config.getProbabilities().getBonusSymbols().getSymbols().keySet();
		
		// Generate standard symbol probabilities
		Map<String, Integer> standardSymbolsMap =
				// Assume equal chance of a standard symbol on every row
				// future improvement would be allow different probabilities of standard symbols on different rows
				config.getProbabilities().getStandardSymbols().get(0).getSymbols();
		
		generateCumulativeProbabilities(standardSymbolsMap, cumulativeProbabilitiesExcludeBonus);
		
		
		// Generate standard & bonus symbols' probabilities
		Map<String, Integer> bonusSymbolsMap =
				config.getProbabilities().getBonusSymbols().getSymbols();
		
		Map<String, Integer> allSymbolsMap = new HashMap<>(standardSymbolsMap);
		allSymbolsMap.putAll(bonusSymbolsMap);
		
		generateCumulativeProbabilities(allSymbolsMap, cumulativeProbabilitiesWithBonus);
	
	}
	
	private void generateCumulativeProbabilities(Map<String, Integer> symbolsMap, Map<Double, String> cumulativeProbabilities) {
		int totalStandardSymbolProbabilities = 
				symbolsMap.values().stream().mapToInt(Integer::intValue).sum();
		double cumulativeProb = 0.0;
		for(Entry<String, Integer> entry : symbolsMap.entrySet()) {
			String symbol = entry.getKey();
			double symbolProbability = ((double) entry.getValue() / (double) totalStandardSymbolProbabilities);
			cumulativeProb+= symbolProbability;
			if(cumulativeProb > 0.99) {
				cumulativeProb = 1;
			}
			cumulativeProbabilities.put(cumulativeProb, symbol);
		}
	}
	
	
	public String getNextSymbol() {
		Map<Double, String> probabilities = 
				hasBonusSymbolBeenGenerated ? cumulativeProbabilitiesExcludeBonus : cumulativeProbabilitiesWithBonus;
		
		double probability = Math.random();
		double lower = 0.0;
		for(Entry<Double, String> e : probabilities.entrySet()) {
			double cumulativeProb = e.getKey();
			String symbol = e.getValue();
			if(probability >= lower && probability <= cumulativeProb) {
				if(bonusSymbols.contains(symbol)) {
					hasBonusSymbolBeenGenerated = true;
				}
				return symbol;
			}
			lower = cumulativeProb;
		}
		throw new IllegalStateException("No symbol could be found for proability: " + probability);
	}
	
	
}
