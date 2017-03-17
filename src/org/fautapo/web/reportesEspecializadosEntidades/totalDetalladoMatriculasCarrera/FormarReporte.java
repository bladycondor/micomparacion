/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesEspecializadosEntidades.totalDetalladoMatriculasCarrera;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

//import java.lang.String;
//import java.awt.*;
//import java.awt.event.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.*;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Programas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class FormarReporte implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //Declaracion de Variables
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    Map modelo = new HashMap();

    String sFecha_ini = cliente.getString(request, "fec_comprobantei");
    String sFecha_fin = cliente.getString(request, "fec_comprobantef");
  
	
	// Parametros de entrada
    modelo.put("fec_comprobantei", cliente.getString(request, "fec_comprobantei"));
    modelo.put("fec_comprobantef", cliente.getString(request, "fec_comprobantef"));

    // Definicion de la consulta SQL
    Perfiles datosTransaccion = new Perfiles(); 
	datosTransaccion.setId_usuario(cliente.getId_usuario());	
    datosTransaccion.setFecha_ini(sFecha_ini);
	datosTransaccion.setFecha_fin(sFecha_fin);
	


    List lTransacciones = this.mi.getRepCajasDetalladoCarrera(datosTransaccion);

    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
 
 	
	if (lTransacciones.size() <= 0) {
	    return new ModelAndView("Aviso", "mensaje", "NO EXISTEN REGISTROS");
	}
	modelo.put("lTransacciones", lTransacciones);
    
	Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    modelo.put("nombres", cliente.getNombres());
    return new ModelAndView("reportesEspecializadosEntidades/totalDetalladoMatriculasCarrera/FormarReporte", modelo);
  }
}
