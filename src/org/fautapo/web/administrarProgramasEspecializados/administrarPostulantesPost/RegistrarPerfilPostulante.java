package org.fautapo.web.administrarProgramasEspecializados.administrarPostulantesPost;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class RegistrarPerfilPostulante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Para wayka
    Tramites datosTramite = new Tramites();
    String sId_proceso = request.getParameter("id_proceso");
    String sTitulo = request.getParameter("titulo");
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    int iResultado=0;
    
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_tramite = request.getParameter("id_tramite");
    String sId_postulante = request.getParameter("id_postulante");
    int iId_tipo_perfil = cliente.getInt(request,"id_tipo_perfil");
    int iId_perfil = cliente.getInt(request,"id_perfil");
    String sId_perfil_proceso_p[] = request.getParameterValues("id_perfil_proceso_p");
    String sId_perfil_proceso="";

    if (sId_perfil_proceso_p != null) {
      if (Integer.parseInt(sId_tramite) == 0) {
        return new ModelAndView("Error","mensaje","El tramite no ha pasado");
      }
      //RECUPERAMOS id_proceso_perfil 
      for (int i = 0; i< sId_perfil_proceso_p.length; i++) {
        //System.out.println("EL ID perfil proceso -->"+ i +" = "+ sId_perfil_proceso_p[i]);
        if(i==0) {
	  sId_perfil_proceso += sId_perfil_proceso_p[i];
	}
	else{
	  sId_perfil_proceso += ":"+sId_perfil_proceso_p[i];
	}  
      }
      //Registramos los valores de id_perfil en wayka
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite.setEtiqueta("id_perfil_proceso");
      datosTramite.setValor(sId_perfil_proceso);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    }

    //Sacamos los datos del tramite
    datosTramite = new Tramites(); 
    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
    datosTramite.setUlt_usuario(cliente.getId_usuario());
    iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);      
      
    //String sMensaje="Se realizo el registro";
    //modelo.put("mensaje",sMensaje);
    //Buscamos los datos del postulante
    Postulantes datosPst = new Postulantes();
    datosPst.setId_postulante(Integer.parseInt(sId_postulante));
    datosPst = this.mi.getPstBuscarPostulanteNombres(datosPst);
    modelo.put("datosPst", datosPst);
    Postulantes datosColegio = new Postulantes();
    datosColegio.setId_persona(datosPst.getId_persona());
    datosColegio = this.mi.getBuscarPstPersonaColegio(datosColegio);
    modelo.put("datosColegio",datosColegio);

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    
    //Sacamos el formato de la hora
    Abm formatoHora = new Abm();
    formatoHora.setCampo("formato_hora");
    formatoHora.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
    
    return new ModelAndView("administrarProgramasEspecializados/administrarPostulantesPost/ImprimirDatosPostulante", modelo);
    
  }
}
