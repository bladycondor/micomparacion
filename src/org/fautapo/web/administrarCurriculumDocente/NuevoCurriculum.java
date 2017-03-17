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

public class NuevoCurriculum implements Controller 
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
      String sId_persona = request.getParameter("id_persona");
      modelo.put("id_persona", sId_persona);
      String sId_curriculum = request.getParameter("id_curriculum");
      modelo.put("id_curriculum", sId_curriculum);
      String sBorrar = request.getParameter("borrar");
      modelo.put("borrar", sBorrar);
      
      if((sId_persona != null)&&(!sId_persona.equals(""))){
        Curriculum curriculum = new Curriculum();
        curriculum.setId_persona(Integer.parseInt(sId_persona));
        List lRubros = this.mi.cvGetListarRubros(curriculum);
        modelo.put("lRubros", lRubros);
        List lSubRubros = this.mi.cvGetListarSubRubros(curriculum);
        modelo.put("lSubRubros", lSubRubros);
        if((sId_curriculum != null)&&(!sId_curriculum.equals(""))&&(!sId_curriculum.equals("0"))){
          curriculum.setId_curriculum(Integer.parseInt(sId_curriculum));
          curriculum = (Curriculum) (this.mi.cvGetListarRubrosPersona(curriculum)).get(0);
          modelo.put("curriculum", curriculum);
        }       
      }
      else {
        return new ModelAndView("Aviso", "mensaje", "No ingreso la persona");
      }
      return new ModelAndView("administrarCurriculumDocente/NuevoCurriculum", modelo); //enviar modelo a jsp
   }
}