/****************************************
 @usuario          :: Dali Aparicio
 @fec_registro     :: 17.10.2005
 @ult_usuario      :: Dali Aparicio
 @fec_modificacion :: 17.10.2005
*****************************************/
package org.fautapo.web.listarDibRep;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ListarAbmGeneral implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //CONTROL DE SESION
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");

    String _nombres = cliente.getNombres();

    int id_consulta = Integer.parseInt(request.getParameter("c"));
    Abm abm = new Abm();
    abm.setId_consulta(id_consulta);
    Abm buscarConsulta = this.mi.getBuscarConsulta(abm);
    modelo.put("consulta", buscarConsulta);
    modelo.put("id_consulta",request.getParameter("c"));

    List listarCamposCondicion = this.mi.getListarCamposCondicion(abm);

    for (int i = 0; i < listarCamposCondicion.size(); i++) {
      Abm aux = (Abm)listarCamposCondicion.get(i);

      Abm foranea = (Abm) this.mi.getBuscarForanea(aux);

      if (foranea != null) {
        String condicion = "";
	System.out.println("campo_foraneo='" + aux.getCampo_padre() +"'");
	if(aux.getCampo_padre().equals(""))
	aux.setCampo_padre("''");
        foranea.setCondicion(condicion);
	foranea.setCampo(foranea.getId_campo_foraneo() + "#~~#" + foranea.getCampo_foraneo() + "#~~#" + aux.getCampo_padre());
        aux.setCombo(this.mi.getListarCombos(foranea));
        listarCamposCondicion.set(i, aux);
      }

    }
    
    modelo.put("listarCamposCondicion", listarCamposCondicion);
    return new ModelAndView("listarDibRep/ListarCondiciones", modelo);
  }
}