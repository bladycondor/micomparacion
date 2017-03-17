package org.fautapo.web.administrarAcls2;

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
import org.fautapo.domain.Abm;
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
    String sPermiso = request.getParameter("permiso");
    String sTabla = request.getParameter("tabla");

    //Recuperamos los valores del jsp
    String sId_proceso = request.getParameter("id_proceso");
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_actividad", sId_actividad);
    modelo.put("permiso", sPermiso);
    modelo.put("tabla", sTabla);

    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);
    if ((sId_proceso != null) && (!"".equals(sId_proceso))) {
      //Listamos las actividades
      Actividades proceso = new Actividades();
      proceso.setId_proceso(Integer.parseInt(sId_proceso));
      List lActividades = this.mi.getListarActividadesLimbo(proceso);
      modelo.put("lActividades", lActividades);

      if ((sTabla != null) && (!"".equals(sTabla))) {
        Abm abm = new Abm();
	abm.setTabla(sTabla);
        Abm tabla = (Abm) this.mi.getBuscarTabla1(abm);
	tabla.setPermiso("l");
	//Listamos los campos de la tabla
        List lCampos = this.mi.getListarCamposTabla(tabla);
        modelo.put("lCampos", lCampos);
	
        if ((sId_actividad != null) && (!"".equals(sId_actividad))) {
          //Registramos los acls      
          if ("Agregar".equals(sBoton) && (sPermiso != null) && (!"".equals(sPermiso))) {
            datosCampo.setId_proceso(Integer.parseInt(sId_proceso));
            datosCampo.setId_actividad(Integer.parseInt(sId_actividad));
            datosCampo.setPermiso(sPermiso);
            datosCampo.setTabla(sTabla);
            datosCampo.setUlt_usuario(cliente.getId_usuario());
            String sCampo[] = request.getParameterValues("campo_a");
            if (sCampo != null) {
              for (int i = 0; i< sCampo.length; i++) {
                datosCampo.setCampo(sCampo[i]);
                int iResultado  = this.mi.setRegistrarAcl2(datosCampo);
              }
            }
          }
          // Fin del registro del acl
	  
          //Eliminamos el acl
          if ("Eliminar".equals(sBoton)) {
            datosCampo.setId_proceso(Integer.parseInt(sId_proceso));
            datosCampo.setId_actividad(Integer.parseInt(sId_actividad));
            datosCampo.setPermiso(sPermiso);
            datosCampo.setTabla(sTabla);
            datosCampo.setUlt_usuario(cliente.getId_usuario());
            String sCampo[] = request.getParameterValues("campo_e");
            if (sCampo != null) {
              for (int i = 0; i< sCampo.length; i++) {
                datosCampo.setCampo(sCampo[i]);
                int iResultado  = this.mi.setEliminarAcl2(datosCampo);
              }
            }
          }
	  //Fin de la eliminacion de los acls
     
          //Listamos los campos que ya tienen asignado un acl
          Campos campos = new Campos();
          campos.setId_proceso(Integer.parseInt(sId_proceso));
          campos.setId_actividad(Integer.parseInt(sId_actividad));
          campos.setTabla(sTabla);
          List lCamposAcl = this.mi.getListarCamposAcl2(campos);
          modelo.put("lCamposAcl", lCamposAcl);
        }
      }
    }
    
    //Listamos las tablas
    List lTablas = this.mi.getListarTablas();
    modelo.put("lTablas", lTablas);
      
    return new ModelAndView("administrarAcls2/RegistrarAcl", modelo);
  }
}