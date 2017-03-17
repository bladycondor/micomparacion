package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesNuevosPost;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import javax.swing.*;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeImageHandler;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class RegistrarMatriculaEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }


 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Para wayka
    String sId_proceso = request.getParameter("id_proceso");
    String sTitulo = request.getParameter("titulo");
    String sId_tramite = request.getParameter("id_tramite");
    String sAplicacion = request.getParameter("aplicacion");
    modelo.put("id_tramite",sId_tramite);
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    modelo.put("aplicacion", sAplicacion);
    int iResultadoDoc=0;
     //Sacando el el dato de wayka
    if (Integer.parseInt(sId_tramite) == 0) {
        return new ModelAndView("Error","mensaje","El tramite no ha pasado");
    } 
    int iId_tramite = cliente.getInt(request, "id_tramite");
    modelo.put("id_tramite", Integer.toString(iId_tramite));
    //Buscamos el id_estudiante
    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_estudiante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_estudiante = Integer.parseInt(tramite.getValores());
    //Sacando la gestion de wayka
    tramite.setEtiqueta("gestion_matricula");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iGestion = Integer.parseInt(tramite.getValores());
    //Sacando el periodo de wayka  
    tramite.setEtiqueta("periodo_matricula");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iPeriodo = Integer.parseInt(tramite.getValores());
    //Sacando el tipo_descuento de wayka
    tramite.setEtiqueta("id_tipo_descuento");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_tipo_descuento = Integer.parseInt(tramite.getValores());
    
    if (iId_estudiante > 0) {
      Estudiantes datoEst = new Estudiantes();
      datoEst.setId_estudiante(iId_estudiante);
      datoEst.setGestion(iGestion);
      datoEst.setPeriodo(iPeriodo);
      datoEst.setId_tipo_descuento(iId_tipo_descuento);
      datoEst.setId_rol(cliente.getId_rol());
      datoEst.setUlt_usuario(cliente.getId_usuario());
      int iId_matricula_resultado = this.mi.setRegistrarMatriculaEstudiante(datoEst);
      
      if (iId_matricula_resultado > 0) {
        //Buscamos la matricula
	datoEst.setId_matricula(iId_matricula_resultado);
        datoEst = this.mi.getBuscarMatriculaEstPrs(datoEst);
        modelo.put("datoEst",datoEst);    
	modelo.put("id_matricula", Integer.toString(iId_matricula_resultado));
        modelo.put("clave","123456"); //Por defecto
        //Registramos el acceso del estudiante
        Estudiantes datoAcceso = new Estudiantes();
        datoAcceso.setId_matricula(iId_matricula_resultado);
        datoAcceso.setApodo(Integer.toString(iId_matricula_resultado));
        datoAcceso.setClave("123456"); //Por defecto
        datoAcceso.setId_rol(cliente.getId_rol());
        datoAcceso.setUlt_usuario(cliente.getUlt_usuario());
        int iResultadoAcceso = this.mi.setRegistrarApodoClaveMatricula(datoAcceso);
        
        //Registramos los valores de id_perfil en wayka
        //Datos de id_matricula
        Tramites datosTramite = new Tramites();
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setEtiqueta("id_matricula");
        datosTramite.setValor(Integer.toString(iId_matricula_resultado));
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        int iResultado1 = this.mi.setRegistrarValorLimbo2(datosTramite);
	
	//Sacamos los datos del tramite
	//Tramites datosTramite = new Tramites();
        datosTramite = new Tramites();
	datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite = this.mi.getBuscarTramite(datosTramite);
        
	//Sacamos los datos del formulario
	Campos datosForm = new Campos();
	datosForm.setId_proceso(datosTramite.getId_proceso());
	datosForm = this.mi.getBuscarFormulario1(datosForm);

        //insertamos los datos en la tabla tr_fr_log para verficar que el usuario reviso el formulario
        Tramites datosFrLog = new Tramites();
        datosFrLog.setId_tramite(Integer.parseInt(sId_tramite)); 
        datosFrLog.setId_proceso(datosTramite.getId_proceso());
        datosFrLog.setId_form(datosForm.getId_form());
        datosFrLog.setId_actividad(datosTramite.getId_actividad_actual());
        datosFrLog.setId_estado("R");
        datosFrLog.setUlt_usuario(cliente.getId_usuario());
        int iResultado = this.mi.setInsertarFrLog(datosFrLog);
      }
      else{
        //Buscamos si ya eata registrado
	tramite = new Tramites();
        tramite.setId_tramite(iId_tramite);
        tramite.setEtiqueta("id_matricula");
        tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
        int iId_matricula = Integer.parseInt(tramite.getValores());
	//Buscamos la matricula
	datoEst.setId_matricula(iId_matricula);
        datoEst = this.mi.getBuscarMatriculaEstPrs(datoEst);
        modelo.put("datoEst",datoEst);    
	modelo.put("id_matricula", Integer.toString(iId_matricula_resultado));
	
        //return new ModelAndView("Error","mensaje","El estudiante ya esta matriculado para la gestion"+Integer.toString(cliente.getGestion())+" y el periodo "+Integer.toString(cliente.getPeriodo()));
      }
      String sMensaje="Se realizo el registro";
      modelo.put("mensaje",sMensaje);
      
      //BARBECUDE 
      Barcode barcode = BarcodeFactory.createCode128(Integer.toString(datoEst.getId_estudiante()));
      try {
        // Necesitamos un canal de salida donde escribir la imagen
	String fichero = "/opt/tomcat/webapps"+sAplicacion+"adjuntosMi/barcodeEstudiantes/";
	FileOutputStream fos = new FileOutputStream(fichero+"barcode_"+Integer.toString(datoEst.getId_estudiante())+".jpg");
	//Permite que la utilidad de imagenes de Barbecue haga todo el trabajo sucio
	BarcodeImageHandler.outputBarcodeAsJPEGImage(barcode, fos);
	modelo.put("ruta",sAplicacion+"adjuntosMi/barcodeEstudiantes/"+"barcode_"+Integer.toString(datoEst.getId_estudiante())+".jpg");
      }
      catch (IOException e) {
	// Gestion de errores
      }
      // FIN BARBECUE
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesNuevosPost/VerDatosMatriculaEstudiante", modelo);
    }
    
    
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesNuevosPost/EntradaBuscarPostulantes", modelo);
  }
}
