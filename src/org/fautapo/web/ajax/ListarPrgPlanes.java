package org.fautapo.web.ajax;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Planes;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarPrgPlanes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    Planes plan = new Planes();
    plan.setId_programa(cliente.getInt(request, "id_programa"));
    List lista = this.mi.getPrgListarPlanes(plan);
    String json = "";
    for (int i = 0; i < lista.size(); i++) {
      plan = (Planes) lista.get(i);
      json += ",{\"id_prg_plan\":\"" + plan.getId_prg_plan() + "\",\"plan\":\"" + plan.getTipo_grado()+" - "+plan.getId_plan() + "\",\"id_plan\":\"" +plan.getId_plan()+ "\"}";
    }
    if (json.equals("")) json = "[]";
    else json = "[" + json.substring(1) + "]";
    return new ModelAndView("ajax/Json", "json", json);
  }
}