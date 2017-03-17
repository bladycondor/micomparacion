package org.fautapo.web.reportesAcademicos.verActaPorFases;

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
import org.fautapo.domain.Libretas;
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

public class ListarFases implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String _nombres = cliente.getNombres();

    String sId_programa = request.getParameter("id_programa");
    String sId_plan = request.getParameter("id_plan");
    String sId_asignacion = request.getParameter("id_asignacion");
    
    modelo.put("id_programa", sId_programa);    
    modelo.put("id_plan", sId_plan);    
    modelo.put("id_asignacion", sId_asignacion);

    //Lista de Programas (carreras)
    Programas programa = new Programas();
    programa.setId_programa(Integer.parseInt(sId_programa));
    programa = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("datosPrograma", programa);
    
    //Buscamos los datos de la asignacion
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(Integer.parseInt(sId_asignacion));
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
    
    //Sacamos lista de fases < 1000
    Libretas datosLibreta = new Libretas();
    datosLibreta.setId_departamento(datosAsignacion.getId_departamento());
    datosLibreta.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosLibreta.setGestion(datosAsignacion.getGestion());
    datosLibreta.setPeriodo(datosAsignacion.getPeriodo());
    List lFases = this.mi.getLbrListarFases2(datosLibreta);
    modelo.put("lFases", lFases);
    
    return new ModelAndView("reportesAcademicos/verActaPorFases/ListarFases", modelo);
  }
}