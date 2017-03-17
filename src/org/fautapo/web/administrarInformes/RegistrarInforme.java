package org.fautapo.web.administrarInformes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-20
*/

public class RegistrarInforme implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Informes datosInforme = new Informes();
    int iId_informe = 0;
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    String sId_proceso   = request.getParameter("id_proceso");
    String sId_actividad = request.getParameter("id_actividad");
    String sId_informe   = request.getParameter("id_informe");
    String sInforme      = request.getParameter("informe");
    String sDescripcion  = request.getParameter("descripcion");
    String sContenido    = request.getParameter("contenido");
    String sAccion       = request.getParameter("accion");
    String sBoton        = request.getParameter("boton");

    modelo.put("accion", sAccion);
    modelo.put("id_actividad", sId_actividad);
    modelo.put("id_proceso", sId_proceso);

    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(Integer.parseInt(sId_proceso));
    datosProceso = this.mi.getBuscarProceso(datosProceso);
    modelo.put("datosProceso", datosProceso);

    //Listamos las actividades
    List lActividades = this.mi.getListarActividades(datosProceso);
    modelo.put("lActividades", lActividades);

    if ((sId_informe != null) && (!"".equals(sId_informe))) {
      //Sacamos los datos del informe
      datosInforme.setId_informe(Integer.parseInt(sId_informe));
      datosInforme = this.mi.getBuscarInforme(datosInforme);
      datosInforme.setContenido(datosInforme.getContenido().replace("\r\n",""));
      modelo.put("datosInforme", datosInforme);
      iId_informe = Integer.parseInt(sId_informe);
    }

    if ("Ver".equals(sAccion)) {
      Actividades actividad = new Actividades();
      actividad.setId_actividad(datosInforme.getId_actividad());
      actividad = this.mi.getBuscarActividad(actividad);
      modelo.put("actividad", actividad);
      return new ModelAndView("administrarInformes/VerInforme", modelo);
    }
    
    //Registramos los cambios del informe
    if ("Guardar".equals(sBoton)) {
      datosInforme.setId_informe(iId_informe);
      datosInforme.setId_proceso(Integer.parseInt(sId_proceso));
      datosInforme.setId_actividad(Integer.parseInt(sId_actividad));
      datosInforme.setInforme(sInforme);
      datosInforme.setDescripcion(sDescripcion);
      datosInforme.setContenido(sContenido);
      datosInforme.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setRegistrarInforme(datosInforme);
      if (iResultado == 0) {
        return new ModelAndView("Error", "mensaje", "Los datos no fueron registrados");
      }
    }

    //Eliminamos el informe
    if ("Si".equals(sBoton)) {
      datosInforme.setId_informe(Integer.parseInt(sId_informe));
      datosInforme.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setEliminarInforme(datosInforme);
      if (iResultado == 0) {
        return new ModelAndView("Error", "mensaje", "El informe no fué eliminado");
      }
    }

    if (("No".equals(sBoton)) || ("Si".equals(sBoton)) || ("Guardar".equals(sBoton))) {
      //Listamos los procesos según el acceso del usuario
      List lProcesos = this.mi.getListarProcesosAcceso(cliente);
      modelo.put("lProcesos", lProcesos);
      
      if ((!"".equals(sId_proceso)) && (sId_proceso != null)) {
        //Listamos los informes por actividades del proceso elegido
        Informes informe = new Informes();
        informe.setId_proceso(Integer.parseInt(sId_proceso));
        List lInformes = this.mi.getListarInformes(informe);
        modelo.put("lInformes", lInformes);
      }
      return new ModelAndView("administrarInformes/ListarInformes", modelo);
    }


    return new ModelAndView("administrarInformes/RegistrarInforme", modelo);
  }
}