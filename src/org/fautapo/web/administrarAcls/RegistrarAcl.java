package org.fautapo.web.administrarAcls;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.Boolean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Roles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-21
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-22
*/

public class RegistrarAcl implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Campos datosCampo = new Campos();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sBoton = request.getParameter("boton");		//recupera el valor del boton
    String sId_actividad = request.getParameter("id_actividad");
    String sId_tipo_permiso = request.getParameter("id_tipo_permiso");
    String sHabilitado = request.getParameter("habilitado");

    //Recuperamos los valores del jsp
    String sId_proceso = request.getParameter("id_proceso");
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_actividad", sId_actividad);
    modelo.put("id_tipo_permiso", sId_tipo_permiso);
    modelo.put("habilitado", sHabilitado);

    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);
    if ((sId_proceso != null) && (!"".equals(sId_proceso))) {
      //Listamos las actividades
      Actividades proceso = new Actividades();
      proceso.setId_proceso(Integer.parseInt(sId_proceso));
      List lActividades = this.mi.getListarActividadesNoLimbo(proceso);
      modelo.put("lActividades", lActividades);

      //Buscamos el formulario que corresponde al proceso
      Campos datosFormulario = new Campos();
      datosFormulario.setId_proceso(Integer.parseInt(sId_proceso));
      datosFormulario = (Campos) this.mi.getBuscarFormulario1(datosFormulario);
    
      //Listamos los campos de un formulario
      List lCampos = this.mi.getListarCampos(datosFormulario);
      modelo.put("lCampos", lCampos);
      
      if ((sId_actividad != null) && (!"".equals(sId_actividad))) {
        //Registramos los acls      
        if ("Agregar".equals(sBoton) && (sId_tipo_permiso != null) && (!"".equals(sId_tipo_permiso))) {
          if ("true".equals(sHabilitado)) {
            datosCampo.setHabilitado(true);
          }
          else {
            datosCampo.setHabilitado(false);
          }
          datosCampo.setId_proceso(Integer.parseInt(sId_proceso));
          datosCampo.setId_actividad(Integer.parseInt(sId_actividad));
          datosCampo.setId_form(datosFormulario.getId_form());
          datosCampo.setId_tipo_permiso(sId_tipo_permiso);
          datosCampo.setUlt_usuario(cliente.getId_usuario());
          String sId_campo[] = request.getParameterValues("id_campo_a");
          if (sId_campo != null) {
            for (int i = 0; i< sId_campo.length; i++) {
              datosCampo.setId_campo(Integer.parseInt(sId_campo[i]));
              int iResultado  = this.mi.setRegistrarAcl(datosCampo);
            }
          }
        }
        // Fin del registro del acl
	  
        //Eliminamos el acl
        if ("Eliminar".equals(sBoton)) {
          datosCampo.setId_proceso(Integer.parseInt(sId_proceso));
          datosCampo.setId_actividad(Integer.parseInt(sId_actividad));
          datosCampo.setId_form(datosFormulario.getId_form());
          datosCampo.setId_tipo_permiso(sId_tipo_permiso);
          datosCampo.setUlt_usuario(cliente.getId_usuario());
          String sId_campo[] = request.getParameterValues("id_campo_e");
          if (sId_campo != null) {
            for (int i = 0; i< sId_campo.length; i++) {
              datosCampo.setId_campo(Integer.parseInt(sId_campo[i]));
              int iResultado  = this.mi.setEliminarAcl(datosCampo);
            }
          }
        }
	//Fin de la eliminacion de los acls
     
        //Listamos los campos que ya tienen asignado un acl
        if (datosFormulario != null) {
          datosFormulario.setId_actividad(Integer.parseInt(sId_actividad));
          List lCamposAcl = this.mi.getListarCamposAcl(datosFormulario);
          modelo.put("lCamposAcl", lCamposAcl);
	}
      }
    }
    
    //Listamos los tipos de permisos
    List lTiposPermisos = this.mi.getListarTiposPermisos();
    modelo.put("lTiposPermisos", lTiposPermisos);

    return new ModelAndView("administrarAcls/RegistrarAcl", modelo);
  }
}