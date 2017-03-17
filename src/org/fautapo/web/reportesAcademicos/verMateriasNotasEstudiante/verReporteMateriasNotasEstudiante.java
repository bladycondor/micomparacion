package org.fautapo.web.reportesAcademicos.verMateriasNotasEstudiante;

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
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Libretas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class verReporteMateriasNotasEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    String sPeriodo = request.getParameter("periodo");
    String sGestion = request.getParameter("gestion");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_materia = request.getParameter("id_materia");
    String sId_docente = request.getParameter("id_docente");
    String sId_grupo = request.getParameter("id_grupo");
    String sId_modelo_ahorro = request.getParameter("id_modelo_ahorro");
    String sMateria = request.getParameter("materia");
    String sSigla = request.getParameter("sigla");

    modelo.put("periodo", sPeriodo);
    modelo.put("gestion", sGestion);
    modelo.put("materia", sMateria);
    modelo.put("sigla", sSigla);
    modelo.put("cliente", cliente);
    
    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    
    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Buscar Asignacion Docente
    Asignaciones asignar_docente = new Asignaciones();
    asignar_docente.setId_docente(Integer.parseInt(sId_docente));
    asignar_docente.setId_materia(Integer.parseInt(sId_materia));
    asignar_docente.setGestion(Integer.parseInt(sGestion));
    asignar_docente.setPeriodo(Integer.parseInt(sPeriodo));
    asignar_docente.setId_modelo_ahorro(Integer.parseInt(sId_modelo_ahorro));    
    asignar_docente.setId_programa(datosEstudiante.getId_programa());    
    asignar_docente.setId_grupo(Integer.parseInt(sId_grupo));    
    Asignaciones asig_doc  = this.mi.getDctBuscarAsignacionDocenteMateria(asignar_docente);
    
    //Sacamos datos del FCLDepartamento
    Libretas buscar = new Libretas();
    buscar.setId_departamento(asig_doc.getId_departamento());
    buscar.setId_tipo_evaluacion(asig_doc.getId_tipo_evaluacion());
    buscar.setGestion(Integer.parseInt(sGestion));  
    buscar.setPeriodo(Integer.parseInt(sPeriodo));  
    List lListarFases = this.mi.getLbrListarFases(buscar);
    modelo.put("lListarFases", lListarFases); 
    
    //listar fases tipos notas de la definicion de evaluacion
    buscar.setId_materia(Integer.parseInt(sId_materia));
    buscar.setId_grupo(Integer.parseInt(sId_grupo));
    buscar.setId_modelo_ahorro(Integer.parseInt(sId_modelo_ahorro));
    List lListarFasesTiposDefinicion = this.mi.getLbrTiposnotasListarDefinicion(buscar);
    modelo.put("lfasesTiposnotas", lListarFasesTiposDefinicion); 
    
    //Listamos materia con notas continuas
    Libretas datosLibretas = new Libretas();
    datosLibretas.setId_departamento(asig_doc.getId_departamento());
    datosLibretas.setId_tipo_evaluacion(asig_doc.getId_tipo_evaluacion());
    datosLibretas.setId_materia(Integer.parseInt(sId_materia));
    datosLibretas.setId_grupo(Integer.parseInt(sId_grupo));
    datosLibretas.setGestion(Integer.parseInt(sGestion));
    datosLibretas.setPeriodo(Integer.parseInt(sPeriodo));
    datosLibretas.setId_tipo_docente(asig_doc.getId_tipo_docente());  
    datosLibretas.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosLibretas.setId_modelo_ahorro(Integer.parseInt(sId_modelo_ahorro));
    
    List lnotasEvaluacionContinua = this.mi.getEstListarNotasEvaluacionContinua(datosLibretas);
    modelo.put("lnotasContinua", lnotasEvaluacionContinua);
    
    return new ModelAndView("reportesAcademicos/verMateriasNotasEstudiante/verReporteMateriasNotasEstudiante", modelo);
  }
}