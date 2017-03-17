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
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-18
*/

public class ListarTuplas implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_dominio = request.getParameter("id_dominio");

    //Buscamos los datos del dominio
    Dominios datosDominio = new Dominios();
    datosDominio.setId_dominio(Integer.parseInt(sId_dominio));
    datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);
    modelo.put("datosDominio", datosDominio);
    
    //Listamos las tuplas del dominio
    List lTuplas = this.mi.getListarTuplas(datosDominio);
    modelo.put("lTuplas", lTuplas);
    modelo.put("id_dominio", sId_dominio);
    
    return new ModelAndView("administrarTuplas/ListarTuplas", modelo);
    
   }
}