package com.app.maps.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
/**
 * Clase que se utiliza para tratar los archivos de entrada
 * @author German vargas angeles (fisckoer 28/11/2017)
 *
 */
public class Archivo {

	
	private static final String LINUX="LINUX";
	/**
	 * Metodo que se utiliza para leer los archivos y obtener los atributos de cada uno 
	 * @param file archivo que se tratara
	 * @return lista de atributos 
	 */
	public static List<String> leerArchivo(File file) {
		FileReader fr;
		List<String> list = new ArrayList<String>();
		HashSet<String> sinRepetir = new HashSet<String>();
		
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.contains("private")) {
					list.add(linea);
					sinRepetir.add(linea);
				}
			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			FileNotFoundException noFound = e;
			try {
				throw noFound;
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			try {
				throw e;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println(list);
		return list;
	}

	/**
	 * Metodo que nos guarda el archivo 
	 * @param nombre nombre del archivo
	 * @param archivo contenido del archivo 
	 * @param path ruta del archivo 
	 * @throws IOException 
	 */
	public static void guardarArchivo(String nombre, String archivo, String path) throws IOException {
		if(LINUX.equalsIgnoreCase(System.getProperty("os.name"))){
			path = path+"/mapper/";
			nombre =path+ nombre + ".java";
		}else{
			path = path +"\\mapper\\";
			nombre =path+ nombre + ".java";
		}
			
		File file = new File(nombre);
		File file2 = new File(path);
		if(!file2.exists()) {
			file2.mkdirs();
		}
		file2=null;
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(file));
		bw.write(archivo);
		bw.close();
	}
}
