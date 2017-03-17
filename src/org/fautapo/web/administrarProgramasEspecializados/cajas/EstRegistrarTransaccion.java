package org.fautapo.web.administrarProgramasEspecializados.cajas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Literales;
import org.fautapo.domain.Instituciones;

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
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class EstRegistrarTransaccion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    int iResultado = 0; double iDescuento = 0; int iId_tipo_descuento = 0; int iGestion = 0; int iPeriodo = 0; int iCantidad = 0; 
	int iId_tipo_clasificacion=0;
    String sId_perfil_procesox = ""; int iNro_recibo = 0;
    
    int iId_tramite = cliente.getInt(request, "id_tramite");
    int iId_proceso = cliente.getInt(request, "id_proceso");
    modelo.put("id_tramite", Integer.toString(iId_tramite));

    //INICIO - Verificamos que el tramite no haya sido atendido
    Tramites datosTramite = new Tramites();
    datosTramite.setId_tramite(iId_tramite);
    datosTramite = this.mi.getBuscarTramite(datosTramite);
	
   //Aqui es para sacar el Tramite	
	if(iId_proceso==27){
	   modelo.put("datosTramite", datosTramite);   
    }
    if ("L".equals(datosTramite.getId_estado())) {
      modelo.put("mensaje", "El tramite ya fue atendido");
      return new ModelAndView("administrarProgramasEspecializados/cajas/Error", modelo);
    }
    //FIN -Verificamos que el tramite no haya sido atendido

    //Sacamos el id_estudiante de los datos de wayka
    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_estudiante");
    tramite = this.mi.getBuscarCampoGw(tramite);
    int iId_estudiante = Integer.parseInt(tramite.getValores());

    System.out.println("id_estudiante-------"+iId_estudiante);
    
    String sId_perfil_proceso = "";
    Perfiles perfil = new Perfiles();
    perfil.setId_proceso(iId_proceso);
    List listaPerfiles = this.mi.getTrnMiListarPerfilesProceso(perfil);
    if (listaPerfiles.size() == 1) {
      perfil = (Perfiles) listaPerfiles.get(0);
      sId_perfil_proceso = perfil.getId_perfil_proceso();
      //Buscamos en wayka si hay un campo cantidad
      try {
        tramite = new Tramites();
        tramite.setId_tramite(iId_tramite);
        tramite.setEtiqueta("cantidad");
        tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
        int iCantidadx = Integer.parseInt(tramite.getValores());
        sId_perfil_proceso = sId_perfil_proceso+"~"+iCantidadx;
      } catch(Exception e) {};
    } else {
      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
      tramite.setEtiqueta("id_perfil_proceso");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      sId_perfil_proceso = tramite.getValores();
    }

    String sPerfiles_procesos[] = sId_perfil_proceso.split(":");
    try {
      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
      tramite.setEtiqueta("descuento");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      iDescuento = Double.valueOf(tramite.getValores()).doubleValue();

      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
      tramite.setEtiqueta("id_tipo_descuento");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      iId_tipo_descuento = Integer.parseInt(tramite.getValores());
      //Averiguamos el nombre del tipo de descuento
      Perfiles descuento = new Perfiles();
      descuento.setId_tipo_descuento(iId_tipo_descuento);
      modelo.put("descuento", this.mi.getTrnBuscarTipoDescuento(descuento));
    } catch(Exception e) {
      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
    }
    //Sacamos los datos del estudiante
    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_estudiante(iId_estudiante);
    estudiante = this.mi.getEstBuscarEstudianteNombres(estudiante);
    modelo.put("estudiante", estudiante);

    //Sacamos la gestion y periodo
    try {
      Tramites tramite1 = new Tramites();
      tramite1.setId_tramite(iId_tramite);
      tramite1.setEtiqueta("gestion_matricula");
      tramite1 = (Tramites) this.mi.getBuscarCampoGw(tramite1);
      iGestion = Integer.parseInt(tramite1.getValores());

      tramite1 = new Tramites();
      tramite1.setId_tramite(iId_tramite);
      tramite1.setEtiqueta("periodo_matricula");
      tramite1 = (Tramites) this.mi.getBuscarCampoGw(tramite1);
      iPeriodo = Integer.parseInt(tramite1.getValores());
    } catch(Exception e) {}
    if (iGestion==0 || iPeriodo==0) {
      iGestion = cliente.getGestion();
      iPeriodo = cliente.getPeriodo();
    }
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    
    tramite = (Tramites) this.mi.getBuscarTramite(tramite);
    	
    Perfiles transaccion = new Perfiles();
    Perfiles trn_detalle = new Perfiles();
    String sTransacciones = "";
    for (int i=0; i < sPerfiles_procesos.length; i++) {
      String sDatos_perfil_proceso[] = sPerfiles_procesos[i].split("~");
      if (sDatos_perfil_proceso.length == 1) {
        sId_perfil_procesox = sPerfiles_procesos[i];
        System.out.println("id_perfil_proceso-if-for-----"+sId_perfil_procesox);
	iCantidad = 1;
      } else {
        sId_perfil_procesox = sDatos_perfil_proceso[0];
        System.out.println("id_perfil_proceso-else-for------"+sId_perfil_procesox);
	iCantidad = Integer.parseInt(sDatos_perfil_proceso[1]);
        System.out.println("cantidad-else-for-----"+iCantidad);    
      }
      // tabla: transacciones
      transaccion.setId_perfil_proceso(sId_perfil_procesox);
      transaccion = this.mi.getPrcBuscarPerfil(transaccion);
      transaccion.setId_estudiante(iId_estudiante);
      transaccion.setId_persona(estudiante.getId_persona());
      transaccion.setId_programa(estudiante.getId_programa());
      transaccion.setId_tipo_descuento(iId_tipo_descuento);
      transaccion.setId_sede(cliente.getId_almacen());
      transaccion.setRemitente(tramite.getDe());
      transaccion.setCantidad(iCantidad);
      transaccion.setDeposito(0);
      transaccion.setGestion(iGestion);
      transaccion.setPeriodo(iPeriodo);
      transaccion.setUlt_usuario(cliente.getId_usuario());
      System.out.println("trn_id_estudiante-----"+transaccion.getId_estudiante());
      System.out.println("trn_id_persona------"+transaccion.getId_persona());
      System.out.println("trn_id_programa-----"+transaccion.getId_programa());
      System.out.println("trn_id_tipo_descuento-----"+transaccion.getId_tipo_descuento());
      System.out.println("trn_id_sede-----"+transaccion.getId_sede());
      System.out.println("trn_Remitente-----"+transaccion.getRemitente());
      System.out.println("trn_cantidad-----"+transaccion.getCantidad());
      System.out.println("trn_gestion-----"+transaccion.getGestion());
      System.out.println("trn_periodo-----"+transaccion.getPeriodo());
      System.out.println("trn_ult_usuario-----"+transaccion.getUlt_usuario());
      iResultado = this.mi.setPrsRegistrarTransaccion(transaccion);
      System.out.println("trn_iResultado-----"+iResultado);
      sTransacciones += ":" + iResultado;
      System.out.println("trn_transacciones-----"+sTransacciones);
       
	    
      //tabla: trn_detalles
      transaccion.setDescuento(iDescuento);
      transaccion.setId_perfil_proceso(sPerfiles_procesos[i]);
      System.out.println("trn_descuento-----"+transaccion.getDescuento());
      System.out.println("trn_id_perfil_proceso-----"+transaccion.getId_perfil_proceso());
      List listaConceptos = this.mi.getEstListarConceptos(transaccion);
      System.out.println("tamanio_lista-----"+listaConceptos.size());
      if (iResultado > 0) {
        for (int j=0; j < listaConceptos.size(); j++) {
          Perfiles cajita = (Perfiles) listaConceptos.get(j);
  	  trn_detalle.setId_transaccion(iResultado);
	  trn_detalle.setId_perfil(transaccion.getId_perfil());
	  trn_detalle.setId_concepto(cajita.getId_concepto());
          trn_detalle.setId_tipo_perfil(transaccion.getId_tipo_perfil());
	  trn_detalle.setId_tipo_clasificacion(cajita.getId_tipo_clasificacion());
	  iId_tipo_clasificacion=cajita.getId_tipo_clasificacion();
	  trn_detalle.setCosto(cajita.getCosto());
	  trn_detalle.setCantidad(cajita.getCantidad());
          trn_detalle.setDescuento(cajita.getDescuento());
	  trn_detalle.setUlt_usuario(cliente.getId_usuario());
          System.out.println("detalle_id_transaccion-----"+trn_detalle.getId_transaccion());
          System.out.println("detalle_id_perfil-----"+trn_detalle.getId_perfil());
          System.out.println("detalle_id_concepto-----"+trn_detalle.getId_concepto());
          System.out.println("detalle_id_tipo_perfil-----"+trn_detalle.getId_tipo_perfil());
          System.out.println("detalle_id_tipo_clasificacion-----"+trn_detalle.getId_tipo_clasificacion());
          System.out.println("detalle_costo-----"+trn_detalle.getCosto());
          System.out.println("detalle_cantidad-----"+trn_detalle.getCantidad());
          System.out.println("detalle_descuento-----"+trn_detalle.getDescuento());
          System.out.println("detalle_ult_usuario-----"+trn_detalle.getUlt_usuario());
          int iDetalle = this.mi.setRegistrarTrnDetalle(trn_detalle);
          System.out.println("detalle_iDetalle-----"+iDetalle);
        }
      }
    }

    transaccion.setProceso(sTransacciones.substring(1));
    List lTransacciones = this.mi.getTrnListarTransacciones(transaccion);
    if (lTransacciones.size() > 0) {
      //Sacamos el numero de recibo
      Perfiles datosRecibo = new Perfiles();
      datosRecibo.setId_sede(cliente.getId_almacen());
      //datosRecibo.setGestion(iGestion);
	  datosRecibo.setGestion(cliente.getGestion());
      iNro_recibo = this.mi.getTrnBuscarSiguienteNroRecibo(datosRecibo);
    }
    List lTransacciones2 = new ArrayList();
    for (int i=0; i<lTransacciones.size(); i++) {
      Perfiles auxiliar = (Perfiles) lTransacciones.get(i);
      auxiliar.setNro_recibo(iNro_recibo+"/"+cliente.getGestion());
      this.mi.setTrnActualizarNroRecibo(auxiliar);
      lTransacciones2.add(auxiliar);
    }
    modelo.put("lTransacciones", lTransacciones2);
    
    //String sId_perfil_proceso = "";
    double total = 0;
    for (int i=0; i < lTransacciones.size(); i++) {
      transaccion = (Perfiles) lTransacciones.get(i);
      total += transaccion.getPagado();
    }
    Literales literal = new Literales();
    modelo.put("literal", literal.convert(total));
    modelo.put("total", String.valueOf(total));

    transaccion.setId_perfil_proceso(sId_perfil_proceso);
    List lPerfiles = this.mi.getTrnPrcListarPerfiles(transaccion);
    modelo.put("lPerfiles", lPerfiles);

    //Sacamos el listado de trn_detalles
    transaccion.setProceso(sTransacciones.substring(1));
    List lDetalles = this.mi.getTrnListarTrnDetalles2(transaccion);
    modelo.put("lDetalles", lDetalles);

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos el formato de la hora
    Abm formatoHora = new Abm();
    formatoHora.setCampo("formato_hora");
    formatoHora.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
    
    Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
      modelo.put("datosInstitucionsede", datosInstitucionSede);
    }

	//---Guarda las Matriculas
	if (iId_estudiante > 0) {
	 if ((iId_proceso==4) || (iId_proceso==3)||(iId_proceso==25)){
	 
      Estudiantes datoEst = new Estudiantes();
      datoEst.setId_estudiante(iId_estudiante);
      //datoEst.setGestion(cliente.getGestion());
      //datoEst.setPeriodo(cliente.getPeriodo());
      datoEst.setGestion(iGestion);
      datoEst.setPeriodo(iPeriodo);
      
	  datoEst.setUlt_usuario(cliente.getId_usuario());
	  datoEst.setId_tipo_descuento(iId_tipo_descuento);
	  datoEst.setTransaccion(iNro_recibo+"/"+cliente.getGestion());
      datoEst.setId_tipo_clasificacion(iId_tipo_clasificacion); //(cajita.getId_tipo_clasificacion());		

		
	  datoEst.setId_rol(cliente.getId_rol());      
      int iId_matricula_resultado = this.mi.setRegistrarMatriculaEstudiante(datoEst);
      
      if (iId_matricula_resultado > 0) {
        //Buscamos la matricula
	    datoEst.setId_matricula(iId_matricula_resultado);
        datoEst = this.mi.getBuscarMatriculaEstPrs(datoEst);
        modelo.put("datoEst",datoEst);    
	    modelo.put("id_matricula", Integer.toString(iId_matricula_resultado));
        modelo.put("clave","123456"); //Por defecto
        //Registramos los accesos del estudiante
        Estudiantes datoAcceso = new Estudiantes();
        datoAcceso.setId_matricula(iId_matricula_resultado);
        //datoAcceso.setApodo(Integer.toString(iId_matricula_resultado));
		datoAcceso.setApodo(Integer.toString(iId_estudiante));		
        //datoAcceso.setClave("123456"); //Por defecto
		datoAcceso.setClave(datoEst.getDip()); //Por defecto
        datoAcceso.setId_rol(cliente.getId_rol());
        datoAcceso.setUlt_usuario(cliente.getId_usuario());
        int iResultadoAcceso = this.mi.setRegistrarApodoClaveMatricula(datoAcceso);      
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
	
        return new ModelAndView("Error","mensaje","El estudiante ya esta matriculado para la gestion"+Integer.toString(cliente.getGestion())+" y el periodo "+Integer.toString(cliente.getPeriodo()));
      }
     }    
    }
    
	//---Finde las grabar Matriculas
	
    tramite.setUlt_usuario(cliente.getId_usuario());
    this.mi.setRegistrarTrPrFrLogLimbo(tramite);

    return new ModelAndView("administrarProgramasEspecializados/cajas/EstListarConceptosImpresion", modelo);
  }
}