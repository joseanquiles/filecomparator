package com.joseanquiles.comparator.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joseanquiles.comparator.Line;

public class IgnoreMultilineCommentsPlugin implements ComparatorPlugin {
	
	private static final String REGEX = "(?s)/\\*.*?\\*/";
	
	public void setParameters(Map<String, String> params) {
	}

	public List<Line> run(List<Line> lines) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lines.size(); i++) {
			sb.append(lines.get(i).getLine()).append("\n");
		}
		String text = sb.toString();
		// remove comments
		text = text.replaceAll(REGEX, "");
		// convert to lines again
		String[] splitted = text.split("\\r?\\n");
		List<Line> processed = new ArrayList<Line>();
		for (int i = 0; i < splitted.length; i++) {
			Line line = new Line(i+1, splitted[i]);
			processed.add(line);
		}
		return processed;
	}
	
}
