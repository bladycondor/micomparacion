package org.fautapo.web.administrarKardex.administrarProcesoKardex;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-18
*/

public class ListarProcesosKardexs implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
     Map modelo = new HashMap();

     Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
     String sId_tipo_proceso = "3"; //Proceso de tipo kardex

     //Listar Dominios
     List lProcesosKardexs = this.mi.getListarProcesosKardexs();
     modelo.put("lProcesosKardexs",lProcesosKardexs);
     
     modelo.put("id_tipo_proceso",sId_tipo_proceso);
     
     return new ModelAndView("administrarKardex/administrarProcesoKardex/ListarProcesosKardexs", modelo);
   }
}