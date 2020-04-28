package com.joseanquiles.comparator.plugin;

import java.util.List;
import java.util.Map;

import com.joseanquiles.comparator.Line;

public interface ComparatorPlugin {

	public void setParameters(Map<String, String> params);
	
	public List<Line> run(List<Line> lines);
	
}
