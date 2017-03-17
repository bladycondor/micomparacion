package org.fautapo.web.asignacionDocenteMateriasUniversitaria;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Modelos_ahorros;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-18
 */

public class ListarMaterias implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    int iId_programa=0; String sId_plan="";
    
     Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    if ("".equals(Integer.toString(cliente.getId_rol()))) {
      modelo.put("mensaje", "No se le permite entrar a este módulo");
      return new ModelAndView("Error", modelo);
    }
 
    //Recuperamos las variables
    int iGestion = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo = Integer.parseInt(request.getParameter("periodo"));
    iId_programa = Integer.parseInt(request.getParameter("id_programa"));
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
	//Para la sacar la fase de resolucion de la designacion
	 int iResultadoFaseResolucion =0;
    //Buscamos los datos de prg_planes
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);
    modelo.put("id_plan", sId_plan);    
    if(datosPrgPlan == null)
      return new ModelAndView("Aviso","mensaje","No existe el plan seleccionado en Programas -, Planes");
    
    if(iId_tipo_evaluacion == 0)
      return new ModelAndView("Aviso","mensaje","Seleccione el Tipo de Evaluacion");  
    // sId_plan = this.mi.getBuscarPrgPlanidplan(iId_prg_plan);
    //Buscando el programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("programa", datosPrograma);
	
	//Buscando la fase de la resolucion
    Asignaciones asigna = new Asignaciones();
    asigna.setId_programa(iId_programa);
	System.out.println("El id_programa dct -->"+Integer.toString(asigna.getId_programa()));
	asigna.setId_plan(datosPrgPlan.getId_plan());
	modelo.put("id_plan", sId_plan);    
	System.out.println("El id_plan dct1 -->"+asigna.getId_plan()+sId_plan);
	 asigna.setId_tipo_evaluacion(iId_tipo_evaluacion);
	 System.out.println("El id_tipo_evaluacion dct -->"+Integer.toString(asigna.getId_tipo_evaluacion()));
	  asigna.setGestion(iGestion);
	  System.out.println("La gestion dct -->"+Integer.toString(asigna.getGestion()));
	   asigna.setPeriodo(iPeriodo);
	   System.out.println("El periodo dct -->"+Integer.toString(asigna.getPeriodo()));
	   modelo.put("asigna",asigna);
	   
	try{
   iResultadoFaseResolucion = this.mi.setBuscar_id_fase_resolucionFinal(asigna);  
    System.out.println("LA FASE DE RESOLUCION -->"+iResultadoFaseResolucion);
	modelo.put("iResultadoFaseResolucion", Integer.toString(iResultadoFaseResolucion));}catch(NullPointerException e){
System.out.println("Excepcion llenada de id fase resolucion");
} 

   // modelo.put("asigna", asigna);
    
    //Buscar Tipo evaluacion
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval", datosTipoEval);
    
    //Listamos las materias del plan mas sus grupos
    Modelos_ahorros aux = new Modelos_ahorros();
    aux.setId_programa(iId_programa);
    System.out.println("El id_programa dct -->"+Integer.toString(aux.getId_programa()));
    aux.setId_plan(datosPrgPlan.getId_plan());  //Sacando el plan de prg_planes
    System.out.println("El id_plan dct -->"+aux.getId_plan());
    aux.setGestion(iGestion);
    System.out.println("La gestion dct -->"+Integer.toString(aux.getGestion()));
    aux.setPeriodo(iPeriodo);
    System.out.println("El periodo dct -->"+Integer.toString(aux.getPeriodo()));
    aux.setId_tipo_grado(datosPrgPlan.getId_tipo_grado()); //Sacando el id_tipo_grado Universitario-Vestibular
    System.out.println("El id_tipo_grado dct -->"+Integer.toString(aux.getId_tipo_grado()));
    //try{
	List listaPlanEstudio = this.mi.getListarPlanProgramaModeloAhorro(aux);
    for (int i = 0; i < listaPlanEstudio.size(); i++) {
      Modelos_ahorros materias = (Modelos_ahorros) listaPlanEstudio.get(i);
      aux.setId_materia(materias.getId_materia());
      System.out.println("El id_materia dct -->"+Integer.toString(materias.getId_materia()));
      aux.setId_modelo_ahorro(materias.getId_modelo_ahorro());
      System.out.println("El id_modelo_ahorro dct -->"+Integer.toString(materias.getId_modelo_ahorro()));
      aux.setId_tipo_evaluacion(iId_tipo_evaluacion);
      System.out.println("El id_tipo_evaluacion dct -->"+Integer.toString(aux.getId_tipo_evaluacion()));
	  
      materias.setGrupos(this.mi.getDptoListarGruposMateriaTipoEvaluacion(aux)); //esta funcion fue modificada.
      materias.setNro_grupos(materias.getGrupos().size());
	  System.out.println("datos de-->"+(materias.getGrupos().size()));
      listaPlanEstudio.set(i, materias);
    }
    
    PagedListHolder listarplanestudios = new PagedListHolder(listaPlanEstudio);
    listarplanestudios.setPageSize(listarplanestudios.getNrOfElements());
    modelo.put("listarplanestudios", listarplanestudios); 
	//}catch(Exception e){ 
	//System.out.println( e.toString() );
	//System.out.println("Ocurrio un error");}
    
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("id_programa", request.getParameter("id_programa"));
    //modelo.put("id_plan", request.getParameter("id_plan"));
    modelo.put("id_materia", request.getParameter("id_materia"));
    
    return new ModelAndView("asignacionDocenteMateriasUniversitaria/ListarMaterias", modelo);
  }
}