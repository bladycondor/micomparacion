package org.fautapo.web.administrarRecibos.eliminarRecibos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Literales;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarConceptosImpresion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();
    modelo.put("cliente", cliente);

    int iId_transaccion = cliente.getInt(request, "id_transaccion");
    //Sacamos los datos para la impresion del RECIBO
    //Sacamos los datos de la transaccion
    Perfiles datosTransaccion = new Perfiles();
    datosTransaccion.setId_transaccion(iId_transaccion);
    datosTransaccion = this.mi.getTrnBuscarTransaccion(datosTransaccion);
    modelo.put("datosTransaccion", datosTransaccion);

    Literales literal = new Literales();
    modelo.put("literal", literal.convert(datosTransaccion.getPagado()));

    //Sacamos el listado de trn_detalles
    modelo.put("lDetalles", this.mi.getTrnListarTrnDetalles(datosTransaccion));

    // Datos de la Persona
    if (datosTransaccion.getId_persona() > 0) {
      if (datosTransaccion.getId_programa() > 0) {
        Estudiantes estudiante = new Estudiantes();
        estudiante.setId_persona(datosTransaccion.getId_persona());
        estudiante.setId_programa(datosTransaccion.getId_programa());
	estudiante = this.mi.getMiPrsBuscarEstudiante(estudiante);
	estudiante = this.mi.getEstBuscarEstudianteNombres(estudiante);
        modelo.put("estudiante", estudiante);
      } else {
        Personas persona = new Personas();
        persona.setId_persona(datosTransaccion.getId_persona());
        persona = this.mi.getPrsBuscarPersona(persona);
        modelo.put("estudiante", persona);
      }
    } else if (datosTransaccion.getId_persona_pst() > 0) {
      Postulantes postulante = new Postulantes();
      postulante.setId_persona(datosTransaccion.getId_persona_pst());
      if (datosTransaccion.getId_programa() > 0) {
        postulante.setId_programa(datosTransaccion.getId_programa());
	postulante = this.mi.getMiPrsBuscarPostulante(postulante);
        postulante = this.mi.getPstBuscarPostulanteNombres(postulante);
      } else
        postulante = this.mi.getPstBuscarPersona(postulante);
      modelo.put("estudiante", postulante);
    }
    // fin - Datos de la Persona

    //Sacamos los datos del perfil
    Perfiles datosPerfil = new Perfiles();
    datosPerfil.setId_perfil(datosTransaccion.getId_perfil());
    datosPerfil = this.mi.getPrfBuscarPerfil(datosPerfil);
    modelo.put("datosPerfil", datosPerfil);

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos el formato de la hora
    formatoFecha.setCampo("formato_hora");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos los datos de la institucion
    Instituciones institucion = new Instituciones();
    institucion.setId_institucion(1); //--------------------------ESTATICO
    institucion = this.mi.getBuscarInstitucion(institucion);
    if (institucion != null) {
      modelo.put("institucion", institucion.getInstitucion());
      modelo.put("logo", institucion.getLogo());
      modelo.put("actividad", institucion.getActividad());
    }
    return new ModelAndView("administrarRecibos/eliminarRecibos/ListarConceptosImpresion", modelo);
  }
}