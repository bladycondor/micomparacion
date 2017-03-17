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

public class NuevoDominio implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
     Map modelo = new HashMap();
     Campos auxiliar = new Campos();
     Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

     String sAccion           = request.getParameter("accion");
     String sId_dominio       = request.getParameter("id_dominio");
     String sId_ubicacion_organica = request.getParameter("id_ubicacion_organica");
     String sId_dominio_padre = request.getParameter("id_dominio_padre");
     String sId_tipo_dominio  = request.getParameter("id_tipo_dominio");
     String sId_form          = request.getParameter("id_form");
     String sId_campo         = request.getParameter("id_campo");
     String sSw               = request.getParameter("sw");
     String sTabla            = request.getParameter("tabla");
     String sDominio          = request.getParameter("dominio");
     String sPrivado          = request.getParameter("privado");
     
     //Listado de Dominio(padre)
     List lDominios = this.mi.getListarDominios();
     modelo.put("lDominios", lDominios);
     
     //Listado de Ubicaciones_organicas
     List lUbicacionesOrganicas = this.mi.getListarUbicacionesOrganicas();
     modelo.put("lUbicacionesOrganicas", lUbicacionesOrganicas);
     
     //Lista tipos dominios
     List lTiposDominios = this.mi.getListarTiposDominios();
     modelo.put("lTiposDominios", lTiposDominios);
     
     //Lista de formularios
     List lFormularios = this.mi.getListarFormularios();
     modelo.put("lFormularios", lFormularios);
     
     if ((sId_form != null) && (!"".equals(sId_form))) {
       //Listamos los campos de un formulario
       auxiliar.setId_form(Integer.parseInt(sId_form));
       List lCampos = this.mi.getListarCampos(auxiliar);
       modelo.put("lCampos", lCampos);
     }
     
     //Lista de tablas del dibrap
     List lTablas = this.mi.getListarTablas();
     modelo.put("lTablas", lTablas);

     //Listamos los campos de la tabla elegida
     if ((!"".equals(sTabla)) && (sTabla != null)) {
       Abm tabla = new Abm();
       tabla.setTabla(sTabla);
       tabla = (Abm) this.mi.getBuscarTabla1(tabla);
       Abm lcampos = new Abm();
       lcampos.setId_tabla(tabla.getId_tabla());
       lcampos.setPermiso("p");
       List lCamposTabla = this.mi.getListarCamposTabla(lcampos);
       modelo.put("lCamposTabla", lCamposTabla);
     }
     
     if (sId_dominio != null) {
      if (("Modificar".equals(sAccion)) && ("0".equals(sSw))) {
        Dominios datosDominio = new Dominios();
        datosDominio.setId_dominio(Integer.parseInt(sId_dominio));
        datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);
	sId_form = Integer.toString(datosDominio.getId_form());
        if (sId_form != null) {
          //Lista campos de un formulario
          auxiliar.setId_form(Integer.parseInt(sId_form));
          List lCampos = this.mi.getListarCampos(auxiliar);
          modelo.put("lCampos", lCampos);
        }

        modelo.put("datosDominio", datosDominio);
        modelo.put("id_dominio", Integer.toString(datosDominio.getId_dominio()));
        modelo.put("id_ubicacion_organica", Integer.toString(datosDominio.getId_ubicacion_organica()));
        modelo.put("id_dominio_padre", Integer.toString(datosDominio.getId_dominio_padre()));
        modelo.put("id_tipo_dominio", Integer.toString(datosDominio.getId_tipo_dominio()));
        modelo.put("id_form", Integer.toString(datosDominio.getId_form()));
        modelo.put("id_campo", Integer.toString(datosDominio.getId_campo()));
        modelo.put("dominio", datosDominio.getDominio());
        modelo.put("privado", Boolean.toString(datosDominio.getPrivado()));
        modelo.put("accion", sAccion);
        modelo.put("tabla", datosDominio.getTabla());
        modelo.put("primario", datosDominio.getPrimario());
        modelo.put("campo", datosDominio.getCampo());
	sTabla = datosDominio.getTabla();
	//Listamos los campos de la tabla elegida
        if ((!"".equals(sTabla)) && (sTabla != null)) {
          Abm tabla = new Abm();
          tabla.setTabla(sTabla);
          tabla = (Abm) this.mi.getBuscarTabla1(tabla);
          Abm lcampos = new Abm();
          lcampos.setId_tabla(tabla.getId_tabla());
          lcampos.setPermiso("p");
          List lCamposTabla = this.mi.getListarCamposTabla(lcampos);
          modelo.put("lCamposTabla", lCamposTabla);
        }
        return new ModelAndView("administrarDominios/NuevoDominio", modelo);
      }
    }

    modelo.put("id_dominio", sId_dominio);
    modelo.put("id_ubicacion_organica", sId_ubicacion_organica);
    modelo.put("id_dominio_padre", sId_dominio_padre);
    modelo.put("id_tipo_dominio", sId_tipo_dominio);
    modelo.put("id_form", sId_form);
    modelo.put("id_campo", sId_campo);
    modelo.put("accion", sAccion);
    modelo.put("tabla", sTabla);
    modelo.put("dominio", sDominio);
    modelo.put("privado", sPrivado);
    
    return new ModelAndView("administrarDominios/NuevoDominio", modelo);
      
   }
}