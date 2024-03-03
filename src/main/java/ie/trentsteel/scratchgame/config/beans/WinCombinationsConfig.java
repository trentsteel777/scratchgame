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
		if (count == 3) {
		    return sameSymbol3Times;
		} else if (count == 4) {
		    return sameSymbol4Times;
		} else if (count == 5) {
		    return sameSymbol5Times;
		} else if (count == 6) {
		    return sameSymbol6Times;
		} else if (count == 7) {
		    return sameSymbol7Times;
		} else if (count == 8) {
		    return sameSymbol8Times;
		} else if (count == 9 || count > 9) {
		    return sameSymbol9Times;
		} else {
		    return null;
		}
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
