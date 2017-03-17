package org.fautapo.web.reportesAcademicos.nroPstInscritosAprobadosProgramaTipoAdmision;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */

public class ListarProgramas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Debe volver a la pagina inicial e ingresar de nuevo.");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);

    //Listando los tipos_evaluaciones
    //List lTiposEvaluaciones = this.mi.getTpsListarTiposEvaluaciones();
    //modelo.put("lTiposEvaluaciones", lTiposEvaluaciones);

    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    return new ModelAndView("reportesAcademicos/nroPstInscritosAprobadosProgramaTipoAdmision/ListarProgramas", modelo);
  }
}
