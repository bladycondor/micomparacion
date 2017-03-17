package org.fautapo.web.administrarDominios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-18
*/

public class RegistrarDominio implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
     Map modelo = new HashMap();
     
     Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

     //Guardar    
     String sAccion            = request.getParameter("accion");
     String sId_dominio        = request.getParameter("id_dominio");
     String sDominio           = request.getParameter("dominio");
     String sId_dominio_padre  = request.getParameter("id_dominio_padre");
     String sId_ubicacion_organica  = request.getParameter("id_ubicacion_organica");
     String sPrivado           = request.getParameter("privado");
     String sId_tipo_dominio   = request.getParameter("id_tipo_dominio");
     String sId_form           = request.getParameter("id_form");
     String sId_campo          = request.getParameter("id_campo");
     String sTabla             = request.getParameter("tabla");
     String sPrimario          = request.getParameter("primario");
     String sCampo             = request.getParameter("campo");
     
     if (("".equals(sId_dominio_padre)) || (sId_dominio_padre==null)) {
       sId_dominio_padre="0";
     }
     
     if (("".equals(sId_form)) || (sId_form==null)) {
       sId_form="0";
     }
     
     if (("".equals(sId_campo)) || (sId_campo==null)) {
       sId_campo="0";
     }
     
     if (("Adicionar".equals(sAccion)) || ("Modificar".equals(sAccion))) {
       if (("".equals(sDominio)) || ("".equals(sId_ubicacion_organica))) {
         return new ModelAndView("Aviso","mensaje","Faltan Introducir Datos");
       }
       Dominios datosDominio = new Dominios();
       if ((sId_dominio != null) && (!"".equals(sId_dominio))) {
         datosDominio.setId_dominio(Integer.parseInt(sId_dominio));
       }
       datosDominio.setId_dominio_padre(Integer.parseInt(sId_dominio_padre));
       datosDominio.setId_ubicacion_organica(Integer.parseInt(sId_ubicacion_organica));
       datosDominio.setId_tipo_dominio(Integer.parseInt(sId_tipo_dominio));
       datosDominio.setId_form(Integer.parseInt(sId_form));
       datosDominio.setId_campo(Integer.parseInt(sId_campo));
       datosDominio.setDominio(sDominio);
       datosDominio.setTabla(sTabla);
       datosDominio.setPrimario(sPrimario);
       datosDominio.setCampo(sCampo);
       datosDominio.setUlt_usuario(cliente.getId_usuario());
       if ("true".equals(sPrivado)) {
         datosDominio.setPrivado(true);
       }
       else {
         datosDominio.setPrivado(false);
       }
       int iResultado = this.mi.setRegistrarDominio(datosDominio);
       if (iResultado == 0) {
         return new ModelAndView("Error","mensaje","Los datos no fueron registrados");
       }
     }
    
     if ("Eliminar".equals(sAccion)) {
       if (sId_dominio != null) {
          //Verificamos si el dominio existe en otras tablas
         Abm abm = new Abm();
	 abm.setTabla("tr_dominios");
         int iDependientes = this.mi.getContarDependientes(abm);
         Dominios dominio= new Dominios();
         dominio.setId_dominio(Integer.parseInt(sId_dominio));
         dominio.setUlt_usuario(cliente.getId_usuario());
         int iValor = this.mi.setEliminarDominio(dominio);
	 //Si el id_dominio es dominio_padre
	 if (iValor == 0) {
           String sMensaje = "No se puedo eliminar el dominio "+"'"+ sDominio +"'. "+ "porque tiene dependencias";
           return new ModelAndView("Error", "mensaje", sMensaje);
	 }
       }
     }
     
     //Listamos los Dominios
     List lDominios = this.mi.getListarDominios();
     modelo.put("lDominios", lDominios);

     return new ModelAndView("administrarDominios/ListarDominios", modelo);
   }
}