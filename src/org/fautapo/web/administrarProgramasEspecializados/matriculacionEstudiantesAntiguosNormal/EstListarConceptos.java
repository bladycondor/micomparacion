package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguosNormal;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class EstListarConceptos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int iDescuento=0; int iId_estudiante=0; String sId_perfil_proceso="0";
    Tramites datosTramite = new Tramites();
    int iId_tramite;int iResultado;String sCadena = "";
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    String sId_tramite = cliente.getString(request, "id_tramite");
    int iId_proceso = cliente.getInt(request, "id_proceso");
    //modelo.put("id_tramite", sId_tramite);
    modelo.put("id_proceso", Integer.toString(iId_proceso));
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));

    //WAYKA
    String sId_estudiante = request.getParameter("id_estudiante");
    if((sId_estudiante == null) || ("".equals(sId_estudiante))) return new ModelAndView("Error", "mensaje", "No existe el R.U. del estudiante");
    String sDescuento = request.getParameter("descuento");
    String sId_tipo_descuento = request.getParameter("id_tipo_descuento");

    if ((sId_tramite == null) || ("".equals(sId_tramite))) {
      //Crear un tramite
      Tramites tramite = new Tramites();
      tramite.setId_proceso(iId_proceso);
      tramite.setPara(cliente.getId_usuario());
      iId_tramite = this.mi.setInsertarTramiteLimbo(tramite); //CREA UN TRAMITE
      if (iId_tramite == 0) {
        return new ModelAndView("Error","mensaje","El trámite no se creó");
      }
    }
    else {
      iId_tramite = Integer.parseInt(sId_tramite);
      modelo.put("id_tramite",sId_tramite);
    }

    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    
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
      //Datos de los Nombres
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("prs_nombres");
      datosTramite.setValor(datosEstudiante.getPaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos del Paterno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("prs_paterno");
      datosTramite.setValor(datosEstudiante.getPaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos del Materno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("prs_materno");
      datosTramite.setValor(datosEstudiante.getMaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos del Materno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("prs_dip");
      datosTramite.setValor(datosEstudiante.getDip());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

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
      //Datos del Programa
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("direccion");
      datosTramite.setValor(datosEstudiante.getDireccion());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    try {
      //Datos del Programa
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("nacionalidad");
      datosTramite.setValor(datosEstudiante.getNacionalidad());
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
      //Registramos el id_tipo_descuento
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("id_tipo_descuento");
      datosTramite.setValor(sId_tipo_descuento);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    //Registramos en tr_pr_fr_log
    //iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);

    
    //FIN WAYKA

    //CAJAS
    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(iId_proceso);
    modelo.put("datosProceso", this.mi.getBuscarProceso(datosProceso));

    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_estudiante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    iId_estudiante = Integer.parseInt(tramite.getValores());
    
    
    
    Perfiles perfil = new Perfiles();
    perfil.setId_proceso(iId_proceso);
    List listaPerfiles = this.mi.getTrnMiListarPerfilesProceso(perfil);
    if (listaPerfiles.size() == 1) {
      perfil = (Perfiles) listaPerfiles.get(0);
      sId_perfil_proceso = perfil.getId_perfil_proceso();
    } else {
      tramite.setEtiqueta("id_perfil_proceso");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      sId_perfil_proceso = tramite.getValores();
    }

    //Sacamos los datos del estudiante
    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_estudiante(iId_estudiante);
    modelo.put("estudiante", this.mi.getEstBuscarEstudianteNombres(estudiante));

    //Sacamos los datos del perfil_proceso
    Perfiles datosPerfilProceso = new Perfiles();
    datosPerfilProceso.setId_perfil_proceso(sId_perfil_proceso);
    datosPerfilProceso = this.mi.getPrcBuscarPerfil(datosPerfilProceso);

    //Sacamos los datos del perfil
    Perfiles datosPerfil = new Perfiles();
    datosPerfil.setId_perfil(datosPerfilProceso.getId_perfil());
    datosPerfil = this.mi.getPrfBuscarPerfil(datosPerfil);
    modelo.put("datosPerfil", datosPerfil);

    try {
      tramite.setEtiqueta("descuento");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      iDescuento = Integer.parseInt(tramite.getValores());
    } catch(Exception e) {};

    perfil.setId_estudiante(iId_estudiante);
    perfil.setId_perfil_proceso(sId_perfil_proceso);
    perfil.setDescuento(iDescuento);

    List listaConceptos = this.mi.getEstListarConceptos(perfil);
    modelo.put("listaConceptos", listaConceptos);
    float total = 0;
    for (int i = 0; i < listaConceptos.size(); i++){
      Perfiles cajita = (Perfiles) listaConceptos.get(i);
      total += cajita.getCosto() - cajita.getDescuento();
    }
    modelo.put("total", String.valueOf(total));

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    //FIN CAJAS
    modelo.put("id_tramite", Integer.toString(iId_tramite));
    return new ModelAndView("administrarProgramasEspecializados/cajas/EstListarConceptos", modelo);
  }
}