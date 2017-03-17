/*****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 15.09.2006
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesEspecializados.estadisticas.avanceLibretas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.*;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ListarCondiciones implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    Map modelo = new HashMap();
    Funciones f = new Funciones(request, modelo, mi);

    Facultades facultad = new Facultades();
    facultad.setId_universidad(1);
    modelo.put("lFacultades", this.mi.getUnvListarFacultades(facultad));

    modelo.put("cliente", cliente);
    if (cliente.getId_programa() > 0) {
      Programas programa = new Programas();
      programa.setId_programa(cliente.getId_programa());
      programa = this.mi.getPrgBuscarPrograma(programa);
      modelo.put("programa", programa);
    }

    //Listando los tipos_evaluaciones
    List lTiposEvaluaciones = this.mi.getTpsListarTiposEvaluaciones();
    modelo.put("lTiposEvaluaciones", lTiposEvaluaciones);

    return new ModelAndView("reportesEspecializados/estadisticas/avanceLibretas/ListarCondiciones", modelo);
  }
}