package org.fautapo.web.ajax;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Grupos;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarMtrGruposNoAsignados implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    //Sacamos el listado de los grupos no asignados
    Grupos grupo = new Grupos();
    grupo.setId_materia(cliente.getInt(request, "id_materia"));
    grupo.setId_tipo_evaluacion(cliente.getInt(request, "id_tipo_evaluacion"));
    grupo.setId_modelo_ahorro(cliente.getInt(request, "id_modelo_ahorro"));
    grupo.setGestion(cliente.getInt(request, "gestion"));
    grupo.setPeriodo(cliente.getInt(request, "periodo"));
    List lista = this.mi.getMtrListarGruposNoAsignados(grupo);
    String json = "";
    for (int i = 0; i < lista.size(); i++) {
      grupo = (Grupos) lista.get(i);
      json += ",{\"id_grupo\":\"" + grupo.getId_grupo() + "\",\"grupo\":\"" + grupo.getGrupo() + "\"}";
    }
    if (json.equals("")) json = "[]";
    else json = "[" + json.substring(1) + "]";
    return new ModelAndView("ajax/Json", "json", json);
  }
}