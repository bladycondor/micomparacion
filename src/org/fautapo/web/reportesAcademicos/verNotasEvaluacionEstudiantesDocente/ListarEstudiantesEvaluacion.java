package org.fautapo.web.reportesAcademicos.verNotasEvaluacionEstudiantesDocente;

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
import org.fautapo.domain.Docentes;
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

public class ListarEstudiantesEvaluacion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    
    String sId_asignacion      = request.getParameter("id_asignacion");
    String sId_programa        = request.getParameter("id_programa");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String sBandera            = request.getParameter("bandera");
    String sGestion            = request.getParameter("gestion");
    String sPeriodo            = request.getParameter("periodo"); 
    
    modelo.put("gestion", sGestion);   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    modelo.put("periodo", sPeriodo);
    modelo.put("id_rol",Integer.toString(cliente.getId_rol()));    
    modelo.put("id_programa", sId_programa);
    
    //Convertimos a entero los datos necesarios
    int iGestion = Integer.parseInt(sGestion);
    int iPeriodo = Integer.parseInt(sPeriodo);
    int iId_tipo_evaluacion = Integer.parseInt(sId_tipo_evaluacion);    
    
    if(("".equals(sId_asignacion) || (sId_asignacion == null)))
      return new ModelAndView("Error","mensaje","No ingreso la asignacion del docente");
      
    //Buscamos la asignacion docente
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(Integer.parseInt(sId_asignacion));
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);    
    
    if(datosAsignacion == null)
      return new ModelAndView("Error","mensaje", "No existen registros de asignacion del docente");
    
    //Buscamos al docente
    Docentes datosDocente = new Docentes();
    datosDocente.setId_docente(datosAsignacion.getId_docente());
    datosDocente = this.mi.getBuscarDocente(datosDocente);
    modelo.put("datosDocente", datosDocente);
    
    //Listar Estudiantes notas evaluacion est_libretas
    Libretas datosEstudiantes = new Libretas();
    datosEstudiantes.setId_materia(datosAsignacion.getId_materia());
    System.out.println("El id_materia -->"+ Integer.toString(datosEstudiantes.getId_materia()));
    datosEstudiantes.setId_grupo(datosAsignacion.getId_grupo());
    System.out.println("El Id_grupo -->"+ Integer.toString(datosEstudiantes.getId_grupo()));
    datosEstudiantes.setGestion(datosAsignacion.getGestion());
    System.out.println("El Gestion -->"+ Integer.toString(datosEstudiantes.getGestion()));
    datosEstudiantes.setPeriodo(datosAsignacion.getPeriodo());
    System.out.println("El Periodo -->"+ Integer.toString(datosEstudiantes.getPeriodo()));
    datosEstudiantes.setId_departamento(datosAsignacion.getId_departamento());
    System.out.println("ElcId_departamento -->"+ Integer.toString(datosEstudiantes.getId_departamento()));
    datosEstudiantes.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    System.out.println("El Id_tipo_evaluacion -->"+ Integer.toString(datosEstudiantes.getId_tipo_evaluacion()));
    datosEstudiantes.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    System.out.println("El Id_modelo_ahorro -->"+ Integer.toString(datosEstudiantes.getId_modelo_ahorro()));
    List lEstudiantesEvaluacion = this.mi.getListarNotasEstudiantesLibretas(datosEstudiantes);
    modelo.put("lEstudiantesEvaluacion",  lEstudiantesEvaluacion);
    System.out.println("El tamano de la lista -->"+ Integer.toString(lEstudiantesEvaluacion.size()));

    //Sacamos datos de dct_asigancion
    Libretas buscar = new Libretas();
    buscar.setId_departamento(datosAsignacion.getId_departamento());
    buscar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    buscar.setGestion(datosAsignacion.getGestion());  
    buscar.setPeriodo(datosAsignacion.getPeriodo());  
    List lListarFases = this.mi.getLbrListarFases(buscar);
    modelo.put("lListarFases", lListarFases); 
    
    //listar fases tipos notas de la definicion de evaluacion
    buscar.setId_materia(datosAsignacion.getId_materia());
    buscar.setId_grupo(datosAsignacion.getId_grupo());
    buscar.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    List lFasesTiposDefinicion = this.mi.getLbrTiposnotasListarDefinicion(buscar);
    modelo.put("lFasesTiposNotas", lFasesTiposDefinicion); 
     
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
      
    return new ModelAndView("reportesAcademicos/verNotasEvaluacionEstudiantesDocente/ListarEstudiantesEvaluacion", modelo);
  }
}