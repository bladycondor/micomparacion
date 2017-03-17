package org.fautapo.web.administrarNoticias;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tableros;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04_03
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-03
*/

public class RegistrarNoticia implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    Tableros tableros;
    String sId_tablero = request.getParameter("id_tablero");
    String sId_tipo_aviso = request.getParameter("id_tipo_aviso");
    String sId_tipo_tablero = request.getParameter("id_tipo_tablero");
    String sId_rol = request.getParameter("id_rol");
    String sNoticia = request.getParameter("noticia");
    String sMensaje = request.getParameter("mensaje");
    
    if ((!"".equals(sId_tipo_tablero)) && (!"".equals(sId_tipo_aviso)) && (!"".equals(sId_rol)) && (!"".equals(sNoticia)) && (!"".equals(sMensaje))) {
      Tableros datosTablero = new Tableros();
      if ((sId_tablero != null) && (!"".equals(sId_tablero))) {
        datosTablero.setId_tablero(Integer.parseInt(sId_tablero));
      }
      datosTablero.setId_tipo_aviso(Integer.parseInt(sId_tipo_aviso));
      datosTablero.setId_tipo_tablero(Integer.parseInt(sId_tipo_tablero));
      datosTablero.setId_rol(Integer.parseInt(sId_rol));
      datosTablero.setNoticia(sNoticia);
      datosTablero.setMensaje(sMensaje);
      datosTablero.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setRegistrarTablero(datosTablero);
      if (iResultado == 1) {
	List lNoticias  = this.mi.getListarNoticias();
        modelo.put("lNoticias", lNoticias);
        modelo.put("id_usuario", Integer.toString(cliente.getId_usuario()));
        return new ModelAndView("administrarNoticias/VerNoticias",modelo);
      }
    }
    return new ModelAndView("Error","mensaje","Faltan datos");
  }
}