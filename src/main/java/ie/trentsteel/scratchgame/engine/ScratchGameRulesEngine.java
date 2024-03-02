package ie.trentsteel.scratchgame.engine;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ie.trentsteel.scratchgame.beans.SymbolState;
import ie.trentsteel.scratchgame.config.beans.ScratchGameConfig;
import ie.trentsteel.scratchgame.config.beans.WinCombinationsConfig;

/**
 * Class containing scratch game rules
 */
public class ScratchGameRulesEngine {
	
	private WinCombinationsConfig wc;
	private Map<String, SymbolState> symbolStates; 
	
	public ScratchGameRulesEngine(ScratchGameConfig config) {
		// Create starting game state
		this.symbolStates = 
				config.getSymbols()
					.entrySet()
					.stream()
					.collect(Collectors.toMap(Entry::getKey, SymbolState::new));
		
		this.wc = config.getWinCombinations();
	}
	
	
	/**
	 * Runs ScratchGame.
	 * 
	 * Runtime: O(row x columns)
	 * 
	 * @param mx matrix represent scratch game
	 * @param map all symbols in config
	 * 
	 * @return winning symbols
	 */
	public Map<String, SymbolState> calculateFinalGameState(String[][] mx) {
		int diagLen = mx.length - 1;
		int diagonalLTRCount = 0;
		int diagonalRTLCount = 0;
		for(int r = 0, k = diagLen; r < mx.length; r++, k--) {
			
			// same_symbols_diagonally_left_to_right
			if(mx[0][0] == mx[r][r]) {
				diagonalLTRCount+=1;
			}
			if(diagonalLTRCount == mx.length) {
				symbolStates.get(mx[0][0]).addWinCondition(wc.leftToRight());
			}
			
			// same_symbols_diagonally_right_to_left
			if(mx[0][diagLen] == mx[r][k]) {
				diagonalRTLCount+=1;
			}
			if(diagonalRTLCount == mx.length) {
				symbolStates.get(mx[0][diagLen]).addWinCondition(wc.rightToLeft());
			}
			
			int rowCount = 0;
			int colCount = 0;
			for(int c = 0; c < mx[r].length; c++) {

				// same_symbol_x_times
				symbolStates.get(mx[r][c]).incrementCount();
				
				// same_symbols_horizontally
				if(mx[r][0].equals(mx[r][c])) {
					rowCount+=1;
				}
				if(rowCount == mx[r].length) {
					symbolStates.get(mx[r][0]).addWinCondition(wc.horizontally());
				}
				
				// same_symbols_vertically
				if(mx[0][r].equals(mx[c][r])) {
					colCount+=1;
				}
				if(colCount == mx.length) {
					symbolStates.get(mx[0][r]).addWinCondition(wc.vertically());
				}
				
			}
			
		}
		
		// Filter to winning symbols
		symbolStates =  symbolStates.entrySet()
					.stream()
					.filter(this::isStandardSymbolWithCountGreaterThanOrEqualToThreeOrIsBonusSymbolWithCountOne)
					.map(this::addCountWinConditionToStandardSymbols)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		if(doesNotHaveAtLeastOneWinningStandardSymbol(symbolStates)) {
			symbolStates = Collections.emptyMap(); // Game is lost
		}
		
		return symbolStates;
		
	}

	
	private boolean isStandardSymbolWithCountGreaterThanOrEqualToThreeOrIsBonusSymbolWithCountOne(Entry<String, SymbolState> entry) {
		SymbolState symbolState = entry.getValue();
		return (symbolState.isStandardSymbol() && symbolState.hasCountGreaterThanOrEqualToThree()) || 
				(symbolState.isBonusSymbol() && symbolState.getCount() == 1);
	}
	
	private Entry<String, SymbolState> addCountWinConditionToStandardSymbols(Entry<String, SymbolState> entry) {
		SymbolState symbolState = entry.getValue();
		if(symbolState.isStandardSymbol()) {
			symbolState.addWinCondition(wc.fromCount(symbolState.getCount()));
		}
		return entry;
	}
	
	private boolean doesNotHaveAtLeastOneWinningStandardSymbol(Map<String, SymbolState> symbolStates) {
		return symbolStates
				.values()
				.stream()
				.filter(SymbolState::isStandardSymbol)
				.count() < 1;
	}
	
}
