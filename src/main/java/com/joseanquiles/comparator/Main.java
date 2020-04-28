package com.joseanquiles.comparator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.difflib.patch.AbstractDelta;
import com.joseanquiles.comparator.filecompare.FileComparator;
import com.joseanquiles.comparator.plugin.ComparatorPlugin;
import com.joseanquiles.comparator.plugin.IgnoreBlankPlugin;
import com.joseanquiles.comparator.plugin.IgnoreMultilineCommentsPlugin;
import com.joseanquiles.comparator.plugin.IgnoreRegularExpressionPlugin;
import com.joseanquiles.comparator.util.DeltaUtil;
import com.joseanquiles.comparator.util.FileUtil;

public class Main {

	
	public static void main(String[] args) {
		try {
			
			String[] ignore = new String[] {
				"class",	
			};
			
			//File sourceDir = new File("d:\\REPOSITORIOS\\INFA\\0_AT\\dao\\dao-SPMessage\\tags\\4.8.0-1-1\\");
			//File revisedDir = new File("d:\\REPOSITORIOS\\INFA\\1_CO\\dao\\dao-SPMessage\\branches\\v4.8.0_PESP_2007_v1\\");
			
			File sourceDir = new File("d:\\REPOSITORIOS\\INFA\\0_AT\\cgt-node\\cgt-FindSPMessageVerification\\tags\\4.0.0-1-1\\");
			File revisedDir = new File("d:\\REPOSITORIOS\\INFA\\1_CO\\cgt-node\\cgt-FindSPMessageVerification\\branches\\v4.0.0_PESP_2007_v1\\");
			
			List<File> sourceFiles = FileUtil.exploreDir(sourceDir, ignore);
			List<File> revisedFiles = FileUtil.exploreDir(revisedDir, ignore);
			
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
			System.out.println("DELETED FILES:");
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
			System.out.println("CREATED FILES:");
			for (int i = 0; i < created.size(); i++) {
				System.out.println(created.get(i));
			}

			// STEP 3: files in both, source and revised
			for (int i = 0; i < common1.size(); i++) {
				List<ComparatorPlugin> pluginList = new ArrayList<ComparatorPlugin>();
				
				pluginList.add(new IgnoreBlankPlugin());
				
				Map<String, String> params1 = new HashMap<String, String>();
				params1.put("regexp", ".*informaci.*|.*Buscar.*");
				ComparatorPlugin p1 = new IgnoreRegularExpressionPlugin();
				p1.setParameters(params1);
				pluginList.add(p1);
				
				pluginList.add(new IgnoreMultilineCommentsPlugin());
				
				FileComparator fc = new FileComparator(common1.get(i), common2.get(i));
				List<AbstractDelta<String>> deltas = fc.compare(pluginList);
				if (deltas.size() > 0) {
					System.out.println("===================================================");
					System.out.println("FICHERO:" + common1.get(i));
					System.out.println("---------------------");
					for (int j = 0; j < deltas.size(); j++) {
						//System.out.println(deltas.get(j));
						System.out.println(DeltaUtil.delta2String(deltas.get(j)));
					}
				}
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
