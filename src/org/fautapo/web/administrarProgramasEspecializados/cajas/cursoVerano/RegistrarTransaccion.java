package org.fautapo.web.administrarProgramasEspecializados.cajas.cursoVerano;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Literales;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Materias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class RegistrarTransaccion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Tramites datosTramite = new Tramites();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();
    modelo.put("cliente", cliente);

    String vId_perfil_materia[] = request.getParameterValues("id_perfil_materia");
    int iId_estudiante = cliente.getInt(request, "id_estudiante");
    int iId_perfil = cliente.getInt(request, "id_perfil");
    int iId_proceso = cliente.getInt(request, "id_proceso");
    int iGestion1 = cliente.getGestion();//cliente.getInt(request, "gestion");
    int iPeriodo1 = cliente.getPeriodo();//cliente.getInt(request, "periodo");

    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");

    int iResultado; int iNro_recibo = 0;
    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(iId_proceso);
    modelo.put("proceso", this.mi.getBuscarProceso(datosProceso));
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));

    //Crear un tramite
    Tramites tramite = new Tramites();
    tramite.setId_proceso(iId_proceso);
    tramite.setPara(cliente.getId_usuario());
    int iId_tramite = this.mi.setInsertarTramiteLimbo(tramite); //CREA UN TRAMITE

    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_estudiante(iId_estudiante);
    estudiante = this.mi.getEstBuscarEstudianteNombres(estudiante);
    modelo.put("estudiante", estudiante);
    
    List lMaterias = new ArrayList();
    double total = 0;
    for (int i=0; i < vId_perfil_materia.length; i++) {
      Perfiles datosPerfilMateria = new Perfiles();
      datosPerfilMateria.setId_perfil_materia(Integer.parseInt(vId_perfil_materia[i]));
      datosPerfilMateria = this.mi.getTrnBuscarPerfilMateria(datosPerfilMateria);
      // inicio - Recupar las materas elegidas
      Materias materia = new Materias();
      materia.setId_materia(datosPerfilMateria.getId_materia());
      materia = this.mi.getMtrBuscarMateria(materia);

      // REGISTRAR EN est_programaciones
      Estudiantes datosEstudiante = new Estudiantes();
      datosEstudiante.setId_estudiante(iId_estudiante);
      datosEstudiante.setId_materia(materia.getId_materia());
      datosEstudiante.setId_grupo(1); //Por defecto
      datosEstudiante.setId_tipo_evaluacion(datosPerfilMateria.getId_tipo_evaluacion());
      datosEstudiante.setGestion(iGestion);
      datosEstudiante.setPeriodo(iPeriodo);
      datosEstudiante.setId_rol(cliente.getId_rol());
      datosEstudiante.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarEstProgramacionTipo(datosEstudiante);
      if (iResultado == 1) {
        total += datosPerfilMateria.getCosto();
        lMaterias.add(materia);
      }
    }
    modelo.put("lMaterias", lMaterias);
    // fin - Recupar las materas elegidas

    Perfiles perfil = new Perfiles();
    perfil.setId_proceso(iId_proceso);
    List listaPerfiles = this.mi.getTrnMiListarPerfilesProceso(perfil);
    String sId_perfil_proceso="0";
    if (listaPerfiles.size() == 1) {
      perfil = (Perfiles) listaPerfiles.get(0);
      sId_perfil_proceso = perfil.getId_perfil_proceso();
    } else {
      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
      tramite.setEtiqueta("id_perfil_proceso");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      sId_perfil_proceso = tramite.getValores();
    }
    
    Perfiles transaccion = new Perfiles();
    Perfiles trn_detalle = new Perfiles();
    String sTransacciones = "";
    String sPerfiles_procesos[] = sId_perfil_proceso.split(":");
    for (int i=0; i < sPerfiles_procesos.length; i++) {
      transaccion.setId_perfil_proceso(sPerfiles_procesos[i]);
      transaccion = this.mi.getPrcBuscarPerfil(transaccion);
      transaccion.setId_estudiante(iId_estudiante);
      transaccion.setId_persona(estudiante.getId_persona());
      transaccion.setId_programa(estudiante.getId_programa());
      transaccion.setId_tipo_descuento(0);
      transaccion.setId_sede(cliente.getId_almacen());
      transaccion.setRemitente(cliente.getId_usuario());
      transaccion.setDeposito(0);
      transaccion.setGestion(iGestion1);
      transaccion.setPeriodo(iPeriodo1);
      transaccion.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setPrsRegistrarTransaccion(transaccion);
      sTransacciones += ":" + iResultado;

      transaccion.setDescuento(0);
      transaccion.setId_perfil_proceso(sPerfiles_procesos[i]);
      List listaConceptos = this.mi.getEstListarConceptos(transaccion);
      if (iResultado > 0) {
        for (int j=0; j < listaConceptos.size(); j++) {
          Perfiles cajita = (Perfiles) listaConceptos.get(j);
  	  trn_detalle.setId_transaccion(iResultado);
	  trn_detalle.setId_perfil(transaccion.getId_perfil());
	  trn_detalle.setId_concepto(cajita.getId_concepto());
          trn_detalle.setId_tipo_perfil(transaccion.getId_tipo_perfil());
	  trn_detalle.setId_tipo_clasificacion(cajita.getId_tipo_clasificacion());
	  trn_detalle.setCantidad(1);
	  trn_detalle.setCosto(total);
          trn_detalle.setDescuento(cajita.getDescuento());
	  trn_detalle.setUlt_usuario(cliente.getId_usuario());
          int iDetalle = this.mi.setRegistrarTrnDetalle(trn_detalle);
        }
      }
    }
    transaccion.setProceso(sTransacciones.substring(1));
    List lTransacciones = this.mi.getTrnListarTransacciones(transaccion);
    if (lTransacciones.size() > 0) {
      //Sacamos el numero de recibo
      Perfiles datosRecibo = new Perfiles();
      datosRecibo.setId_sede(cliente.getId_almacen());
      datosRecibo.setGestion(iGestion1);
      iNro_recibo = this.mi.getTrnBuscarSiguienteNroRecibo(datosRecibo);
    }
    List lTransacciones2 = new ArrayList();
    for (int i=0; i<lTransacciones.size(); i++) {
      Perfiles auxiliar = (Perfiles) lTransacciones.get(i);
      auxiliar.setNro_recibo(iNro_recibo+"/"+iGestion1);
      this.mi.setTrnActualizarNroRecibo(auxiliar);
      lTransacciones2.add(auxiliar);
    }
    modelo.put("lTransacciones", lTransacciones2);
    
    //String sId_perfil_proceso = "";
    total = 0;
    for (int i=0; i < lTransacciones.size(); i++) {
      transaccion = (Perfiles) lTransacciones.get(i);
      total += transaccion.getPagado();
      //sId_perfil_proceso += ":" + transaccion.getId_perfil();
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
    
    //Registramos los datos en wayka
    String sNombre_completo = estudiante.getNombres()+" "+estudiante.getPaterno()+" "+estudiante.getMaterno();
    //Registramos los valores en wayka
    try {
      //Datos del Nombre completo
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("nombre_completo");
      datosTramite.setValor(sNombre_completo);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
    
    try {
      //Datos del Nombre
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("nombres");
      datosTramite.setValor(estudiante.getNombres());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
    
    try {
      //Datos del paterno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("paterno");
      datosTramite.setValor(estudiante.getPaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
    
    try {
      //Datos del materno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("materno");
      datosTramite.setValor(estudiante.getMaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}
    
    try {
      //Datos del Nombre
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("id_estudiante");
      datosTramite.setValor(Integer.toString(estudiante.getId_estudiante()));
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    } catch(Exception e) {}

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
    
    tramite.setId_tramite(iId_tramite);
    tramite.setUlt_usuario(cliente.getId_usuario());
    this.mi.setRegistrarTrPrFrLogLimbo(tramite);

    return new ModelAndView("administrarProgramasEspecializados/cajas/cursoVerano/RegistrarTransaccion", modelo);
  }
}