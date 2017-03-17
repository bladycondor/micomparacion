package org.fautapo.web.administrarProgramasEspecializados.buscarEstudianteDocente;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-23
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-23
*/

public class ListarInformesProceso1 implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    int iResultado; int iId_tramite; String sCadena = ""; Tramites datosTramite = new Tramites();
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_proceso = request.getParameter("id_proceso");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_tramite = request.getParameter("id_tramite");
    String sDescuento = request.getParameter("descuento");
    String sId_tipo_descuento = request.getParameter("id_tipo_descuento");

    String sId_persona = request.getParameter("id_persona");

    if ((sId_tramite == null) || ("".equals(sId_tramite))) {
      //Crear un tramite
      Tramites tramite = new Tramites();
      tramite.setId_proceso(Integer.parseInt(sId_proceso));
      tramite.setPara(cliente.getId_usuario());
      iId_tramite = this.mi.setInsertarTramiteLimbo(tramite); //CREA UN TRAMITE
      if (iId_tramite == 0) {
        return new ModelAndView("Error","mensaje","El trámite no se creó");
      }
    }
    else {
      iId_tramite = Integer.parseInt(sId_tramite);
    }
    System.out.println("id_estudiante--------->"+sId_estudiante);
    System.out.println("id_persona--------->"+sId_persona);

      //Sacamos los datos del Estudiante-Docente
      Personas datosEstudiante = new Personas();
      datosEstudiante.setDip(sId_estudiante);
      datosEstudiante = this.mi.getEstBuscarEstudianteDocente(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);

/*
    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
*/
    
    String sNombre_completo = datosEstudiante.getNombres()+" "+datosEstudiante.getPaterno()+" "+datosEstudiante.getMaterno();

    //Registramos los valores en wayka
    try {
      //Datos del Nombre
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("nombre_completo");
      datosTramite.setValor(sNombre_completo);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}


    try {
      //Datos del Identificador Id_etudiante
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("id_persona");
      datosTramite.setValor(sId_persona);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

/*
    try {
      //Datos de los Nombres
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("nombres");
      datosTramite.setValor(datosEstudiante.getPaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos del Paterno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("paterno");
      datosTramite.setValor(datosEstudiante.getPaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos del Materno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("materno");
      datosTramite.setValor(datosEstudiante.getMaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
*/
    try {
      //Datos del Materno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("dip");
      datosTramite.setValor(datosEstudiante.getDip());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
/*
    try {
      //Datos del Identificador Id_etudiante
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("id_estudiante");
      datosTramite.setValor(sId_estudiante);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
    
    try {
      //Datos del Programa
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("programa");
      datosTramite.setValor(datosEstudiante.getPrograma());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos de la Facultad
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("facultad");
      datosTramite.setValor(datosEstudiante.getFacultad());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
*/
    try {
      //Datos del Direccion
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("direccion");
      datosTramite.setValor(datosEstudiante.getDireccion());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos del Nacionalidad
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("nacionalidad");
      datosTramite.setValor(datosEstudiante.getNacionalidad());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos del telefono
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("tefeleno");
      datosTramite.setValor(datosEstudiante.getTelefono());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos del Celular
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("celular");
      datosTramite.setValor(datosEstudiante.getTelefono());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
     //Registramos los requisitos
      String sRequisitos[] = request.getParameterValues("id_tupla");
      for (int k=0; k < sRequisitos.length; k++) {
        if (sRequisitos[k] != null ) {
          sCadena = sCadena+"id_codigo:"+sRequisitos[k]+"###";
        }
      }
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("requisitos");
      datosTramite.setValor(sCadena);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Registramos el descuento
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("descuento");
      datosTramite.setValor(sDescuento);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
    
    try {
      //Registramos el descuento
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("id_tipo_descuento");
      datosTramite.setValor(sId_tipo_descuento);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
    

    //Registramos en tr_pr_fr_log
    iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);

    //Sacamos los datos del tramite
    datosTramite = new Tramites();
    datosTramite.setId_tramite(iId_tramite);
    datosTramite = this.mi.getBuscarTramite(datosTramite);
    modelo.put("datosTramite", datosTramite);
        
    //Sacamos la lista de informes de esta actividad
    List lInformes = this.mi.getListarInformesActividad(datosTramite);
    modelo.put("lInformes", lInformes);

    int iCantInformes = lInformes.size();
    modelo.put("cantInformes", Integer.toString(iCantInformes));

    String sNombreInforme = Integer.toString(iId_tramite)+"_"+cliente.getId_usuario(); 
    modelo.put("nombre_informe", sNombreInforme);

    System.out.println("id_persona--------->"+sId_persona);

    return new ModelAndView("administrarProgramasEspecializados/buscarEstudianteDocente/ListarInformesProceso1", modelo);
  }
}