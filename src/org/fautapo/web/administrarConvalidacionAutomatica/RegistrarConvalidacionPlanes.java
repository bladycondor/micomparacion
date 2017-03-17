package org.fautapo.web.administrarConvalidacionAutomatica;

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

public class RegistrarConvalidacionPlanes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iResultado = 0; Planes datosPlan = new Planes();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    if (("".equals(request.getParameter("id_plan_antiguo"))) || ("".equals(request.getParameter("id_plan_nuevo")))) {
      return new ModelAndView("Error", "mensaje", "Faltan datos el cambio no puede completarse");
    }

    String sId_prg_plan_antiguo = request.getParameter("id_prg_plan_antiguo");
    String sId_prg_plan_nuevo = request.getParameter("id_prg_plan_nuevo");
    String sId_materia = request.getParameter("id_materia");
    String sId_programa = request.getParameter("id_programa");
    String sId_facultad = request.getParameter("id_facultad");
    String sId_mencion = request.getParameter("id_mencion");
    String sNivel_academico = request.getParameter("nivel_academico");
    String sAccion = request.getParameter("accion");
    
    modelo.put("id_facultad", sId_facultad);
    modelo.put("id_programa", sId_programa);
    modelo.put("id_prg_plan_antiguo", sId_prg_plan_antiguo);
    modelo.put("id_prg_plan_nuevo", sId_prg_plan_nuevo);
    modelo.put("id_materia", sId_materia);
    modelo.put("id_mencion", sId_mencion);
    modelo.put("nivel_academico", sNivel_academico);

    //Sacamos los datos del plan antiguo
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(Integer.parseInt(sId_prg_plan_antiguo));
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    String sId_plan_antiguo = datosPrgPlan.getId_plan();
    modelo.put("datosPrgPlanAntiguo", datosPrgPlan);

    //Sacamos los datos del plan nuevo
    datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(Integer.parseInt(sId_prg_plan_nuevo));
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    String sId_plan_nuevo = datosPrgPlan.getId_plan();
    modelo.put("datosPrgPlanNuevo", datosPrgPlan);

    //Registramos la convalidacion
    if ((!"".equals(sId_materia) && sId_materia != null)) {
      datosPlan = new Planes();
      datosPlan.setId_programa(Integer.parseInt(sId_programa));
      datosPlan.setId_plan_ant(sId_plan_antiguo);
      datosPlan.setId_plan(sId_plan_nuevo);
      datosPlan.setId_materia(Integer.parseInt(sId_materia));
      datosPlan.setId_mencion(Integer.parseInt(sId_mencion));
      datosPlan.setNivel_academico(Integer.parseInt(sNivel_academico));
      datosPlan.setId_rol(cliente.getId_rol());
      datosPlan.setUlt_usuario(cliente.getId_usuario());

      if ("Adicionar".equals(sAccion)) {
        //Recuperamos las materias del antiguo plan
        String sId_materia_anterior_adicionar[] = request.getParameterValues("id_materia_anterior_adicionar");
        if (sId_materia_anterior_adicionar != null) {
          for (int j = 0; j< sId_materia_anterior_adicionar.length; j++) {
            //Sacamos los datos de la materia_ant del plan_ant
            Planes datosMateriaAnt = new Planes();
            datosMateriaAnt.setId_programa(Integer.parseInt(sId_programa));
            datosMateriaAnt.setId_plan(sId_plan_antiguo);
            datosMateriaAnt.setId_materia(Integer.parseInt(sId_materia_anterior_adicionar[j]));
	    datosMateriaAnt = this.mi.getBuscarMateriaPlan(datosMateriaAnt);

            //Completamos los datos para el registro
  	    datosPlan.setId_materia_ant(Integer.parseInt(sId_materia_anterior_adicionar[j]));
            datosPlan.setId_tipo_evaluacion(datosMateriaAnt.getId_tipo_evaluacion());
            datosPlan.setId_tipo_grado(datosMateriaAnt.getId_tipo_grado());
            datosPlan.setId_tipo_materia(datosMateriaAnt.getId_tipo_materia());
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
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    //Sacamos los datos de la Materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(Integer.parseInt(sId_materia));
    datosMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("datosMateria", datosMateria);
    
    //Sacamos los datos de la Mencion
    Planes datosMencion = new Planes();
    datosMencion.setId_mencion(Integer.parseInt(sId_mencion));
    datosMencion = this.mi.getMncBuscarMencion(datosMencion);
    modelo.put("datosMencion", datosMencion);
    
    //Sacamos el listado de las materias del plan antiguo
    datosPlan = new Planes();
    datosPlan.setId_programa(Integer.parseInt(sId_programa));
    datosPlan.setId_plan(sId_plan_nuevo);
    datosPlan.setId_plan_ant(sId_plan_antiguo);
    datosPlan.setId_materia(Integer.parseInt(sId_materia));
    datosPlan.setId_mencion(Integer.parseInt(sId_mencion));
    List lMateriasPlanAntiguoConvalidadas = this.mi.getListarMateriasPlanAnterior(datosPlan);
    modelo.put("lMateriasPlanAntiguoConvalidadas", lMateriasPlanAntiguoConvalidadas);

    List lMateriasPlanAntiguoSinConvalidar = this.mi.getListarMateriasPlanAnterior2(datosPlan);
    modelo.put("lMateriasPlanAntiguoSinConvalidar", lMateriasPlanAntiguoSinConvalidar);

    return new ModelAndView("administrarConvalidacionAutomatica/ListarPlanesConvalidacion2", modelo);
  }
}