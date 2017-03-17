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

public class RegistrarMateriaPlan implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Planes datosPlan = new Planes();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    String sId_facultad = request.getParameter("id_facultad");
    String sId_prg_plan = request.getParameter("id_prg_plan");
    String sId_materia = request.getParameter("id_materia");
    String sId_tipo_materia = request.getParameter("id_tipo_materia");
    String sId_mencion = request.getParameter("id_mencion");
    String sNivel_academico = request.getParameter("nivel_academico");
    String sId_mtr_plan = request.getParameter("id_mtr_plan");
    String sAccion = request.getParameter("accion");
    
    modelo.put("id_facultad", sId_facultad);
    modelo.put("id_prg_plan", sId_prg_plan);

    //Sacamos los datos del plan
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(Integer.parseInt(sId_prg_plan));
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);

    //Eliminamos la materia del plan
    if (("Eliminar").equals(sAccion)) {
      datosPlan = new Planes();
      datosPlan.setId_mtr_plan(Integer.parseInt(sId_mtr_plan));
      int iResultado  = this.mi.setEliminarMtrPlan(datosPlan);
      if (iResultado == 2) {
        modelo.put("mensaje", "No se puede eliminar la materia ya que tiene pre-requisitos");
        return new ModelAndView("administrarPlanesDeEstudio/Error", modelo);
      }
    }

    //Registramos la nueva materia en el plan
    if (("Adicionar").equals(sAccion)) {
      datosPlan = new Planes();
      datosPlan.setId_programa(datosPrgPlan.getId_programa());
      datosPlan.setId_plan(datosPrgPlan.getId_plan());
      datosPlan.setId_plan_ant(datosPrgPlan.getId_plan());
      datosPlan.setId_mencion(Integer.parseInt(sId_mencion));
      datosPlan.setNivel_academico(Integer.parseInt(sNivel_academico));
      datosPlan.setId_tipo_materia(Integer.parseInt(sId_tipo_materia));
      datosPlan.setId_tipo_grado(datosPrgPlan.getId_tipo_grado());
      datosPlan.setId_rol(cliente.getId_rol());
      datosPlan.setUlt_usuario(cliente.getId_usuario());
      datosPlan.setId_materia(Integer.parseInt(sId_materia));
      datosPlan.setId_materia_ant(Integer.parseInt(sId_materia));
      int iResultado  = this.mi.setRegistrarMtrPlan(datosPlan);
    }

    if (("Modificar").equals(sAccion)) {
      datosPlan = new Planes();
      datosPlan.setId_programa(datosPrgPlan.getId_programa());
      datosPlan.setId_plan(datosPrgPlan.getId_plan());
      datosPlan.setId_plan_ant(datosPrgPlan.getId_plan());
      datosPlan.setId_mencion(Integer.parseInt(sId_mencion));
      datosPlan.setNivel_academico(Integer.parseInt(sNivel_academico));
      datosPlan.setId_tipo_materia(Integer.parseInt(sId_tipo_materia));
      datosPlan.setId_tipo_grado(datosPrgPlan.getId_tipo_grado());
      datosPlan.setId_rol(cliente.getId_rol());
      datosPlan.setUlt_usuario(cliente.getId_usuario());
      datosPlan.setId_mtr_plan(Integer.parseInt(sId_mtr_plan));
      int iResultado = this.mi.setModificarMtrPlan(datosPlan);
      if (iResultado == 2) {
        modelo.put("mensaje", "No se puede modificar el tipo de materia, ya que es electiva de otra materia");
        return new ModelAndView("administrarPlanesDeEstudio/Error", modelo);
      }
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

    //Sacamos el listado de las materias del plan nuevo
    datosPlan = new Planes();
    datosPlan.setId_programa(datosPrgPlan.getId_programa());
    datosPlan.setId_plan(datosPrgPlan.getId_plan());
    datosPlan.setId_tipo_grado(datosPrgPlan.getId_tipo_grado());
    List lPlanDeEstudios = this.mi.getListarMateriasPlanRequisitos(datosPlan);
    modelo.put("lPlanDeEstudios", lPlanDeEstudios);

    //Sacamos el listado de las materias electivas
    List lElectivasPlanDeEstudios = this.mi.getListarMateriasElectivasPlan(datosPlan);
    modelo.put("lElectivasPlanDeEstudios", lElectivasPlanDeEstudios);

    return new ModelAndView("administrarPlanesDeEstudio/ListarPlanDeEstudio", modelo);
  }
}