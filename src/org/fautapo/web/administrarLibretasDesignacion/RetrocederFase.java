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
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;   
import org.fautapo.domain.Roles;
import org.fautapo.domain.Planes;
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

public class RetrocederFase implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
 int _id_rol = cliente.getId_rol();
 //int iId_programa=0;    
   //String sUlt_usuario = cliente.getId_usuario() + "|" + cliente.getId_rol();
    
   // String sId_tipo_nota_s     = "";  //AUXILIAR 
   // int iId_asignacieon = cliente.getInt(request, "id_asignacion");
   // String sId_tipo_grado      = request.getParameter("id_tipo_grado");        
  //  String sId_programa        = request.getParameter("id_programa");
    
  //  System.out.println("id_asignacion_docente--------avanzar fase------>" + Integer.toString(iId_asignacion));
    
  //  modelo.put("nombres", cliente.getNombres());
  //  modelo.put("id_tipo_grado", sId_tipo_grado);    
  //  modelo.put("id_programa", sId_programa);    
 //try{ int iId_programa = Integer.parseInt(request.getParameter("id_programa"));
 // }catch(NumberFormatException ex){
  //      	System.out.println("No es un numero"+iId_programa);
   // 	}
	 int iId_programa = cliente.getInt(request, "id_programa");
	int iGestion     = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo     = Integer.parseInt(request.getParameter("periodo"));
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
	int iResultadoFaseResolucion =0;
	 int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
	String sId_plan="";
	
   //sId_plan  = request.getParameter("id_plan");
    //String sAccion   = request.getParameter("accion");
     //Buscamos los datos de prg_planes
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);
    modelo.put("id_plan", sId_plan);  
  //  int iId_dpto_grupo = cliente.getInt(request, "id_dpto_grupo");
  //  int iId_asignacion = cliente.getInt(request, "id_asignacion");
  //  int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
   modelo.put("id_programa", request.getParameter("id_programa"));  
    modelo.put("gestion", request.getParameter("iGestion"));
    modelo.put("periodo", request.getParameter("iPeriodo"));
    modelo.put("id_tipo_evaluacion", request.getParameter("iId_tipo_evaluacion"));  	
  //  modelo.put("programa", request.getParameter("programa"));        
  //  modelo.put("id_materia", request.getParameter("id_materia"));        
  //  modelo.put("materia", request.getParameter("materia"));        
  //  modelo.put("id_prg_plan", request.getParameter("id_prg_plan"));
 //   modelo.put("id_asignacion", request.getParameter("id_asignacion"));
   // modelo.put("accion", sAccion);
   //Sacamos los datos del programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("programa", datosPrograma);

	int fase=0;
	//Buscando la fase de la resolucion
    Asignaciones asigna = new Asignaciones();
    asigna.setId_programa(iId_programa);
	System.out.println("El id_programa dct -->"+Integer.toString(asigna.getId_programa()));
	asigna.setId_plan(datosPrgPlan.getId_plan());
	modelo.put("id_plan", sId_plan);    
	System.out.println("El id_plan dct2 -->"+asigna.getId_plan());
	asigna.setId_tipo_evaluacion(iId_tipo_evaluacion);
	 System.out.println("El id_tipo_evaluacion dct2 -->"+Integer.toString(asigna.getId_tipo_evaluacion()));
	  asigna.setGestion(iGestion);
	  System.out.println("La gestion dct2 -->"+Integer.toString(asigna.getGestion()));
	   asigna.setPeriodo(iPeriodo);
	   System.out.println("El periodo dct2 -->"+Integer.toString(asigna.getPeriodo()));
	   modelo.put("asigna",asigna);
    try{
   fase = this.mi.setBuscar_id_fase_resolucionFinal(asigna);  
    System.out.println("LA FASE DE RESOLUCION -->"+fase);
	modelo.put("fase", Integer.toString(fase));}catch(NullPointerException e){
System.out.println("Excepcion llenada");
}
	
	//buscamos el rol que tenga el usuario
	Roles rolusu=new Roles();
	rolusu.setId_rol(cliente.getId_rol());
	modelo.put("rolusu",rolusu);
	
    //Buscamos la asignacion docente
    Asignaciones buscarAsignacion = new Asignaciones();
   buscarAsignacion.setId_programa(iId_programa);
	System.out.println("El id_programa dct -->"+Integer.toString(buscarAsignacion.getId_programa()));
	buscarAsignacion.setGestion(iGestion);
	System.out.println(buscarAsignacion.getGestion());
	buscarAsignacion.setPeriodo(iPeriodo);
	System.out.println(buscarAsignacion.getPeriodo());
	buscarAsignacion.setId_tipo_evaluacion(iId_tipo_evaluacion);
	System.out.println(buscarAsignacion.getId_tipo_evaluacion());
   // Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocenteDesignacion(buscarAsignacion);
   
    
   // if(datosAsignacion == null)
   //   return new ModelAndView("Error", "mensaje", "No se encontr&oacute; la asignaci&oacute;n para la gestion"+iGestion+iPeriodo+iId_tipo_evaluacion+iId_departamento);
    // modelo.put("datosAsignacion", datosAsignacion);
	if(fase==1 && _id_rol==33){
	iResultadoFaseResolucion = this.mi.setRegistrarRetrocederFaseResolucion(buscarAsignacion);
	System.out.println(iResultadoFaseResolucion);
	if(iResultadoFaseResolucion==0){
	return new ModelAndView("Error","mensaje","No se pudo realizar el retroceso de las designaciones.");}
	
    }
	else if(fase==2 && _id_rol==91){
	iResultadoFaseResolucion = this.mi.setRegistrarRetrocederFaseResolucion(buscarAsignacion);
	System.out.println(iResultadoFaseResolucion);
	if(iResultadoFaseResolucion==0){
	return new ModelAndView("Error","mensaje","No se pudo realizar el retroceso de las designaciones.");}
	
    }
	
    else{
	return new ModelAndView("Error","mensaje","No se pudo realizar el retroceso de las designaciones, usted no tiene el permiso para retroceder esta fase.");}
    return new ModelAndView("administrarLibretasDesignacion/RetrocederFase", modelo);
  }
}