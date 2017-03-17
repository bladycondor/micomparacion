package org.fautapo.web.administrarLibretasDesignacion;

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
//import org.fautapo.domain.Memo;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;   
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;
import org.fautapo.domain.Planes;
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
    
   // String sId_tipo_nota_s     = "";  //AUXILIAR 
   // int iId_asignacion = cliente.getInt(request, "id_asignacion");
   // String sId_tipo_grado      = request.getParameter("id_tipo_grado");        
  //  String sId_programa        = request.getParameter("id_programa");
    
  //  System.out.println("id_asignacion_docente--------avanzar fase------>" + Integer.toString(iId_asignacion));
    
  //  modelo.put("nombres", cliente.getNombres());
  //  modelo.put("id_tipo_grado", sId_tipo_grado);    
  //  modelo.put("id_programa", sId_programa);    
    int iId_departamento = cliente.getInt(request,"id_programa");
	int iGestion     = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo     = Integer.parseInt(request.getParameter("periodo"));
     int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
	int iId_programa = Integer.parseInt(request.getParameter("id_programa"));
	int iResultadoFaseResolucion =0;
 int ibuscarResultadoFaseResolucion=0;
	String sId_plan="";

  //  String sId_plan  = request.getParameter("id_plan");
    //String sAccion   = request.getParameter("accion");
   
  //  int iId_dpto_grupo = cliente.getInt(request, "id_dpto_grupo");
  //  int iId_asignacion = cliente.getInt(request, "id_asignacion");
  //  int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    modelo.put("id_departamento", request.getParameter("iId_departamento")); 
    modelo.put("gestion", request.getParameter("iGestion"));
    modelo.put("periodo", request.getParameter("iPeriodo"));
    modelo.put("id_tipo_evaluacion", request.getParameter("iId_tipo_evaluacion"));  	
  //  modelo.put("programa", request.getParameter("programa"));        
  //  modelo.put("id_materia", request.getParameter("id_materia"));        
  //  modelo.put("materia", request.getParameter("materia"));        
  //  modelo.put("id_prg_plan", request.getParameter("id_prg_plan"));
 //   modelo.put("id_asignacion", request.getParameter("id_asignacion"));
   // modelo.put("accion", sAccion);
    
    //Buscamos la asignacion docente
    Asignaciones buscarAsignacion = new Asignaciones();
    buscarAsignacion.setId_departamento(iId_departamento);
	System.out.println("El id departamento es: "+buscarAsignacion.getId_departamento());
	buscarAsignacion.setGestion(iGestion);
	System.out.println("la gestion es: "+buscarAsignacion.getGestion());
	buscarAsignacion.setPeriodo(iPeriodo);
	System.out.println("El periodo es: "+buscarAsignacion.getPeriodo());
	buscarAsignacion.setId_tipo_evaluacion(iId_tipo_evaluacion);
	System.out.println("El id evaluacion es: "+buscarAsignacion.getId_tipo_evaluacion());
   // Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocenteDesignacion(buscarAsignacion);
   
    
   // if(datosAsignacion == null)
   //   return new ModelAndView("Error", "mensaje", "No se encontr&oacute; la asignaci&oacute;n para la gestion"+iGestion+iPeriodo+iId_tipo_evaluacion+iId_departamento);
    // modelo.put("datosAsignacion", datosAsignacion);
	iResultadoFaseResolucion = this.mi.setRegistrarFaseResolucion(buscarAsignacion);
	
	if(iResultadoFaseResolucion==0)
	return new ModelAndView("Error","mensaje","No se realizo el avance de las designaciones o la fase ya a sido avanzada");
	
      //Buscando la fase de la resolucion
    Asignaciones asigna = new Asignaciones();
    asigna.setId_programa(iId_programa);
	System.out.println("El id_programa dct -->"+Integer.toString(asigna.getId_programa()));
	 asigna.setId_tipo_evaluacion(iId_tipo_evaluacion);
	 System.out.println("El id_tipo_evaluacion dct -->"+Integer.toString(asigna.getId_tipo_evaluacion()));
	  asigna.setGestion(iGestion);
	  System.out.println("La gestion dct -->"+Integer.toString(asigna.getGestion()));
	   asigna.setPeriodo(iPeriodo);
	   System.out.println("El periodo dct -->"+Integer.toString(asigna.getPeriodo()));
	   modelo.put("asigna",asigna);
	   //asigna=
	try{
   ibuscarResultadoFaseResolucion = this.mi.setBuscar_id_fase_resolucion(asigna);  
    System.out.println("LA FASE DE RESOLUCION -->"+ibuscarResultadoFaseResolucion);
	modelo.put("ibuscarResultadoFaseResolucion", Integer.toString(ibuscarResultadoFaseResolucion));}catch(NullPointerException e){
System.out.println("Excepcion llenada");
}
    
    return new ModelAndView("administrarLibretasDesignacion/AvanzarFase", modelo);
  }
}