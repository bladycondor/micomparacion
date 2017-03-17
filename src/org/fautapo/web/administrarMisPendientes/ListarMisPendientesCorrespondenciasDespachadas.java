package org.fautapo.web.administrarMisPendientes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.EnviarCorreo;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ListarMisPendientesCorrespondenciasDespachadas implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Character caracter;
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_tipo_proceso = request.getParameter("id_tipo_proceso");
    modelo.put("id_tipo_proceso", sId_tipo_proceso);

    //Listar correspondencia despachada 
    Tramites tramite = new Tramites();
    tramite.setDe(cliente.getId_usuario());
    List listaTramites = this.mi.getListarTramitesMiosCorrespondenciaDe(tramite);
    for (int i = 0; i < listaTramites.size(); i++) {
      Tramites tramitin = (Tramites) listaTramites.get(i);
      tramitin.setLista(this.mi.getListarCamposReferencia(tramitin));
      listaTramites.set(i, tramitin);
    }
    modelo.put("lCorresDespachada", listaTramites);
    
    //Colocamos el nombre de FAUTAPO
    String sInstitucion = (caracter = new Character((char)70)).toString()+(caracter = new Character((char)65)).toString()+(caracter = new Character((char)85)).toString();
    sInstitucion = sInstitucion+(caracter = new Character((char)84)).toString()+(caracter = new Character((char)65)).toString()+(caracter = new Character((char)80)).toString()+(caracter = new Character((char)79)).toString();
    modelo.put("institucion", sInstitucion);
    //FIN Colocamos el nombre de FAUTAPO

    return new ModelAndView("administrarMisPendientes/ListarMisPendientesCorrespondenciasDespachadas", modelo);
  }
}