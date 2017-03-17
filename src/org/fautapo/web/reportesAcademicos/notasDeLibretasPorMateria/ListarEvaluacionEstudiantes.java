package org.fautapo.web.reportesAcademicos.notasDeLibretasPorMateria;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Planes;

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

public class ListarEvaluacionEstudiantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    String sId_materia         = request.getParameter("id_materia");
    String sId_programa        = request.getParameter("id_programa");
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String sGestion            = request.getParameter("gestion");
    String sPeriodo            = request.getParameter("periodo"); 
    
    modelo.put("gestion", sGestion);   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    modelo.put("periodo", sPeriodo);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    modelo.put("id_materia", sId_materia);
    modelo.put("id_programa", sId_programa);
    
    //Convertimos a entero los datos necesarios
    int iGestion = Integer.parseInt(sGestion);
    int iPeriodo = Integer.parseInt(sPeriodo);
    int iId_tipo_evaluacion = Integer.parseInt(sId_tipo_evaluacion);
    //Sacamos el id_materia y id_modelo_ahorro
    String sDatos[] = sId_materia.split(":");
    int iId_materia = Integer.parseInt(sDatos[0]);
    int iId_modelo_ahorro = Integer.parseInt(sDatos[1]);
    iId_modelo_ahorro = -1000000;  //CAMBIARLO
    int iId_grupo = Integer.parseInt(sDatos[2]);
    
    //Buscamos los datos de prg_planes
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);

    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosPrgPlan.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Buscamos Tipo Evaluacion
    Libretas datosTipoEvaluacion = new Libretas();
    datosTipoEvaluacion.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEvaluacion = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEvaluacion);
    modelo.put("datosTipoEvaluacion", datosTipoEvaluacion);

    //Sacamos los datos de la Materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(iId_materia);
    datosMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("datosMateria", datosMateria);

    //listamos los estudiantes con notas evaluacion  continua
    Libretas datosEstudiantes = new Libretas();
    datosEstudiantes.setId_materia(iId_materia);
    datosEstudiantes.setId_grupo(iId_grupo);
    datosEstudiantes.setGestion(iGestion);
    datosEstudiantes.setPeriodo(iPeriodo);
    datosEstudiantes.setId_departamento(datosMateria.getId_departamento());
    datosEstudiantes.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosEstudiantes.setId_modelo_ahorro(iId_modelo_ahorro);
    List levaluacionContinua = this.mi.getListarEstudiantesEvaluacionContinua(datosEstudiantes);
    modelo.put("levalContinua", levaluacionContinua);

    //Sacamos datos del FCLDepartamento
    Libretas buscar = new Libretas();
    buscar.setId_departamento(datosMateria.getId_departamento());
    buscar.setId_tipo_evaluacion(iId_tipo_evaluacion);
    buscar.setGestion(iGestion);  
    buscar.setPeriodo(iPeriodo);  
    List lListarFases = this.mi.getLbrListarFases(buscar);
    modelo.put("lListarFases", lListarFases); 
    
    //listar fases tipos notas de la definicion de evaluacion
    buscar.setId_materia(iId_materia);
    buscar.setId_grupo(iId_grupo);
    buscar.setId_modelo_ahorro(iId_modelo_ahorro);
    List lListarFasesTiposDefinicion = this.mi.getLbrTiposnotasListarDefinicion(buscar);
    modelo.put("lfasesTiposnotas", lListarFasesTiposDefinicion); 
     
    //Sacando la lista de estudiantes programados a la materia, evaluación regualar
    Libretas datosEstProg = new Libretas();
    datosEstProg.setId_materia(iId_materia);
    datosEstProg.setId_grupo(iId_grupo);
    datosEstProg.setId_modelo_ahorro(iId_modelo_ahorro); 
    datosEstProg.setGestion(iGestion);
    datosEstProg.setPeriodo(iPeriodo);
    datosEstProg.setId_tipo_evaluacion(iId_tipo_evaluacion);
    List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
    modelo.put("listaEstudiantes", lEstudiantes);
      
    return new ModelAndView("reportesAcademicos/notasDeLibretasPorMateria/ListarEvaluacionEstudiantes", modelo);
  }
}