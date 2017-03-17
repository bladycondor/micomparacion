package org.fautapo.web.administrarConvalidacionManual.autorizarConvalidacion;

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
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2008-05-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2008-05-05
 */


public class RegistrarNotasConvalidacionManual implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Datos del Usuario
    int iId_usuario = cliente.getId_usuario();
    String sUsuario = cliente.getNombres();
    
    //Recuperando variables del jsp
    String sId_programa = request.getParameter("id_programa");
    String sId_convalidacion = request.getParameter("id_convalidacion");
    String sMensaje="";
    String sAccion = request.getParameter("accion");
    Planes datosPlanes = new Planes();
    //Votamos los datos
    modelo.put("id_programa", sId_programa);
    modelo.put("usuario", sUsuario);
    modelo.put("id_usuario", Integer.toString(iId_usuario));
    modelo.put("id_convalidacion", sId_convalidacion);

    if("".equals(sId_programa)) return new ModelAndView("Error","mensaje", "Seleccione el programa");  
    //Buscamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Buscamos la convalidacion
    if((!"".equals(sId_convalidacion)) && (sId_convalidacion != null) && ("Convalidar".equals(sAccion))) {
      //Primero buscar convalidacion
      datosPlanes.setId_convalidacion(Integer.parseInt(sId_convalidacion));
      Planes datosConvalidacion = this.mi.getBuscarConvalidacionManual(datosPlanes);
      modelo.put("datosConvalidacion", datosConvalidacion);
      if(datosConvalidacion != null) {
        Estudiantes datosEstudiante = new Estudiantes();
	datosEstudiante.setId_estudiante(datosConvalidacion.getId_estudiante());
        datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
        modelo.put("datosEstudiante",datosEstudiante);
        if (datosEstudiante == null) {
	  sMensaje="El estudiante con R.U. : "+ Integer.toString(datosConvalidacion.getId_estudiante())+ "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.";
	  modelo.put("mensaje",sMensaje);
	  return new ModelAndView("administrarConvalidacionManual/autorizarConvalidacion/Aviso",modelo);
        }
	//Verificamos si tiene matricula para la gestion y periodo
        Estudiantes datosMatricula = new Estudiantes();
        datosMatricula.setId_estudiante(datosConvalidacion.getId_estudiante());
        datosMatricula.setGestion(datosConvalidacion.getGestion());    
        datosMatricula.setPeriodo(datosConvalidacion.getPeriodo());    
        datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
        if (datosMatricula == null) {
          sMensaje="El estudiante con R.U. "+ Integer.toString(datosConvalidacion.getId_estudiante())+" no esta matriculado para la gestion "+Integer.toString(datosConvalidacion.getGestion())+" y periodo "+Integer.toString(datosConvalidacion.getPeriodo());
	  modelo.put("mensaje",sMensaje);
	 return new ModelAndView("administrarConvalidacionManual/autorizarConvalidacion/Aviso",modelo);
        }
        if ("B".equals(datosEstudiante.getId_estado())) {
	  sMensaje="El estudiante con R.U. "+Integer.toString(datosConvalidacion.getId_estudiante())+" esta bloqueado. El estudiante debe regularizar su situacion.";
	  modelo.put("mensaje",sMensaje);
	  return new ModelAndView("administrarConvalidacionManual/autorizarConvalidacion/Aviso",modelo);
        }
        
	//Verificamos si existe registrado el estudiante tiene registrado en la tabla notas
	Planes datosNotas = new Planes();
	datosNotas.setId_convalidacion(datosConvalidacion.getId_convalidacion());
	datosNotas.setId_estudiante(datosConvalidacion.getId_estudiante());
	List lNotasCruceCnvDetalles = this.mi.getListarNotasCruceCnvDetalles(datosNotas);
	if(lNotasCruceCnvDetalles.size() > 0){
	  sMensaje="El estudiante con R.U. : "+ Integer.toString(datosConvalidacion.getId_estudiante()) + "no puede realizar la convalidacion por que tiene materias registradas ya con notas. Como se muestra en el siguiente detalle: ";
	  modelo.put("mensaje",sMensaje);
	  modelo.put("lNotasCruceCnvDetalles",lNotasCruceCnvDetalles);
	  return new ModelAndView("administrarConvalidacionManual/autorizarConvalidacion/Aviso",modelo);
	}
	//Registramos en la tabla notas
	datosNotas.setId_matricula(datosMatricula.getId_matricula());
	datosNotas.setGestion(datosConvalidacion.getGestion());
	datosNotas.setPeriodo(datosConvalidacion.getPeriodo());
	datosNotas.setNro_resolucion(datosConvalidacion.getNro_resolucion());
	datosNotas.setUlt_usuario(cliente.getId_usuario());
	int iResultado = this.mi.setRegistrarEstNotasConvalidacionManual(datosNotas);
	if(iResultado == 1) {
          sMensaje="Se ha realizado la convalidacion para el estudiante con R.U. "+ Integer.toString(datosConvalidacion.getId_estudiante())+" para la gestion "+Integer.toString(datosConvalidacion.getGestion())+" y periodo "+Integer.toString(datosConvalidacion.getPeriodo());
	  modelo.put("mensaje",sMensaje);
	 return new ModelAndView("administrarConvalidacionManual/autorizarConvalidacion/Aviso",modelo);
        }        
	
      } 
    
    } 
    else{
      return new ModelAndView("Error","mensaje", "No ingreso la Convalidacion");
    }
    
    //Listamos las convalidaciones realizadas por el usuario
    datosPlanes.setId_programa(datosPrograma.getId_programa());
    datosPlanes.setUlt_usuario(cliente.getId_usuario());
    List lConvalidacionManualPrograma  = this.mi.getListarConvalidacionManualPrograma(datosPlanes);
    modelo.put("lConvalidacionManualPrograma", lConvalidacionManualPrograma);

    
    return new ModelAndView("administrarConvalidacionManual/autorizarConvalidacion/ListarConvalidacionesPrograma", modelo);
  }
}
