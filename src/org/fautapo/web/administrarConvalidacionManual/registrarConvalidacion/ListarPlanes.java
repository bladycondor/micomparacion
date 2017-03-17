package org.fautapo.web.administrarConvalidacionManual.registrarConvalidacion;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Universidades;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarPlanes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));

    //Sacamos el listado de las facultades
    Universidades datosUniversidad = new Universidades();
    datosUniversidad.setId_universidad(cliente.getId_universidad());
    List lFacultades = this.mi.getUnvListarFacultades(datosUniversidad);
    modelo.put("lFacultades", lFacultades);
    
    //Sacamos el listado de los programas
    List lProgramas = this.mi.getUnvListarProgramas(datosUniversidad);    
    modelo.put("lProgramas", lProgramas);

    //Sacamos el listado de los planes
    List lPlanes = this.mi.getUnvListarPlanes(datosUniversidad);
    modelo.put("lPlanes", lPlanes);

    return new ModelAndView("administrarConvalidacionManual/registrarConvalidacion/ListarPlanes", modelo);
  }
}