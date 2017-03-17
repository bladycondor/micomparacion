package org.fautapo.web.administrarRecibos.eliminarRecibos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Estudiantes;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class BorrarRecibo implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    int iId_transaccion = cliente.getInt(request, "id_transaccion");
	String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_estudiante = request.getParameter("id_estudiante");
	String iId_perfil = request.getParameter("id_perfil");
	
    Estudiantes estudiante = new Estudiantes();
	
	estudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
	estudiante.setGestion(Integer.parseInt(sGestion));
	estudiante.setPeriodo(Integer.parseInt(sPeriodo));
	estudiante.setId_estado("X");
	estudiante.setUlt_usuario(cliente.getId_usuario());
	

    if (iId_perfil.equals("4")){	
	   this.mi.setRegistrarCambiarEstadoMatricula(estudiante);}
	
	Perfiles transaccion = new Perfiles();
    transaccion.setId_transaccion(iId_transaccion);	
	
	
	
    this.mi.setTrnBorrarTransaccion(transaccion);
		
    List lDetalles = this.mi.getTrnListarTrnDetalles(transaccion);
    for (int i=0; i < lDetalles.size(); i++) {
      Perfiles detallito = (Perfiles) lDetalles.get(i);
      this.mi.setTrnBorrarDetalle(detallito);
    }
    modelo.put("mensaje", "El recibo y sus dependencias se borraron correctamente."+iId_perfil);
    
    return new ModelAndView("administrarRecibos/eliminarRecibos/Aviso", modelo);
  }
}