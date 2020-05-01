package com.joseanquiles.comparator.configuration;

import java.util.HashMap;
import java.util.Map;

class PluginConfiguration {

	String pluginName = null;
	
	boolean enabled = true;
	
	Map<String, String> parameters = new HashMap<>();
	
}
