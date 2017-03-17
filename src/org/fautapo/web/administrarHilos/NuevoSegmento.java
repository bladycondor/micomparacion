package org.fautapo.web.administrarHilos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.lang.String;
import java.io.*;
import org.apache.commons.fileupload.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Hilos;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-04
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-04
*/

public class NuevoSegmento implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) { this.mi = mi;}

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    int iId_usuario = cliente.getId_usuario();
    String sNombres = cliente.getNombres();
    int iId_rol = cliente.getId_rol();
    
    String sAplicacion = request.getParameter("aplicacion");
    String sId_hilo = request.getParameter("id_hilo");
    String sId_tipo_segmento = request.getParameter("id_tipo_segmento");
    String sId_destinatario = request.getParameter("id_destinatario");
    String sSegmento = request.getParameter("detalle");
    String sBoton = request.getParameter("boton");
	
    modelo.put("id_hilo", sId_hilo);
    modelo.put("aplicacion", sAplicacion);

    //Creamos un nuevo segmento
    if ("Enviar".equals(sBoton)) {
      Hilos hilo = new Hilos();
      hilo.setId_hilo(Integer.parseInt(sId_hilo));
      hilo.setId_tipo_segmento(Integer.parseInt(sId_tipo_segmento));
      hilo.setId_destinatario(Integer.parseInt(sId_destinatario));
      hilo.setSegmento(sSegmento);
      hilo.setId_remitente(iId_usuario);
      int iId_segmento = this.mi.setRegistrarSegmento(hilo);
      return new ModelAndView("administrarHilos/NuevoSegmento1", modelo);
    }
    
    //Listando los destinatarios
    Hilos hilo = new Hilos();
    hilo.setId_hilo(Integer.parseInt(sId_hilo));
    hilo.setId_duenio(iId_usuario);
    List lDestinatarios = this.mi.getListarDestinatarios(hilo);
    modelo.put("lDestinatarios", lDestinatarios);

    //Sacamos los datos del hilo
    Hilos auxiliar = new Hilos();
    auxiliar = this.mi.getBuscarHilo(hilo);
    modelo.put("hilo", auxiliar);    

    //Listando los tipos de hilos
    List lTiposSegmentos = this.mi.getListarTiposSegmentos();
    modelo.put("lTiposSegmentos", lTiposSegmentos);

    return new ModelAndView("administrarHilos/NuevoSegmento", modelo);
  }
}
