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
import org.fautapo.domain.Materias;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class ListarPostulantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    Postulantes datosPostulante;
    //Recuperando variables del jsp
    String sId_postulante = request.getParameter("id_postulante");
    String sNombres = request.getParameter("nombres");
    String sDip = request.getParameter("dip");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    
    
    if ("".equals(sId_postulante) && ("".equals(sNombres))  && ("".equals(sDip)))
    {  
       modelo.put("gestion", sGestion);
       modelo.put("periodo", sPeriodo);
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
	  
	  //Para determinar si aprobo o reprobo el estudiante
      if(!"A".equals(datosPostulante.getId_estado())) return new ModelAndView("Aviso", "mensaje", "El postulante no esta Aprobado/Habilitado");
      
      //Sacamos el listado del plan por id_tipo_grado
      Planes datosPlan = new Planes();
      datosPlan.setId_programa(datosPostulante.getId_programa());
      datosPlan.setId_plan(datosPostulante.getId_plan());
      datosPlan.setId_tipo_grado(datosPostulante.getId_tipo_grado());
      List lMateriasPlanTipoGrado = this.mi.getListarMateriasPlanTipoGrado(datosPlan);
      modelo.put("lMateriasPlanTipoGrado", lMateriasPlanTipoGrado);
      //System.out.println("Tamanio de la lista  Certificado-->"+Integer.toString(lMateriasPlanTipoGrado.size()));
      
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
 
      if((datosPostulante.getId_tipo_admision() == 1) || (datosPostulante.getId_tipo_admision() == 2)|| (datosPostulante.getId_tipo_admision() == 22)) {
        //Vemos si el postulante esta habilitado
    //    if(datosPostulante.getId_estado() != "A") return new ModelAndView("Aviso", "mensaje", "El postulante no esta Aprobado/Habilitado");
       return new ModelAndView("reportesAcademicos/impresionAdmisionPostulantes/ImprimirCertificadoHabilitacionPostulante",modelo);
      } 
      if((datosPostulante.getId_tipo_admision() == 4) || (datosPostulante.getId_tipo_admision() == 10)) {
	//Listando Facultades
        Universidades datosUniversidad = new Universidades();
        datosUniversidad.setId_universidad(cliente.getId_universidad());
        List lFacultades = this.mi.getUnvListarFacultades(datosUniversidad);
        modelo.put("lFacultades", lFacultades);
        //Sacamos el listado de los programas
        List lProgramas = this.mi.getUnvListarProgramas(datosUniversidad);    
        modelo.put("lProgramas", lProgramas);
        //Listar Plan del programa actual
	Planes datoPlan = new Planes();
	datoPlan.setId_programa(datosPostulante.getId_programa());
        List lPlanesActual= this.mi.getListarPrgPlanesActual(datoPlan);
        modelo.put("lPlanesActual",lPlanesActual);
       
        return new ModelAndView("reportesAcademicos/impresionAdmisionPostulantes/PreDatosImprimirSolicitudAdminEspProgramaPostulante",modelo);	    
        //return new ModelAndView("reportesAcademicos/impresionAdmisionPostulantes/ImprimirSolicitudAdminEspProgramaPostulante",modelo);	
      }	
      
      if((datosPostulante.getId_tipo_admision() > 2) && (datosPostulante.getId_tipo_admision() != 4) && (datosPostulante.getId_tipo_admision() != 10)) 
        return new ModelAndView("reportesAcademicos/impresionAdmisionPostulantes/ImprimirSolicitudAdmisionEspecialPostulante",modelo);
        
      //return new ModelAndView("reportesAcademicos/impresionAdmisionPostulantes/ImprimirDatosPostulante",modelo);
    }
    
    //Si la busqueda es por CI
    if (!"".equals(sDip)) {
      datosPostulante = new Postulantes();
      datosPostulante.setDip(sDip);
      List lPostulantes = this.mi.getMiListarPostulantesDip(datosPostulante);
      modelo.put("lPostulantes", lPostulantes);
    }
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosPostulante = new Postulantes();
      datosPostulante.setNombres(sNombres);
      List lPostulantes = this.mi.getMiListarPostulantesNombre(datosPostulante);
      modelo.put("lPostulantes", lPostulantes);
    }
    
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    return new ModelAndView("reportesAcademicos/impresionAdmisionPostulantes/ListarDatosPostulantes",modelo);
  }
}
