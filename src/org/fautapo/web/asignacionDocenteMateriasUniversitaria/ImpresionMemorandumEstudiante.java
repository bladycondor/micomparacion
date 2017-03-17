package org.fautapo.web.asignacionDocenteMateriasUniversitaria;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import org.springframework.beans.support.PagedListHolder;

import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Docentes;
//import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Abm;
//import org.fautapo.domain.logic.MiFacade;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */
public class ImpresionMemorandumEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    modelo.put("cliente",cliente);
    modelo.put("gestion",request.getParameter("gestion"));
    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) modelo.put("datosInstitucion", datosInstitucion);    
  /*  
    String nombre_completo = request.getParameter("nombre_completo");
    String id_perfil = request.getParameter("id_perfil");  
    String programa  = request.getParameter("programa");  
    String area      = request.getParameter("area");  
    String sigla     = request.getParameter("sigla");  
    String materia   = request.getParameter("materia");  
    String carga_horaria   = request.getParameter("carga_horaria");  
    
    int iGestion     = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo     = Integer.parseInt(request.getParameter("periodo"));
    int iId_programa = Integer.parseInt(request.getParameter("id_programa"));
    String sId_plan  = request.getParameter("id_plan");
    String sAccion   = request.getParameter("accion");   
  int iId_dpto_grupo = cliente.getInt(request, "id_dpto_grupo");
    int iId_asignacion = cliente.getInt(request, "id_asignacion");
	
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");

    modelo.put("gestion", request.getParameter("gestion"));
    modelo.put("periodo", request.getParameter("periodo"));
    modelo.put("id_programa", request.getParameter("id_programa"));    
    modelo.put("programa", request.getParameter("programa"));        
    modelo.put("id_materia", request.getParameter("id_materia"));        
    modelo.put("materia", request.getParameter("materia"));        
    modelo.put("id_prg_plan", request.getParameter("id_prg_plan"));
    modelo.put("id_asignacion", request.getParameter("id_asignacion"));
    modelo.put("accion", sAccion);
    
      
    	
    modelo.put("nombre_completo",nombre_completo);
    modelo.put("id_perfil",id_perfil);
    modelo.put("programa",programa);
    modelo.put("area",area);
    modelo.put("sigla",sigla);
    modelo.put("materia",materia);    

    modelo.put("carga_horaria",carga_horaria);
    
	*/
	int iGestion     = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo     = Integer.parseInt(request.getParameter("periodo"));
    int iId_programa = Integer.parseInt(request.getParameter("id_programa"));
    String sId_plan  = request.getParameter("id_plan");
	int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    String sAccion   = request.getParameter("accion");
   
	
    int iId_dpto_grupo = cliente.getInt(request, "id_dpto_grupo");
    int iId_asignacion = cliente.getInt(request, "id_asignacion");
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");

    modelo.put("gestion", request.getParameter("gestion"));
    modelo.put("periodo", request.getParameter("periodo"));
    modelo.put("id_programa", request.getParameter("id_programa"));    
    modelo.put("programa", request.getParameter("programa"));        
    modelo.put("id_materia", request.getParameter("id_materia"));        
    modelo.put("materia", request.getParameter("materia"));        
    modelo.put("id_prg_plan", request.getParameter("id_prg_plan"));
    modelo.put("id_asignacion", request.getParameter("id_asignacion"));
    modelo.put("accion", sAccion);
    
    //Buscamos la asignacion docente y su memo
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(iId_asignacion);
	System.out.print("El id asignacion para el memo es: "+datosAsignacion.getId_asignacion());
    datosAsignacion = this.mi.getDctBuscarAsignacionDocentemaslafuncion(datosAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
	
	   
    if(datosAsignacion != null) {
      //Buscamos al docente
     Docentes datosDocente = new Docentes();
     datosDocente.setId_docente(datosAsignacion.getId_docente()); 
     datosDocente = this.mi.getBuscarDocente(datosDocente);
     modelo.put("datosDocente", datosDocente);
    }
    	  
	//Buscamos los datos de prg_planes
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);
    if(datosPrgPlan == null)
      return new ModelAndView("Error","mensaje", "No existe el Programa-Plan");
      
    //Buscamos dpto_grupo
    Grupos datosDptoGrupo = new Grupos();
    datosDptoGrupo.setId_dpto_grupo(iId_dpto_grupo);
    datosDptoGrupo = this.mi.getDptoBuscarGrupo(datosDptoGrupo);
    modelo.put("datosDptoGrupo", datosDptoGrupo);
    if(datosDptoGrupo == null)
      return new ModelAndView("Error","mensaje", "No existen datos en Dpto-Grupos");
    
    //Sacamos los datos del programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("programa", datosPrograma);

    //Sacamos los datos de la Materia
    Materias materia = new Materias();
    materia.setId_materia(datosDptoGrupo.getId_materia());  //Sacamos la materia de dpto_Grupo
    System.out.println("La id_materia buscando -->"+ Integer.toString(materia.getId_materia()));
    materia = (Materias) this.mi.getMtrBuscarMateria(materia);
    modelo.put("materia", materia);
    
    
    //Listamos docentes 
    List lDocentesTodos = this.mi.getListarDocentesTodos();
    modelo.put("lDocentesTodos", lDocentesTodos);
    System.out.println("Tamanio de la lista -->"+ Integer.toString(lDocentesTodos.size()));
    
    //Listamos los tipos
    List lTiposDocentes = this.mi.getListarTiposDocentes();
    modelo.put("lTiposDocentes", lTiposDocentes);
    List lTiposAsignaciones = this.mi.getListarTiposAsignaciones();
    modelo.put("lTiposAsignaciones",lTiposAsignaciones);
    
    
	//Listamos funciones
    List lTiposFunciones = this.mi.getListarTiposFunciones();
    modelo.put("lTiposFunciones", lTiposFunciones);    
	
	//Sacamos el formato de la fecha	
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    
  //  Asignaciones asigna = new Asignaciones();
   //   asigna.setGestion(iGestion);
   //   asigna.setPeriodo(iPeriodo);
   //   asigna.setId_programa(Integer.parseInt(iId_programa));
	//   System.out.println("El id_programa es -->"+Integer.parseInt(iId_programa));
   //   asigna.setId_plan(sId_plan);
	//  asigna.setId_tipo_evaluacion(iId_tipo_evaluacion);
   //   List lAsignacionDocentesMateria = this.mi.getDctListarAsignacionDocenteMateriaFuncion(asigna);
   //   modelo.put("lAsignacionDocentesMateria", lAsignacionDocentesMateria);
	  
    
	
    return new ModelAndView("asignacionDocenteMateriasUniversitaria/Memo",modelo);
  }
}
