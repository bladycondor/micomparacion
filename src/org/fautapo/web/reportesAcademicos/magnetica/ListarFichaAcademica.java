package org.fautapo.web.reportesAcademicos.magnetica;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarFichaAcademica implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;    
    //Recuperando variables del jsp
    String sId_persona    = request.getParameter("id_persona");
    int iRegistro;
    int iId_persona;         
    //int iId_persona_entero;
    modelo.put("id_persona",sId_persona);
    try{         
        iRegistro = Integer.parseInt(request.getParameter("registro"));
        String arreglo[] = sId_persona.split(" ");
        sId_persona =  arreglo[0];
        String ssId_persona = sId_persona.substring(1);    	    
	iId_persona = Integer.parseInt(ssId_persona);  
	//iId_persona_entero = Integer.parseInt(request.getParameter("id_persona_entero"));
	//modelo.put("id_persona",Integer.toString(iId_persona));
      } catch(Exception e) {
      modelo.put("registro","0");
      modelo.put("mensaje","IDENTIFICADOR NO VALIDO");
      return new ModelAndView("reportesAcademicos/magnetica/Entrada", modelo);
    }  

    datosEstudiante = new Estudiantes();
    datosEstudiante.setId_persona(iId_persona);
    int cnt_registros = this.mi.getBuscarEstudiantePersona(datosEstudiante).size();
    if(cnt_registros!=0){

    
    if(iRegistro<cnt_registros){
        datosEstudiante = (Estudiantes)this.mi.getBuscarEstudiantePersona(datosEstudiante).get(iRegistro);    
	}
    else{
	iRegistro = 0;	
	datosEstudiante = (Estudiantes)this.mi.getBuscarEstudiantePersona(datosEstudiante).get(iRegistro);      
	}
      //Mostramos la imagen del estudiante, siempre sera uno por imagen cargada
      Estudiantes imagenEst = new Estudiantes();
      imagenEst.setId_estudiante(datosEstudiante.getId_estudiante());
      imagenEst.setId_estado("I");
      List lImagenes = this.mi.getListarAdjuntosEstudiante(imagenEst);
      modelo.put("lImagenes", lImagenes);	
    }else{
      modelo.put("mensaje","IDENTIFICADOR NO VALIDO");
      return new ModelAndView("reportesAcademicos/magnetica/Entrada", modelo);    
    }	
    datosEstudiante = this.mi.getEstBuscarEstudianteAccesos(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);      
    
    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);    
    
    //Listamos la ficha academica del estudiante
    List lFichaAcademica = this.mi.getEstListarFichaAcademica(datosEstudiante);
    modelo.put("lFichaAcademica", lFichaAcademica);
    
    //Listamos la ficha academica convalidada del estudiante
    List lFichaAcademicaConvalidada = this.mi.getEstListarFichaAcademicaConvalidada(datosEstudiante);
    modelo.put("lFichaAcademicaConvalidada", lFichaAcademicaConvalidada);
    //Sacamos los datos del Estudiante
    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante2", datosEstudiante);      
    iRegistro++;      
    if(cnt_registros==iRegistro) modelo.put("registro","0");
    else 
    modelo.put("registro",Integer.toString(iRegistro));
    return new ModelAndView("reportesAcademicos/magnetica/ListarFichaAcademica", modelo);
  }
}