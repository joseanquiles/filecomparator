package com.joseanquiles.comparator.filecompare;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.InsertDelta;
import com.github.difflib.patch.Patch;
import com.joseanquiles.comparator.Line;
import com.joseanquiles.comparator.plugin.ComparatorPlugin;
import com.joseanquiles.comparator.util.FileUtil;
import com.joseanquiles.comparator.util.LineUtil;

public class FileComparator {

	private List<Line> originalLines;
	private List<Line> revisedLines;

	public FileComparator(File original, File revised) throws IOException {

		this.originalLines = FileUtil.file2Lines(original);
		this.revisedLines = FileUtil.file2Lines(revised);

	}

	public List<AbstractDelta<String>> compare(List<ComparatorPlugin> plugins) throws DiffException {
		List<Line> original = this.originalLines;
		List<Line> revised = this.revisedLines;
		for (int i = 0; i < plugins.size(); i++) {
			original = plugins.get(i).run(original);
			revised = plugins.get(i).run(revised);
		}
		
		List<String> lines1 = LineUtil.lines2String(original);
		List<String> lines2 = LineUtil.lines2String(revised);
		Patch<String> patch = DiffUtils.diff(lines1, lines2);
		
		List<AbstractDelta<String>> deltas = patch.getDeltas();
		//for (int i = 0; i < deltas.size(); i++) {
		//	System.out.println(deltas.get(i));
		//}
		
		List<AbstractDelta<String>> result = new ArrayList<AbstractDelta<String>>();
		for (int i = 0; i < deltas.size(); i++) {
			AbstractDelta<String> delta = deltas.get(i);
			AbstractDelta<String> newDelta = null;
			
			int sourcePosition = delta.getSource().getPosition() < original.size() ? delta.getSource().getPosition() : original.size()-1;
			int targetPosition = delta.getTarget().getPosition() < revised.size() ? delta.getTarget().getPosition() : revised.size()-1;
			Chunk<String> sourceChunk = new Chunk<String>(original.get(sourcePosition).getLinenumber(), delta.getSource().getLines());
			Chunk<String> targetChunk = new Chunk<String>(revised.get(targetPosition).getLinenumber(), delta.getTarget().getLines());
			
			switch (delta.getType()) {
			case DELETE:
				newDelta = new DeleteDelta<String>(sourceChunk, targetChunk);
				break;
			case CHANGE:
				newDelta = new ChangeDelta<String>(sourceChunk, targetChunk);
				break;
			case INSERT:
				newDelta = new InsertDelta<String>(sourceChunk, targetChunk);
				break;
			default:
				break;
			}
			result.add(newDelta);
		}
		
		return result;
		
	}

}
