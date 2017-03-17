package org.fautapo.web.administrarTuplas;

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
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-20
*/

public class NuevaTupla implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
   Map modelo = new HashMap();

     Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

     String sAccion     = request.getParameter("accion");
     String sId_tupla   = request.getParameter("id_tupla");
     String sId_dominio = request.getParameter("id_dominio");
     
     //Listamos las tuplas del dominio
     Dominios datosDominio = new Dominios();
     datosDominio.setId_dominio(Integer.parseInt(sId_dominio));
     List lTuplas = this.mi.getListarTuplasPadre(datosDominio);
     modelo.put("lTuplas", lTuplas);
     modelo.put("id_dominio", sId_dominio);

     if ((sId_tupla != null) && ("Modificar".equals(sAccion))) {
       Dominios datosTupla = new Dominios();
       datosTupla.setId_tupla(Integer.parseInt(sId_tupla));
       datosTupla = (Dominios) this.mi.getBuscarTupla(datosTupla);
       modelo.put("datosTupla", datosTupla);
       modelo.put("obligatorio", Boolean.toString(datosTupla.getObligatorio()));
       modelo.put("id_tupla", sId_tupla);
    }
    modelo.put("id_tupla", sId_tupla);
    modelo.put("accion", sAccion);
    
    return new ModelAndView("administrarTuplas/NuevaTupla", modelo);
   }
}