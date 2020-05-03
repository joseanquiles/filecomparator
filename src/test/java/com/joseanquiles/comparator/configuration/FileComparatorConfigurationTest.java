package com.joseanquiles.comparator.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.joseanquiles.comparator.plugin.ComparatorPlugin;

public class FileComparatorConfigurationTest {

	private static final String CONFIG_FILE = "src/test/resources/test01.yaml";
	
	private FileComparatorConfiguration config;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.config = new FileComparatorConfiguration(CONFIG_FILE);
	}

	@Test
	public void nameTest() {
		assertEquals("test01", config.getName());
	}

	@Test
	public void descriptionTest() {
		assertEquals("test01 config file", config.getDescription());
	}

	@Test
	public void sourceTest() {
		assertEquals("src/test/resources/original", config.getSource());
	}

	@Test
	public void targetTest() {
		assertEquals("src/test/resources/revised", config.getTarget());
	}

	@Test
	public void outputFileTest() {
		assertNull(config.getOutputFile());
	}
	
	@Test
	public void ignoreFilesTest() {
		assertEquals(3, config.getIgnoreTypes().size());
		List<String> ignored = new ArrayList<String>();
		ignored.add("class");
		ignored.add("docx");
		ignored.add("doc");
		assertEquals(ignored, config.getIgnoreTypes());
	}
	
	@Test
	public void getPluginsForFileJavaTest() throws Exception {
		List<ComparatorPlugin> plugins = config.getPluginsForFile(new File("Test.java"));
		assertEquals(4, plugins.size());
	}

	@Test
	public void getPluginsForFileXmlTest() throws Exception {
		List<ComparatorPlugin> plugins = config.getPluginsForFile(new File("test.xml"));
		assertEquals(2, plugins.size());
	}


}
