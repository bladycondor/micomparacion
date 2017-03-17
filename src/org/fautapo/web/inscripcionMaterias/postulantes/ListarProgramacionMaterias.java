package org.fautapo.web.inscripcionMaterias.postulantes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Personas;
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


public class ListarProgramacionMaterias implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    Postulantes datosPostulante;
    //Recuperando variables del jsp
    String sId_postulante = request.getParameter("id_postulante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_programa = request.getParameter("id_programa");
    
    if ("".equals(sId_postulante) && ("".equals(sNombres)) && ("".equals(sCi)))
    {  
       modelo.put("gestion", sGestion);
       modelo.put("periodo", sPeriodo);
       modelo.put("id_programa", sId_programa);
       return new ModelAndView("inscripcionMaterias/postulantes/BuscarPostulantes",modelo);
    }
    
    if(!"".equals(sId_postulante))
    {
      //Sacando los datos del postulante    
      datosPostulante = new Postulantes();
      try {
        datosPostulante.setId_postulante(Integer.parseInt(sId_postulante));
      }
      catch(Exception e){
        return new ModelAndView("Error","mensaje","Para el R.P. inserte un dato de tipo entero ");
      }
      datosPostulante.setId_programa(Integer.parseInt(sId_programa));	
      datosPostulante = this.mi.getPstBuscarPostulantePrograma(datosPostulante);
      modelo.put("datosPostulante",datosPostulante);
      if(datosPostulante == null){
        return new ModelAndView("Aviso","mensaje","No existe el  R.P.  "+ sId_postulante);
      }
      //Sacando los datos personales del Postulante encontrado
      //Postulantes datosPersona = new Personas();
      datosPostulante.setId_persona(datosPostulante.getId_persona());
      Postulantes datosPersona = this.mi.getPstBuscarPersona(datosPostulante);
      modelo.put("datosPersona", datosPersona);
      //Sacando el programa en que esta el estludiante
      Programas datosPrograma = new Programas();
      datosPrograma.setId_programa(datosPostulante.getId_programa());
      datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
      modelo.put("datosPrograma", datosPrograma);
      
      //Buscamos el periodo
      Programas buscarPeriodo= new Programas();
      buscarPeriodo.setId_programa(datosPostulante.getId_programa());
      buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);
      modelo.put("id_periodo", Integer.toString(buscarPeriodo.getId_periodo()));
      
      //Sacando los parametros de programacion de prg_detalles
      Programas parametro = new Programas();
      parametro.setId_programa(datosPostulante.getId_programa());
      parametro.setId_plan(datosPostulante.getId_plan());
      parametro.setId_tipo_grado(datosPostulante.getId_tipo_grado());
      parametro.setId_tipo_programacion(3); //COMO POSTULANTE
      parametro.setGestion(Integer.parseInt(sGestion));
      parametro.setPeriodo(Integer.parseInt(sPeriodo));
      List lParametros = this.mi.getPrgBuscarDetalles(parametro);
      if(lParametros.size() == 0) {
        String sMensaje="No existen parámetros de programación";
        modelo.put("gestion", sGestion);
        modelo.put("periodo", sPeriodo);
        modelo.put("mensaje", sMensaje);
        return new ModelAndView("inscripcionMaterias/postulantes/ListarProgramacionMaterias", modelo);      
      }
      else{
        //Listamos las materia programadas  para el postulante
        Materias programacion = new Materias();
        programacion.setId_postulante(Integer.parseInt(sId_postulante));
        programacion.setGestion(Integer.parseInt(sGestion));
        programacion.setPeriodo(Integer.parseInt(sPeriodo));
        List lMaterias = this.mi.getPstPrgListarProgramacionMateriasAut(programacion);
        for (int i = 0; i < lMaterias.size(); i++) {
          Materias materia = (Materias) lMaterias.get(i);
          if (materia.getCupo_restante() > 0) {
    	    programacion.setId_materia(materia.getId_materia());
 	    programacion.setId_modelo_ahorro(0);
            materia.setGrupos(this.mi.getDptoListarMateriaGrupo(programacion));
	    lMaterias.set(i, materia);
          }
        }
	modelo.put("lMaterias",lMaterias);
        modelo.put("gestion", sGestion);
        modelo.put("periodo", sPeriodo);
        modelo.put("lParametros", lParametros);
        return new ModelAndView("inscripcionMaterias/postulantes/ListarProgramacionMaterias", modelo);      
      }
    }
    
    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosPostulante = new Postulantes();
      datosPostulante.setDip(sCi);
      datosPostulante.setId_programa(Integer.parseInt(sId_programa));
      List lPostulantes = this.mi.getPstListarPostulantesDip(datosPostulante);
      modelo.put("lPostulantes", lPostulantes);
      modelo.put("gestion", sGestion);
      modelo.put("periodo", sPeriodo);
      modelo.put("id_programa", sId_programa);
      return new ModelAndView("inscripcionMaterias/postulantes/ListarDatosPostulantes", modelo);
    }
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosPostulante = new Postulantes();
      datosPostulante.setNombres(sNombres);
      datosPostulante.setId_programa(Integer.parseInt(sId_programa));
      List lPostulantes = this.mi.getPstListarPostulantesNombres(datosPostulante);
      modelo.put("lPostulantes", lPostulantes);
      modelo.put("gestion", sGestion);
      modelo.put("periodo", sPeriodo);
      modelo.put("id_programa", sId_programa);
      return new ModelAndView("inscripcionMaterias/postulantes/ListarDatosPostulantes", modelo);      
    }
    
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_programa", sId_programa);
    return new ModelAndView("inscripcionMaterias/postulantes/ListarDatosPostulantes", modelo);
    
  }
}
