package org.fautapo.web.reportesAcademicos.verActaPreCierre;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Asignaciones;
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

public class listarAsignacionesDocentesPrograma implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String _nombres = cliente.getNombres();
    int _id_docente = cliente.getId_usuario();
    int _id_rol = cliente.getId_rol();    

    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_facultad = request.getParameter("id_facultad");
    String sId_programa = request.getParameter("id_programa");
    String sId_plan = request.getParameter("id_plan");
    
    int iGestion = Integer.parseInt(sGestion);
    int iPeriodo = Integer.parseInt(sPeriodo);
    
    modelo.put("gestion", sGestion);    
    modelo.put("periodo", sPeriodo);
    modelo.put("id_programa", sId_programa);    
    modelo.put("id_plan", sId_plan);    
    modelo.put("id_facultad", sId_facultad);
    modelo.put("periodo", sPeriodo);
    
    if((!"".equals(sId_programa)) && (!"".equals(sId_plan))) {
      //Lista de Programas (carreras)
      Programas programa = new Programas();
      programa.setId_programa(Integer.parseInt(sId_programa));
      programa = this.mi.getPrgBuscarPrograma(programa);
      modelo.put("datosPrograma", programa);
      //Sacamos lista de docente con sus asignaciones a las materias
      Asignaciones datosAsignacion = new Asignaciones();
      datosAsignacion.setGestion(iGestion);
      datosAsignacion.setPeriodo(iPeriodo);
      datosAsignacion.setId_programa(Integer.parseInt(sId_programa));
      datosAsignacion.setId_plan(sId_plan);
      List lAsignacionDocentesProgramaPlan = this.mi.getDctListarAsignacionDocenteProgramaPlan(datosAsignacion);
      modelo.put("lAsignacionDocentesProgramaPlan", lAsignacionDocentesProgramaPlan);
    
      //Sacamos los datos de la Facultad
      Facultades datosFacultad = new Facultades();
      datosFacultad.setId_facultad(Integer.parseInt(sId_facultad));
      datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
      modelo.put("datosFacultad", datosFacultad);
    
      modelo.put("id_rol",Integer.toString(_id_rol));     
    }
    else{
      return new ModelAndView("Errro", "mensaje","Faltan datos");
    }      
      return new ModelAndView("reportesAcademicos/verActaPreCierre/listarAsignacionesDocentesPrograma", modelo);
  }
}