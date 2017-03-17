package org.fautapo.web.habilitarPostulantes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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


public class MostrarPostulantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    int iId_postulante = cliente.getInt(request,"id_postulante");    
    String sId_facultad = request.getParameter("id_facultad");
    String sId_programa = request.getParameter("id_programa");
    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");

    String sId_postulante_hab[] = request.getParameterValues("id_postulante_hab");
    String sId_postulante;
    Postulantes datosP= new Postulantes();
    List lPostulantesSelec =new ArrayList();
    

    if (sId_postulante_hab != null) {
      System.out.println("El tamanio del id_postulante -->"+Integer.toString(sId_postulante_hab.length));
      for (int i = 0; i< sId_postulante_hab.length; i++) {
        datosP = new Postulantes();
	sId_postulante = sId_postulante_hab[i];
        System.out.println("EL ID POSTULANTE -->"+ i +" = "+ sId_postulante);
        datosP.setId_postulante(Integer.parseInt(sId_postulante));
	datosP = this.mi.getPstBuscarPostulanteNombres(datosP);
	lPostulantesSelec.add(datosP);
      }
   }
   System.out.println("El tamanio de la lista lPostulantesSelec  -->"+Integer.toString(lPostulantesSelec.size()));
   modelo.put("lPostulantesSelec", lPostulantesSelec);
   modelo.put("id_facultad", sId_facultad);
   modelo.put("id_programa", sId_programa);   
   modelo.put("gestion", sGestion);
   modelo.put("periodo", sPeriodo);

   return new ModelAndView("habilitarPostulantes/MostrarPostulantes", modelo);
    
  }
}
