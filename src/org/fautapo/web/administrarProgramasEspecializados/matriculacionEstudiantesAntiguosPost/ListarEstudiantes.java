package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguosPost;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
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

public class ListarEstudiantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");
    String sRu     = cliente.getString(request, "ru");
    String sBotonRu = request.getParameter("botonRu");
    
    if ("".equals(sRu))
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosPost/EntradaBuscarEstudiantes");
    
    List listaEstudiantes;
    Estudiantes datosEstudiante = new Estudiantes();
    if (!"".equals(sRu) && (sRu != null)) {
      //Ver si esta maticulado
      datosEstudiante.setId_estudiante(Integer.parseInt(sRu));
      datosEstudiante.setGestion(cliente.getGestion());
      datosEstudiante.setPeriodo(cliente.getPeriodo());
      Estudiantes verMatricula = this.mi.getMtrBuscarMatricula(datosEstudiante);

      //Buscar al estudiante
      datosEstudiante = this.mi.getEstBuscarEstudiantePrsPos(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
   
	  //Sacamos si el estudiante no tiene deudas pendientes
	  Estudiantes datosDeuda = new Estudiantes();
      datosDeuda.setId_estudiante(Integer.parseInt(sRu));	
      datosDeuda = this.mi.getBuscarUltimaEstDeuda(datosDeuda);	

   	 
      if (datosEstudiante == null) return new ModelAndView("Error","mensaje", "El estudiante con R.U. "+sRu+" no existe en Vicerectorado de Postgrado.");      
	  if(datosDeuda !=null) { return new ModelAndView("Aviso", "mensaje", "El Estudiante con R.U.= "+ sRu+" tiene deudas de : "+datosDeuda.getTipo_deuda()+" pasar por Registros e Inscripciones ");}
   
      //Listar PrsDocumentos y Prs_compromisos
      Personas documento = new Personas();
      documento.setId_persona(datosEstudiante.getId_persona());
      List lPrsCompromisosTodo= this.mi.getListarPrsCompromisosPersona(documento);
      modelo.put("lPrsCompromisosTodo",lPrsCompromisosTodo);
      List lPrsDocumentosTodo= this.mi.getListarPrsDocumentosPersona(documento);
      modelo.put("lPrsDocumentosTodo",lPrsDocumentosTodo);
      //Listar el ultimo est_regularizacion
      Estudiantes lUltimoEstRegularizacion= this.mi.getMiBuscarUltimoEstRegularizacion(datosEstudiante);
      modelo.put("lUltimoEstRegularizacion",lUltimoEstRegularizacion);
    }

    //Para wayka
    modelo.put("id_proceso", cliente.getString(request, "id_proceso"));
    modelo.put("titulo", cliente.getString(request, "titulo"));
    modelo.put("id_tramite", cliente.getString(request, "id_tramite"));
    
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosPost/ListarEstudiantes", modelo);
  }
}