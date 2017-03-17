package org.fautapo.web.reportesAcademicos.verNotasEvaluacionPorEstudiante;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Abm;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarMateriasProgramadas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    int iGestion=0; int iPeriodo=0;
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    try {
      iGestion = Integer.parseInt(sGestion);
      iPeriodo = Integer.parseInt(sPeriodo);
    }
    catch(Exception e) {
      modelo.put("gestion", Integer.toString(cliente.getGestion()));
      modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
      modelo.put("usuario", cliente.getNombres());  
      return new ModelAndView("reportesAcademicos/verProgramacionEstudiante/Entrada", modelo);      
    }

    // Comprobamos es quien debe, de acuerdo a su clave
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(cliente.getId_usuario());
    datosEstudiante.setClave(request.getParameter("clave"+request.getParameter("hora")));

    if (null == this.mi.getComprobarEstudiante(datosEstudiante)) {
      modelo.put("gestion", Integer.toString(cliente.getGestion()));
      modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
      modelo.put("usuario", cliente.getNombres());
      return new ModelAndView("reportesAcademicos/verNotasEvaluacionPorEstudiante/Entrada", modelo);
    }
    
    modelo.put("gestion", request.getParameter("gestion"));
    modelo.put("periodo", request.getParameter("periodo"));
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    
    //Sacamos los datos del Estudiante
    datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(cliente.getId_usuario());
    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    
    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Listamos la programacion del estudiante con docentes
    datosEstudiante.setGestion(iGestion);
    datosEstudiante.setPeriodo(iPeriodo);
    List lProgramacion = this.mi.getEstListarProgramacion(datosEstudiante);
    modelo.put("lProgramacion", lProgramacion);
    
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));


    return new ModelAndView("reportesAcademicos/verNotasEvaluacionPorEstudiante/ListarMateriasProgramadas", modelo);
  }
}