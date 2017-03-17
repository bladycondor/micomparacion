package org.fautapo.web.rectificacionDeNotas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Libretas;
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

public class ConfirmarRectificacion implements Controller {

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
    
    //Votamos los datos
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("id_nota", Integer.toString(iId_nota));
    modelo.put("id_matricula", Integer.toString(iId_matricula));
    modelo.put("id_grupo", Integer.toString(iId_grupo));
    
    //Sacamos los datos de la programacion de materia
    Notas datosNota = new Notas();
    datosNota.setId_nota(iId_nota);
    datosNota = this.mi.getBuscarNota(datosNota);
    modelo.put("datosNota", datosNota);
    
    //Sacamos datos de la materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(iId_materia);
    datosMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("datosMateria", datosMateria);

    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);

    //Sacamos los datos del Tipo Evaluacion
    Libretas datosTipoEvaluacion = new Libretas();
    datosTipoEvaluacion.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEvaluacion = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEvaluacion);
    modelo.put("datosTipoEvaluacion", datosTipoEvaluacion);

    return new ModelAndView("rectificacionDeNotas/ConfirmarRectificacion", modelo);
  }
}
