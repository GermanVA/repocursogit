package com.app.maps.dto;

import java.io.File;

public class DtoCreatorMapper {

	private File clase1;
	private File clase2;
	private String paquete;
	private String version;
	private String claseName;
	private boolean generarMapperTest;
	public File getClase1() {
		return clase1;
	}
	public void setClase1(File clase1) {
		this.clase1 = clase1;
	}
	public File getClase2() {
		return clase2;
	}
	public void setClase2(File clase2) {
		this.clase2 = clase2;
	}
	public String getPaquete() {
		return paquete;
	}
	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getClaseName() {
		return claseName;
	}
	public void setClaseName(String claseName) {
		this.claseName = claseName;
	}
	public boolean isGenerarMapperTest() {
		return generarMapperTest;
	}
	public void setGenerarMapperTest(boolean generarMapperTest) {
		this.generarMapperTest = generarMapperTest;
	}
	@Override
	public String toString() {
		return "DtoCreatorMapper [clase1=" + clase1 + ", clase2=" + clase2 + ", paquete=" + paquete + ", version="
				+ version + ", claseName=" + claseName + ", generarMapperTest=" + generarMapperTest + "]";
	}
	
	
	
	
	
	
}
