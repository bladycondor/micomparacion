package org.fautapo.web.imprimirProrrogas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Clientes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class ListarCompromisos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();
    modelo.put("cliente", cliente);

    int iId_estudiante = cliente.getInt(request, "id_estudiante");
    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_estudiante(iId_estudiante);
	estudiante.setIns_sede(cliente.getId_almacen());
    estudiante = this.mi.getEstBuscarEstudianteNombresSede(estudiante);
	if(estudiante == null){
 return new ModelAndView("Error", "mensaje", "El Estudiante no pertenece a su Area y/o sede, de acuerdo a Reglamento no puede usted acceder a esta informacion");
}
    modelo.put("estudiante", estudiante);
    modelo.put("lCompromisos", this.mi.getMiEstListarCompromisos(estudiante));
    return new ModelAndView("imprimirProrrogas/ListarCompromisos", modelo);
  }
}