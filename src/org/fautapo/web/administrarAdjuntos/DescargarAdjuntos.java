package org.fautapo.web.administrarAdjuntos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Iterator;
import java.io.*;
import java.lang.Boolean;
import org.apache.commons.fileupload.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Adjuntos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.apache.commons.fileupload.*;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-27
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-27
*/

public class DescargarAdjuntos implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iResultado = 0; int iContador =0;
    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAplicacion = request.getParameter("aplicacion");
    String sId_tramite = request.getParameter("id_tramite");   
    
    //Listamos los adjuntos
    Adjuntos datosAdjunto = new Adjuntos();
    datosAdjunto.setId_tramite(Integer.parseInt(sId_tramite));
    List lAdjuntos = this.mi.getListarAdjuntos(datosAdjunto);
    modelo.put("lAdjuntos", lAdjuntos);
    modelo.put("aplicacion", sAplicacion);
    //Fin Listamos los adjuntos
    
    return new ModelAndView("administrarAdjuntos/DescargarAdjuntos", modelo);
  }
}
