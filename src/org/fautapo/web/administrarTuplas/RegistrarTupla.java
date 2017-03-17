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

public class RegistrarTupla implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Dominios datosTupla = new Dominios();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAccion         = request.getParameter("accion");
    String sId_tupla       = request.getParameter("id_tupla");
    String sId_dominio     = request.getParameter("id_dominio");
    String sTupla          = request.getParameter("tupla");
    String sId_tupla_padre = request.getParameter("id_tupla_padre");
    String sObligatorio    = request.getParameter("obligatorio");

    if (("".equals(sId_tupla_padre)) || (sId_tupla_padre == null)) {
      sId_tupla_padre="0";
    }
     
    if (("Adicionar".equals(sAccion)) || ("Modificar".equals(sAccion))) {
      if ("".equals(sTupla)) {
        return new ModelAndView("Aviso","mensaje","Faltan Introducir Datos");
      }
      if ((sId_tupla != null) && (!"".equals(sId_tupla))) {
        datosTupla.setId_tupla(Integer.parseInt(sId_tupla));
      }
      if ("true".equals(sObligatorio)) {
        datosTupla.setObligatorio(true);
      }
      else {
        datosTupla.setObligatorio(false);
      }

      //Registramos los datos de la tupla
      datosTupla.setId_dominio(Integer.parseInt(sId_dominio));
      datosTupla.setTupla(sTupla);
      datosTupla.setId_tupla_padre(Integer.parseInt(sId_tupla_padre));
      datosTupla.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setRegistrarTupla(datosTupla); //crea una tupla
      if (iResultado == 0) {
        return new ModelAndView("Aviso", "mensaje", "Los datos no fueron registrados");
      }
    }
     
    if ("Eliminar".equals(sAccion)) {
      if (sId_tupla != null) {
        //Verificamos si el dominio existe en otras tablas
        datosTupla.setId_tupla(Integer.parseInt(sId_tupla));
        datosTupla.setUlt_usuario(cliente.getId_usuario());
	int iResultado = this.mi.setEliminarTupla(datosTupla);
	//Si el id_tupla es tupla_padre
	if (iResultado == 0) {
          String sMensaje = "No se puede eliminar el tupla "+"'"+ sTupla +"'. "+ "Por que es Tupla Padre";
          return new ModelAndView("Aviso","mensaje", sMensaje);
	}
      }
    }
    
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