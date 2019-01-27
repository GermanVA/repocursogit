package com.app.maps.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.lang.StringUtils;

import com.app.maps.controller.CreatorMapper;
import com.app.maps.dto.DtoCreatorMapper;

/**
 * 
 * @author German vargas angeles (fisckoer 28/11/2017)
 *	Vista para la generacion de mapeos de entrada facade -> businesss
 */
public class MainView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JButton button1;
	private JTextField clase1;
	private JTextField clase2;
	private JTextField paquete;
	private JTextField version;
	private JButton button2;
	private File path = null;
	private JTextField nombreClase;
	private File claseFile1;
	private File claseFile2;
	private JButton generar;
	private JButton creditos;
	private JTextArea mapper;
	private JTextArea logMapper;
	private JScrollPane scroll;
	private JScrollPane scrollLog;
	private CreatorMapper creatorMapper;
	private JCheckBox mapperTest;
	
	private static final int WIDTH=1440;
	private static final int HEIGHT=900;
	private double width;
	private double height;
	
	
	private static final int POS_X_INICIAL=200;
	private static final int POS_Y_INICIA_=100;
	
	

	public MainView() {
		super("Mapper Creator");
		initComponents();
		this.setVisible(true);

	}

	private void initComponents() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(200, 100, getAncho(900), getAlto(600));
		this.setLayout(null);
		
		label1 = new JLabel("Introdusca los siguientes datos para crear sus mapeos");
		label1.setBounds(0, 0, getAncho(500), getAlto(20));
		label1.setFont(setFonctGVA());
		this.add(label1);

		label2 = new JLabel("Paquete *");
		label2.setFont(setFonctGVA());
		label2.setBounds(0, 20, getAncho(200), getAlto(20));
		this.add(label2);
		paquete = new JTextField();
		paquete.setBounds(0, 40, getAncho(500), getAlto(20));
		paquete.setToolTipText("Ejemplo de paquete: com.app.mx");
		paquete.setFont(setFonctGVA());
		this.add(paquete);

		label3 = new JLabel("version *");
		label3.setFont(setFonctGVA());
		label3.setBounds(0, 60, getAncho(500), getAlto(20));
		this.add(label3);
		version = new JTextField();
		version.setFont(setFonctGVA());
		version.setBounds(0, 80, getAncho(50), getAlto(20));
		version.setToolTipText("Se usara para generar los nombres componentes ejemplo: mapper-v1");
		this.add(version);
		
		label6= new JLabel("Nombre de la clase mapper");
		label6.setFont(setFonctGVA());
		label6.setBounds(0,100,getAncho(200),getAlto(20));
		this.add(label6);
		nombreClase = new JTextField();
		nombreClase.setFont(setFonctGVA());
		nombreClase.setBounds(0, 120, getAncho(200), getAlto(20));
		this.add(nombreClase);

		label4 = new JLabel("Clase 1 *");
		label4.setFont(setFonctGVA());
		label4.setBounds(0, 140, getAncho(500), getAlto(20));
		this.add(label4);
		clase1 = new JTextField();
		clase1.setFont(setFonctGVA());
		clase1.setBounds(0, 160, getAncho(400), getAlto(20));
		clase1.setToolTipText("Se debe agregar la clase Dto facade");
		this.add(clase1);
		button1 = new JButton("browser");
		button1.setFont(setFonctGVA());
		button1.addActionListener(this);
		button1.setBounds(getX(401), 160, getAncho(100), getAlto(20));
		this.add(button1);

		label5 = new JLabel("Clase 2 *");
		label5.setFont(setFonctGVA());
		label5.setBounds(0, 180, getAncho(500), getAlto(20));
		this.add(label5);
		clase2 = new JTextField();
		clase2.setFont(setFonctGVA());
		clase2.setBounds(0, 200, getAncho(400), getAlto(20));
		clase2.setToolTipText("Se debe agregar la clase Dto business");
		this.add(clase2);
		button2 = new JButton("browser");
		button2.setFont(setFonctGVA());
		button2.addActionListener(this);
		button2.setBounds(getX(401), 200, getAncho(100), getAlto(20));
		this.add(button2);
		
		mapperTest = new JCheckBox("Generar mapper test");
		mapperTest.setFont(getFont());
		mapperTest.setSelected(true);
		mapperTest.setBounds(5, 220, getAncho(200), getAlto(20));
		this.add(mapperTest);
		
		generar = new JButton("Generar");
		generar.setFont(setFonctGVA());
		generar.addActionListener(this);
		generar.setBounds(0, 260, getAncho(100), getAlto(20));
		this.add(generar);
		mapper = new JTextArea(50,500);
		mapper.setFont(setFonctGVA());
		scroll = new JScrollPane(mapper);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(getX1(101), 260, getAncho(500), getAlto(300));
		this.add(scroll);
		logMapper = new JTextArea();
		logMapper.setFont(setFonctGVA());
		scrollLog = new JScrollPane(logMapper);
		scrollLog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollLog.setBounds(getX(610), 40, getAncho(300), getAlto(400));
		this.add(scrollLog);
		creditos = new JButton("Creditos");
		creditos.setBounds(0, 280, getAncho(100), getAlto(20));
		creditos.addActionListener(this);
		creditos.setFont(setFonctGVA());
		this.add(creditos);
		

	}

	public Font setFonctGVA(){
		if(this.width>this.WIDTH) {
			return new Font("Calibri", 3, 14);
		}else {
			return new Font("Calibri", 3, 12);
		}
	}
	
	private int getAlto(int height) {
//		System.out.println("getAlto()");
		//obtenermos el porcentage de la pantalla donde se realizo el desarrollo
		double heightPD=((double)height*100.0)/this.HEIGHT;
//		System.out.println("Porcentage pantalla desarrollo: "+heightPD);
		//obtenemos los pixeles de la pantalla donde se esta usando la aplicacion
		int heightPU=(int)((heightPD*this.height)/100);
//		System.out.println("Pixeles de la pantalla en la que se usa la APP: "+heightPU);
		return heightPU;
	}
	private int getAncho(int width) {
//		System.out.println("getAncho()");
		double widthPD=(double) (((double)width*100.0)/this.WIDTH);
//		System.out.println("Porcentage pantalla desarrollo: "+widthPD);
		int widthPU=(int)((widthPD*this.width)/100);
//		System.out.println("Pixeles de la pantalla en la que se usa la APP: "+widthPU);
		return widthPU;
	}
	
	private int getX(int x) {
		if(this.width>this.WIDTH) {
			return x+200;
		}else{
			return x;
		}
	}
	private int getX1(int x) {
		if(this.width>this.WIDTH) {
			return x+30;
		}else{
			return x;
		}
	}
	private int getY(int y) {
		return 0;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button1) {
			claseFile1 = loadFile();
			clase1.setText(claseFile1.getAbsolutePath());
		}
		if (e.getSource() == button2) {
			claseFile2 = loadFile();
			clase2.setText(claseFile2.getAbsolutePath());
		}
		if (e.getSource() == generar) {
			mapper.setText("");
			logMapper.setText("");
			if (validarDatosObligatorios()) {
				generarMapeo();
				
			}
		}
		if(e.getSource()==creditos) {
			JOptionPane.showMessageDialog(this,"Realizado por Germ�n Vargas Angeles \n german.vargas.angeles@gmail.com", "Creditos" ,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private boolean validarDatosObligatorios() {
		boolean valid = true;
		if (StringUtils.isBlank(paquete.getText())) {
			messageError("paquete",true);
			valid = false;
		} else if (StringUtils.isBlank(version.getText())) {
			messageError("version",true);
			valid = false;
		} else if (claseFile1 == null) {
			messageError("Clase 1",true);
			valid = false;
		} else if (claseFile2 == null) {
			messageError("Clase 2",true);
		}
		return valid;
	}

	private void messageError(String args,boolean simple) {
		String message = "";
		if(simple) {
			message = "El campo " + args + " es obligatorio";
		}else {
			message = args;
		}
		JOptionPane.showMessageDialog(this,message, "Ocurrio un error" ,
				JOptionPane.ERROR_MESSAGE);
	}

	private void generarMapeo() {
		System.out.println("Procesando el mapeo");
		DtoCreatorMapper dto = new DtoCreatorMapper();
		dto.setClase1(claseFile1);
		dto.setClase2(claseFile2);
		dto.setPaquete(paquete.getText());
		dto.setVersion(version.getText());
		dto.setClaseName(nombreClase.getText());
		dto.setGenerarMapperTest(mapperTest.isSelected());
		creatorMapper = new CreatorMapper(dto,this);
		try {
			String ruta=creatorMapper.manofuacturarMapeo();
			JOptionPane.showMessageDialog(this,"El mapeo se genero exitosamente y se guardo en "+ruta+"", "" ,
					JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e) {
			messageError("Ocurrio un erro inesperado: "+e.getMessage(), false);
		}
	}

	private File loadFile() {
		File file = null;

		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos java", "java");
		JFileChooser jfc = null;
		if (path == null) {
			jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		} else {
			jfc = new JFileChooser(path);
		}
		jfc.setFileFilter(filtro);
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			file = jfc.getSelectedFile();
			path = file.getParentFile();
//			System.out.println(file.getAbsolutePath());
		}
		return file;
	}

	public JTextArea getMapper() {
		return mapper;
	}

	public void setMapper(JTextArea mapper) {
		this.mapper = mapper;
	}

	public JTextArea getLogMapper() {
		return logMapper;
	}

	public void setLogMapper(JTextArea logMapper) {
		this.logMapper = logMapper;
	}
	

}
