/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesEspecializados.totalGeneral;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


import java.lang.String;
import java.awt.*;
import java.awt.event.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.fautapo.domain.*;

import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;



public class FormarReporte implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //Declaracion de Variables
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    Map modelo = new HashMap();
    Funciones f = new Funciones(request, modelo, mi);
      
    String sFecha_ini = cliente.getString(request, "fec_comprobantei");
    String sFecha_fin = cliente.getString(request, "fec_comprobantef");
  
    // Parametros de entrada
    modelo.put("fec_comprobantei", f.sRequest("fec_comprobantei"));
    modelo.put("fec_comprobantef", f.sRequest("fec_comprobantef"));

    // Definicion de la consulta SQL
   Perfiles datosTransaccion = new Perfiles(); 
   datosTransaccion.setId_usuario(cliente.getId_usuario());	
   datosTransaccion.setFecha_ini(sFecha_ini);
   datosTransaccion.setFecha_fin(sFecha_fin);
   
   List lTransacciones = this.mi.getRepCajasTransaccionesPorPrograma(datosTransaccion);
  
   modelo.put ("lTransacciones", lTransacciones);
   modelo.put("nombres", cliente.getNombres());
  
   return new ModelAndView("reportesEspecializados/totalGeneral/FormarReporte", modelo);
  }
}
