package org.fautapo.web.abmDptoGrupos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */

public class ModificaRegistro implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Map modelo = new HashMap();
    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    int _yabe = cliente.getInt(request, "_yabe");
    modelo.put("id_programa", Integer.toString(iId_programa));
    modelo.put("id_tipo_evaluacion", Integer.toString(iId_tipo_evaluacion));
    modelo.put("id_prg_plan", Integer.toString(iId_prg_plan));
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("_yabe", Integer.toString(_yabe));

    //Sacamos los datos del plan
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);

    //Buscamos Tipo Evaluacion
    Libretas datosTipoEvaluacion = new Libretas();
    datosTipoEvaluacion.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEvaluacion = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEvaluacion);
    modelo.put("datosTipoEvaluacion", datosTipoEvaluacion);    

    //Sacamos los datos del programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    modelo.put("programa", this.mi.getPrgBuscarPrograma(programa));

    //Listamos las materias del plan de estudios
    Planes plan = new Planes();
    plan.setId_plan(datosPrgPlan.getId_plan());
    plan.setId_programa(iId_programa);
    plan.setId_tipo_grado(datosPrgPlan.getId_tipo_grado());
    List lMaterias = this.mi.getPlnListarMaterias(plan);
    modelo.put("lMaterias", lMaterias);

    //Sacamos el formato de la fecha del sistema
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos los datos del grupo
    Grupos grupo = new Grupos();
    grupo.setId_dpto_grupo(_yabe);
    grupo = this.mi.getMiDptoBuscarGrupo(grupo);
    modelo.put("grupo", grupo);
    
    //Listamos los grupos sin asignacion de cupo
    List lGrupos = this.mi.getMtrListarGruposNoAsignados(grupo);
    grupo = this.mi.getGrpBuscarGrupo(grupo);
    lGrupos.add(0, grupo);
    modelo.put("lGrupos", lGrupos);

    return new ModelAndView("abmDptoGrupos/ModificaRegistro", modelo);
  }
}