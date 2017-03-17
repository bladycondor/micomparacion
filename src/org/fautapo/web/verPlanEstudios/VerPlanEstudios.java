package org.fautapo.web.verPlanEstudios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class VerPlanEstudios implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Map modelo = new HashMap();
    modelo.put("cliente", cliente);

    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    modelo.put("id_prg_plan", Integer.toString(iId_prg_plan));

    //Sacamos los datos del plan
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);

    //Listamos los datos del plan de estudios
    Planes plan = new Planes();
    plan.setId_plan(datosPrgPlan.getId_plan());
    plan.setId_tipo_grado(datosPrgPlan.getId_tipo_grado());
    plan.setId_programa(iId_programa);
    modelo.put("lMaterias", this.mi.getListarMateriasPlan(plan));
    modelo.put("lRequisitos", this.mi.getPlnListarMateriasRequisitos(plan));

    //Sacamos los datos del programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    modelo.put("programa", this.mi.getPrgBuscarPrograma(programa));
    
    return new ModelAndView("verPlanEstudios/VerPlanEstudios", modelo);
  }
}