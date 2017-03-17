package org.fautapo.web.rectificacionDeNotas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Notas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */

public class RegistrarRectificacion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Recuperando variables del jsp
    int iId_nota = cliente.getInt(request, "id_nota");
    int iId_estudiante = cliente.getInt(request, "id_estudiante");
    int iId_matricula = cliente.getInt(request, "id_matricula");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    int iId_materia = cliente.getInt(request, "id_materia");
    int iId_grupo = cliente.getInt(request, "id_grupo");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    int iNota = cliente.getInt(request, "nota");
    String sResolucion = cliente.getString(request, "nro_resolucion");
    
    //Votamos los datos
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    
    //Sacamos los datos de la nota
    Notas datosNota = new Notas();
    datosNota.setId_nota(iId_nota);
    datosNota = this.mi.getBuscarNota(datosNota);
    modelo.put("datosNota", datosNota);

    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);

    //Registramos la rectificacion
    Notas datosRectificacion = new Notas();
    datosRectificacion.setId_nota(iId_nota);
    datosRectificacion.setId_estudiante(iId_estudiante);
    datosRectificacion.setId_matricula(iId_matricula);
    datosRectificacion.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosRectificacion.setId_materia(iId_materia);
    datosRectificacion.setId_grupo(iId_grupo);
    datosRectificacion.setNota(iNota);
    datosRectificacion.setGestion(iGestion);
    datosRectificacion.setPeriodo(iPeriodo);
    datosRectificacion.setObservacion(sResolucion);
    datosRectificacion.setUlt_usuario(cliente.getId_usuario());
    int iResultado = this.mi.setRegistrarRectificacion(datosRectificacion);
    if (iResultado == 0) {
      modelo.put("mensaje", "La rectificacion no pudo realizarse");
      return new ModelAndView("Error", modelo);
    }

    //Sacando los datos personales del Estudiante encontrado
    Personas datosPersona = new Personas();
    datosPersona.setId_persona(datosEstudiante.getId_persona());
    datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
    modelo.put("datosPersona", datosPersona);
    
    //Listamos las materias a rectificar
    datosNota = new Notas();
    datosNota.setId_estudiante(iId_estudiante);
    datosNota.setGestion(iGestion);
    datosNota.setPeriodo(iPeriodo);
    List lNotas = this.mi.getListarNotasRectificar(datosNota);
    modelo.put("lNotas", lNotas);
    
    return new ModelAndView("rectificacionDeNotas/ListarNotas", modelo);
  }
}