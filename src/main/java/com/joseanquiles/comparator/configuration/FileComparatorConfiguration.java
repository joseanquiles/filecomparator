package com.joseanquiles.comparator.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FileComparatorConfiguration {

	String name = "";
	String description = "";
	String sourceDir = "";
	String targetDir = "";
	String outputFile = null;
	List<FileTypeConfiguration> fileTypes = new ArrayList<>();
	List<String> ignoreTypes = new ArrayList<>();
	
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
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		
		// check root element
		if (!"filecomparator".equals(doc.getDocumentElement().getNodeName())) {
			throw new Exception(configFile + " : document root is not filecomparator");
		}
		
		// name
		try {
			this.name = doc.getElementsByTagName("name").item(0).getTextContent();
			System.out.println("NAME:" + this.name);
		} catch (Exception e) {
			// ignore
		}
		
		// description
		try {
			this.description = doc.getElementsByTagName("description").item(0).getTextContent();
			System.out.println("DESCRIPTION:" + this.description);
		} catch (Exception e) {
			// ignore
		}
		
		// source dir
		try {
			this.sourceDir = doc.getElementsByTagName("source-dir").item(0).getTextContent();
			System.out.println("SOURCE DIR:" + this.sourceDir);
		} catch (Exception e) {
			// ignore
		}

		// target dir
		try {
			this.targetDir = doc.getElementsByTagName("target-dir").item(0).getTextContent();
			System.out.println("TARGET DIR:" + this.targetDir);
		} catch (Exception e) {
			// ignore
		}
		
		// output file
		try {
			this.outputFile = doc.getElementsByTagName("output-file").item(0).getTextContent().trim();
			if (this.outputFile.length() == 0) {
				this.outputFile = null;
			}
			System.out.println("OUTPUT FILE:" + this.outputFile);
		} catch (Exception e) {
			// ignore
		}
		
		// ignore files
		NodeList ignoreNodes = doc.getElementsByTagName("ignore-files");
		for (int i = 0; i < ignoreNodes.getLength(); i++) {
			try {
				NodeList ignoreNodesInternal = ignoreNodes.item(i).getChildNodes();
				for (int j = 0; j < ignoreNodesInternal.getLength(); j++) {
					String ignore = ignoreNodesInternal.item(j).getTextContent().trim();
					if (ignore != null && ignore.length() > 0) {
						this.ignoreTypes.add(ignore);
						System.out.println("    IGNORE:" + ignore);
					}
				}
			} catch (Exception e) {
				// ignore
			}
		}
		
		// file types
		NodeList filetypeNodes = doc.getElementsByTagName("filetypes");
		for (int i = 0; i < filetypeNodes.getLength(); i++) {
			try {
				NodeList filetypeNodesInternal = filetypeNodes.item(i).getChildNodes();
				for (int j = 0; j < filetypeNodesInternal.getLength(); j++) {
					NodeList filetypeChildren = filetypeNodesInternal.item(j).getChildNodes();
					for (int k = 0; k < filetypeChildren.getLength(); k++) {
						
					}
				}
			} catch (Exception e) {
				// ignore
			}
		}
		
	}
	
}
