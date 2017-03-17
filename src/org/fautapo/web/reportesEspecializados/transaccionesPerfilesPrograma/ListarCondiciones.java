/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 15.09.2006
 @ult_usuario      :: Dayana Torrico
 @fec_modificacion :: 2008-05-02
*****************************************/
package org.fautapo.web.reportesEspecializados.transaccionesPerfilesProgramas;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Accesos;

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

    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    Perfiles datosPerfil = new Perfiles();
    modelo.put("lPerfiles", this.mi.getTrnListarPerfiles());

    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    modelo.put("cliente", cliente);
    return new ModelAndView("reportesEspecializados/transaccionesPerfilesProgramas/ListarCondiciones", modelo);
  }
}