package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
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
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");
    String sRu     = cliente.getString(request, "ru");
    String sNombres  = cliente.getString(request, "nombre");
    String sBotonRu = request.getParameter("botonRu");
    String sBotonNombre = request.getParameter("botonNombre");
    
    if ("".equals(sRu) && "".equals(sNombres))
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguos//EntradaBuscarEstudiantes");
    Map modelo = new HashMap();
    
    List listaEstudiantes;
    Estudiantes datosEstudiante = new Estudiantes();
    if (!"".equals(sRu)) {
      //Ver si esta maticulado
      datosEstudiante.setId_estudiante(Integer.parseInt(sRu));
      datosEstudiante.setGestion(cliente.getGestion());
      datosEstudiante.setPeriodo(cliente.getPeriodo());
      Estudiantes verMatricula = this.mi.getMtrBuscarMatricula(datosEstudiante);
      //Buscar al estudiante
      datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
      if( verMatricula != null){
        datosEstudiante.setId_estado("M");
      }
      modelo.put("datosEstudiante", datosEstudiante);
      //Verificar si esta matriculado para la gestion actual
    }
    else {
      if (!"".equals(sNombres)) {
        System.out.println("El ENTRAR NOMBRES-->"+ sNombres);
        datosEstudiante.setNombres(sNombres);
	System.out.println("El setNombres() que entra-->"+ datosEstudiante.getNombres());
        datosEstudiante.setId_programa(cliente.getId_programa());  //cambiar esto
	System.out.println("El setId_programa() que Entra -->"+ datosEstudiante.getId_programa());
	
        List lEstudiantes = this.mi.getEstListarEstudiantesNombres(datosEstudiante);
	System.out.println("El tamanio de la lista PRIMERO lEstudiantes-->"+ Integer.toString(lEstudiantes.size()));
        Estudiantes verEstudiante = new Estudiantes();
	int iHasta =lEstudiantes.size();
        for(int i=0; i < iHasta; i++) {
	  System.out.println("Entra aquisito"+i);
          verEstudiante =(Estudiantes) lEstudiantes.get(i);
	  int iId_estudiante = verEstudiante.getId_estudiante();
	  verEstudiante.setGestion(cliente.getGestion());
	  verEstudiante.setPeriodo(cliente.getPeriodo());
	  Estudiantes verMatricula = this.mi.getMtrBuscarMatricula(verEstudiante);
          if( verMatricula != null) {
            verEstudiante.setId_estado("M");
	    lEstudiantes.add(verEstudiante);
          }	
	}  
        modelo.put("lEstudiantes", lEstudiantes);
	System.out.println("El tamanio de la lista lEstudiantes-->"+ Integer.toString(lEstudiantes.size()));
      }
    }
    //Para wayka
    modelo.put("id_proceso", cliente.getString(request, "id_proceso"));
    modelo.put("titulo", cliente.getString(request, "titulo"));
    modelo.put("id_tramite", cliente.getString(request, "id_tramite"));
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("nombre", sNombres);
    modelo.put("nombre", sRu);
    
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguos/ListarEstudiantes", modelo);
  }
}