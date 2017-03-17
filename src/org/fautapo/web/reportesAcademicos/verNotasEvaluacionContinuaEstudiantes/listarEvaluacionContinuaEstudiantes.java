package org.fautapo.web.reportesAcademicos.verNotasEvaluacionContinuaEstudiantes;

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
import org.fautapo.domain.FormatosNum;   
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

public class listarEvaluacionContinuaEstudiantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    String sId_materia         = request.getParameter("id_materia");
    String sId_grupo           = request.getParameter("id_grupo");  
    String sGrupo              = request.getParameter("grupo");  
    String sId_programa        = request.getParameter("id_programa");
    String sId_fase            = request.getParameter("id_fase");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String sTipo_evaluacion    = request.getParameter("tipo_evaluacion");
    String sId_modelo_ahorro   = request.getParameter("id_modelo_ahorro");
    String sMateria            = request.getParameter("materia");
    String sSigla              = request.getParameter("sigla");
    String sPrograma           = request.getParameter("programa");    
    String sBandera            = request.getParameter("bandera");
    String sGestion            = request.getParameter("gestion");
    String sPeriodo            = request.getParameter("periodo"); 
    String sId_departamento    = request.getParameter("id_departamento");    
    String sNombres_docente    = request.getParameter("nombres");    
    
    modelo.put("gestion", sGestion);   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    modelo.put("periodo", sPeriodo);
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_docente",Integer.toString(cliente.getId_usuario()));
    modelo.put("id_rol",Integer.toString(cliente.getId_rol()));    
    modelo.put("id_grupo", sId_grupo);
    modelo.put("grupo", sGrupo);
    modelo.put("id_fase", sId_fase);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    modelo.put("tipo_evaluacion", sTipo_evaluacion);
    modelo.put("id_modelo_ahorro", sId_modelo_ahorro);
    modelo.put("id_materia", sId_materia);
    modelo.put("materia", sMateria);
    modelo.put("sigla", sSigla);
    modelo.put("id_programa", sId_programa);
    modelo.put("programa", sPrograma);
    modelo.put("id_departamento", sId_departamento);    
    modelo.put("docente", sNombres_docente);    
    
    //Convertimos a entero los datos necesarios
    int iGestion = Integer.parseInt(sGestion);
    int iPeriodo = Integer.parseInt(sPeriodo);
    
    int iId_materia = Integer.parseInt(sId_materia);
    int iId_fase = Integer.parseInt(sId_fase);    
    int iId_tipo_evaluacion = Integer.parseInt(sId_tipo_evaluacion);    
    int iId_grupo = Integer.parseInt(sId_grupo);    
    int iId_modelo_ahorro = Integer.parseInt(sId_modelo_ahorro);
    int iId_departamento = Integer.parseInt(sId_departamento);
    
    //listamos los estudiantes con notas evaluacion  continua
    Libretas datosEstudiantes = new Libretas();
    datosEstudiantes.setId_materia(iId_materia);
    datosEstudiantes.setId_grupo(iId_grupo);
    datosEstudiantes.setGestion(iGestion);
    datosEstudiantes.setPeriodo(iPeriodo);
    datosEstudiantes.setId_departamento(iId_departamento);
    datosEstudiantes.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosEstudiantes.setId_modelo_ahorro(iId_modelo_ahorro);
    List levaluacionContinua = this.mi.getListarEstudiantesEvaluacionContinua(datosEstudiantes);
    modelo.put("levalContinua", levaluacionContinua);

    //Sacamos datos del FCLDepartamento
    Libretas buscar = new Libretas();
    buscar.setId_departamento(iId_departamento);
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
      
    return new ModelAndView("reportesAcademicos/verNotasEvaluacionContinuaEstudiantes/listarEvaluacionContinuaEstudiantes", modelo);
  }
}