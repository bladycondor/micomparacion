package org.fautapo.web.reportesAcademicos.impresionAdmisionPostulantes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class MostrarSolicitudCambioPrograma implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    Postulantes datosPostulante;
    //Recuperando variables del jsp
    String sId_postulante = request.getParameter("id_postulante");
    String sFacultad_origen = request.getParameter("facultad_origen");
    String sPrograma_origen = request.getParameter("programa_origen");
    String sFec_solicitud = request.getParameter("fec_solicitud");
    
    int iId_programa_origen = cliente.getInt(request, "id_programa_origen");
    int iId_facultad_origen = cliente.getInt(request, "id_facultad_origen");
    
    if ("".equals(sId_postulante))
    {  
       modelo.put("gestion", Integer.toString(cliente.getGestion()));
       modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
       return new ModelAndView("reportesAcademicos/impresionAdmisionPostulantes/EntradaBuscarPostulantes",modelo);
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
      //datosPostulante.setId_programa(Integer.parseInt(sId_programa));	
      datosPostulante = this.mi.getPstBuscarPostulanteNombres(datosPostulante);
      modelo.put("datosPostulante",datosPostulante);
      if(datosPostulante == null){
        return new ModelAndView("Aviso","mensaje","No existe el  R.P.  "+ sId_postulante);
      }
      
      if((datosPostulante.getId_tipo_admision() != 4) && (datosPostulante.getId_tipo_admision() != 10)) { 
        return new ModelAndView("Aviso", "mensaje", "Pertenece a otro tipo de admision. Revise los datos.");
      }
      	
      //Sacamos los datos de la institucion
      Instituciones datosInstitucion = new Instituciones();
      datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
      datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
      if (datosInstitucion !=null) {
        modelo.put("datosInstitucion", datosInstitucion);
      }
      //Sacamos el formato de la fecha
      Abm formatoFecha = new Abm();
      formatoFecha.setCampo("formato_fecha");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
      //Fin Institucion
      //Sacamos los datos de la Facultad
      Facultades datosFacultad = new Facultades();
      datosFacultad.setId_facultad(iId_facultad_origen);
      datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
      modelo.put("datosFacultad", datosFacultad);
      
      //Sacando el programa en que esta el estudiante
      Programas datosPrograma = new Programas();
      datosPrograma.setId_programa(iId_programa_origen);
      datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
      modelo.put("datosPrograma", datosPrograma);
      
      //Modifcamos el id_programa_anterior postulante
      datosPostulante.setId_postulante(datosPostulante.getId_postulante());
      datosPostulante.setId_programa_ant(iId_programa_origen);
      datosPostulante.setId_rol(cliente.getId_rol());
      datosPostulante.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setPstRegistrarProgramaAnterior(datosPostulante);
    }
    
    return new ModelAndView("reportesAcademicos/impresionAdmisionPostulantes/ImprimirSolicitudAdminEspProgramaPostulante",modelo);	
  }
}
