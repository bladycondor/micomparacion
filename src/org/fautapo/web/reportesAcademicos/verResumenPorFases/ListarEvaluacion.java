package org.fautapo.web.reportesAcademicos.verResumenPorFases;

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
import org.fautapo.domain.Abm;
import org.fautapo.domain.Programas;
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

public class ListarEvaluacion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    

    String sId_asignacion = request.getParameter("id_asignacion");
    String sId_programa = request.getParameter("id_programa");
    String sId_plan = request.getParameter("id_plan");
    
    modelo.put("id_plan", sId_plan);
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_docente",Integer.toString(cliente.getId_usuario()));
    
    //Buscamos los datos de la asignacion
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(Integer.parseInt(sId_asignacion));
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
    
    //Sacamos las notas finales de fase
    Libretas datosEstudiantes = new Libretas();
    datosEstudiantes.setId_programa(Integer.parseInt(sId_programa));
    datosEstudiantes.setId_materia(datosAsignacion.getId_materia());
    datosEstudiantes.setId_grupo(datosAsignacion.getId_grupo());
    datosEstudiantes.setGestion(datosAsignacion.getGestion());
    datosEstudiantes.setPeriodo(datosAsignacion.getPeriodo());
    datosEstudiantes.setId_departamento(datosAsignacion.getId_departamento());
    datosEstudiantes.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosEstudiantes.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    List lEvaluaciones = this.mi.getListarEvaluacionesFinalesFase(datosEstudiantes);
    modelo.put("lEvaluaciones", lEvaluaciones);

    //Datos totales por materia
    datosEstudiantes.setId_programa(datosAsignacion.getId_programa());
    List lTotales = this.mi.getTotalAprobadosReprobadosMateria(datosEstudiantes);
    modelo.put("lTotales", lTotales);

    //Sacamos datos del FCLDepartamento
    Libretas buscar = new Libretas();
    buscar.setId_departamento(datosAsignacion.getId_departamento());
    buscar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    buscar.setGestion(datosAsignacion.getGestion());
    buscar.setPeriodo(datosAsignacion.getPeriodo());
    List lListarFases = this.mi.getLbrListarFases(buscar);
    modelo.put("lFases", lListarFases);
     
    //Sacando la lista de estudiantes programados a la materia, evaluación regualar
    Libretas datosEstProg = new Libretas();
    datosEstProg.setId_materia(datosAsignacion.getId_materia());
    datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
    datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    datosEstProg.setGestion(datosAsignacion.getGestion());
    datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
    datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
    modelo.put("lEstudiantes", lEstudiantes);

    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(datosPrograma.getId_facultad());
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);

    //Buscamos el grado_academico por programa e id_plan
    Libretas datosGrados = new Libretas();
    datosGrados.setId_programa(Integer.parseInt(sId_programa));
    datosGrados.setId_plan(sId_plan);
    datosGrados = this.mi.getBuscarGradoAcademicoPrograma(datosGrados);
    modelo.put("datosGrados", datosGrados);

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
      
    return new ModelAndView("reportesAcademicos/verResumenPorFases/ListarEvaluacion", modelo);
  }
}