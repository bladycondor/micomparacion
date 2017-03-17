package org.fautapo.web.moverNotasMatriculas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Notas;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */

public class GrabarNotasMatriculadas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    // Comprobamos es quien debe, de acuerdo a su clave
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(cliente.getId_usuario());
    usuario.setClave(request.getParameter("clave" + request.getParameter("hora")));
    
    String accion = request.getParameter("accion");
    String mensaje="";
    Notas mover = new Notas();
    
    if(accion == null){
      if (null == this.mi.getComprobarUsuario(usuario)) {
        return new ModelAndView("moverNotasMatriculas/MoverNotastasMatriculadas", "cliente", cliente);
      }
    }else{
      if(accion.equals("Aceptar")){
	mover.setGestion(Integer.parseInt(request.getParameter("gestion")));
	mover.setPeriodo(Integer.parseInt(request.getParameter("periodo")));
	Notas registro = this.mi.getMtcMoverMatriculados(mover);
        mensaje="Se registro correctamente";
	modelo.put("mensaje", mensaje);
	return new ModelAndView("moverNotasMatriculas/ConfirmarNotasMatriculadas", modelo);
      }
    }
    
    modelo.put("gestion", request.getParameter("gestion"));
    modelo.put("periodo", request.getParameter("periodo"));
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    
    mensaje="Esta seguro que desea Mover las notas con matriculas de la ";
    modelo.put("mensaje", mensaje);
    return new ModelAndView("moverNotasMatriculas/GrabarNotasMatriculadas", modelo);
  }
}