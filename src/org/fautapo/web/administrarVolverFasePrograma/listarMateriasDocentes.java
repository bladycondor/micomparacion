package org.fautapo.web.administrarVolverFasePrograma;

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
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Modelos_ahorros;
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

public class listarMateriasDocentes implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String _nombres = cliente.getNombres();
    int _id_rol = cliente.getId_rol();    

    //Recuperamos las variables
    int iGestion = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo = Integer.parseInt(request.getParameter("periodo"));
    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_facultad = cliente.getInt(request, "id_facultad");
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    //Buscamos los datos de prg_planes
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);
    
    if(datosPrgPlan == null)
      return new ModelAndView("Aviso","mensaje","No existe el plan seleccionado en Programas - Planes");
    
    if(iId_tipo_evaluacion == 0)
      return new ModelAndView("Aviso","mensaje","Seleccione el Tipo de Evalucion");  
    
    
    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(iId_facultad);
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);
    //Buscando el programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("datosPrograma", datosPrograma);
    
    //Buscar Tipo evaluacion
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval", datosTipoEval);
    
    //Listamos las materias del plan mas sus grupos
    Modelos_ahorros aux = new Modelos_ahorros();
    aux.setId_programa(iId_programa);
    aux.setId_plan(datosPrgPlan.getId_plan());  //Sacando el plan de prg_planes
    aux.setGestion(iGestion);
    aux.setPeriodo(iPeriodo);
    aux.setId_tipo_grado(datosPrgPlan.getId_tipo_grado()); //Sacando el id_tipo_grado Universitario-Vestibular
    List listaPlanEstudio = this.mi.getListarPlanProgramaModeloAhorro(aux);
    Libretas datosAsignacion = new Libretas();
    for (int i = 0; i < listaPlanEstudio.size(); i++) {
      Modelos_ahorros asignacion = (Modelos_ahorros) listaPlanEstudio.get(i);
      aux.setId_materia(asignacion.getId_materia());
      aux.setId_tipo_evaluacion(iId_tipo_evaluacion);
      asignacion.setMaterias(this.mi.getListarMateriasCerrarLibretaDctAsignacion(aux));
      asignacion.setNro_asignaciones(asignacion.getMaterias().size());
      listaPlanEstudio.set(i, asignacion);
    }
    
    PagedListHolder listarplanestudios = new PagedListHolder(listaPlanEstudio);
    listarplanestudios.setPageSize(listarplanestudios.getNrOfElements());
    modelo.put("listarplanestudios", listarplanestudios);
    
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("id_programa", request.getParameter("id_programa"));
    modelo.put("id_materia", request.getParameter("id_materia"));
    modelo.put("id_rol",Integer.toString(_id_rol));     
    
    return new ModelAndView("administrarVolverFasePrograma/listarMateriasDocentes", modelo);
  }
}