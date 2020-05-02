package com.joseanquiles.comparator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.difflib.patch.AbstractDelta;
import com.joseanquiles.comparator.configuration.FileComparatorConfiguration;
import com.joseanquiles.comparator.filecompare.FileComparator;
import com.joseanquiles.comparator.plugin.ComparatorPlugin;
import com.joseanquiles.comparator.util.DeltaUtil;
import com.joseanquiles.comparator.util.FileUtil;

public class Main {

	
	public static void main(String[] args) {
		try {
			
			FileComparatorConfiguration config = new FileComparatorConfiguration("src/main/resources/config.sample.yaml");
			
			File sourceDir = new File(config.getSourceDir());
			File revisedDir = new File(config.getTargetDir());
			
			List<File> sourceFiles = FileUtil.exploreDir(sourceDir, config.getIgnoreTypes());
			List<File> revisedFiles = FileUtil.exploreDir(revisedDir, config.getIgnoreTypes());
			
			List<File> deleted = new ArrayList<File>();
			List<File> created = new ArrayList<File>();
			List<File> common1 = new ArrayList<File>();
			List<File> common2 = new ArrayList<File>();
			
			// STEP 1: files in original not in revised
			for (int i = 0; i < sourceFiles.size(); i++) {
				File f1 = sourceFiles.get(i);
				File f2 = FileUtil.transformBasePath(sourceDir, revisedDir, f1);
				if (!f2.exists()) {
					deleted.add(f1);
				} else {
					common1.add(f1);
					common2.add(f2);
				}
			}
			System.out.println("===============================================================");
			System.out.println("DELETED FILES, total " + deleted.size());
			for (int i = 0; i < deleted.size(); i++) {
				System.out.println(deleted.get(i));
			}

			// STEP 2: files in revised not in source
			for (int i = 0; i < revisedFiles.size(); i++) {
				File f2 = revisedFiles.get(i);
				File f1 = FileUtil.transformBasePath(revisedDir, sourceDir, f2);
				if (!f1.exists()) {
					created.add(f1);
				}
			}
			System.out.println("===============================================================");
			System.out.println("CREATED FILES, total " + created.size());
			for (int i = 0; i < created.size(); i++) {
				System.out.println(created.get(i));
			}

			// STEP 3: files in both, source and revised
			System.out.println("===============================================================");
			System.out.println("MODIFIED FILES, total " + common1.size());
			for (int i = 0; i < common1.size(); i++) {
				
				File original = common1.get(i);
				File revised = common2.get(i);
				
				List<ComparatorPlugin> pluginList = config.getPluginsForFile(original);
								
				FileComparator fc = new FileComparator(original, revised);
				List<AbstractDelta<String>> deltas = fc.compare(pluginList);
				if (deltas.size() > 0) {
					System.out.println("===================================================");
					System.out.println("FICHERO:" + common1.get(i));
					System.out.println("---------------------");
					for (int j = 0; j < deltas.size(); j++) {
						System.out.println(DeltaUtil.delta2String(deltas.get(j)));
					}
				}
			}
			
			System.out.println("===============================================================");
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
