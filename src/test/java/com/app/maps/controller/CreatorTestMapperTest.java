package com.app.maps.controller;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.app.maps.dto.DtoCreatorMapper;
import com.app.maps.view.MainView;

public class CreatorTestMapperTest {

	CreatorTestMapper mapperTest;
	private DtoCreatorMapper in ;
	private File clase1;
	private File clase2;
	

	@Before
	public void setUp() {
		clase2 = new File("C:\\Mapeos\\AccountT\\DtoIntAccountTransaction.java");
		clase1 = new File("C:\\Mapeos\\AccountT\\DtoAccountTransaction.java");
		in = new DtoCreatorMapper();
		in.setClase1(clase1);
		in.setClase2(clase2);
		in.setClaseName("AdressMapper");
		in.setPaquete("com.app.bbva.facade.v1.mapper");
		in.setVersion("v1");
		mapperTest = new CreatorTestMapper(in, new MainView());
	}
	
	
	@Test
	public void generarMapperTest(){
		System.out.println(mapperTest.generarPlantilla(null));
		System.out.println(System.getProperty("os.name"));
	}
}
