/****************************************
 @usuario          :: Dali Aparicio
 @fec_registro     :: 17.10.2005
 @ult_usuario      :: Dali Aparicio
 @fec_modificacion :: 17.10.2005
*****************************************/
package org.fautapo.web.dibRep;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ConfirmarBorrado implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //CONTROL DE SESION
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");

    Abm aux = new Abm();
    String _nombres = cliente.getNombres();
    List tablas_dibrep = new ArrayList();
    String t="";
    int id_consulta = Integer.parseInt(request.getParameter("c"));
    Abm abm = new Abm();
    abm.setId_consulta(id_consulta);
    Abm buscarConsulta = this.mi.getBuscarConsulta(abm);

    
    String consulta = buscarConsulta.getConsulta();

    modelo.put("consulta", buscarConsulta);

    return new ModelAndView("dibRep/ConfirmarBorrado", modelo);
  }
  
  
}