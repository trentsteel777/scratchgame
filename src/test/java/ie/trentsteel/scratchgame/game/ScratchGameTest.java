package ie.trentsteel.scratchgame.game;

import static ie.trentsteel.scratchgame.utils.TestConstants.A;
import static ie.trentsteel.scratchgame.utils.TestConstants.B;
import static ie.trentsteel.scratchgame.utils.TestConstants.C;
import static ie.trentsteel.scratchgame.utils.TestConstants.D;
import static ie.trentsteel.scratchgame.utils.TestConstants.E;
import static ie.trentsteel.scratchgame.utils.TestConstants.F;
import static ie.trentsteel.scratchgame.utils.TestConstants.MISS;
import static ie.trentsteel.scratchgame.utils.TestConstants.P1000;
import static ie.trentsteel.scratchgame.utils.TestConstants.P500;
import static ie.trentsteel.scratchgame.utils.TestConstants.X10;
import static ie.trentsteel.scratchgame.utils.TestConstants.X5;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ie.trentsteel.scratchgame.config.ConfigHelper;
import ie.trentsteel.scratchgame.config.beans.ScratchGameConfig;
import ie.trentsteel.scratchgame.utils.TestConstants;
import lombok.SneakyThrows;

@ExtendWith(MockitoExtension.class)
class ScratchGameTest {
	
	private static final Double BETTING_AMOUNT = 100.0;

	private static ScratchGameConfig config;
	
	@BeforeAll
	@SneakyThrows
	static void init() {
		config = ConfigHelper.load(TestConstants.PATH_CONFIG);
	}
	
	@Test
	void testLosingGameWithBonusSymbol() {
		String[][] mx = { 
				{ C, A, D }, 
				{ C, P1000, F }, 
				{ E, A, B } 
			};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(0.0);
	}
	
	@Test
	void testBThreeTimesWithPlus500() {
		String[][] mx = { 
				{ F, P500, C }, 
				{ B, A, B }, 
				{ C, F, B } 
			};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(3000.0);
	}
	
	@Test
	void testBThreeTimesWithMiss() {
		String[][] mx = { 
				{ F, MISS, C }, 
				{ B, A, B }, 
				{ C, F, B } 
		};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(0.0);
	}
	
	@Test
	void testBThreeTimesWithPlus1000() {
		String[][] mx = { 
				{ F, P1000, C }, 
				{ B, A, B }, 
				{ C, F, B } 
			};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(3500.0);
	}
	
	@Test
	void testBThreeTimesWith5x() {
		String[][] mx = { 
				{ F, X5, C }, 
				{ B, A, B }, 
				{ C, F, B } 
		};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(12500.0);
	}
	
	@Test
	void testBThreeTimesWith10x() {
		String[][] mx = { 
				{ F, X10, C }, 
				{ B, A, B }, 
				{ C, F, B } 
		};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(25000.0);
	}
	
	@Test
	void testBFourTimes() {
		String[][] mx = { 
				{ B, E, F }, 
				{ B, E, B }, 
				{ C, F, B } 
		};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(3750.0);
	}
	
	@Test
	void testBFiveTimes() {
		String[][] mx = { 
				{ B, B, F }, 
				{ B, E, B }, 
				{ C, F, B } 
		};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(5000.0);
	}
	
	@Test
	void testBSixTimes() {
		String[][] mx = { 
				{ B, B, F }, 
				{ B, E, B }, 
				{ C, B, B } 
		};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(7500.0);
	}
	
	@Test
	void testBSevenTimes() {
		String[][] mx = { 
				{ B, B, F }, 
				{ B, B, B }, 
				{ C, B, B } 
		};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(250000.0);
	}
	
	@Test
	void testBEightTimes() {
		String[][] mx = { 
				{ B, B, F }, 
				{ B, B, B }, 
				{ B, B, B } 
		};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(2000000.0);
	}
	
	@Test
	void testBNineTimes() {
		String[][] mx = { 
				{ B, B, B }, 
				{ B, B, B }, 
				{ B, B, B } 
		};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(80000000.0);
	}
	
	@Test
	void testBThreeTimesAFiveTimesWithPlus500() {
		String[][] mx = { 
				{ A, P500, A }, 
				{ B, A, B }, 
				{ A, A, B } 
			};
		
		ScratchGame game = new ScratchGame(config);
		game.run(BETTING_AMOUNT, mx);
		assertThat(game.getReward()).isEqualTo(53000.0);
	}
	
	// TODO: There are a few dozen more combinations that could be tested..
	
}

