package org.fautapo.web.administrarVolverFase;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Asignaciones;
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

public class listarDocentes implements Controller {
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

    String gestion = request.getParameter("gestion");
    String periodo = request.getParameter("periodo");
    String Sid_departamento = request.getParameter("id_departamento");
    String Sid_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String Sid_facultad = request.getParameter("id_facultad");
    

    String Sid_departamento1 = request.getParameter("id_departamento1");
    String Sid_facultad1 = request.getParameter("id_facultad1");

    //int _gestion = Integer.parseInt(gestion);
    //int _periodo = Integer.parseInt(periodo);
    //int _Id_departamento = Integer.parseInt(Sid_departamento);
    //int _Id_tipo_evaluacion = Integer.parseInt(Sid_tipo_evaluacion);
    //int _Id_facultad = Integer.parseInt(Sid_facultad);
	
	int _gestion = 0;
    int _periodo = 0;
    int _Id_departamento = 0;
    int _Id_tipo_evaluacion = 0;
    int _Id_facultad = 0;

    modelo.put("gestion", gestion);    
    modelo.put("periodo", periodo);
    
	if (Sid_departamento == null)
	{
	  _gestion = Integer.parseInt(gestion);
      _periodo = Integer.parseInt(periodo);
      _Id_departamento = Integer.parseInt(Sid_departamento1);
      _Id_tipo_evaluacion = Integer.parseInt(Sid_tipo_evaluacion);
	  System.out.print("VAlor "+Sid_departamento1);

     // _Id_facultad = Integer.parseInt(Sid_facultad1);	   
	   
       modelo.put("id_departamento", Sid_departamento1);
       modelo.put("id_tipo_evaluacion", Sid_tipo_evaluacion);
      // modelo.put("id_facultad", Sid_facultad1);	
	
	}
	else
	{
	  _gestion = Integer.parseInt(gestion);
      _periodo = Integer.parseInt(periodo);
      _Id_departamento = Integer.parseInt(Sid_departamento);
      _Id_tipo_evaluacion = Integer.parseInt(Sid_tipo_evaluacion);
      _Id_facultad = Integer.parseInt(Sid_facultad);
	  
	   modelo.put("id_departamento", Sid_departamento);
       modelo.put("id_tipo_evaluacion", Sid_tipo_evaluacion);
       modelo.put("id_facultad", Sid_facultad);	
	}
    
    
    //Sacamos datos del FCLDepartamento
    Departamentos buscar = new Departamentos();
    buscar.setId_departamento(_Id_departamento);
    Departamentos datosFclDepartamento = this.mi.getDptBuscarDepartamento(buscar);
    modelo.put("fcldepartamento", datosFclDepartamento.getDepartamento());
    
    //Sacamos el listado de los tipos_evaluaciones
    Libretas datosTipoEvaluacion = new Libretas();
    datosTipoEvaluacion.setId_tipo_evaluacion(_Id_tipo_evaluacion);
    datosTipoEvaluacion = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEvaluacion);
    modelo.put("datosTipoEvaluacion", datosTipoEvaluacion);
    
    
    //Sacamos lista de docente con sus asignaciones a las materias
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setGestion(_gestion);
    datosAsignacion.setPeriodo(_periodo);
    datosAsignacion.setId_departamento(_Id_departamento);
    datosAsignacion.setId_tipo_evaluacion(_Id_tipo_evaluacion);
    List listaDatosAsignacion = this.mi.getListarDocentesProgramados(datosAsignacion);
    modelo.put("datosAsignacion", listaDatosAsignacion);
    
    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(_Id_facultad);
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);
    
    modelo.put("id_rol",Integer.toString(_id_rol));     
    return new ModelAndView("administrarVolverFase/listarDocentes", modelo);
  }
}