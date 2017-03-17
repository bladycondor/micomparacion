package org.fautapo.web.administrarActividades;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-14
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-16
*/

public class ListarActividades implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
 
    List lActividades2 = new ArrayList();

    //Sacamos los valores de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    //Recuperamos los valores del jsp
    String sId_proceso = request.getParameter("id_proceso");
    modelo.put("id_proceso", sId_proceso);

    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);
    if (sId_proceso != null) {
      //Listamos las actividades
      Actividades proceso = new Actividades();
      proceso.setId_proceso(Integer.parseInt(sId_proceso));
      List lActividades = this.mi.getListarActividades(proceso);
      for (int i=0; i < lActividades.size(); i++) {
        Actividades auxiliar = (Actividades) lActividades.get(i);
        String sValor = auxiliar.getActuacion();
        String sCadena = "";
        //Listando las actuaciones
        String sAuxiliar[] = sValor.split("###");
        for (int ac=0; ac < sAuxiliar.length; ac++) {
          String sActuacion[] = sAuxiliar[ac].split(":");
          if (sActuacion.length > 1) {
            sCadena = sActuacion[1]+ "[" +sActuacion[0]+ "], "+sCadena;
   	  }
	  else {
	    sCadena = sAuxiliar[ac];
	  }
        }
        auxiliar.setActuacion(sCadena);
        //Fin Listando las actuaciones
      
        //Listando los tipos de alertas
        List lTiposAlertas = this.mi.getListarTiposAlertasAct(auxiliar);
        sCadena = "";
        for (int ac=0; ac < lTiposAlertas.size(); ac++) {
          Actividades auxiliar2 = (Actividades) lTiposAlertas.get(ac);
	  if (auxiliar2.getId_actividad() != 0) {
  	    if (ac == 0) {
  	      sCadena = auxiliar2.getTipo_alerta() + ", ";
	    }
	    else {
              sCadena = sCadena + auxiliar2.getTipo_alerta() + ", ";
	    }
	  }
        }
        auxiliar.setTipo_alerta(sCadena);
        lActividades2.add(auxiliar);
      }
      modelo.put("lActividades", lActividades2);
    }
    return new ModelAndView("administrarActividades/ListarActividades", modelo);
  }
}