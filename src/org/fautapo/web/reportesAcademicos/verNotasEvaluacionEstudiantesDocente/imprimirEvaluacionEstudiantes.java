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
import org.fautapo.domain.Abm;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.Facultades;

import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;   
import org.fautapo.domain.Docentes;   
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

public class imprimirEvaluacionEstudiantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    
    String sId_asignacion      = request.getParameter("id_asignacion");
    String sId_materia         = request.getParameter("id_materia");
    String sId_grupo           = request.getParameter("id_grupo");  
    String sGrupo              = request.getParameter("grupo");  
    String sId_programa        = request.getParameter("id_programa");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String sId_modelo_ahorro   = request.getParameter("id_modelo_ahorro");
    String sGestion            = request.getParameter("gestion");
    String sPeriodo            = request.getParameter("periodo"); 
    String sId_departamento    = request.getParameter("id_departamento");    
    String[] sDatos_impresion    = request.getParameterValues("datos_impresion");    
    String sPaginacion    = request.getParameter("paginacion");

     if(("".equals(sPaginacion) || (sPaginacion == null))){  sPaginacion="15";}
    
    modelo.put("gestion", sGestion);   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    modelo.put("periodo", sPeriodo);
    modelo.put("id_rol",Integer.toString(cliente.getId_rol()));    
    modelo.put("id_grupo", sId_grupo);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    modelo.put("id_modelo_ahorro", sId_modelo_ahorro);
    modelo.put("id_materia", sId_materia);
    modelo.put("id_programa", sId_programa);
    modelo.put("id_departamento", sId_departamento);    
    modelo.put("paginacion",sPaginacion);
    System.out.println("Spaginacino"+sPaginacion);
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
    
    
    //convertimos los datos impresion a la lista
    if(sDatos_impresion!=null){
      List lListaImpresion = new ArrayList();
      for (int i = 0; i < sDatos_impresion.length; i++){
        String datos_est[]= sDatos_impresion[i].split("/");
        Libretas aux1 = new Libretas();
        System.out.println(datos_est[0]+"-"+datos_est[1]+"-"+datos_est[2]+"-"+datos_est[3]);
        aux1.setNro_nota(Integer.parseInt(datos_est[0]));
        aux1.setId_tipo_nota(Integer.parseInt(datos_est[1]));
        aux1.setId_fase(Integer.parseInt(datos_est[2]));
	aux1.setNumero(Integer.parseInt(datos_est[3]));
        lListaImpresion.add(aux1);
      }
      modelo.put("lListaImpresion", lListaImpresion); 
    }
    
    //Listar Estudiantes notas evaluacion est_libretas
    Libretas datosEstudiantes = new Libretas();
    datosEstudiantes.setId_materia(datosAsignacion.getId_materia());
    datosEstudiantes.setId_grupo(datosAsignacion.getId_grupo());
    datosEstudiantes.setGestion(datosAsignacion.getGestion());
    datosEstudiantes.setPeriodo(datosAsignacion.getPeriodo());
    datosEstudiantes.setId_departamento(datosAsignacion.getId_departamento());
    datosEstudiantes.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosEstudiantes.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    List lEstudiantesEvaluacion = this.mi.getListarNotasEstudiantesLibretas(datosEstudiantes);
    modelo.put("lEstudiantesEvaluacion",  lEstudiantesEvaluacion);
    //modelo.put("levalContinua", levaluacionContinua);
    System.out.println("El tamano de la lista dos -->"+ Integer.toString(lEstudiantesEvaluacion.size()));

    
    //ESTADO MATERIAS
    List lEvaluaciones = this.mi.getListarEvaluacionesFinalesFase(datosEstudiantes);
    modelo.put("lEvaluacionesf", lEvaluaciones);







       //Datos totales por materia
    datosEstudiantes.setId_programa(datosAsignacion.getId_programa());
    List lTotales = this.mi.getTotalAprobadosReprobadosMateria(datosEstudiantes);
    modelo.put("lTotales", lTotales);




    
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
    //modelo.put("lFasesTiposNotas", lFasesTiposDefinicion); 
    modelo.put("lfasesTiposnotas", lFasesTiposDefinicion);  
    
    //Sacando la lista de estudiantes programados a la materia, evaluación regualar
    Libretas datosEstProg = new Libretas();
    datosEstProg.setId_materia(datosAsignacion.getId_materia());
    datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
    datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro()); 
    datosEstProg.setGestion(datosAsignacion.getGestion());
    datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
    datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
    //modelo.put("lEstudiantes", lEstudiantes); 
    modelo.put("listaEstudiantes", lEstudiantes);

    
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
      
    return new ModelAndView("reportesAcademicos/verNotasEvaluacionEstudiantesDocente/imprimirEvaluacionEstudiantes", modelo);
  }
}
