package org.fautapo.web.reportesAcademicos.reporteEstBecasTrabajo;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import org.fautapo.domain.Abm;
/**
 * @autor UAP
 * @fec_registro 2008-17-04
 * @ult_usuario UAP
 * @fec_modificacion 2007-17-04
 */

public class ListarEstBecasTrabajoFuncional implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    String gestion     = request.getParameter("gestion");
    String periodo     = request.getParameter("periodo");
    String sId_ubicacion_organica = request.getParameter("id_ubicacion_organica");

    //buscamos la ubicacion organica
    Actividades actividad = new Actividades();
    actividad.setId_ubicacion_organica(Integer.parseInt(sId_ubicacion_organica));    
    actividad=this.mi.getBuscarUbicacionOrganica(actividad); 
    modelo.put("ubicacion_organica",actividad);    
    
    modelo.put("gestion",gestion);    
    modelo.put("periodo",periodo);
    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
    modelo.put("datosInstitucion", datosInstitucion);
    }      

    Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
      modelo.put("datosInstitucionsede", datosInstitucionSede);
    }
    
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    
    //sacamos a los estudiantes con beca-trabajo
    
    Estudiantes estudiante = new Estudiantes();
    estudiante.setGestion(Integer.parseInt(gestion));
    estudiante.setPeriodo(Integer.parseInt(periodo));
    estudiante.setId_ubicacion_organica(Integer.parseInt(sId_ubicacion_organica));
    
    List listarEstBecasTrabajoFuncional = this.mi.getListarEstBecasTrabajoFuncional(estudiante);
    modelo.put("listarEstBecasTrabajoFuncional",listarEstBecasTrabajoFuncional);
    return new ModelAndView("reportesAcademicos/reporteEstBecasTrabajo/ListarEstBecasTrabajoFuncional", modelo);
  }
}



