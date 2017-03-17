package org.fautapo.web.reportesTramites.actividadesPorRoles;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
*/

public class ListarActividadesPorRoles implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
  
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    String sSw="0";

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sBoton = request.getParameter("boton");
    String sId_proceso = request.getParameter("id_proceso");
    modelo.put("id_proceso", sId_proceso);

    //Listamos los procesos según el acceso del usuario
    try {
      List lProcesos = this.mi.getListarProcesosAcceso(cliente);
      modelo.put("lProcesos", lProcesos);
    }
    catch(Exception e) {
      return new ModelAndView("Error","mensaje","El usuario no tiene área definida");
    }
    
    if ("Buscar".equals(sBoton)) {
      try {
        sSw="1";
        Actividades datosActividad = new Actividades();
        datosActividad.setId_proceso(Integer.parseInt(sId_proceso));
        String sValores = this.mi.getListarActividadesRoles(datosActividad);//lista de valores concatenados desde la funcion
        String sCad[] = sValores.split("####");//inicio de la cadena y sus valores
        List listita = new ArrayList();
        for (int k=0; k < sCad.length; k++) {
          Tramites auxiliar = new Tramites();
	  String sValor[] = (sCad[k]).split("#~~#");//muetra los valores con el separador #~~#
          List listita2 = new ArrayList();
	  for (int w=0; w < sValor.length; w++) {
  	    Tramites auxiliar2 = new Tramites();
	    auxiliar2.setValor(sValor[w]);
	    listita2.add(auxiliar2);
	  }
          auxiliar.setLista(listita2);
          listita.add(auxiliar);
        }
        modelo.put("lActividadesRoles", listita);
      }
      catch(Exception e) {
        sSw = "0";
      }
    }
    
    modelo.put("sw", sSw);
    modelo.put("boton", sBoton);
    
    return new ModelAndView("reportesTramites/actividadesPorRoles/ListarActividadesPorRoles", modelo);
  }
}