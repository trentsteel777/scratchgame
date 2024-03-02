package ie.trentsteel.scratchgame.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonHelper {

	public static final ObjectMapper MAPPER = new ObjectMapper();
	
	static {
		MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
	
	public static String prettyJsonString(Object object)  {
		try {
			return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		}
		catch(JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
