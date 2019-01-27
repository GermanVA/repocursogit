package com.app.maps.controller;

import java.util.ArrayList;
import java.util.List;

import com.app.maps.dto.DtoCreatorMapper;
import com.app.maps.view.MainView;

public class CreatorTestMapper {

	private DtoCreatorMapper in;
	private List<String> tipoAtributos;
	private List<String> nombreAtributos;
	private String dto;
	private String dtoInt;

	public CreatorTestMapper(DtoCreatorMapper in, MainView view) {
		this.in = in;
	}

	public String generarPlantilla(List<String> argumentos) {
		StringBuilder claseTest = new StringBuilder();
		claseTest.append("package " + in.getPaquete() + ";\n\n\n");
		claseTest.append("import org.junit.Test;\n");
		claseTest.append("import org.junit.Assert;\n");
		claseTest.append("import org.junit.runner.RunWith;\n");
		claseTest.append("import org.mockito.InjectMocks;\n");
		claseTest.append("import org.mockito.junit.MockitoJUnitRunner;\n");
		claseTest.append("import com.bbva.mzic.serviceutils.rm.utils.comparers.BeanComparator;");
		if(nombreAtributos!=null&&nombreAtributos.size()>0) {
			claseTest.append("\nimport org.mockito.Mock;\n");
			claseTest.append("import java.util.Collection;\n");
		}
		claseTest.append("\n");
		claseTest.append("\n");
		claseTest.append("@RunWith(MockitoJUnitRunner.class)\n");
		claseTest.append("public class " + in.getClaseName() + "Test {\n\n");
		claseTest.append("@InjectMocks\n");
		claseTest.append("private " + in.getClaseName() + " mapper = new " + in.getClaseName() + "();\n\n");
		agregarMocks(argumentos, claseTest);
		claseTest.append("private Factory factory = new Factory();\n\n");
		//dto = "Dto"+dto;
		claseTest.append(testMapToInnerNull());
		claseTest.append(testMapToOuterNull());
		claseTest.append(testMapToInnerBlank());
		claseTest.append(testMapToOuterBlank());
		claseTest.append(testMapToInner());
		claseTest.append(testMapToOuter());
		claseTest.append("\n\n}");

		return claseTest.toString();
	}

	private String testMapToOuter() {
		StringBuilder test = new StringBuilder();
		test.append("@Test\n");
		test.append("public void mapToOuter(){\n");
		test.append("final " + dtoInt + " in= factory.create"+dtoInt+"();\n");
		test.append("final " + dto + " out = mapper.mapToOuter(in);\n");
		if(nombreAtributos!=null&&nombreAtributos.size()>0) {
			test.append("Collection<String> ignores = new ArrayList<>();\n");
			for(String name :nombreAtributos) {
				test.append("ignores.add(\""+name+"\");\n");
			}
		}
		test.append("Assert.assertTrue(BeanComparator.compareBeans(out, in");
		if(nombreAtributos!=null&&nombreAtributos.size()>0) {
			test.append(",ignores");
		}
		test.append("));\n");
		test.append("}\n\n");
		return test.toString();

	}

	private String testMapToInner() {
		StringBuilder test = new StringBuilder();
		test.append("@Test\n");
		test.append("public void mapToInner(){\n");
		test.append("final " + dto + " in= factory.create"+dto+"();\n");
		test.append("final " + dtoInt + " out = mapper.mapToInner(in);\n");
		if(nombreAtributos!=null&&nombreAtributos.size()>0) {
			test.append("Collection<String> ignores = new ArrayList<>();\n");
			for(String name :nombreAtributos) {
				test.append("ignores.add(\""+name+"\");\n");
			}
		}
		test.append("Assert.assertTrue(BeanComparator.compareBeans(out, in");
		if(nombreAtributos!=null&&nombreAtributos.size()>0) {
			test.append(",ignores");
		}
		test.append("));\n");
		test.append("}\n\n");
		return test.toString();
	}

	private Object testMapToOuterBlank() {
		StringBuilder test = new StringBuilder();
		test.append("@Test\n");
		test.append("public void mapToOuterBlankTest(){\n");
		test.append("final " + dto + " out = mapper.mapToOuter(new " + dtoInt + "());\n");
		test.append("Assert.assertNotNull(out);\n");
		test.append("}\n\n");
		return test.toString();
	}

	private String testMapToInnerBlank() {
		StringBuilder test = new StringBuilder();
		test.append("@Test\n");
		test.append("public void mapToInnerBlankTest(){\n");
		test.append("final " + dtoInt + " out = mapper.mapToInner(new " + dto + "());\n");
		test.append("Assert.assertNotNull(out);\n");
		test.append("}\n\n");
		return test.toString();
	}

	private String testMapToOuterNull() {
		StringBuilder test = new StringBuilder();
		test.append("@Test\n");
		test.append("public void mapToOuterNullTest(){\n");
		test.append("final " + dto + " out = mapper.mapToOuter(null);\n");
		test.append("Assert.assertNull(out);\n");
		test.append("}\n\n");
		return test.toString();
	}

	private String testMapToInnerNull() {
		StringBuilder test = new StringBuilder();
		test.append("@Test\n");
		test.append("public void mapToInnerNullTest(){\n");
		test.append("final " + dtoInt + " out = mapper.mapToInner(null);\n");
		test.append("Assert.assertNull(out);\n");
		test.append("}\n\n");
		return test.toString();
	}

	private void agregarMocks(List<String> argumentos, StringBuilder claseMapeo) {

		List<String> tiposAtributos = null;
		if (tipoAtributos != null && tipoAtributos.size() > 0) {
			tiposAtributos = new ArrayList<String>();
			for (String tipo : tipoAtributos) {
				if (!tiposAtributos.contains(tipo)) {
					tiposAtributos.add(tipo);
				}
			}
		}

		if (tiposAtributos != null && tiposAtributos.size() > 0) {
			for (String tipo : tiposAtributos) {
				String name = "";
				if (tipo.contains("List")) {
					name = tipo.replace("List", "").replace("<", "").replace(">", "");
					tipo = tipo.replace("List", "").replace("<", "").replace(">", "");
					if (name.contains("Dto")) {
						name = name.substring(3, name.length());
					}
				} else {
					name = tipo.substring(3, tipo.length());
				}
				String dto = "DtoInt" + tipo.substring(3, tipo.length());
				claseMapeo.append("@Mock\n");
				claseMapeo.append("private Mapper<" + dto + "," + tipo + "> " + name.toLowerCase() + "Mapper ;\n\n");
			}
		}

	}

	public void setTipoAtributos(List<String> tipoAtributos) {
		this.tipoAtributos = tipoAtributos;
	}


	public void setDto(String dto) {
		this.dto = dto;
	}

	public void setDtoInt(String dtoInt) {
		this.dtoInt = dtoInt;
	}

	public void setNombreAtributos(List<String> nombreAtributos) {
		this.nombreAtributos = nombreAtributos;
	}
	

}