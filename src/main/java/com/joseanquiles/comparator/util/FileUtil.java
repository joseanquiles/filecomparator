package com.joseanquiles.comparator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.joseanquiles.comparator.Line;

public class FileUtil {

	public static  List<Line> file2Lines(File file) throws IOException {
		List<Line> lines = new ArrayList<Line>();
		String line;
		BufferedReader br = new BufferedReader(new FileReader(file));
		int count = 1;
		while ((line = br.readLine()) != null) {
			Line theLine = new Line(count, line);
			lines.add(theLine);
			count++;
		}
		br.close();
		return lines;
	}
	
	public static String getFileExtension(String filename) {
		int i = filename.lastIndexOf('.');
		if (i < 0) {
			return "";
		} else {
			return filename.substring(i+1);
		}
	}
	
	public static List<File> exploreDir(File baseDir, List<String> ignore) {
		List<File> fileList = new ArrayList<File>();
		exploreDirInternal(baseDir, fileList, ignore);
		return fileList;
	}
	
	public static File transformBasePath(File baseFrom, File baseTo, File filename) {
		String bf = baseFrom.getPath();
		String bt = baseTo.getPath();
		String fn = filename.getPath();
		return new File(fn.replace(bf, bt));
	}
	
	private static boolean ignoreFile(File file, List<String> ignore) {
		for (int i = 0; i < ignore.size(); i++) {
			if (file.getName().endsWith(ignore.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	private static void exploreDirInternal(File dir, List<File> fileList, List<String> ignore) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0;i < files.length; i++) {
				if (files[i].isFile() && !ignoreFile(files[i], ignore)) {
					fileList.add(files[i]);
				} else if (files[i].isDirectory()) {
					exploreDirInternal(files[i], fileList, ignore);
				}
			}
		} else {
			return;
		}
	}
	
}
