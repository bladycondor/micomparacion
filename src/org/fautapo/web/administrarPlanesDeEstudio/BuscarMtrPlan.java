package org.fautapo.web.administrarPlanesDeEstudio;

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

public class BuscarMtrPlan implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Si dio volver recuperamos los datos
    String sId_facultad = request.getParameter("id_facultad");
    String sId_prg_plan = request.getParameter("id_prg_plan");
    String sId_mtr_plan = request.getParameter("id_mtr_plan");

    modelo.put("id_facultad", sId_facultad);
    modelo.put("id_prg_plan", sId_prg_plan);

    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(Integer.parseInt(sId_facultad));
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);

    //Sacamos los datos del plan
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(Integer.parseInt(sId_prg_plan));
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);

    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosPrgPlan.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    //Sacamos los datos del plan
    Planes datosMtrPlan = new Planes();
    datosMtrPlan.setId_mtr_plan(Integer.parseInt(sId_mtr_plan));
    datosMtrPlan = this.mi.getBuscarMtrPlan(datosMtrPlan);
    modelo.put("datosMtrPlan", datosMtrPlan);
  
    //Sacamos el los datos de la materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(datosMtrPlan.getId_materia());
    datosMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("datosMateria", datosMateria);
  
    //Sacamos el listado de las menciones
    Planes datosPlan = new Planes();
    datosPlan.setId_programa(datosPrgPlan.getId_programa());
    datosPlan.setId_plan(datosPrgPlan.getId_plan());
    List lMenciones = this.mi.getMncListarMenciones(datosPlan);
    modelo.put("lMenciones", lMenciones);

    //Sacamos el listado de las materias
    Planes auxiliar = new Planes();
    auxiliar.setId_plan(datosPrgPlan.getId_plan());
    auxiliar.setId_programa(datosPrgPlan.getId_programa());
    List lMaterias = this.mi.getListarMateriasNoPlan(auxiliar);
    modelo.put("lMaterias", lMaterias);
    
    //Sacamos el listado de los tipos_materias
    List lTiposMaterias = this.mi.getPlnListarTiposMaterias();
    modelo.put("lTiposMaterias", lTiposMaterias);

    return new ModelAndView("administrarPlanesDeEstudio/BuscarMtrPlan", modelo);
  }
}