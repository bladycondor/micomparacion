package org.fautapo.web.reportesAcademicos.verActaPorFases;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Notas;   
import org.fautapo.domain.Docentes;   
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

public class ListarActaCalificaciones implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    

    String sId_asignacion = request.getParameter("id_asignacion");
    String sId_programa   = request.getParameter("id_programa");
    String sId_plan       = request.getParameter("id_plan");    
    String sId_fase       = request.getParameter("id_fase");

    modelo.put("id_plan", sId_plan);    
    modelo.put("nombres", cliente.getNombres());

    //Buscamos el grado_academico por programa e id_plan
    Libretas datosGrados = new Libretas();
    datosGrados.setId_programa(Integer.parseInt(sId_programa));
    datosGrados.setId_plan(sId_plan);
    datosGrados = this.mi.getBuscarGradoAcademicoPrograma(datosGrados);
    modelo.put("datosGrados",datosGrados);

    //Buscamos los datos de la asignacion
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(Integer.parseInt(sId_asignacion));
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);    
    
    //Buscar Docente
    Docentes datosDoc = new Docentes();
    datosDoc.setId_docente(datosAsignacion.getId_docente());
    datosDoc = this.mi.getBuscarDocente(datosDoc);
    modelo.put("datosDoc", datosDoc);
    
    //listamos los estudiantes con Acta de calificaciones (tabla notas)
    Notas datosNotas = new Notas();
    datosNotas.setId_materia(datosAsignacion.getId_materia());
    datosNotas.setId_grupo(datosAsignacion.getId_grupo());
    datosNotas.setGestion(datosAsignacion.getGestion());
    datosNotas.setPeriodo(datosAsignacion.getPeriodo());
    datosNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosNotas.setId_departamento(datosAsignacion.getId_departamento());
    datosNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    datosNotas.setId_fase(Integer.parseInt(sId_fase));
    List listNotas = this.mi.getListarActaCalificacionesPorFase(datosNotas);
    modelo.put("listNotas", listNotas);
   
    //Sacamos el programa  
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(datosPrograma.getId_facultad());
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);

   //Sacamos la fase actual
    Libretas datosFase = new Libretas();
    datosFase.setId_fase(Integer.parseInt(sId_fase));
    datosFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosFase.setId_departamento(datosAsignacion.getId_departamento());
    datosFase.setGestion(datosAsignacion.getGestion());
    datosFase.setPeriodo(datosAsignacion.getPeriodo());
    datosFase = this.mi.getLbrBuscarFase(datosFase);
    modelo.put("datosFase", datosFase);

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

    return new ModelAndView("reportesAcademicos/verActaPorFases/ListarActaCalificaciones", modelo);
  }
}