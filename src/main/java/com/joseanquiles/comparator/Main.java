package com.joseanquiles.comparator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.difflib.patch.AbstractDelta;
import com.joseanquiles.comparator.configuration.FileComparatorConfiguration;
import com.joseanquiles.comparator.filecompare.FileComparator;
import com.joseanquiles.comparator.plugin.ComparatorPlugin;
import com.joseanquiles.comparator.util.ArgsUtil;
import com.joseanquiles.comparator.util.DeltaUtil;
import com.joseanquiles.comparator.util.FileUtil;

public class Main {

	
	private static void printSyntax() {
		System.out.println(Main.class.getName() + " -h -c config-file -s source -t target -o output");
		System.out.println("    -h : show this help");
		System.out.println("    -c : configuration file (default src/main/resources/config.sample.yaml)");
		System.out.println("    -s : source directory or file");
		System.out.println("    -t : target directory or file");
		System.out.println("    -o : output file (default console)");
	}
	
	public static void main(String[] args) {
		try {
			
			Map<String, String> argsMap = ArgsUtil.parseArgs(args);
			
			if (argsMap.containsKey("h")) {
				printSyntax();
			}
			
			String configFile = "src/main/resources/config.sample.yaml";
			if (argsMap.containsKey("c")) {
				configFile = argsMap.get("c");
			}
			
			FileComparatorConfiguration config = new FileComparatorConfiguration(configFile);
			
			File source = new File(config.getSource());
			if (argsMap.containsKey("s")) {
				source = new File(argsMap.get("s"));
			} else {
				source = new File(config.getSource());
			}
			
			File revised = new File(config.getTarget());
			if (argsMap.containsKey("t")) {
				revised = new File(argsMap.get("t"));
			} else {
				revised = new File(config.getTarget());
			}
			
			List<File> sourceFiles = FileUtil.exploreDir(source, config.getIgnoreTypes());
			List<File> revisedFiles = FileUtil.exploreDir(revised, config.getIgnoreTypes());
			
			List<File> deleted = new ArrayList<File>();
			List<File> created = new ArrayList<File>();
			List<File> common1 = new ArrayList<File>();
			List<File> common2 = new ArrayList<File>();
			
			// STEP 1: files in original not in revised
			for (int i = 0; i < sourceFiles.size(); i++) {
				File f1 = sourceFiles.get(i);
				File f2 = FileUtil.transformBasePath(source, revised, f1);
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
				File f1 = FileUtil.transformBasePath(revised, source, f2);
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
				
				File originalFile = common1.get(i);
				File revisedFile = common2.get(i);
				
				List<ComparatorPlugin> pluginList = config.getPluginsForFile(originalFile);
								
				FileComparator fc = new FileComparator(originalFile, revisedFile);
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
