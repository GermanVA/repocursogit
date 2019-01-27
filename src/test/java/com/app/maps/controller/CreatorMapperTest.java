package com.app.maps.controller;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.app.maps.dto.DtoCreatorMapper;
import com.app.maps.view.MainView;

public class CreatorMapperTest {

	private CreatorMapper mapper;
	private File clase1;
	private File clase2;
	private String paquete;
	private String version;
	private DtoCreatorMapper in ;
	
	@Before
	public void setUp() {
		clase2 = new File("/home/german/mappers/DtoIntAccount.java");
		clase1 = new File("/home/german/mappers/DtoAccount.java");
		in = new DtoCreatorMapper();
		in.setClase1(clase1);
		in.setClase2(clase2);
		in.setClaseName("AccountMapper");
		in.setPaquete("com.app.bbva.facade.v1.mapper");
		in.setVersion("v1");
		in.setGenerarMapperTest(true);
		mapper = new CreatorMapper(in,new MainView());
	}
	
	@Test
	public void test() {
		mapper.manofuacturarMapeo();
	}
}
