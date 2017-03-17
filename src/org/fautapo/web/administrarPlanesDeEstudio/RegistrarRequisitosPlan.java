package org.fautapo.web.administrarPlanesDeEstudio;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Materias;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class RegistrarRequisitosPlan implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iResultado = 0; Planes datosPlan = new Planes();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    String sId_prg_plan = request.getParameter("id_prg_plan");
    String sId_mtr_plan = request.getParameter("id_mtr_plan");
    String sId_facultad = request.getParameter("id_facultad");
    String sAccion = request.getParameter("accion");
    
    modelo.put("id_facultad", sId_facultad);
    modelo.put("id_prg_plan", sId_prg_plan);

    //Sacamos los datos del plan
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(Integer.parseInt(sId_prg_plan));
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);

    //Sacamos los datos del plan
    Planes datosMtrPlan = new Planes();
    datosMtrPlan.setId_mtr_plan(Integer.parseInt(sId_mtr_plan));
    datosMtrPlan = this.mi.getBuscarMtrPlan(datosMtrPlan);
    modelo.put("datosMtrPlan", datosMtrPlan);

    //Registramos la convalidacion
    if ((!"".equals(sId_mtr_plan) && sId_mtr_plan != null)) {
      //Sacamos los datos de la materia_ant del plan_ant
      Planes datosMateria = new Planes();
      datosMateria.setId_programa(datosPrgPlan.getId_programa());
      datosMateria.setId_plan(datosPrgPlan.getId_plan());
      datosMateria.setId_materia(datosMtrPlan.getId_materia());
      datosMateria.setId_mencion(datosMtrPlan.getId_mencion());
      datosMateria = this.mi.getBuscarMateriaPlan(datosMateria);

      datosPlan = new Planes();
      datosPlan.setId_programa(datosPrgPlan.getId_programa());
      datosPlan.setId_plan(datosPrgPlan.getId_plan());
      datosPlan.setId_plan_ant(datosPrgPlan.getId_plan());
      datosPlan.setId_materia(datosMtrPlan.getId_materia());
      datosPlan.setId_mencion(datosMtrPlan.getId_mencion());
      datosPlan.setNivel_academico(datosMtrPlan.getNivel_academico());
      datosPlan.setId_tipo_grado(datosMateria.getId_tipo_grado());
      datosPlan.setId_tipo_materia(datosMateria.getId_tipo_materia());
      datosPlan.setId_rol(cliente.getId_rol());
      datosPlan.setUlt_usuario(cliente.getId_usuario());

      if ("Adicionar".equals(sAccion)) {
        //Recuperamos las materias del antiguo plan
        String sId_materia_adicionar[] = request.getParameterValues("id_materia_adicionar");
        if (sId_materia_adicionar != null) {
          for (int j = 0; j< sId_materia_adicionar.length; j++) {
            //Completamos los datos para el registro
  	    datosPlan.setId_materia_ant(Integer.parseInt(sId_materia_adicionar[j]));
            iResultado  = this.mi.setRegistrarMtrPlan(datosPlan);
	  }
        }
        else {
          return new ModelAndView("Error", "mensaje", "Debe seleccionar las materias del antiguo plan");
        }
      }

      if ("Eliminar".equals(sAccion)) {
        //Recuperamos las materias del antiguo plan
        String sId_mtr_plan_eliminar[] = request.getParameterValues("id_mtr_plan_eliminar");
        if (sId_mtr_plan_eliminar != null) {
          for (int j = 0; j< sId_mtr_plan_eliminar.length; j++) {
  	    datosPlan.setId_mtr_plan(Integer.parseInt(sId_mtr_plan_eliminar[j]));
            iResultado  = this.mi.setEliminarMtrPlan(datosPlan);
	    if (iResultado== 2) {
              return new ModelAndView("Error", "mensaje", "No se puede eliminar la materia ya que tiene prerequisitos");
	    }
	  }
        }
        else {
          return new ModelAndView("Error", "mensaje", "Debe seleccionar las materias del antiguo plan");
        }
      }
    }
    else {
      return new ModelAndView("Error", "mensaje", "Debe seleccionar a que materia se convalida en el nuevo plan");
    }

    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(Integer.parseInt(sId_facultad));
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);

    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosPrgPlan.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    //Sacamos los datos de la Materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(datosMtrPlan.getId_materia());
    datosMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("datosMateria", datosMateria);
    
    //Sacamos los datos de la Mencion
    Planes datosMencion = new Planes();
    datosMencion.setId_mencion(datosMtrPlan.getId_mencion());
    datosMencion = this.mi.getMncBuscarMencion(datosMencion);
    modelo.put("datosMencion", datosMencion);
    
    //Sacamos el listado de las materias del plan antiguo
    datosPlan = new Planes();
    datosPlan.setId_programa(datosPrgPlan.getId_programa());
    datosPlan.setId_plan(datosPrgPlan.getId_plan());
    datosPlan.setId_tipo_grado(datosPrgPlan.getId_tipo_grado());
    datosPlan.setId_materia(datosMtrPlan.getId_materia());
    datosPlan.setId_mencion(datosMtrPlan.getId_mencion());
    List lMateriasRequisitos = this.mi.getListarMateriasRequisitos(datosPlan);
    modelo.put("lMateriasRequisitos", lMateriasRequisitos);

    List lMateriasNoRequisitos = this.mi.getListarMateriasNoRequisitos(datosPlan);
    modelo.put("lMateriasNoRequisitos", lMateriasNoRequisitos);

    return new ModelAndView("administrarPlanesDeEstudio/ListarMateriasRequisitos", modelo);
  }
}