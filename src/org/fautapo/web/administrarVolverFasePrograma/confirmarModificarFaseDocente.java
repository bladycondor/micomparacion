package org.fautapo.web.administrarVolverFasePrograma;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;   
import org.fautapo.domain.Docentes; 

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-08
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-08
 */

public class confirmarModificarFaseDocente implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    String sId_tipo_nota_s     = "";  
    
    
    //Recuperamos las variables
    int iGestion = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo = Integer.parseInt(request.getParameter("periodo"));
    int iId_programa = Integer.parseInt(request.getParameter("id_programa"));
    int iId_facultad = Integer.parseInt(request.getParameter("id_facultad"));
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    String sId_asignacion = request.getParameter("id_asignacion");    
	String sObservacion = request.getParameter("observacion");    
    

    if(("".equals(sId_asignacion) || (sId_asignacion == null)))
      return new ModelAndView("Error", "mensaje", "No ingreso la asignacion de docente");
    
    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(iId_facultad);
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);
    //Buscando el programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("datosPrograma", datosPrograma);
    
    //Buscar Tipo evaluacion
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval", datosTipoEval);
    
    //Buscamos la asignacion docente
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(Integer.parseInt(sId_asignacion));
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
    
    if(datosAsignacion == null)
      return new ModelAndView("Error", "mensaje", "No se encontro la asignacion docente para la materia");
    
    //Sacamos los datos del Docente
    Docentes datosDocente = new Docentes();
    datosDocente.setId_docente(datosAsignacion.getId_docente());
    datosDocente = this.mi.getBuscarDocente(datosDocente);
    modelo.put("datosDocente", datosDocente);    
    
    modelo.put("gestion", Integer.toString(iGestion));   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("id_rol",Integer.toString(cliente.getId_rol()));    
    modelo.put("id_tipo_evaluacion", Integer.toString(iId_tipo_evaluacion));
    modelo.put("id_programa", Integer.toString(iId_programa));
    modelo.put("id_facultad", Integer.toString(iId_facultad));    
    modelo.put("id_asignacion", sId_asignacion);    
	modelo.put("observacion", sObservacion);    
    
    return new ModelAndView("administrarVolverFasePrograma/confirmarModificarFaseDocente", modelo);
  }
}