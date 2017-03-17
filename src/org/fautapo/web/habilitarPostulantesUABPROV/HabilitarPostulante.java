package org.fautapo.web.habilitarPostulantesUABPROV;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class HabilitarPostulante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //int iId_postulante = cliente.getInt(request,"id_postulante");    
    String sId_programa = cliente.getString(request, "id_programa");
    String sId_facultad = cliente.getString(request, "id_facultad");
    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");
    String sId_postulante_hab[] = request.getParameterValues("id_postulante_hab");
    String sId_postulante; int iResultado=0;List listaPostulantes;
    Postulantes datosP= new Postulantes();
    //Postulantes postulante = new Postulantes();
    if (sId_postulante_hab != null) {
      for (int i = 0; i< sId_postulante_hab.length; i++) {
        datosP = new Postulantes();
	sId_postulante = sId_postulante_hab[i];
        System.out.println("EL ID POSTULANTE REGISTRAR -->"+ i +" = "+ sId_postulante);
        datosP.setId_postulante(Integer.parseInt(sId_postulante));
        datosP.setId_estado("A"); //Regis
        datosP.setUlt_usuario(cliente.getId_usuario()); //Regis
	iResultado = this.mi.setPstModificarEstadoPostulante(datosP);
      }
   }
   
   if(iResultado == 1) {
     datosP= new Postulantes();
     datosP.setGestion(Integer.parseInt(sGestion));
     datosP.setPeriodo(Integer.parseInt(sPeriodo));
     datosP.setId_programa(Integer.parseInt(sId_programa));
     listaPostulantes = this.mi.getMiListarPstProgramaGestionPeriodo(datosP);
     modelo.put("listaPostulantes", listaPostulantes); 
   }
   else {
     return new ModelAndView("Error","mensaje", "No se ha realizado el registro");
   }
   
   modelo.put("id_programa", sId_programa);
   modelo.put("id_facultad", sId_facultad);
   modelo.put("gestion", sGestion);
   modelo.put("periodo", sPeriodo);
   
   return new ModelAndView("habilitarPostulantesUABPROV/ListarPostulantes", modelo);
    
  }
}
