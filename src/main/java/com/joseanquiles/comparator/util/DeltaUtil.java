package com.joseanquiles.comparator.util;

import com.github.difflib.patch.AbstractDelta;

public class DeltaUtil {

	public static String delta2String(AbstractDelta<String> delta) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(delta.getType()).append("\n");
		
		int from = delta.getSource().getPosition();
		sb.append("source:").append(from);
		int to = from + delta.getSource().getLines().size() - 1;
		if (from != to) {
			sb.append("-").append(to);
		}
		for (int i = 0; i < delta.getSource().getLines().size(); i++) {
			sb.append("[").append(delta.getSource().getLines().get(i)).append("] ");
		}
		sb.append("\n");
		
		from = delta.getTarget().getPosition();
		sb.append("target:").append(from);
		to = from + delta.getTarget().getLines().size() - 1;
		if (from != to) {
			sb.append("-").append(to);
		}
		for (int i = 0; i < delta.getTarget().getLines().size(); i++) {
			sb.append("[").append(delta.getTarget().getLines().get(i)).append("] ");
		}
		
		return sb.toString();
	}
}
