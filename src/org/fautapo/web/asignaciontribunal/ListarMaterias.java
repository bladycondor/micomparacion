package org.fautapo.web.asignaciontribunal;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
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
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");

    iId_programa = Integer.parseInt(request.getParameter("id_programa"));
    sId_plan = request.getParameter("id_plan");
	String sId_estudiante = request.getParameter("id_estudiante");
	int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");  
    int iId_prg_plan = cliente.getInt(request, "id_programa");
    
    //Buscamos los datos del Programa
	Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("programa", datosPrograma);
    if(datosPrograma == null)
      return new ModelAndView("Aviso","mensaje","No existe la Carrera seleccionadas");
	
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
	datosEstudiante.setId_programa(iId_programa);
    datosEstudiante = this.mi.getEstBuscarEstudiantePrograma(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
	
	if(datosEstudiante == null)
      return new ModelAndView("Aviso","mensaje","El Estudiante no es de su Unidad");

    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante2", datosEstudiante);
	
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);
   
    Estudiantes datosMatricula = new Estudiantes();
	
    if(iId_tipo_evaluacion == 0)
      return new ModelAndView("Aviso","mensaje","Seleccione el Tipo de Evalucion");  
    
	if ("".equals(sId_estudiante)) 
      return new ModelAndView("Aviso","mensaje","Ingrese el Registro Universitario");  
	
	//Buscando los datos del Estudiante
    if (!"".equals(sId_estudiante)) 
	{  
      datosMatricula.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosMatricula.setGestion(Integer.parseInt(sGestion));    
      datosMatricula.setPeriodo(Integer.parseInt(sPeriodo));    
      datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
	}  
	
	if (datosMatricula == null) {
        return new ModelAndView("Aviso", "mensaje", "El estudiante con R.U. "+sId_estudiante+" no esta matriculado para la gestion "+sGestion+" y periodo "+sPeriodo);
    }
   
    //Buscar Tipo evaluacion
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval", datosTipoEval);
    
	//Listamos los miembros del tribunal
    Estudiantes datosEstudiantes = new Estudiantes();
    List lMiembros = this.mi.getlistarMiembrosT(datosEstudiantes);
    modelo.put("lMiembros", lMiembros);

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
    List listaPlanEstudio = this.mi.getListarPlanProgramaModeloAhorro(aux);
    for (int i = 0; i < listaPlanEstudio.size(); i++) {
      Modelos_ahorros materias = (Modelos_ahorros) listaPlanEstudio.get(i);
      aux.setId_materia(materias.getId_materia());
      System.out.println("El id_materia dct -->"+Integer.toString(materias.getId_materia()));
      aux.setId_modelo_ahorro(materias.getId_modelo_ahorro());
      System.out.println("El id_modelo_ahorro dct -->"+Integer.toString(materias.getId_modelo_ahorro()));
      aux.setId_tipo_evaluacion(iId_tipo_evaluacion);
      System.out.println("El id_tipo_evaluacion dct -->"+Integer.toString(aux.getId_tipo_evaluacion()));
      materias.setGrupos(this.mi.getDptoListarGruposMateriaTipoEvaluacion(aux));
      materias.setNro_grupos(materias.getGrupos().size());
      listaPlanEstudio.set(i, materias);
    }
    
    //PagedListHolder listarplanestudios = new PagedListHolder(listaPlanEstudio);
	PagedListHolder listarplanestudios = new PagedListHolder(lMiembros);
    listarplanestudios.setPageSize(listarplanestudios.getNrOfElements());
    modelo.put("listarplanestudios", listarplanestudios);
    
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("id_programa", request.getParameter("id_programa"));
    modelo.put("id_plan", request.getParameter("id_plan"));
    modelo.put("id_materia", request.getParameter("id_materia"));
    
    return new ModelAndView("asignaciontribunal/ListarMaterias", modelo);
  }
}