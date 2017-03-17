package org.fautapo.web.reportesAcademicos.verNotasEvaluacionPorEstudiante;

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
import org.fautapo.domain.Abm;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Libretas;   
import org.fautapo.domain.Instituciones;

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

public class ImprimirNotaEvaluacionEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    String sId_asignacion      = request.getParameter("id_asignacion");
    String sId_programacion    = request.getParameter("id_programacion");  
    String sId_estudiante      = request.getParameter("id_estudiante");  
    String[] sDatos_impresion    = request.getParameterValues("datos_impresion");    

    
    modelo.put("id_asignacion", sId_asignacion);
    modelo.put("id_programacion", sId_programacion);
    modelo.put("id_estudiante", sId_estudiante);    
    
    
    //Convertimos los datos impresion a la lista
    if(sDatos_impresion!=null) {
      List lListaImpresion = new ArrayList();
      for (int i = 0; i < sDatos_impresion.length; i++){
        String datos_est[]= sDatos_impresion[i].split("/");
        Libretas aux1 = new Libretas();
        System.out.println(datos_est[0]+"-"+datos_est[1]+"-"+datos_est[2]);
        aux1.setNro_nota(Integer.parseInt(datos_est[0]));
        aux1.setId_tipo_nota(Integer.parseInt(datos_est[1]));
        aux1.setId_fase(Integer.parseInt(datos_est[2]));
        lListaImpresion.add(aux1);
      }
      modelo.put("lListaImpresion", lListaImpresion); 
    }
    
    
    //Buscamos datos del estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    
    //Buscamos en est_programacion
    if(("".equals(sId_programacion)) && (sId_programacion == null) ) {
      return new ModelAndView("Error", "mensaje", "No ingreso la programacion");
    }
    
    Estudiantes datosProgramacion = new Estudiantes(); 
    datosProgramacion.setId_programacion(Integer.parseInt(sId_programacion));
    datosProgramacion = this.mi.getMiBuscarEstProgramacion(datosProgramacion);    
    modelo.put("datosProgramacion", datosProgramacion);
    
    
    //Buscamos los datos de la asignacion
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(Integer.parseInt(sId_asignacion));
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion); 
    modelo.put("datosAsignacion", datosAsignacion);
    
    if (datosAsignacion == null) {
      return new ModelAndView("Aviso","mensaje","No tiene asignacion de Docente");
    }
    
    //Buscamos al docente
    Docentes datosDocente = new Docentes();
    datosDocente.setId_docente(datosAsignacion.getId_docente());
    datosDocente = this.mi.getBuscarDocente(datosDocente);
    modelo.put("datosDocente", datosDocente);
    
    
    //Sacamos datos del FCLDepartamento
    Libretas buscar = new Libretas();
    buscar.setId_departamento(datosAsignacion.getId_departamento());
    buscar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    buscar.setGestion(datosAsignacion.getGestion());  
    buscar.setPeriodo(datosAsignacion.getPeriodo());  
    List lListarFases = this.mi.getLbrListarFases(buscar);
    modelo.put("lListarFases", lListarFases); 
    
    //listar fases tipos notas de la definicion de evaluacion por datos de est_programacion
    buscar.setId_materia(datosProgramacion.getId_materia());
    buscar.setId_grupo(datosProgramacion.getId_grupo());
    buscar.setId_modelo_ahorro(datosProgramacion.getId_modelo_ahorro());
    List lListarFasesTiposDefinicion = this.mi.getLbrTiposnotasListarDefinicion(buscar);
    modelo.put("lFasesTiposNotas", lListarFasesTiposDefinicion); 
    
    //Listamos materia con notas continuas
    Libretas datosLibretas = new Libretas();
    datosLibretas.setId_departamento(datosAsignacion.getId_departamento());
    datosLibretas.setId_tipo_evaluacion(datosProgramacion.getId_tipo_evaluacion());
    datosLibretas.setId_materia(datosProgramacion.getId_materia());
    datosLibretas.setId_grupo(datosProgramacion.getId_grupo());
    datosLibretas.setGestion(datosProgramacion.getGestion());
    datosLibretas.setPeriodo(datosProgramacion.getPeriodo());
    datosLibretas.setId_tipo_docente(datosAsignacion.getId_tipo_docente());  
    datosLibretas.setId_estudiante(datosProgramacion.getId_estudiante());
    datosLibretas.setId_modelo_ahorro(datosProgramacion.getId_modelo_ahorro());
    List lNotasEvaluacion = this.mi.getEstListarNotasEvaluacionContinua(datosLibretas);
    modelo.put("lNotasEvaluacion", lNotasEvaluacion);
    
    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
    
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos el formato de la hora
    formatoFecha.setCampo("formato_hora");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoFecha));
      
    return new ModelAndView("reportesAcademicos/verNotasEvaluacionPorEstudiante/ImprimirNotaEvaluacionEstudiante", modelo);
  }
}