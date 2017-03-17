package org.fautapo.web.ajax;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Programas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarFclProgramas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    Programas programa = new Programas();
    programa.setId_facultad(cliente.getInt(request, "id_facultad"));
    List lista = this.mi.getFclListarProgramas(programa);
    String json = "";
    for (int i = 0; i < lista.size(); i++) {
      programa = (Programas) lista.get(i);
      json += ",{\"id_programa\":\"" + programa.getId_programa() + "\",\"programa\":\"" + programa.getPrograma() + "\"}";
    }
    if (json.equals("")) json = "[]";
    else json = "[" + json.substring(1) + "]";
    return new ModelAndView("ajax/Json", "json", json);
  }
}