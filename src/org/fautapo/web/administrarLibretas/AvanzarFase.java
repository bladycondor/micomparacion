package org.fautapo.web.administrarLibretas;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;

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

public class AvanzarFase implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    //String sUlt_usuario = cliente.getId_usuario() + "|" + cliente.getId_rol();
    
    String sId_tipo_nota_s     = "";  //AUXILIAR 
    int iId_asignacion = cliente.getInt(request, "id_asignacion");
    String sId_tipo_grado      = request.getParameter("id_tipo_grado");        
    String sId_programa        = request.getParameter("id_programa");
    
    System.out.println("id_asignacion_docente--------avanzar fase------>" + Integer.toString(iId_asignacion));
    
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_tipo_grado", sId_tipo_grado);    
    modelo.put("id_programa", sId_programa);    
    
    //Buscamos la asignacion docente
    Asignaciones buscarAsignacion = new Asignaciones();
    buscarAsignacion.setId_asignacion(iId_asignacion);
    Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocente(buscarAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
    
    if(datosAsignacion == null)
      return new ModelAndView("Error", "mensaje", "No se encontr&oacute; la asignaci&oacute;n docente para la materia");
    
    //Sacamos el programa 
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    Programas buscarPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("buscarPrograma", buscarPrograma);
    modelo.put("id_programa", sId_programa);
    modelo.put("programa", buscarPrograma.getPrograma());

    //Sacamos datos de la materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(datosAsignacion.getId_materia());
    Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("buscarMateria", buscarMateria);
    //modelo.put("id_tipo_nota_s", sId_tipo_nota_s);

    //Sacamos la fase actual de acuerdo a la asignacion del docente
    Libretas datosFase = new Libretas();
    datosFase.setId_fase(datosAsignacion.getId_fase());
    datosFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosFase.setId_departamento(datosAsignacion.getId_departamento());
    datosFase.setGestion(datosAsignacion.getGestion());
    datosFase.setPeriodo(datosAsignacion.getPeriodo());
    Libretas buscarFaseActual = this.mi.getLbrBuscarFase(datosFase);
    modelo.put("buscarFaseActual", buscarFaseActual);
    modelo.put("fase_actual", buscarFaseActual.getFase());
    
    //Sacando la fase maxima
    Libretas datosFaseMax = new Libretas();
    datosFaseMax.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosFaseMax.setId_programa(Integer.parseInt(sId_programa));
    datosFaseMax.setGestion(datosAsignacion.getGestion());
    datosFaseMax.setPeriodo(datosAsignacion.getPeriodo());
    int iId_fase_maxima = this.mi.getLbrBuscarFaseMaxima(datosFaseMax);

    if (Integer.parseInt(sId_tipo_grado) == 1) {  //Universitario
      //Sacando la fase siguiente de acuerdo a Asignacion docente
      Libretas datosFaseA = new Libretas();
      datosFaseA.setId_fase(datosAsignacion.getId_fase()+1);  
      System.out.println("El id_Fase -->"+datosFaseA.getId_fase());
      datosFaseA.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
      System.out.println("El id_tipo_Evaluacion -->"+datosFaseA.getId_tipo_evaluacion());
      datosFaseA.setId_departamento(datosAsignacion.getId_departamento());
      System.out.println("El id_departamento -->"+datosFaseA.getId_departamento());
      datosFaseA.setGestion(datosAsignacion.getGestion());
      System.out.println("El gestion -->"+datosFaseA.getGestion());
      datosFaseA.setPeriodo(datosAsignacion.getPeriodo());
      System.out.println("El periodo -->"+datosFaseA.getPeriodo());
      Libretas buscarFaseSiguiente = this.mi.getLbrBuscarFase(datosFaseA);
      if(buscarFaseSiguiente ==null)
        modelo.put("fase_siguiente", "Pre-Cierre de Libreta (No existen mas fases definidas)");
      else	
        modelo.put("fase_siguiente", buscarFaseSiguiente.getFase());
    }
    if (Integer.parseInt(sId_tipo_grado) !=1) { //Vestibular / Postgrado/etc..
      //Sacando la fase siguiente
      Libretas datosFaseA = new Libretas();
      datosFaseA.setId_fase(iId_fase_maxima);
      datosFaseA.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
      datosFaseA.setId_departamento(datosAsignacion.getId_departamento());
      datosFaseA.setGestion(datosAsignacion.getGestion());
      datosFaseA.setPeriodo(datosAsignacion.getPeriodo());
      Libretas buscarFaseSiguiente = this.mi.getLbrBuscarFase(datosFaseA);
      if(buscarFaseSiguiente ==null)
        modelo.put("fase_siguiente", "Pre-Cierre de Libreta (No existen mas fases definidas)");
      else	
        modelo.put("fase_siguiente", buscarFaseSiguiente.getFase());
    }	
    
    
    return new ModelAndView("administrarLibretas/AvanzarFase", modelo);
  }
}