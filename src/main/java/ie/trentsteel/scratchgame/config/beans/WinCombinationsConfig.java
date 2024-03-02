package ie.trentsteel.scratchgame.config.beans;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinCombinationsConfig {
	@JsonAlias("same_symbol_3_times")
    private WinCombinationConfig sameSymbol3Times;
	@JsonAlias("same_symbol_4_times")
    private WinCombinationConfig sameSymbol4Times;
	@JsonAlias("same_symbol_5_times")
    private WinCombinationConfig sameSymbol5Times;
	@JsonAlias("same_symbol_6_times")
    private WinCombinationConfig sameSymbol6Times;
	@JsonAlias("same_symbol_7_times")
    private WinCombinationConfig sameSymbol7Times;
	@JsonAlias("same_symbol_8_times")
    private WinCombinationConfig sameSymbol8Times;
	@JsonAlias("same_symbol_9_times")
    private WinCombinationConfig sameSymbol9Times;
	@JsonAlias("same_symbols_horizontally")
    private WinCombinationConfig sameSymbolsHorizontally;
	@JsonAlias("same_symbols_vertically")
    private WinCombinationConfig sameSymbolsVertically;
	@JsonAlias("same_symbols_diagonally_left_to_right")
    private WinCombinationConfig sameSymbolsDiagonallyLeftToRight;
	@JsonAlias("same_symbols_diagonally_right_to_left")
    private WinCombinationConfig sameSymbolsDiagonallyRightToLeft;
	
	public WinCombinationConfig fromCount(int count) {
		return switch(count) {
			case 3 -> sameSymbol3Times;
			case 4 -> sameSymbol4Times;
			case 5 -> sameSymbol5Times;
			case 6 -> sameSymbol6Times;
			case 7 -> sameSymbol7Times;
			case 8 -> sameSymbol8Times;
			case 9 -> sameSymbol9Times;
			default -> null;
		};
	}
	
	public WinCombinationConfig horizontally() {
		return sameSymbolsHorizontally;
	}
	public WinCombinationConfig vertically() {
		return sameSymbolsVertically;
	}
	
	public WinCombinationConfig leftToRight() {
		return sameSymbolsDiagonallyLeftToRight;
	}
	
	public WinCombinationConfig rightToLeft() {
		return sameSymbolsDiagonallyRightToLeft;
	}
	
}
