package org.fautapo.web.administrarProgramasEspecializados.administrarPstPersona;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class NuevoPstPersona implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    int iGestion= cliente.getGestion();
    int iPeriodo = cliente.getPeriodo();
    int iId_facultad = cliente.getId_facultad();
    int iId_programa = cliente.getId_programa();
    int iResultadoT =0;String sBandera;
    //Para wayka
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    String sTitulo = request.getParameter("titulo");
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);
    
    //Listar Perfiles
    if(!"".equals(sId_proceso) && (sId_proceso != null)){
      //Para Cajas 
      Perfiles datoPerfil = new Perfiles();
      datoPerfil.setId_proceso(Integer.parseInt(sId_proceso));
      List lPerfilesProcesos = this.mi.getTrnMiListarPerfilesProceso(datoPerfil);
      if(lPerfilesProcesos.size() == 1){
         sBandera ="1";
	 modelo.put("bandera",sBandera);
      }
      modelo.put("lPerfilesProcesos", lPerfilesProcesos);
    }
    else {
      return new ModelAndView("Error", "mensaje", "No existe el proceso. Verifique");
    }
    
    try {
      //Sacamos el id_campo
      Tramites datosTramite = new Tramites();
      datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
      datosTramite.setEtiqueta("requisitos");
      int iId_campo = this.mi.getBuscarIdCampoLimbo(datosTramite);

      //Buscamos los datos del campo
      Campos datosCampo = new Campos();
      datosCampo.setId_campo(iId_campo);
      datosCampo = this.mi.getBuscarCampoForm(datosCampo);

      //Sacamos los datos del dominio
      Dominios datosDominio =  new Dominios();
      datosDominio.setId_dominio(datosCampo.getId_dominio());
      datosDominio = this.mi.getBuscarDominio(datosDominio);
    
      //Sacamos los datos de los requisitos
      datosTramite = new Tramites();
      datosTramite.setId_dominio(datosDominio.getId_dominio());
      datosTramite.setId_tupla_padre(datosDominio.getId_tupla_padre());
      datosTramite.setId_tipo_dominio(datosDominio.getId_tipo_dominio());
      datosTramite.setId_dominio_padre(datosDominio.getId_dominio_padre());
      List lTuplas = this.mi.getListarCombos2(datosTramite);
      modelo.put("lTuplas", lTuplas);
    } catch(Exception e) {
      System.out.println("El proceso no tiene requisitos");
    }
    
    try {
      //Verificamos si el perfil puede tener descuento
      Perfiles datosPerfil = new Perfiles();
      datosPerfil.setId_proceso(Integer.parseInt(sId_proceso));
      List lPerfiles = this.mi.getTrnMiListarPerfilesProceso(datosPerfil);
      datosPerfil = (Perfiles) lPerfiles.get(0);
      int iTieneDescuento = this.mi.getTrnPerfilTieneDescuento(datosPerfil);
      modelo.put("tieneDescuento", Integer.toString(iTieneDescuento));
      modelo.put("descuento", "0");
      //Listamos tipos descuentos
      List lTiposDescuentos = this.mi.getTrnListarTiposDescuentos();
      modelo.put("lTiposDescuentos", lTiposDescuentos);    
    } catch(Exception e) {
      return new ModelAndView("Error", "mensaje", "El proceso no tiene asignado un perfil");
    }
    
    
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("cliente", cliente);
    return new ModelAndView("administrarProgramasEspecializados/administrarPstPersona/NuevoPstPersona", modelo);
  }
}
