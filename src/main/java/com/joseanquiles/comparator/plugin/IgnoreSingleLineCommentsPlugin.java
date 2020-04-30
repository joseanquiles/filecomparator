package com.joseanquiles.comparator.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joseanquiles.comparator.Line;

public class IgnoreSingleLineCommentsPlugin implements ComparatorPlugin {

	private static final String REGEX = "//.*?\\n\",\"\\n";
	
	public void setParameters(Map<String, String> params) {
		// nothing to do
	}

	public List<Line> run(List<Line> lines) {
		List<Line> processed = new ArrayList<Line>();
		for (int i = 0; i < lines.size(); i++) {
			Line line = lines.get(i);
			String str = line.getLine().replaceAll(REGEX, "\n");
			line.setLine(str);
			processed.add(line);
		}
		return processed;
	}
	
}
