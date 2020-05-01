package com.joseanquiles.comparator.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.scanner.ScannerException;

public class FileComparatorConfiguration {

	String name = "";
	String description = "";
	String sourceDir = "";
	String targetDir = "";
	String outputFile = null;
	List<String> ignoreTypes = new ArrayList<>();
	List<FileTypeConfiguration> fileTypes = new ArrayList<>();
	
	public FileComparatorConfiguration(String configFile) throws Exception {
		File file = new File(configFile);
		if (!file.exists()) {
			throw new Exception(configFile + " : does not exist");
		}
		if (!file.isFile()) {
			throw new Exception(configFile + " : is not a file");
		}
		if (!file.canRead()) {
			throw new Exception(configFile + " : cannot be read");
		}
		
		FileInputStream fis = new FileInputStream(configFile);
		
		Yaml yaml = new Yaml();
		Map<String, Object> yamlMap = null;
		try {
			yamlMap = (Map<String, Object>)yaml.load(fis);			
		} catch (ScannerException e) {
			throw new Exception("Error parsing configuration file: " + configFile + " : " + e.getMessage());
		}
		
		this.name = (String)yamlMap.get("name");
		this.description = (String)yamlMap.get("description");
		this.sourceDir = (String)yamlMap.get("source-dir");
		this.targetDir = (String)yamlMap.get("target-dir");
		
		List<Object> ignoreList = (List<Object>)yamlMap.get("ignore-files");
		for (int i = 0; i < ignoreList.size(); i++) {
			this.ignoreTypes.add((String)ignoreList.get(i));
		}
		
		List<Object> filetypesList = (List<Object>)yamlMap.get("file-types");
		
		
		fis.close();
	}
	
}
