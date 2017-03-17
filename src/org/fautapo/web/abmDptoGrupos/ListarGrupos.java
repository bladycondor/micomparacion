package org.fautapo.web.abmDptoGrupos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Libretas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-01-13
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarGrupos implements Controller {
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
    modelo.put("id_prg_plan", Integer.toString(iId_prg_plan));
    modelo.put("id_programa", Integer.toString(iId_programa));
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("id_tipo_evaluacion", Integer.toString(iId_tipo_evaluacion));

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

    //Sacamos el listado de los grupos
    Grupos grupo = new Grupos();
    grupo.setId_programa(iId_programa);
    grupo.setId_plan(datosPrgPlan.getId_plan());
    grupo.setId_tipo_grado(datosPrgPlan.getId_tipo_grado());
    grupo.setId_tipo_evaluacion(iId_tipo_evaluacion);
    grupo.setGestion(iGestion);
    grupo.setPeriodo(iPeriodo);
    modelo.put("lGrupos", this.mi.getPrgListarGrupos(grupo));
    
    //Sacamos los datos del programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    modelo.put("programa", this.mi.getPrgBuscarPrograma(programa));

    return new ModelAndView("abmDptoGrupos/ListarGrupos", modelo);
  }
}