package org.fautapo.web.inscripcionMaterias.postulantes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Grupos;
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


public class RegistrarProgramacionMaterias implements Controller {

  private MiFacade mi;;
     
  public void setMi(MiFacade mi) {this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //String sUlt_usuario = cliente.getId_usuario()+"|"+cliente.getId_rol() ;    

    int iResultado;
    String sId_postulante = request.getParameter("id_postulante");
    String sId_periodo    = request.getParameter("id_periodo");
    int iTotal_materias   = Integer.parseInt(request.getParameter("total_materias"));
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    
    //Sacando los datos del Postulante    
    Postulantes datosPostulante = new Postulantes();
    datosPostulante.setId_postulante(Integer.parseInt(sId_postulante));
    datosPostulante = this.mi.getPstBuscarPostulante(datosPostulante);
    
    //Buscamos el periodo
    Programas buscarPeriodo= new Programas();
    buscarPeriodo.setId_programa(datosPostulante.getId_programa());
    buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);
    

    boolean bBandera = true;
    for (int i = 0; i < iTotal_materias; i++) {
      String sMateria = request.getParameter("materia" + i);
      String sDatos[] = sMateria.split(":"); 
      Grupos grupo = new Grupos();
      grupo.setId_materia(Integer.parseInt(sDatos[0]));
      grupo.setId_modelo_ahorro(Integer.parseInt(sDatos[1]));
      grupo.setGestion(Integer.parseInt(sGestion));
      grupo.setPeriodo(Integer.parseInt(sPeriodo));
      grupo.setId_grupo(Integer.parseInt(sDatos[2]));

      grupo = this.mi.getDptoBuscarCupoRestanteGrupo(grupo);

      if (grupo.getCupo_actual() > 0) {
        Programas programacion = new Programas();
	programacion.setId_postulante(datosPostulante.getId_postulante());
	programacion.setId_materia(Integer.parseInt(sDatos[0]));
	programacion.setId_grupo(Integer.parseInt(sDatos[2]));
	programacion.setId_modelo_ahorro(Integer.parseInt(sDatos[1]));
	programacion.setGestion(Integer.parseInt(sGestion));
	programacion.setPeriodo(Integer.parseInt(sPeriodo));
	programacion.setId_rol(cliente.getId_rol());	       //CRCB
	programacion.setUlt_usuario(cliente.getId_usuario());
        this.mi.setPstProgramacionMateria(programacion);

      }
      else {
        bBandera = false;
      }
    }
    if (!bBandera) {
      return new ModelAndView("Aviso","mensaje","Alguna de sus materias no fueron programadas por falta de plazas");
    }
    return new ModelAndView("Aviso","mensaje","Las materias elegidas fueron registradas correctamente");
  }
}