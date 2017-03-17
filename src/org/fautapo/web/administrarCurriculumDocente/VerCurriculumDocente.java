package org.fautapo.web.administrarCurriculumDocente;

import org.fautapo.domain.Curriculum;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.logic.MiFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2007-10-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2007-10-18
*/

public class VerCurriculumDocente implements Controller 
{
  private MiFacade mi;
  public void setMi(MiFacade mi)  {this.mi = mi;}
   
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception 
  {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String sNombres = cliente.getNombres();
    int iId_docente = cliente.getId_usuario();
    //int iId_docente = cliente.getInt(request,"id_docente");
    int iId_rol = cliente.getId_rol();    
    String sBandera = request.getParameter("bandera");
    String sBoton = request.getParameter("botoncillo");
    modelo.put("nombres", sNombres);
    
    //Verifica si es docente
    if(iId_docente > 0) {
      // Verifica al adocente
      //Listamos su curriculum 
      Docentes datosDoc = new Docentes();
      datosDoc.setId_docente(iId_docente);
      datosDoc = this.mi.getBuscarDocente(datosDoc);
      modelo.put("datosDoc", datosDoc);
      if(datosDoc != null) {
        Curriculum datosC = new Curriculum();
        datosC.setId_persona(datosDoc.getId_persona());
        List lCurriculum = this.mi.cvGetListarRubrosPersona(datosC);  //Modif
        modelo.put("lCurriculum",lCurriculum);
        //Listar Imagenes
        datosC.setId_docente(datosDoc.getId_docente());
        datosC.setId_estado("I");
        List lImagenes = this.mi.getListarAdjuntosDocente(datosC);
        modelo.put("lImagenes", lImagenes);
      
      }
      else {
        return new ModelAndView("Error","mensaje","No existen registros como docente");
      }
    }
    else {
      return new ModelAndView("Error","mensaje","Faltan datos");
    }  
    
    modelo.put("nombres", sNombres);  
    modelo.put("bandera", "1");  
    return new ModelAndView("administrarCurriculumDocente/VerCurriculumDocente", modelo); 
  }
}