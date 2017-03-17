package org.fautapo.web.administrarInformes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-20
*/

public class ListarInformes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    //Recuperamos los valores del jsp
    String sId_proceso = request.getParameter("id_proceso");
    modelo.put("id_proceso", sId_proceso);

    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);
    if ((!"".equals(sId_proceso)) && (sId_proceso != null)) {
      //Listamos los informes por actividades del proceso elegido
      Informes informe = new Informes();
      informe.setId_proceso(Integer.parseInt(sId_proceso));
      List lInformes = this.mi.getListarInformes(informe);
      modelo.put("lInformes", lInformes);
    }

    return new ModelAndView("administrarInformes/ListarInformes", modelo);
  }
}