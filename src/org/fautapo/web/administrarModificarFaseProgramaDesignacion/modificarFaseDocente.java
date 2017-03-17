package org.fautapo.web.administrarModificarFaseProgramaDesignacion;

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

public class modificarFaseDocente implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    
    String sId_asignacion      = request.getParameter("id_asignacion");
    String sId_fase            = request.getParameter("id_fase");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String sGestion            = request.getParameter("gestion");
    String sPeriodo            = request.getParameter("periodo"); 
    String sId_programa        = request.getParameter("id_programa");    
    String sId_facultad        = request.getParameter("id_facultad");    
    String sObservacion 	   = request.getParameter("observacion");    
    
    modelo.put("id_fase", sId_fase);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    modelo.put("id_programa", sId_programa);
    modelo.put("id_facultad", sId_facultad);    
    modelo.put("id_asignacion", sId_asignacion);    
    
	modelo.put("observacion", sObservacion); 
    
	int iResultado =0;
    String sMensaje="";
    if(("".equals(sId_asignacion) || (sId_asignacion == null)))
      return new ModelAndView("Error", "mensaje", "No ingreso la asignacion de docente");
      
    //Sacamos todos los datos de Asignacion Docente
    //Buscamos la asignacion docente
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(Integer.parseInt(sId_asignacion));	
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
    
    if(datosAsignacion == null)
      return new ModelAndView("Error", "mensaje", "No se encontro la asignacion docente para la materia");
    
    
    //Sacamos la fase minima de la materia
    Libretas datosFase1 = new Libretas();
    datosFase1.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosFase1.setId_programa(Integer.parseInt(sId_programa));
    datosFase1.setGestion(datosAsignacion.getGestion());
    datosFase1.setPeriodo(datosAsignacion.getPeriodo());
    int iMin_fase = this.mi.getLbrBuscarFaseMinima(datosFase1);
    
    
    //Retrocede fase y anula est_libretas
    Libretas datos = new Libretas();
    datos.setId_materia(datosAsignacion.getId_materia());
    datos.setId_grupo(datosAsignacion.getId_grupo());
    datos.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    datos.setGestion(datosAsignacion.getGestion());
    datos.setPeriodo(datosAsignacion.getPeriodo());
    datos.setId_docente(datosAsignacion.getId_docente());
    datos.setId_departamento(datosAsignacion.getId_departamento());
    datos.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datos.setId_fase(datosAsignacion.getId_fase());
    datos.setId_rol(cliente.getId_rol());
    datos.setId_usuario(cliente.getId_usuario());
	datos.setObservacion(sObservacion);
    iResultado = this.mi.setModificarFaseDocenteCerrarLibreta(datos);        

    System.out.print("Resultado de la modificacion____"+iResultado);
    
    if (iResultado == 1) {
     sMensaje = "Los cambios se guardaron  correctamente";
     
    }    
    else {
      sMensaje = "No se puedo retroceder la fase";
    }    
    
    modelo.put("mensaje", sMensaje);
    modelo.put("gestion", Integer.toString(datosAsignacion.getGestion()));   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    modelo.put("periodo", Integer.toString(datosAsignacion.getPeriodo()));
    modelo.put("id_programa", sId_programa);
    return new ModelAndView("administrarModificarFaseProgramaDesignacion/Aviso", modelo);
  }
}