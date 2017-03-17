package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesNuevos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarPostulantes implements Controller {
 
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    
    List lPostulantes;
    List lEstudiantes;
    Estudiantes buscarEst = new Estudiantes();
    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");
    String sNombre  = (request.getParameter("nombre")).trim();
    String sDip     = (request.getParameter("dip")).trim();
    String sId_tipo_admision_entrada = request.getParameter("id_tipo_admision_entrada");
    
    System.out.println("Entrada Gestion"+sGestion);
    System.out.println("Entrada Periodo"+sPeriodo);
    System.out.println("Nombre --> "+sNombre);
    System.out.println("DIP --> "+sDip);
    System.out.println("Id_tipo_admision_entrada --> "+sId_tipo_admision_entrada);
    
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("nombre", sNombre);
    modelo.put("dip", sDip);
    modelo.put("id_tipo_admision_entrada", sId_tipo_admision_entrada);
    
    //Para wayka
    modelo.put("id_proceso", cliente.getString(request, "id_proceso"));
    modelo.put("titulo", cliente.getString(request, "titulo"));
    modelo.put("id_tramite", cliente.getString(request, "id_tramite"));
    
    //Verificamos el ingreso del tipo de admision
    if((!"".equals(sId_tipo_admision_entrada))  && (!"".equals(sGestion)) && (!"".equals(sPeriodo)) && ((!"".equals(sDip)) || (!"".equals(sNombre)))) {
      
      //Buscar Tipo de admision
      buscarEst.setId_tipo_admision(Integer.parseInt(sId_tipo_admision_entrada));
      Estudiantes datosTipoAdm = this.mi.getBuscarTipoAdmision(buscarEst);
      modelo.put("datosTipoAdm",datosTipoAdm);
      
      //Verificando si el tipo de admison es por Carrera Paralela o Por traspaso de carreras
      if(("4".equals(sId_tipo_admision_entrada)) || ("10".equals(sId_tipo_admision_entrada)) || ("3".equals(sId_tipo_admision_entrada)) || ("18".equals(sId_tipo_admision_entrada)))  {
        //Listamos de estudiantes
        if ("".equals(sDip)) {
         buscarEst.setNombres(sNombre);
         lEstudiantes = this.mi.getEstListarEstudiantesNombres2(buscarEst);
        } 
	else {
          buscarEst.setDip(sDip);
          lEstudiantes = this.mi.getEstListarEstudiantesDip2(buscarEst);
        }
        modelo.put("lEstudiantes", lEstudiantes);
            
        return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesNuevos/ListarEstudiantes", modelo);
      
      }
      else {
        //Listamos de la tabla postulantes
        Postulantes postulante = new Postulantes();
	postulante.setId_tipo_admision(Integer.parseInt(sId_tipo_admision_entrada));
        if ("".equals(sDip)) {
         postulante.setNombres(sNombre);
	 
         lPostulantes = this.mi.getMiListarPostulantesNombreTipoAdm(postulante);
        } 
	else {
          postulante.setDip(sDip);
          lPostulantes = this.mi.getMiListarPostulantesDipTipoAdm(postulante);
        }
        modelo.put("lPostulantes", lPostulantes);
        System.out.println("Tamanio de la lista -->"+Integer.toString(lPostulantes.size()));
        return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesNuevos/ListarPostulantes", modelo);
      }
        
    }
    
    //Listando los tipos
    List lTiposAdmisiones = this.mi.getListarTiposAdmisiones();
    modelo.put("lTiposAdmisiones",lTiposAdmisiones);
    
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesNuevos/EntradaBuscarPostulantes");
    
  }
}