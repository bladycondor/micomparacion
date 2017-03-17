package org.fautapo.web.reportesAcademicos.asignacionDocentes;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Abm;
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

public class ListarAsignacionesDocentesPrograma implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");


    String _nombres = cliente.getNombres();
    int _id_docente = cliente.getId_usuario();
    int _id_rol = cliente.getId_rol();    

    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_programa = request.getParameter("id_programa");
    String sId_plan = request.getParameter("id_plan");
    
    int iGestion = Integer.parseInt(sGestion);
    int iPeriodo = Integer.parseInt(sPeriodo);
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
	

	modelo.put("evaluacion",Integer.toString(iId_tipo_evaluacion));
    modelo.put("gestion", sGestion);    
    modelo.put("periodo", sPeriodo);
    modelo.put("id_programa", sId_programa);    
    modelo.put("id_plan", sId_plan);    
    modelo.put("periodo", sPeriodo);
    
    if ((!"".equals(sId_programa)) && (!"".equals(sId_plan))) {

      //Lista de Programas (carreras)
      Programas programa = new Programas();
      programa.setId_programa(Integer.parseInt(sId_programa));
      programa = this.mi.getPrgBuscarPrograma(programa);
      modelo.put("datosPrograma", programa);

     //Sacamos los datos de la Facultad
      Facultades datosFacultad = new Facultades();
      datosFacultad.setId_facultad(programa.getId_facultad());
      datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
      modelo.put("datosFacultad", datosFacultad);

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


      //Sacamos lista de docente con sus asignaciones a las materias
      Asignaciones datosAsignacion = new Asignaciones();
      datosAsignacion.setGestion(iGestion);
	   System.out.println("La gestion de esta designacion es: "+datosAsignacion.getGestion());
      datosAsignacion.setPeriodo(iPeriodo);
	   System.out.println("El periodo de esta designacion es: "+datosAsignacion.getPeriodo());
      datosAsignacion.setId_programa(Integer.parseInt(sId_programa));
	   System.out.println("El id_programa es -->"+Integer.parseInt(sId_programa));
      datosAsignacion.setId_plan(sId_plan);
	  System.out.println("El plan es -->"+Integer.parseInt(sId_plan));
	  datosAsignacion.setId_tipo_evaluacion(iId_tipo_evaluacion);
      List lAsignacionDocentesMateria = this.mi.getDctListarAsignacionDocenteMateriaFuncion(datosAsignacion);
      modelo.put("lAsignacionDocentesMateria", lAsignacionDocentesMateria);
    
      modelo.put("id_rol",Integer.toString(_id_rol));     
    }
    else{
      return new ModelAndView("Error", "mensaje","Faltan datos");
    }      
      return new ModelAndView("reportesAcademicos/asignacionDocentes/ListarAsignacionesDocentesPrograma", modelo);
  }
}