/****************************************
 @usuario          :: Dali Aparicio
 @fec_registro     :: 17.10.2005
 @ult_usuario      :: Dali Aparicio
 @fec_modificacion :: 17.10.2005
*****************************************/
package org.fautapo.web.herramientas.combos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Herramientas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class RecargarCombos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //CONTROL DE SESION
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) {return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente.");}
    String _nombres = cliente.getNombres();

    String sId_campo = request.getParameter("id_campo");
    String sPagina = request.getParameter("pagina");
    String sCondicion = request.getParameter("condicion");
    String sEstatico = request.getParameter("estatico");

    String sValor = request.getParameter("valor");


    
    Abm abm = new Abm();
    abm.setId_campo(Integer.parseInt(sId_campo));

    Herramientas foranea = new Herramientas();
    abm = this.mi.getBuscarForanea(abm);

    abm.setId_campo(Integer.parseInt(sId_campo));
    Abm campo = this.mi.getBuscarCampo(abm);


    if (sCondicion != null) {
      sCondicion=sCondicion.trim();
      if (!sCondicion.equals("")) {
        sCondicion = " upper(" + abm.getCampo_foraneo() + ") like upper('" + sCondicion + "%')";
      }
    } else {
     sCondicion="";
    }

    if (sEstatico != null) {
      sEstatico=sEstatico.trim();
      if (!sEstatico.equals("")) {
        if (!sCondicion.equals(""))
         sCondicion = sCondicion + " and " + campo.getCampo_padre() + "=" + sEstatico;  
        else 
         sCondicion = " " + campo.getCampo_padre() + "=" + sEstatico;  
      }
    }
    foranea.setCondicion(sCondicion); 
    if (sValor != null) {
      int iValor = Integer.parseInt(sValor);
      int i = 0;

      foranea.setCampo(abm.getId_campo_foraneo() + "#~~#" + abm.getCampo_foraneo());
      foranea.setTabla_foranea(abm.getTabla_foranea());
      foranea.setId_campo_foraneo(abm.getId_campo_foraneo());


      boolean t = true;
      while (t) {
        i++; 
        foranea.setPagina(i);
        foranea.setCombo(this.mi.getListarCombosPagina(foranea));
        List lCombos = foranea.getCombo();
        for (int j=0; j < lCombos.size(); j++) {
          Herramientas lHerram = (Herramientas)lCombos.get(j);
          if (lHerram.getId_campo()==iValor) {
            t = false;
            break;
          }
        }
      }
    } else {    
      if (sPagina != null) {
        int iPagina = Integer.parseInt(sPagina);
        foranea.setPagina(iPagina);
        iPagina++;
        modelo.put("pagina",Integer.toString(iPagina));
      } else {
        foranea.setPagina(1);
      }
      foranea.setCampo(abm.getId_campo_foraneo() + "#~~#" + abm.getCampo_foraneo());
      foranea.setTabla_foranea(abm.getTabla_foranea());
      foranea.setId_campo_foraneo(abm.getId_campo_foraneo());
      foranea.setCombo(this.mi.getListarCombosPagina(foranea));

    }
    foranea.setCampo(campo.getCampo());
    modelo.put("listadoCombos", foranea);
    return new ModelAndView("herramientas/combos/RecargarCombos", modelo);
  }
}