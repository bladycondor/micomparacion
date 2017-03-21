package org.fautapo.web.reportesAcademicos.imprimirCertificadoNotasCode;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Literales;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Facultades;

import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeImageHandler;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class RegistrarCertificadoNotas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();
	
	
	
    //Recuperando variables del jsp
	String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
	String sId_programa = request.getParameter("id_programa");
    String sRu = request.getParameter("id_estudiante");
    String sNombres = request.getParameter("nombres");
	String sNro_recibo = cliente.getString(request, "nrocertificado");
	String sTodas = request.getParameter("todas");
    String sCi = request.getParameter("ci");
	String sFacultad = request.getParameter("facultad");
	String sPrograma = request.getParameter("programa");
	String sGradoAcademico = request.getParameter("gradoAcademico");
	String sPlan = request.getParameter("plan");
    /*//Votamos los datos*/
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_programa", sId_programa);
    //modelo.put("id_estudiante",sId_estudiante);
	modelo.put("nombres",sNombres);
    modelo.put("nrocertificado",sNro_recibo);
	modelo.put("todas",sTodas);
	modelo.put("sRu",sRu);
	//modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
	 	 
	
    
      //Buscamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
 // if ("".equals(sId_estudiante) ){
    //  return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/", modelo);
    //}
    
   // if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      Estudiantes datosEstudiante = new Estudiantes();
      //try {
      datosEstudiante.setId_estudiante(Integer.parseInt(sRu));
     // } catch(Exception e) {
      //  return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
     // }
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      datosEstudiante = this.mi.getEstBuscarEstudiantePrograma(datosEstudiante);
     // modelo.put("datosEstudiante", datosEstudiante);
	  
	  
     // if (datosEstudiante == null) {
       // return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/Aviso","mensaje","El estudiante con R.U. : "+ sId_estudiante + "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.");
     // }
      //datosEstudiante.setGestion(Integer.parseInt(sGestion));
     // datosEstudiante.setPeriodo(Integer.parseInt(sPeriodo));

       //Buscamos el grado_academico por programa e id_plan
      Libretas datosGrados = new Libretas();
      datosGrados.setId_programa(datosEstudiante.getId_programa());
      datosGrados.setId_plan(datosEstudiante.getId_plan());
      datosGrados = this.mi.getBuscarGradoAcademicoPrograma(datosGrados);
      modelo.put("datosGrados",datosGrados);

      //Sacamos los datos de la Facultad
      Facultades datosFacultad = new Facultades();
      datosFacultad.setId_facultad(datosPrograma.getId_facultad());
      datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
      modelo.put("datosFacultad", datosFacultad);

      //Sacamos los datos del Estudiante
      datosEstudiante = new Estudiantes();
      datosEstudiante.setId_estudiante(Integer.parseInt(sRu));
      datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
      modelo.put("datosEstudiante2", datosEstudiante);
    
      //Sacamos los datos de la institucion
      Instituciones datosInstitucion = new Instituciones();
      datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
      datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
      if (datosInstitucion !=null) {
      //  modelo.put("datosInstitucion", datosInstitucion);
      }

    Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
     modelo.put("datosInstitucionsede", datosInstitucionSede);
    }
	
     //Sacamos el formato de la fecha
      Abm formatoFecha = new Abm();
      formatoFecha.setCampo("formato_fecha");
      formatoFecha.setCodigo("dibrap");
    //  modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
	  
	// tabla: CERTIFICADOS GENERADOS
	
	 
	Estudiantes MaxCode = new Estudiantes();
	
    MaxCode.setid_sede(datosPrograma.getId_sede());
	MaxCode.setgestion_certificado(cliente.getGestion());
	
    int iMaxCode = this.mi.getBuscarMaxCertSede(MaxCode);
	
	System.out.println("MaxCode"+iMaxCode);
	int nro_cert=0;
	if(iMaxCode > 0){
	nro_cert=iMaxCode+1;
	System.out.println("if nroCode"+nro_cert);
	}
	else 
	nro_cert=1;
    
	System.out.println("nroCode"+nro_cert);
   
    modelo.put("MaxCode", MaxCode);
	System.out.println("ESTOY EN NOTASCODE1");
	int iResultado = 0;
	 // System.out.println("ESTOY EN CODE");
	  Estudiantes code = new Estudiantes();
      code.setid_sede(datosPrograma.getId_sede());	  
	  code.setnro_certificado(nro_cert);	
	  code.setgestion_certificado(cliente.getGestion());
	  code.setnro_transaccion(sNro_recibo);	
	  code.setFacultad(sFacultad);	  
	  code.setcarrera(sPrograma);	  
	  code.setnivel(sGradoAcademico);	 
	  code.setplanes(sPlan);
	  code.setperiodo_academico(sPeriodo+'/'+sGestion);
	  code.setRu(Integer.parseInt(sRu));
	  code.setCi(Integer.parseInt(sCi));
	  code.setestudiante(sNombres);
	//  code.setobservacion("ninguna");
	  code.setreimpresiones(0);
	  code.setUlt_usuario(cliente.getId_usuario());
	  System.out.println("ESTOY EN NOTASCODE2");
	  
	

   //modelo.put("nn", nn);
	  
/*System.out.println("CODE-> "+datosPrograma.getId_sede());
System.out.println("CODE-> "+nro_cert);
System.out.println("CODE-> "+cliente.getGestion());
System.out.println("CODE-> "+sNro_recibo);
System.out.println("CODE-> "+sFacultad);
System.out.println("CODE-> "+sPrograma);
System.out.println("CODE-> "+sGradoAcademico);
System.out.println("CODE-> "+sPlan);
System.out.println("CODE-> "+sPeriodo+'/'+sGestion);
System.out.println("CODE-> "+sRu);
System.out.println("CODE-> "+sCi);
System.out.println("CODE-> "+sNombres);
System.out.println("CODE-> "+sTodas);*/
	
	  iResultado = this.mi.setRegistrarCerGen(code); 
	 
	   //Sacamos los datos del certificado
	  List lNotas = new ArrayList();
	   
    /*  Estudiantes datosEstudiante1 = new Estudiantes();
     
     // datosEstudiante1 = this.mi.getEstBuscarEstudianteNombres(datosEstudiante1);
     // modelo.put("datosEstudiante1", datosEstudiante1);
	  
	  datosEstudiante1.setId_estudiante(Integer.parseInt(sRu));
	  datosEstudiante1.setgestion(Integer.parseInt(sGestion));
	  datosEstudiante1.setperiodo(Integer.parseInt(sPeriodo));
	  
	   
	  if ("Si".equals(sTodas)) {
        lNotas1 = this.mi.getListarCertificadoNotasTodas(datosEstudiante1);
		System.out.println("SI");
		System.out.println("lNotasIF "+lNotas1.size());
      }
      if ("No".equals(sTodas)) {
        lNotas1 = this.mi.getListarCertificadoNotasAprobadas(datosEstudiante1);
		System.out.println("NO");
		System.out.println("lNotas "+lNotas1.size());
      }*/
	     datosEstudiante = new Estudiantes();
      //try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sRu));
     // } catch(Exception e) {
      //  return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
     // }
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      datosEstudiante = this.mi.getEstBuscarEstudiantePrograma(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
     // if (datosEstudiante == null) {
       // return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/Aviso","mensaje","El estudiante con R.U. : "+ sId_estudiante + "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.");
     // }
      datosEstudiante.setGestion(Integer.parseInt(sGestion));
      datosEstudiante.setPeriodo(Integer.parseInt(sPeriodo));
	  

      //Sacamos los datos del certificado
      
	  if ("Si".equals(sTodas)) {
        lNotas = this.mi.getListarCertificadoNotasTodas2(datosEstudiante);
      }
      if ("No".equals(sTodas)) {
        lNotas = this.mi.getListarCertificadoNotasAprobadas2(datosEstudiante);
      }
	  
	  System.out.println("ru"+sRu);
	  System.out.println("periodo"+sPeriodo);
	  System.out.println("gestion"+sGestion);
	  System.out.println("todas"+sTodas);
	  System.out.println("lNotas"+lNotas.size());
	  System.out.println("ESTOY EN NOTASCODE3");
	  
      List lMateriasNotas = new ArrayList();
      for (int i=0; i<lNotas.size();i++) {
        Libretas datosLibreta = (Libretas) lNotas.get(i);
        Literales literal = new Literales();
	    datosLibreta.setLiteral(literal.convertNumber(datosLibreta.getNota()));
        lMateriasNotas.add(i, datosLibreta);
		
	    /*System.out.println(iResultado);
		System.out.println(Integer.toString(nro_cert)+'/'+Integer.toString(cliente.getGestion()));
		System.out.println(datosLibreta.getSigla());
		System.out.println(datosLibreta.getNivel_academico());
		System.out.println(datosLibreta.getMateria());
		System.out.println(datosLibreta.getNota());
		System.out.println(literal.convertNumber(datosLibreta.getNota()));
		System.out.println(datosLibreta.getTipo_evaluacion());*/
		
		int iResultadoCertNotas = 0;
		Estudiantes codecert = new Estudiantes();
		codecert.setid_certificados_generados(iResultado);
		codecert.setNro_certificado2(Integer.toString(nro_cert)+'/'+Integer.toString(cliente.getGestion()));
		codecert.setSigla(datosLibreta.getSigla());
		codecert.setnivel2(datosLibreta.getNivel_academico());
		codecert.setasignatura(datosLibreta.getMateria());
		codecert.setnumeral(datosLibreta.getNota());
		codecert.setliteral(literal.convertNumber(datosLibreta.getNota()));
		codecert.setTipo_evaluacion(datosLibreta.getTipo_evaluacion());
	   /* if(datosLibreta.getNota()>50){
		codecert.setobservacion("APROBADO");
		System.out.println("APROBADO");
		}if(datosLibreta.getNota()<51){
		codecert.setobservacion("REPROBADO");
		System.out.println("REPROBADO");
		}*/
		codecert.setId_estado("A");
		codecert.setUlt_usuario(cliente.getId_usuario());
		
	
         //Barcode barcode = BarcodeFactory.createCode128(Integer.toString(datoEst.getId_estudiante()));
		
     

	    iResultadoCertNotas = this.mi.setRegistrarCerGenNotas(codecert);
		
		
		/*System.out.println("0-> "+ lMateriasNotas.get(1));
		System.out.println("1-> "+ lMateriasNotas.get(2));
		System.out.println("2-> "+ lMateriasNotas.get(3));*/
		
		/*System.out.println("notas-> "+nro_cert);
		System.out.println("notas-> "+datosLibreta.getSigla());
		System.out.println("notas-> "+datosLibreta.getNivel());
		System.out.println("notas-> "+datosLibreta.getAsignatura());
		System.out.println("notas-> "+datosLibreta.getNota());
		System.out.println("notas-> "+literal.convertNumber(datosLibreta.getNota()));
		System.out.println("notas-> "+lNotas.getTipo_evaluacion());*/
      }
	  //BARBECUDE 
	  //*NOTA COLOCAR EL NOMBRE DE LA APLICACION
	  String sAplicacion="/moxos/";
	  String sNC=nro_cert+"/"+Integer.toString(cliente.getGestion());
	  String sNC1=nro_cert+"-"+Integer.toString(cliente.getGestion());
	  Barcode barcode = BarcodeFactory.createCode128(sRu+"-"+sNC);
		barcode.setBarHeight(50);
		barcode.setBarWidth(3);
		barcode.setDrawingText(false);
		barcode.setResolution(400);
		
	//barcode.setResolution(300);
	/*  OPTIONAL CONFIGS//
	* barcode.setBarWidth(arg0); 
	* barcode.setLabel(arg0);
	* barcode.setFont(arg0); 
	* barcode.setDrawingText(true);
	*/
         try 
		 {
			//CARPETA DE RESPALDO C:\Program Files\opt\Apache Software Foundation\Tomcat 5.5\webapps
			String fichero1 = "/opt/tomcat/webapps/CodigoBarra/CertificadoNotas/";
			FileOutputStream fos1 = new FileOutputStream(fichero1+"barcode_"+sNC1+".jpg");
			BarcodeImageHandler.outputBarcodeAsJPEGImage(barcode, fos1);
			//CARPETA EN LA APLICACION
			String fichero = "/opt/tomcat/webapps"+sAplicacion+"imagenes/CodigoBarra/CertificadoNotas/";
			FileOutputStream fos = new FileOutputStream(fichero+"barcode_"+sNC1+".jpg");
			BarcodeImageHandler.outputBarcodeAsJPEGImage(barcode, fos);
			modelo.put("ruta",sAplicacion+"imagenes/CodigoBarra/CertificadoNotas/"+"barcode_"+sNC1+".jpg");
		}
         catch (IOException e) 
		 {
			 System.out.println("ERROR AL GENERAR BARBECUE");
		 }
      modelo.put("NC",sNC);
	  modelo.put("NC1",sNC1);
      modelo.put("Ru",sRu);
		 // FIN BARBECUE
      modelo.put("lMateriasNotas", lMateriasNotas);
	  

    return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/RegistrarCertificadoNotas", modelo);
  }
}