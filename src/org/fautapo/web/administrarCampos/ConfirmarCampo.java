package org.fautapo.web.administrarCampos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-21
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-21
*/

public class ConfirmarCampo implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Campos datosCampo = new Campos();
    Dominios datosDominio = new Dominios();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAccion        = request.getParameter("accion");
    String sAccion1       = request.getParameter("accion1");
    String sId_form       = request.getParameter("id_form");
    String sId_proceso    = request.getParameter("id_proceso");    
    String sId_campo      = request.getParameter("id_campo");
    String sId_dominio    = request.getParameter("id_dominio");
    String sId_tipo_validacion = request.getParameter("id_tipo_validacion");
    String sCampo         = request.getParameter("campo");    
    String sFilas         = request.getParameter("filas");    
    String sColumnas      = request.getParameter("columnas");    
    String sCaracteres    = request.getParameter("caracteres");    
    String sRango1        = request.getParameter("rango1");    
    String sRango2        = request.getParameter("rango2"); 
    String sReferencia    = request.getParameter("referencia");    
    String sOperacion     = request.getParameter("operacion");        
    String sNro_fila      = request.getParameter("nro_fila");    
    String sNro_columna   = request.getParameter("nro_columna"); 
    String sFormula       = request.getParameter("formula"); 
    String sObligatorio   = request.getParameter("obligatorio");
    
    //Buscamos los valores del formulario
    Campos datosFormulario = new Campos();
    datosFormulario.setId_form(Integer.parseInt(sId_form));
    datosFormulario = (Campos) this.mi.getBuscarFormulario(datosFormulario);
    modelo.put("datosFormulario", datosFormulario);

    modelo.put("id_proceso", Integer.toString(datosFormulario.getId_proceso()));
    modelo.put("id_form", Integer.toString(datosFormulario.getId_form()));
    modelo.put("id_campo", sId_campo);

    if (("".equals(sId_tipo_validacion)) || (sId_tipo_validacion == null)) {
      sId_tipo_validacion = "T";
    }
    
    if (("Adicionar".equals(sAccion)) || ("Modificar".equals(sAccion))) {
      if (("".equals(request.getParameter("campo"))) ||("".equals(request.getParameter("columnas"))) ||
         ("".equals(request.getParameter("filas"))) ||("".equals(request.getParameter("caracteres"))) || 
	 ("".equals(request.getParameter("nro_fila"))) || ("".equals(request.getParameter("nro_columna"))) || 
	 ("".equals(request.getParameter("rango1"))) || ("".equals(request.getParameter("rango2")))) {
         return new ModelAndView("Error","mensaje","Faltan introducir datos");
       }
       datosCampo.setCampo(sCampo);
       datosCampo.setFilas(Integer.parseInt(sFilas));
       datosCampo.setColumnas(Integer.parseInt(sColumnas));    
       datosCampo.setCaracteres(Integer.parseInt(sCaracteres));    
       datosCampo.setRango1(sRango1);
       datosCampo.setRango2(sRango2);
       datosCampo.setNro_fila(Integer.parseInt(sNro_fila));    
       datosCampo.setNro_columna(Integer.parseInt(sNro_columna)); 
       datosCampo.setFormula(sFormula);
      
       modelo.put("datosCampo", datosCampo);  
       modelo.put("id_dominio", sId_dominio);
       modelo.put("id_tipo_validacion", sId_tipo_validacion);
       modelo.put("referencia", sReferencia);
       modelo.put("operacion", sOperacion);
       modelo.put("accion", sAccion);
       modelo.put("accion1", sAccion1);
       modelo.put("obligatorio", sObligatorio);
      
       //Buscamos los datos del dominio
       try {
         datosDominio.setId_dominio(Integer.parseInt(sId_dominio));
         datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);
         modelo.put("datosDominio", datosDominio);
       }
       catch(Exception e) {
         return new ModelAndView("Error","mensaje","Debe seleccionar un dominio");
       }

       //Buscamos los datos del tipo de validacion
       try {
         Campos tipoValidacion = new Campos();
         tipoValidacion.setId_tipo_validacion(sId_tipo_validacion);
         tipoValidacion = (Campos) this.mi.getBuscarTipoValidacion(tipoValidacion);
         modelo.put("datosTipoValidacion", tipoValidacion);
       }
       catch(Exception e) {
         return new ModelAndView("Error","mensaje","Debe seleccionar un tipo de validación");
       }
    }

    if ("Eliminar".equals(sAccion)) {
      datosCampo.setId_campo(Integer.parseInt(sId_campo));
      datosCampo = (Campos) this.mi.getBuscarCampoForm(datosCampo);// saca un registro a ser modificado       
      modelo.put("datosCampo", datosCampo);      
      
      //Buscamos los datos del dominio
      try {
        datosDominio.setId_dominio(datosCampo.getId_dominio());
        datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);
        modelo.put("datosDominio", datosDominio);
      }
      catch(Exception e) {
      }
  
      //Buscamos los datos del tipo de validacion
      try {
        Campos tipoValidacion = new Campos();
        tipoValidacion.setId_tipo_validacion(datosCampo.getId_tipo_validacion());
        tipoValidacion = (Campos) this.mi.getBuscarTipoValidacion(tipoValidacion);
        modelo.put("datosTipoValidacion", tipoValidacion);
      }
      catch(Exception e) { 
      }

      modelo.put("accion", sAccion);
      modelo.put("id_campo", sId_campo);
      modelo.put("id_tipo_validacion", datosCampo.getId_tipo_validacion());
      modelo.put("referencia", Boolean.toString(datosCampo.getReferencia()));        
      modelo.put("operacion", Boolean.toString(datosCampo.getOperacion()));        
      modelo.put("obligatorio", Boolean.toString(datosCampo.getObligatorio()));
    }
    
    return new ModelAndView("administrarCampos/ConfirmarCampo", modelo);
      
   }
}