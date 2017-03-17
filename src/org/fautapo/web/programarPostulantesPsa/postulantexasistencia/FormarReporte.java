/*****************************************
 @usuario          :: Yusara Farah
 @fec_registro     :: 19.02.2016
 @ult_usuario      :: Yusara Farah
 @fec_modificacion :: 19.02.2016
*****************************************/
package org.fautapo.web.programarPostulantesPsa.postulantexasistencia;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

//import java.lang.String;
//import java.awt.*;
//import java.awt.event.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.*;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Asistenciapsa;
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

    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");
  
	
	// Parametros de entrada
    modelo.put("gestion", cliente.getString(request, "gestion"));
    modelo.put("periodo", cliente.getString(request, "periodo"));

    // Definicion de la consulta SQL
    Postulantes datosPostulante = new Postulantes(); 
	//datosPostulante.setId_usuario(cliente.getId_usuario());	
    datosPostulante.setGestion(Integer.parseInt(sGestion));
	datosPostulante.setPeriodo(Integer.parseInt(sPeriodo));
	

    List lTransacciones = this.mi.getRepAsistenciapostulantepsa(datosPostulante);

    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
 
 //	String id_usuario = cliente.getNombres();
	
	if (lTransacciones.size() <= 0) {
	    return new ModelAndView("Aviso", "mensaje", "NO EXISTEN REGISTROS");
	}
	modelo.put("lTransacciones", lTransacciones);
    
	Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    modelo.put("nombres", cliente.getNombres());
    return new ModelAndView("programarPostulantesPsa/postulantexasistencia/FormarReporte", modelo);
  }
}
