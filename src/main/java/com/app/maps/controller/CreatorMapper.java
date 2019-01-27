package com.app.maps.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.app.maps.dto.DtoCreatorMapper;
import com.app.maps.view.MainView;

/**
 * 
 * @author German vargas angeles (fisckoer 28/11/2017)
 *Clase principal donde se generar la plantilla de la clase de mapeo 
 */public class CreatorMapper {

	private static final String OUTER = "OUTER";
	private static final String INNER = "INNER";
	private DtoCreatorMapper in;
	private List<String> nombreAtributos;
	private List<String> tipoAtributos;
	private List<String> nombreAtributosNormal;
	private List<String> tipoAtributosNormal;
	private List<String> enums;
	private String dto;
	private String dtoInt;
	private MainView view;
	private boolean ignore;

	/**
	 * contructor
	 * @param in datos requeridos para generar la plantilla de mapeo
	 * @param view vista para mostrar mensaje y archivo generado
	 */
	public CreatorMapper(DtoCreatorMapper in,MainView view) {
		this.in = in;
		nombreAtributos = new ArrayList<String>();		
		tipoAtributos = new ArrayList<String>();
		nombreAtributosNormal = new ArrayList<String>();
		tipoAtributosNormal = new ArrayList<String>();
		enums = new ArrayList<String>();
		this.view= view;
		ignore = false;
	}

	/**
	 * Metodo principal donde se genera la clase de mapeo
	 * @return regresa el string que es la clase generada
	 */
	public String manofuacturarMapeo() {
		this.view.getLogMapper().append("Limpiando Memorias\n");
		tipoAtributos.clear();
		nombreAtributos.clear();
		nombreAtributosNormal.clear();
		tipoAtributosNormal.clear();
		enums.clear();
		dto = obtenerNombreSinExtension(in.getClase1().getName());
		this.view.getLogMapper().append("\nObteniendo nombre de clase 1: "+dto);
		dtoInt = obtenerNombreSinExtension(in.getClase2().getName());
		this.view.getLogMapper().append("\nObteniendo nombre de clase 2: "+dtoInt);
		List<String> argumentos = Archivo.leerArchivo(in.getClase1());
		this.view.getLogMapper().append("\nObteniendo los argumentos "+argumentos);
		this.view.getLogMapper().append("\n********************Iniciando la generacion de Mapper********************** ");
		String source = generarPlantilla(argumentos);
		try {
			Archivo.guardarArchivo(in.getClaseName(), source, in.getClase1().getParent());
		} catch (IOException e) {
			view.getLogMapper().append("Ocurrio un error al generar el archivo: "+e.getMessage());
			e.printStackTrace();
		}
		if(in.isGenerarMapperTest()) {
			CreatorTestMapper test = new CreatorTestMapper(in, view);
			test.setTipoAtributos(tipoAtributos);
			test.setDto(dto);
			test.setDtoInt(dtoInt);
			test.setNombreAtributos(nombreAtributos);
			source= test.generarPlantilla(argumentos);
			try {
				Archivo.guardarArchivo(in.getClaseName()+"Test", source, in.getClase1().getParent());
			} catch (IOException e) {
				view.getLogMapper().append("Ocurrio un error al generar el archivo: "+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return in.getClase1().getParent()+"\\mapper\\"+in.getClaseName()+".java";
	}

	/**
	 * Metodo donde se genera la plantilla
	 * @param argumentos son los atributos de la clase que se mapeara
	 * @return regresa la plantilla generada
	 */
	private String generarPlantilla(List<String> argumentos) {
		StringBuilder claseMapeo = new StringBuilder();
		this.view.getLogMapper().append("\n********************Agregando Cabeceras********************** ");
		claseMapeo.append("package "+in.getPaquete() + ";\n\n\n");
		claseMapeo.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		claseMapeo.append("import org.springframework.beans.factory.annotation.Qualifier;\n");
		claseMapeo.append("import org.springframework.stereotype.Component;\n");
		claseMapeo.append("import com.bbva.mzic.serviceutils.rm.utils.Mapper;\n");
		claseMapeo.append("\n");
		claseMapeo.append("\n");
		claseMapeo.append("@Component(\"" + in.getClaseName().toLowerCase() + "-" + in.getVersion() + "\")\n");
		claseMapeo.append("public class " + in.getClaseName()+ " implements Mapper<" + dtoInt + "," + dto + ">{\n");
		claseMapeo.append("\n");
		this.view.getLogMapper().append("\n********************Agregando mappers externos ********************** ");
		agregarMapeos(argumentos, claseMapeo);
		claseMapeo.append("public " + dto + " mapToOuter(final " + dtoInt + " outer) {\n");
		claseMapeo.append(dto + " dto=null;\n");
		claseMapeo.append("if(outer!=null){\n");
		claseMapeo.append("	dto = new " + dto + "();\n");
		agregarMapeosDatosSimplesOuter(claseMapeo,OUTER);
		agregarMapeosExternosOuter(claseMapeo);
		claseMapeo.append("}\n");
		claseMapeo.append("return dto;\n");
		claseMapeo.append("}\n\n");
		claseMapeo.append("public " + dtoInt + " mapToInner(final " + dto + " inner) {\n");
		claseMapeo.append(dtoInt + " dto=null;\n");
		claseMapeo.append("if(inner!=null){\n");
		claseMapeo.append("	dto = new " + dtoInt + "();\n");
		agregarMapeosDatosSimplesOuter(claseMapeo,INNER);
		agregarMapeosExternosInner(claseMapeo);
		claseMapeo.append("}\n");
		claseMapeo.append("return dto;\n");
		claseMapeo.append("}\n");

		claseMapeo.append("}");
		System.out.println(claseMapeo.toString());
		view.getMapper().setText(claseMapeo.toString());
		return claseMapeo.toString();
	}

	private void agregarMapeosDatosSimplesOuter(StringBuilder claseMapeo,String mapeo) {
		for (int i = 0; i < nombreAtributosNormal.size(); i++) {
		if(mapeo.equals(OUTER))	{
		claseMapeo.append("dto.set" + primeraLetraMayuscula(nombreAtributosNormal.get(i)) + "( "
				+"outer.get"+ primeraLetraMayuscula(nombreAtributosNormal.get(i)) + "());\n");
		}else if(mapeo.equals(INNER)) {
			claseMapeo.append("dto.set" + primeraLetraMayuscula(nombreAtributosNormal.get(i)) + "( "
					+"inner.get"+ primeraLetraMayuscula(nombreAtributosNormal.get(i)) + "());\n");
		}
		
		
		}
		
	}

	/***
	 * metodo que genera el metodo los mapeos externos en el metodo mapToOuter
	 * @param claseMapeo plantilla donde se agrega los mapeos externos
	 */
	private void agregarMapeosExternosOuter(StringBuilder claseMapeo) {
		this.view.getLogMapper().append("\n********************Mapeos Externos Outer ********************** ");
		for (int i = 0; i < nombreAtributos.size(); i++) {
			if (tipoAtributos.get(i).contains("List")) {
				String dtoList = tipoAtributos.get(i).replace("List", "").replace(">", "").replace("<", "");
				String nombreLista="list"+primeraLetraMayuscula(nombreAtributos.get(i));
				claseMapeo.append("if(outer.get" + primeraLetraMayuscula(nombreAtributos.get(i))
						+ "()!=null&&" + "outer.get" + primeraLetraMayuscula(nombreAtributos.get(i))
						+ "().size()>0){\n");
				claseMapeo.append("List<" + dtoList + "> "+nombreLista+ " = new ArrayList<>();\n");
				claseMapeo.append("for(DtoInt" + dtoList.substring(3, dtoList.length()) + " in:outer.get"
						+ primeraLetraMayuscula(nombreAtributos.get(i)) + "() ){\n");
				claseMapeo.append(
						dtoList + " aux =" +dtoList.substring(3, dtoList.length()).toLowerCase()+ "Mapper.mapToOuter(in); \n");
				claseMapeo.append( nombreLista+".add(aux);\n");
				claseMapeo.append("}\n");
				claseMapeo.append("dto.set" + primeraLetraMayuscula(nombreAtributos.get(i)) + "("+nombreLista+");\n}");
			} else {
				claseMapeo.append("dto.set" + primeraLetraMayuscula(nombreAtributos.get(i)) + "( "
						+ tipoAtributos.get(i).substring(3, tipoAtributos.get(i).length()).toLowerCase() + "Mapper.mapToOuter(" + "outer.get"
						+ primeraLetraMayuscula(nombreAtributos.get(i)) + "()));\n");
			}
		}

	}

	/**
	 * metodo que genera el metodo los mapeos externos en el metodo mapToInner
	 * @param claseMapeo plantilla donde se agrega los mapeos externos
	 */
	private void agregarMapeosExternosInner(StringBuilder claseMapeo) {
		this.view.getLogMapper().append("\n********************Mapeos Externos Inner ********************** ");
		for (int i = 0; i < nombreAtributos.size(); i++) {
			if (tipoAtributos.get(i).contains("List")) {
				String dtoList = tipoAtributos.get(i).replace("List", "").replace(">", "").replace("<", "");
				String nombreLista="list"+primeraLetraMayuscula(nombreAtributos.get(i));
				claseMapeo.append("if(inner.get"+ primeraLetraMayuscula(nombreAtributos.get(i))
				+ "()!=null&&" + "inner.get" + primeraLetraMayuscula(nombreAtributos.get(i))
				+ "().size()>0){\n");
				claseMapeo.append("List<DtoInt" + dtoList.substring(3, dtoList.length())
						+ "> " +nombreLista+ " = new ArrayList<>();\n");
				claseMapeo.append("for(" + dtoList + " in:inner.get"
						+ primeraLetraMayuscula(nombreAtributos.get(i)) + " ()){\n");
				claseMapeo.append("DtoInt"+dtoList.substring(3, dtoList.length()) + " aux =" + dtoList.substring(3, dtoList.length()).toLowerCase()
						+ "Mapper.mapToInner(in); \n");
				claseMapeo.append(nombreLista+".add(aux);\n");
				claseMapeo.append("}\n");
				claseMapeo.append("dto.set" + primeraLetraMayuscula(nombreAtributos.get(i)) + "("+nombreLista+");\n}");
			} else {
				claseMapeo.append("dto.set" + primeraLetraMayuscula(nombreAtributos.get(i)) + "( "
						+tipoAtributos.get(i).substring(3, tipoAtributos.get(i).length()).toLowerCase() + "Mapper.mapToInner(" + "inner.get"
						+ primeraLetraMayuscula(nombreAtributos.get(i)) + "()));\n");
			}
		}

	}

	/**
	 * 
	 * @param pal
	 * @return
	 */
	private String primeraLetraMayuscula(String pal) {
		if(StringUtils.isNotBlank(pal)) {
			pal = pal.substring(0, 1).toUpperCase() + pal.substring(1, pal.length());
		}
		return pal;
	}

	/**
	 * Metodo que agrega el array de los atributos que se ignoraran String [] ignores ={"inore1","ignore2","etc"}
	 * @param claseMapeo plantilla donde se agrega el arreglo ingores
	 * Ya no se usa por ya es permito usar BeanUtils.copyProperties();
	 */
	@Deprecated
	private void agregarIgnores(StringBuilder claseMapeo) {
		this.view.getLogMapper().append("\n********************Agregando atributos a ingnorar********************** ");
		if ((nombreAtributos != null && nombreAtributos.size() > 0)||(enums!=null&&enums.size()>0)) {
			ignore = true;
			claseMapeo.append("String ignores[]={");
			int i = 0;
			for (String name : nombreAtributos) {
				if (i > 0) {
					claseMapeo.append(",");
				}
				claseMapeo.append("\"");
				claseMapeo.append(name);
				claseMapeo.append("\"");
				i++;
			}
			
			for(String name:enums) {
				if (i > 0) {
					claseMapeo.append(",");
				}
				claseMapeo.append("\"");
				claseMapeo.append(name);
				claseMapeo.append("\"");
				i++;
			}
			
			claseMapeo.append("};\n");
		}

	}

	/***
	 * Metodo que agrega los mapeos externos com atributos de la clase
	 * @param argumentos lista con los atributos que seran agregados en la clase
	 * @param claseMapeo plantilla donde se agregara los maepos externos como @Autowire
	 */
	private void agregarMapeos(List<String> argumentos, StringBuilder claseMapeo) {

		for (String arg : argumentos) {
			validarTipo(arg);
		}
		List<String> tiposAtributos = null;
		if(tipoAtributos!=null&&tipoAtributos.size()>0) {
			tiposAtributos = new ArrayList<String>();
			for(String tipo:tipoAtributos) {
				if(!tiposAtributos.contains(tipo)) {
					tiposAtributos.add(tipo);
				}
			}
		}
		
		if (tiposAtributos != null && tiposAtributos.size() > 0) {
			for (String tipo : tiposAtributos) {
				String name = "";
				if (tipo.contains("List")) {
					name=tipo.replace("List", "").replace("<", "").replace(">", "");
					tipo=tipo.replace("List", "").replace("<", "").replace(">", "");
					if(name.contains("Dto")) {
						name=name.substring(3, name.length());
					}
				} else {
					name = tipo.substring(3, tipo.length());
				}
				String dto = "DtoInt" + tipo.substring(3, tipo.length());
				claseMapeo.append("@Autowired\n");
				claseMapeo.append("@Qualifier(\"" + name.toLowerCase() + "-mapper-" + in.getVersion() + "\")\n");
				claseMapeo.append("private Mapper<" + dto + "," + tipo + "> " + name.toLowerCase() + "Mapper ;\n\n");
			}
		}

	}

	private String obtenerNombreSinExtension(String name) {
		return name.substring(0, name.lastIndexOf("."));
	}

	/**
	 * Metodo que valida que los atributos no son de tipo primitivo para gregar en una lista para usarlos en mapeos externos
	 * @param arg cadena de donde se tomara el tipo de atributo entra con el siguiente formata "private DtoAlgo nombre" y se hace una split para separar
	 */
	private void validarTipo(String arg) {
		String tipo = "";
		String nombre = "";
		String agrs[] = arg.split(" ");
		if (agrs.length > 2) {
			if(agrs.length > 6) {
				tipo = agrs[5];
				nombre = agrs[6].replace(";", "");
			}else {
				tipo = agrs[1];
				nombre = agrs[2].replace(";", "");
			}
			if(tipo.toLowerCase().contains("dto")) {
				nombreAtributos.add(nombre);
				tipoAtributos.add(tipo);
			}else
			if(tipo.toLowerCase().contains("enum")) {
				this.view.getLogMapper().append("\nSe econtro un atributo ENUM revisar que se copie correctamente ATRIBUTO: "+tipo+" "+nombre);
				enums.add(nombre);
			}else {
				nombreAtributosNormal.add(nombre);
				tipoAtributosNormal.add(tipo);
			}
		}

	}

}
