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

public class ListarCurriculumDocente implements Controller 
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
    int iId_rol = cliente.getId_rol();    
    String sBandera = request.getParameter("bandera");
    String sBoton = request.getParameter("botoncillo");
    modelo.put("nombres", sNombres);
    
    String sClave = request.getParameter("clave"+request.getParameter("hora"));
    
    if("".equals(sClave)){
      modelo.put("usuario", sNombres);
      return new ModelAndView("administrarCurriculumDocente/EntradaCurriculumDocente", modelo); 
    }
    
    Docentes verificar = new Docentes();
    verificar.setId_docente(iId_docente);
    verificar.setClave(sClave);
    
    if(!"1".equals(request.getParameter("bandera"))){
      verificar = this.mi.getComprobarDocente(verificar);
      if(verificar == null) {
        String mensaje = "Clave incorrecta";
        modelo.put("mensaje",mensaje);
        return new ModelAndView("Error",modelo);
      }
    }
    
    //Listamos su curriculum 
    Docentes datosDoc = this.mi.getBuscarDocente(verificar);
    modelo.put("datosDoc", datosDoc);
    if(datosDoc != null) {
      Curriculum datosC = new Curriculum();
      datosC.setId_persona(datosDoc.getId_persona());
      List lCurriculum = this.mi.cvGetListarRubrosPersona(datosC);  //Modif
      modelo.put("lCurriculum",lCurriculum);
      //Si pidieron eliminar
      if("Eliminar".equals(sBoton)){
        datosC.setId_dct_adjunto(cliente.getInt(request,"id_dct_adjunto"));
	int iElimina = this.mi.setEliminarDctAdjunto(datosC);
      }
      //Listar Imagenes
      datosC.setId_docente(datosDoc.getId_docente());
      datosC.setId_estado("I");
      List lImagenes = this.mi.getListarAdjuntosDocente(datosC);
      modelo.put("lImagenes", lImagenes);
      
    }
    else {
      return new ModelAndView("Error","mensaje","No existe el docente");
    }
    
    modelo.put("nombres", sNombres);  
    modelo.put("bandera", "1");  
    return new ModelAndView("administrarCurriculumDocente/ListarCurriculumDocente", modelo); 
  }
}