package ie.trentsteel.scratchgame.engine;

import static ie.trentsteel.scratchgame.utils.TestConstants.A;
import static ie.trentsteel.scratchgame.utils.TestConstants.B;
import static ie.trentsteel.scratchgame.utils.TestConstants.C;
import static ie.trentsteel.scratchgame.utils.TestConstants.D;
import static ie.trentsteel.scratchgame.utils.TestConstants.E;
import static ie.trentsteel.scratchgame.utils.TestConstants.F;
import static ie.trentsteel.scratchgame.utils.TestConstants.P1000;
import static ie.trentsteel.scratchgame.utils.TestConstants.X10;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ie.trentsteel.scratchgame.beans.SymbolState;
import ie.trentsteel.scratchgame.config.ConfigHelper;
import ie.trentsteel.scratchgame.config.beans.ScratchGameConfig;
import ie.trentsteel.scratchgame.config.beans.WinCombinationsConfig;
import ie.trentsteel.scratchgame.utils.TestConstants;
import lombok.SneakyThrows;

@ExtendWith(MockitoExtension.class)
class ScratchGameRulesEngineTest {

	
	private static ScratchGameConfig config;
	private static WinCombinationsConfig wc;
	private ScratchGameRulesEngine rulesEngine;
	
	@BeforeAll
	@SneakyThrows
	static void init() {
		config = ConfigHelper.load(TestConstants.PATH_CONFIG);
		wc = config.getWinCombinations();
	}
	
	@BeforeEach
	void setup() {
		rulesEngine = new ScratchGameRulesEngine(config);
	}
	
	@Test
	@SneakyThrows
	void testWinConditionLeftToRight() {
		
		String[][] matrix = { 
				{ B, A, A }, 
				{ A, B, B }, 
				{ B, A, B } 
		};
		
		Map<String, SymbolState> winningSymbols = 
				rulesEngine.calculateFinalGameState(matrix);
		
		assertThat(winningSymbols).hasSize(2);
		
		assertThat(winningSymbols.get(A).isStandardSymbol()).isTrue();
		assertThat(winningSymbols.get(A).getWinningConditions())
			.hasSize(1)
			.contains(wc.fromCount(4))
			.doesNotContain(wc.horizontally())
			.doesNotContain(wc.vertically())
			.doesNotContain(wc.rightToLeft())
			.doesNotContain(wc.leftToRight());
		
		assertThat(winningSymbols.get(B).isStandardSymbol()).isTrue();
		assertThat(winningSymbols.get(B).getWinningConditions())
			.hasSize(2)
			.contains(wc.fromCount(5))
			.contains(wc.leftToRight())
			.doesNotContain(wc.vertically())
			.doesNotContain(wc.horizontally())
			.doesNotContain(wc.rightToLeft());
		
	}
	
	@Test
	@SneakyThrows
	void testWinConditionRightToLeft() {
		
		String[][] matrix = { 
				{ A, A, B }, 
				{ A, B, B }, 
				{ B, A, B } 
		};
		
		Map<String, SymbolState> winningSymbols = 
				rulesEngine.calculateFinalGameState(matrix);
		
		assertThat(winningSymbols).hasSize(2);
		
		assertThat(winningSymbols.get(A).isStandardSymbol()).isTrue();
		assertThat(winningSymbols.get(A).getWinningConditions())
			.contains(wc.fromCount(4))
			.doesNotContain(wc.horizontally())
			.doesNotContain(wc.vertically())
			.doesNotContain(wc.rightToLeft())
			.doesNotContain(wc.leftToRight());
		
		assertThat(winningSymbols.get(B).isStandardSymbol()).isTrue();
		assertThat(winningSymbols.get(B).getWinningConditions())
			.contains(wc.fromCount(5))
			.contains(wc.vertically())
			.contains(wc.rightToLeft())
			.doesNotContain(wc.horizontally())
			.doesNotContain(wc.leftToRight());
		
	}
	
	@Test
	@SneakyThrows
	void testWinConditionHorizontally() {
		
		String[][] matrix = { 
				{ A, A, A }, 
				{ A, X10, B }, 
				{ B, A, B } 
		};
		
		Map<String, SymbolState> winningSymbols = 
				rulesEngine.calculateFinalGameState(matrix);
		
		assertThat(winningSymbols).hasSize(3);
		
		assertThat(winningSymbols.get(A).isStandardSymbol()).isTrue();
		assertThat(winningSymbols.get(A).getWinningConditions())
			.hasSize(2)
			.contains(wc.fromCount(5))
			.contains(wc.horizontally())
			.doesNotContain(wc.vertically())
			.doesNotContain(wc.rightToLeft())
			.doesNotContain(wc.leftToRight());
		
		assertThat(winningSymbols.get(B).isStandardSymbol()).isTrue();
		assertThat(winningSymbols.get(B).getWinningConditions())
			.hasSize(1)
			.contains(wc.fromCount(3))
			.doesNotContain(wc.vertically())
			.doesNotContain(wc.horizontally())
			.doesNotContain(wc.rightToLeft())
			.doesNotContain(wc.leftToRight());
		
		assertThat(winningSymbols.get(X10).getWinningConditions()).isNullOrEmpty();
		assertThat(winningSymbols.get(X10).isBonusSymbol()).isTrue();
		
	}
	

	
	@Test
	@SneakyThrows
	void testWinConditionVertically() {
		
		String[][] matrix = { 
				{ A, A, B }, 
				{ A, P1000, B }, 
				{ A, A, B } 
			};

		Map<String, SymbolState> winningSymbols = 
				rulesEngine.calculateFinalGameState(matrix);

		assertThat(winningSymbols).hasSize(3);
		assertThat(winningSymbols.get(A).isStandardSymbol()).isTrue();
		assertThat(winningSymbols.get(A).getWinningConditions())
			.hasSize(2)
			.contains(wc.fromCount(5))
			.contains(wc.vertically())
			.doesNotContain(wc.horizontally())
			.doesNotContain(wc.rightToLeft())
			.doesNotContain(wc.leftToRight());

		assertThat(winningSymbols.get(B).isStandardSymbol()).isTrue();
		assertThat(winningSymbols.get(B).getWinningConditions())
			.hasSize(2)
			.contains(wc.fromCount(3))
			.contains(wc.vertically())
			.doesNotContain(wc.horizontally())
			.doesNotContain(wc.rightToLeft())
			.doesNotContain(wc.leftToRight());
		
		assertThat(winningSymbols.get(P1000).getWinningConditions()).isNullOrEmpty();
		assertThat(winningSymbols.get(P1000).isBonusSymbol()).isTrue();

	}
	
	@Test
	@SneakyThrows
	void testLosingGameWithBonusSymbol() {
		
		String[][] matrix = { 
				{ C, A, D }, 
				{ C, P1000, F }, 
				{ E, A, B } 
			};

		Map<String, SymbolState> winningSymbols = 
				rulesEngine.calculateFinalGameState(matrix);

		assertThat(winningSymbols).isEmpty();

	}
	
}

