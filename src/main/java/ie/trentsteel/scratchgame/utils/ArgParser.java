package ie.trentsteel.scratchgame.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ie.trentsteel.scratchgame.beans.ApplicationArgs;

public class ArgParser {

	private static final String ERR_MSG_BETTING_AMOUNT = "Betting amount must be a positive number.";
	private static final String ERR_MSG_ARGS = "ERROR: please pass arguments as: java -jar scratchgame.jar --config <CONFIG_FILE_PATH> --betting-amount <BETTING_AMOUNT>";
	
	private static final String ARG_CONFIG_LOCATION = "--config";
	private static final String ARG_BETTING_AMOUNT = "--betting-amount";
	
	public static ApplicationArgs parse(String[] args) {
		List<String> argsList = new LinkedList<>();
		Collections.addAll(argsList, args);
		
		if(argsList.size() != 4 || !argsList.contains(ARG_CONFIG_LOCATION) || !argsList.contains(ARG_BETTING_AMOUNT)) {
			throw new IllegalStateException(ERR_MSG_ARGS);
		}
		
		try {
			int configLocationIndex = argsList.indexOf(ARG_CONFIG_LOCATION) + 1;
			int bettingAmountIndex = argsList.indexOf(ARG_BETTING_AMOUNT) + 1;
			
			String configLocation = argsList.get(configLocationIndex);
			Double bettingAmount = Double.valueOf(argsList.get(bettingAmountIndex));
			
			if(bettingAmount < 1) {
				throw new IllegalStateException(ERR_MSG_BETTING_AMOUNT);
			}
			
			return new ApplicationArgs(configLocation, bettingAmount);
		}
		catch(Exception e) {
			System.out.println(ERR_MSG_ARGS);
			System.out.println(ERR_MSG_BETTING_AMOUNT);
			throw e;
		}
		
	}
	
}
