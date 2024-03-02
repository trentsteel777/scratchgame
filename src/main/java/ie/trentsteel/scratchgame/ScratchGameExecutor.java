package ie.trentsteel.scratchgame;

import ie.trentsteel.scratchgame.beans.ApplicationArgs;
import ie.trentsteel.scratchgame.config.ConfigHelper;
import ie.trentsteel.scratchgame.config.beans.ScratchGameConfig;
import ie.trentsteel.scratchgame.game.ScratchGame;
import ie.trentsteel.scratchgame.utils.ArgParser;

public class ScratchGameExecutor {
	
	/**
	 * Application entry point for ScratchGame. 
	 * 
	 * Assumptions: Only 1 bonus symbol can be generated
	 * 
	 */
	public static void main(String[] args) throws Exception {
		
		ApplicationArgs appArgs = ArgParser.parse(args);
		
		ScratchGameConfig config = ConfigHelper.load(appArgs.getConfigLocation());
		
		ScratchGame scratchGame = new ScratchGame(config);
		
		scratchGame.run(appArgs.getBettingAmount());
		
		System.out.println(scratchGame.toJsonString());
		
	}
	
}
