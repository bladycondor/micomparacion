package org.fautapo.web.administrarPerfilesMaterias;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Libretas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
*/

public class ListarMaterias implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    String sId_facultad = request.getParameter("id_facultad");
    String sId_programa = request.getParameter("id_programa");
    String sId_plan = request.getParameter("id_plan");
    String sId_perfil = request.getParameter("id_perfil");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");

    modelo.put("id_facultad", sId_facultad);
    modelo.put("id_programa", sId_programa);
    modelo.put("id_plan", sId_plan);
    modelo.put("id_perfil", sId_perfil);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    
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

    //Sacamos los datos del perfil
    Perfiles datosPerfil = new Perfiles();
    datosPerfil.setId_perfil(Integer.parseInt(sId_perfil));
    datosPerfil = this.mi.getPrfBuscarPerfil(datosPerfil);
    modelo.put("datosPerfil", datosPerfil);

    //Buscamos Tipo Evaluacion
    Libretas datosTipoEvaluacion = new Libretas();
    datosTipoEvaluacion.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));
    datosTipoEvaluacion = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEvaluacion);
    modelo.put("datosTipoEvaluacion", datosTipoEvaluacion);

    //Sacamos el listado de las materias del plan
    Planes datosPlan = new Planes();
    datosPlan.setId_programa(Integer.parseInt(sId_programa));
    datosPlan.setId_plan(sId_plan);
    datosPlan.setId_perfil(Integer.parseInt(sId_perfil));
    datosPlan.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));
    datosPlan.setGestion(Integer.parseInt(sGestion));
    datosPlan.setPeriodo(Integer.parseInt(sPeriodo));
    List lPlanMaterias = this.mi.getTrnListarPerfilesMaterias(datosPlan);
    modelo.put("lPlanMaterias", lPlanMaterias);

    return new ModelAndView("administrarPerfilesMaterias/ListarMaterias", modelo);
  }
}