package com.joseanquiles.comparator.util;

import java.util.ArrayList;
import java.util.List;

import com.joseanquiles.comparator.Line;

public class LineUtil {

	public static List<String> lines2String(List<Line> lines) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < lines.size(); i++) {
			result.add(lines.get(i).getLine());
		}
		return result;
	}
	
}
