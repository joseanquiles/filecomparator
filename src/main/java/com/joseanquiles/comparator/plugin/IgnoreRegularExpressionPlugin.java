package com.joseanquiles.comparator.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joseanquiles.comparator.Line;

public class IgnoreRegularExpressionPlugin implements ComparatorPlugin {

	private String regexp;
	
	/**
	 * Params: regexp
	 */
	public void setParameters(Map<String, String> params) {
		this.regexp = params.get("regexp");
	}

	public List<Line> run(List<Line> lines) {
		List<Line> processed = new ArrayList<Line>();
		for (int i = 0; i < lines.size(); i++) {
			Line line = lines.get(i);
			String str = line.getLine();
			if (!str.matches(this.regexp)) {
				processed.add(line);				
			}
		}
		return processed;
	}

}
