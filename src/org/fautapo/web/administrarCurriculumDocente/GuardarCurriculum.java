package org.fautapo.web.administrarCurriculumDocente;

import org.fautapo.domain.Curriculum;
import org.fautapo.domain.Clientes;
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


public class GuardarCurriculum implements Controller 
{
  private MiFacade mi;
  public void setMi(MiFacade mi)  {this.mi = mi;}
   
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception 
  {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    Map modelo = new HashMap();
    
    String sBandera = request.getParameter("bandera");
    modelo.put("bandera",sBandera);
    try {  
      String sId_persona = request.getParameter("id_persona");
      modelo.put("id_persona", sId_persona);
      String sId_rubro = request.getParameter("id_rubro");
      String sId_sub_rubro = request.getParameter("id_sub_rubro");
      String sDetalle = request.getParameter("detalle");
      String sDel = request.getParameter("del");
      String sAl = request.getParameter("al");
      String sId_curriculum = request.getParameter("id_curriculum");
      String sBorrar = request.getParameter("borrar");
      if((sId_persona != null)&&(!sId_persona.equals(""))){
        if((sId_curriculum == null)||(sId_curriculum.equals(""))){ sId_curriculum = "0";}
        Curriculum curriculum = new Curriculum();
        curriculum.setId_estado("A");
        if((sBorrar!=null)&&(sBorrar.equals("borrar"))) curriculum.setId_estado("X");
        curriculum.setId_curriculum(Integer.parseInt(sId_curriculum));
        curriculum.setId_persona(Integer.parseInt(sId_persona));
        curriculum.setId_rubro(Integer.parseInt(sId_rubro));      
        curriculum.setId_sub_rubro(Integer.parseInt(sId_sub_rubro));
        curriculum.setDetalle(sDetalle);
        curriculum.setDel(sDel);
        curriculum.setAl(sAl);
	curriculum.setId_rol(cliente.getId_rol());
        curriculum.setUlt_usuario(cliente.getId_usuario());
        this.mi.cvSetRegistrarCurriculum(curriculum);
        modelo.put("mensaje", "Se guardaron los datos correctamente");
      }
      else {
        return new ModelAndView("Aviso", "mensaje", "No ingreso la persona");
      }
      return new ModelAndView("administrarCurriculumDocente/GuardarCurriculum", modelo); //enviar modelo a jsp
    }
    catch(Exception e) {
      System.out.println("Error GuardarActivo:" + e.getMessage());
      return new ModelAndView("Aviso", "mensaje", "No se pudo realizar la accion");
    }
  }
}