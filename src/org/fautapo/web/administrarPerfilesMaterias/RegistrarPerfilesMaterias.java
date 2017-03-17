package org.fautapo.web.administrarPerfilesMaterias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Planes;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class RegistrarPerfilesMaterias implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    String sId_plan = cliente.getString(request, "id_plan");
    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_perfil = cliente.getInt(request, "id_perfil");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    int iNro_materias = cliente.getInt(request, "nro_materias");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");

    Planes plan = new Planes();
    plan.setId_perfil(iId_perfil);
    plan.setId_programa(iId_programa);
    plan.setId_plan(sId_plan);
    plan.setId_tipo_evaluacion(iId_tipo_evaluacion);
    plan.setGestion(iGestion);
    plan.setPeriodo(iPeriodo);
    plan.setUlt_usuario(cliente.getId_usuario());
    for (int i=0; i < iNro_materias; i++) {
      int iId_materia = cliente.getInt(request, "materia" + i);
      double fCosto = Double.valueOf(cliente.getString(request, "costo" + iId_materia)).doubleValue();
      if (fCosto > 0.0) {
	plan.setId_materia(iId_materia);
	plan.setCosto(fCosto);
	this.mi.setTrnRegistrarPerfilMateria(plan);
      }
    }
    return new ModelAndView("Aviso", "mensaje", "Los datos se registraron correctamente");
  }
}