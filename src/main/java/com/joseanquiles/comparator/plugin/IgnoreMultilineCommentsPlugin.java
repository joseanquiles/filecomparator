package com.joseanquiles.comparator.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joseanquiles.comparator.Line;

public class IgnoreMultilineCommentsPlugin implements ComparatorPlugin {
	
	private static final String REGEX = "(?s)/\\*.*?\\*/";
	
	public void setParameters(Map<String, String> params) {
	}

	public List<Line> run(List<Line> lines) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lines.size(); i++) {
			sb.append(lines.get(i).getLine()).append("\n");
		}
		String text = sb.toString();
		// remove comments
		text = text.replaceAll(REGEX, "");
		// convert to lines again
		String[] splitted = text.split("\\r?\\n");
		List<Line> processed = new ArrayList<Line>();
		for (int i = 0; i < splitted.length; i++) {
			Line line = new Line(i+1, splitted[i]);
			processed.add(line);
		}
		return processed;
	}

	public static void main(String[] args) {
		String regexp = "(?s)/\\*.*?\\*/";
		String java =
				"package com.telefonica.infa.dao.spmessage.repository;\r\n" + 
				"\r\n" + 
				"import com.telefonica.infa.model.SpisaEntSpecCharUse;\r\n" + 
				"import javax.persistence.criteria.CriteriaBuilder;\r\n" + 
				"import javax.persistence.criteria.CriteriaQuery;\r\n" + 
				"import javax.persistence.criteria.Predicate;\r\n" + 
				"import javax.persistence.criteria.Root;\r\n" + 
				"import org.springframework.data.jpa.domain.Specification;\r\n" + 
				"\r\n" + 
				"public class FindEntSpecCharUse {\r\n" + 
				"\r\n" + 
				"    /*\r\n" + 
				"     * busca por el name de la EntitySpecCharUse\r\n" + 
				"     */\r\n" + 
				"\r\n" + 
				"    public static Specification<SpisaEntSpecCharUse> byName(String escuNaEntSpecCharUse) {\r\n" + 
				"        return new Specification<SpisaEntSpecCharUse>() {\r\n" + 
				"            @Override\r\n" + 
				"            public Predicate toPredicate(Root<SpisaEntSpecCharUse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {\r\n" + 
				"                return null;\r\n" + 
				"            }\r\n" + 
				"        };\r\n" + 
				"    }\r\n" + 
				"\r\n" + 
				"    /*\r\n" + 
				"     * Filtra por id de SPMessageSpec asociado\r\n" + 
				"     */\r\n" + 
				"\r\n" + 
				"    public static Specification<SpisaEntSpecCharUse> bySPMessageSpec(Long roenIdSpMessageSpec) {\r\n" + 
				"        return new Specification<SpisaEntSpecCharUse>() {\r\n" + 
				"            @Override\r\n" + 
				"            public Predicate toPredicate(Root<SpisaEntSpecCharUse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {\r\n" + 
				"                return null;\r\n" + 
				"            }\r\n" + 
				"        };\r\n" + 
				"    }\r\n" + 
				"}\r\n" + 
				"";
		String cleanJava = java.replaceAll(regexp, "");
		System.out.println(cleanJava);
		
	}
	
}
