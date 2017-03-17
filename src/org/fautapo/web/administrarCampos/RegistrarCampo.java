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

public class RegistrarCampo implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
   
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

    if (("Adicionar".equals(sAccion)) || ("Modificar".equals(sAccion))) {
      if (("".equals(request.getParameter("id_dominio"))) || ("".equals(request.getParameter("campo"))) ||
         ("".equals(request.getParameter("columnas"))) || ("".equals(request.getParameter("filas"))) || 
         ("".equals(request.getParameter("caracteres"))) || ("".equals(request.getParameter("id_validacion"))) || 
         ("".equals(request.getParameter("referencia"))) || ("".equals(request.getParameter("operacion"))) || 
	 ("".equals(request.getParameter("obligatorio")))) {
         return new ModelAndView("Error","mensaje","Faltan introducir datos");
      }
      Campos datosCampo = new Campos();
      if ((sId_campo != null) && (!"".equals(sId_campo))) {
        datosCampo.setId_campo(Integer.parseInt(sId_campo));
      }
      if ((sRango1 == null) || ("".equals(sRango1))) {
        sRango1 = "0";
      }
      if ((sRango2 == null) || ("".equals(sRango2))) {
        sRango2 = "0";
      }
      datosCampo.setId_proceso(Integer.parseInt(sId_proceso));
      datosCampo.setId_form(Integer.parseInt(sId_form));
      datosCampo.setId_dominio(Integer.parseInt(sId_dominio));
      datosCampo.setId_tipo_validacion(sId_tipo_validacion);
      datosCampo.setCampo(sCampo);
      datosCampo.setFilas(Integer.parseInt(sFilas));
      datosCampo.setColumnas(Integer.parseInt(sColumnas));    
      datosCampo.setCaracteres(Integer.parseInt(sCaracteres));    
      datosCampo.setRango1(sRango1);    
      datosCampo.setRango2(sRango2);
      datosCampo.setNro_fila(Integer.parseInt(sNro_fila));    
      datosCampo.setNro_columna(Integer.parseInt(sNro_columna)); 
      datosCampo.setUlt_usuario(cliente.getId_usuario());
      if ("true".equals(sReferencia)) {
        datosCampo.setReferencia(true);
      }
      else {
        datosCampo.setReferencia(false);
      }
      if ("true".equals(sOperacion)) {
        datosCampo.setOperacion(true);
	datosCampo.setFormula(sFormula);
      }
      else {
        datosCampo.setOperacion(false);
      }
      if ("true".equals(sObligatorio)) {
        datosCampo.setObligatorio(true);
      }
      else {
        datosCampo.setObligatorio(false);
      }
      int iResultado = this.mi.setRegistrarCampo(datosCampo);
    }

    if ("Eliminar".equals(sAccion)) {
      if ("".equals(sId_campo)) {
        return new ModelAndView("Error","mensaje","Faltan introducir datos");
      }
      Campos datosCampo = new Campos();
      datosCampo.setId_campo(Integer.parseInt(sId_campo));
      datosCampo.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setEliminarCampo(datosCampo);
      if (iResultado == 0) {
        return new ModelAndView("Error","mensaje","El registro a eliminar tiene dependencias");
      }
    }
    
    //Listamos los formularios
    List lFormularios = this.mi.getListarFormulariosAcceso(cliente);
    modelo.put("lFormularios", lFormularios);

    if ((sId_form != null) && (!"".equals(sId_form))) {
      //Buscamos los valores del formulario
      Campos datosFormulario = new Campos();
      datosFormulario.setId_form(Integer.parseInt(sId_form));
      datosFormulario = (Campos) this.mi.getBuscarFormulario(datosFormulario);
      modelo.put("datosFormulario", datosFormulario);

      //Listamos los campos de un formulario
      Campos auxiliar = new Campos();
      auxiliar.setId_form(Integer.parseInt(sId_form));
      List lCampos = this.mi.getListarCampos(auxiliar);
      modelo.put("lCampos", lCampos);
    }

    modelo.put("id_form", sId_form);
    return new ModelAndView("administrarCampos/ListarCampos", modelo);
  }
}