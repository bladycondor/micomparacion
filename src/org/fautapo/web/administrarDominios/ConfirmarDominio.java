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
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-18
*/

public class ConfirmarDominio implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
     Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    //Guardar Nuevo    
    String sAccion           = request.getParameter("accion");
    String sAccion1          = request.getParameter("accion1");
    String sId_dominio       = request.getParameter("id_dominio");
    String sDominio          = request.getParameter("dominio");
    String sId_ubicacion_organica = request.getParameter("id_ubicacion_organica");
    String sId_dominio_padre = request.getParameter("id_dominio_padre");
    String sPrivado          = request.getParameter("privado");
    String sId_tipo_dominio  = request.getParameter("id_tipo_dominio");
    String sId_form          = request.getParameter("id_form");
    String sId_campo         = request.getParameter("id_campo");
    String sTabla            = request.getParameter("tabla");
    String sPrimario         = request.getParameter("primario");
    String sCampo            = request.getParameter("campo");
    
    Dominios datosDominio = new Dominios();
    
    if ("Adicionar".equals(sAccion)) {
      if (("".equals(request.getParameter("id_ubicacion_organica"))) || ("".equals(request.getParameter("dominio")))||
         ("".equals(request.getParameter("privado"))) || ("".equals(request.getParameter("id_tipo_dominio")))) {
	 return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
      }
      if (("2".equals(sId_tipo_dominio)) && (("".equals(sId_form)) || ("".equals(sId_campo)))) {
        return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
      }
      if (("3".equals(sId_tipo_dominio)) && ("".equals(sTabla))) {
        return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
      }

      datosDominio.setDominio(sDominio);
      datosDominio.setTabla(sTabla);
      datosDominio.setPrimario(sPrimario);
      datosDominio.setCampo(sCampo);
      if ("si".equals(sPrivado)) {
        datosDominio.setPrivado(true);
      }
      else {
        datosDominio.setPrivado(false);
      }
      //Buscando Ubicaciones_organicas
      try {
        Actividades auxiliar = new Actividades();
        auxiliar.setId_ubicacion_organica(Integer.parseInt(sId_ubicacion_organica));
        auxiliar = (Actividades) this.mi.getBuscarUbicacionOrganica(auxiliar);
        modelo.put("datosUbicacionOrganica", auxiliar);
      }
      catch(Exception e) {
        return new ModelAndView("Error", "mensaje","Seleccione el área para el dominio");
      }
      
      if (!"".equals(sId_dominio_padre)) {
        //Buscar Dominio(padre)
        Dominios dominio = new Dominios();
        dominio.setId_dominio(Integer.parseInt(sId_dominio_padre));
        dominio = (Dominios) this.mi.getBuscarDominio(dominio);
        modelo.put("datosDominioPadre", dominio);
      }

      if ((sId_form != null) && (!"".equals(sId_form)) && (sId_campo != null) && (!"".equals(sId_campo))) {
        //Buscamos los valores del formulario
        Campos formulario = new Campos();
        formulario.setId_form(Integer.parseInt(sId_form));
        formulario = (Campos) this.mi.getBuscarFormulario(formulario);
        modelo.put("datosFormulario", formulario);

        //Sacamos los datos del campo
        Campos campo = new Campos();
        campo.setId_campo(Integer.parseInt(sId_campo));
        campo = (Campos) this.mi.getBuscarCampoForm(campo);
        modelo.put("datosCampo", campo);
      }

      //Sacamos los datos del tipo de dominio
      Dominios tipoDominio = new Dominios();
      tipoDominio.setId_tipo_dominio(Integer.parseInt(sId_tipo_dominio));
      tipoDominio = (Dominios) this.mi.getBuscarTipoDominio(tipoDominio);
      modelo.put("datosTipoDominio", tipoDominio);
      
      modelo.put("datosDominio", datosDominio);
      modelo.put("accion", sAccion);
      modelo.put("accion1", sAccion1);
      modelo.put("id_tipo_dominio", sId_tipo_dominio);
      modelo.put("id_form", sId_form);
      modelo.put("id_campo", sId_campo);
    }
    
    if ("Modificar".equals(sAccion)) { 
      if (("".equals(request.getParameter("id_ubicacion_organica"))) || ("".equals(request.getParameter("dominio"))) ||
         ("".equals(request.getParameter("privado"))) ) {
	 return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
      }	 
      datosDominio.setDominio(sDominio);
      datosDominio.setTabla(sTabla);
      datosDominio.setPrimario(sPrimario);
      datosDominio.setCampo(sCampo);
      if ("si".equals(sPrivado)) {
        datosDominio.setPrivado(true);
      }
      else {
        datosDominio.setPrivado(false);
      }
      //Buscar ubicaciones_organicas
      try {
        Actividades auxiliar = new Actividades();
        auxiliar.setId_ubicacion_organica(Integer.parseInt(sId_ubicacion_organica));
        auxiliar = (Actividades) this.mi.getBuscarUbicacionOrganica(auxiliar);
        modelo.put("datosUbicacionOrganica", auxiliar);
      }
      catch(Exception e) {
        return new ModelAndView("Error", "mensaje","Seleccione el área para el dominio");
      }
      
      //Buscar Dominio(padre)
      if (!"".equals(sId_dominio_padre)) {
        Dominios dominio = new Dominios();
        dominio.setId_dominio(Integer.parseInt(sId_dominio_padre));
        dominio = (Dominios) this.mi.getBuscarDominio(dominio);
        modelo.put("datosDominioPadre", dominio);
      }

      if ((sId_form != null) && (sId_campo != null)) { 
        //Buscamos los valores del formulario
        Campos formulario = new Campos();
        formulario.setId_form(Integer.parseInt(sId_form));
        formulario = (Campos) this.mi.getBuscarFormulario(formulario);
        modelo.put("datosFormulario", formulario);

        //Sacamos los datos del campo
        Campos campo = new Campos();
        campo.setId_campo(Integer.parseInt(sId_campo));
        campo = (Campos) this.mi.getBuscarCampoForm(campo);
        modelo.put("datosCampo", campo);
      }

      //Sacamos los datos del tipo de dominio
      Dominios tipoDominio = new Dominios();
      tipoDominio.setId_tipo_dominio(Integer.parseInt(sId_tipo_dominio));
      tipoDominio = (Dominios) this.mi.getBuscarTipoDominio(tipoDominio);
      modelo.put("buscarTipoDominio", tipoDominio);

      modelo.put("datosDominio", datosDominio);
      modelo.put("accion", sAccion);
      modelo.put("accion1", sAccion1);
      modelo.put("id_tipo_dominio", sId_tipo_dominio);
      modelo.put("id_form", sId_form);
      modelo.put("id_campo", sId_campo);
    } 
    
    if ("Eliminar".equals(sAccion)) {
      datosDominio.setId_dominio(Integer.parseInt(sId_dominio));
      datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);

      //Buscamos las ubicaciones_organicas
      Actividades auxiliar = new Actividades();
      auxiliar.setId_ubicacion_organica(datosDominio.getId_ubicacion_organica());
      auxiliar = (Actividades) this.mi.getBuscarUbicacionOrganica(auxiliar);
      modelo.put("datosUbicacionOrganica", auxiliar);

      if (datosDominio.getId_dominio_padre() !=0) {
        //Busca DOMINIO PADRE
        Dominios dominio = new Dominios();
        dominio.setId_dominio(datosDominio.getId_dominio_padre());
        dominio = (Dominios) this.mi.getBuscarDominio(dominio);
        modelo.put("datosDominioPadre", dominio);
      }

      //Sacamos los datos del tipo de dominio
      Dominios tipoDominio = new Dominios();
      tipoDominio.setId_tipo_dominio(datosDominio.getId_tipo_dominio());
      tipoDominio = (Dominios) this.mi.getBuscarTipoDominio(tipoDominio);
      modelo.put("datosTipoDominio", tipoDominio);
      modelo.put("id_tipo_dominio", Integer.toString(tipoDominio.getId_tipo_dominio()));

      modelo.put("datosDominio", datosDominio);
      modelo.put("accion", sAccion);
      modelo.put("id_dominio", sId_dominio);
    }
    
    modelo.put("id_dominio", sId_dominio);
    return new ModelAndView("administrarDominios/ConfirmarDominio", modelo);
      
   }
}