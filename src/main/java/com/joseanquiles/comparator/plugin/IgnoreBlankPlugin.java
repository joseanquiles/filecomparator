package com.joseanquiles.comparator.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joseanquiles.comparator.Line;

public class IgnoreBlankPlugin implements ComparatorPlugin {

	public void setParameters(Map<String, String> params) {
		// nothing to do
	}

	public List<Line> run(List<Line> lines) {
		List<Line> processed = new ArrayList<Line>();
		for (int i = 0; i < lines.size(); i++) {
			Line line = lines.get(i);
			String str = line.getLine().trim();
			if (str.length() > 0) {
				line.setLine(str);
				processed.add(line);
			}
		}
		return processed;
	}

}
