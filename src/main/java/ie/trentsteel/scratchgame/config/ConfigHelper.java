package ie.trentsteel.scratchgame.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.fasterxml.jackson.annotation.JsonAlias;

import ie.trentsteel.scratchgame.config.beans.ScratchGameConfig;
import ie.trentsteel.scratchgame.config.beans.WinCombinationsConfig;
import ie.trentsteel.scratchgame.utils.JsonHelper;

public class ConfigHelper {
	
	public static ScratchGameConfig load(String jsonFile) throws IOException, ReflectiveOperationException {
		
		File file = new File(jsonFile);
		
		ScratchGameConfig slotMachineConfig = JsonHelper.MAPPER.readValue(file, ScratchGameConfig.class);
		
		if(slotMachineConfig.getRows() != slotMachineConfig.getColumns()) {
			throw new IllegalStateException("Row and column size must be the same. Please update config.");
		}
		
		setWinCombinationConfigDescriptions(slotMachineConfig);
		
		return slotMachineConfig;
	}
	
	private static void setWinCombinationConfigDescriptions(ScratchGameConfig slotMachineConfig)
		throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		
        WinCombinationsConfig config = slotMachineConfig.getWinCombinations();
        Field[] fields = config.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonAlias.class)) {
                JsonAlias jsonAlias = field.getAnnotation(JsonAlias.class);
                
                // make it accessible
                field.setAccessible(true);

                // obtain the field value from the object instance
                Object fieldValue = field.get(config);
                
                // get declared method
                Method myMethod = fieldValue.getClass().getDeclaredMethod("setDesc", new Class[]{ String.class });
                
                // invoke method on the instance of the field from object instance
                myMethod.invoke(fieldValue, jsonAlias.value()[0]);
                
            }
        }
        
	}
	
	
}
