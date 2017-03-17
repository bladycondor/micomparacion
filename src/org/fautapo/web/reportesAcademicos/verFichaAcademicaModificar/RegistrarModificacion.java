package org.fautapo.web.reportesAcademicos.verFichaAcademicaModificar;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Notas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class RegistrarModificacion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Map modelo = new HashMap();

    int iId_programa = cliente.getInt(request, "id_programa");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    int Nnota = cliente.getInt(request, "nota");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
	String Iid_estado = request.getParameter("id_estado");
	String SObservacion = request.getParameter("observacion");
	
	int iId_estudiante = cliente.getInt(request, "id_estudiante");
 	int _yabe = cliente.getInt(request, "_yabe"); 
	
	
	
    modelo.put("id_programa", Integer.toString(iId_programa));
    modelo.put("gestion", Integer.toString(iGestion));
	modelo.put("periodo", Integer.toString(iPeriodo));
	modelo.put("nota", Integer.toString(Nnota));
    modelo.put("id_tipo_evaluacion", Integer.toString(iId_tipo_evaluacion));
    modelo.put("id_estado",Iid_estado);
    modelo.put("observacion",SObservacion);
	
	//Registramos la rectificacion de la Nota
    Notas datosRectificacion = new Notas();
    datosRectificacion.setId_nota(_yabe);
    datosRectificacion.setGestion(iGestion);
    datosRectificacion.setPeriodo(iPeriodo);
    datosRectificacion.setNota(Nnota);
    datosRectificacion.setId_tipo_evaluacion(iId_tipo_evaluacion);
	datosRectificacion.setId_estado(Iid_estado);
    datosRectificacion.setObservacion(SObservacion);		  
    datosRectificacion.setUlt_usuario(cliente.getId_usuario());
	datosRectificacion.setId_rol(cliente.getId_rol());
    int iResultado = this.mi.setRegistrarRectificacionNota(datosRectificacion);
    if (iResultado == 0) {
      modelo.put("mensaje", "La rectificacion no pudo realizarse");
      return new ModelAndView("Error", modelo);
    }

	//Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
         
    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
       modelo.put("datosInstitucion", datosInstitucion);
    }

    Notas lFichaAcademica = new Notas(); 
	lFichaAcademica.setId_nota(_yabe);	 
    lFichaAcademica = this.mi.getEstListarFichaAcademicaBuscar(lFichaAcademica);
	if (lFichaAcademica == null) {
       return new ModelAndView("Error", "mensaje", "No encuentra el registro de la asignatura");
    }
    modelo.put("lFichaAcademica", lFichaAcademica);
	modelo.put("nombres", cliente.getNombres());
	
    return new ModelAndView("reportesAcademicos/verFichaAcademicaModificar/Distro", modelo);
  }
}