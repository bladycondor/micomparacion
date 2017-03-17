package org.fautapo.web.reportesAcademicos.certificadoAlumnoInscrito;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
//segundo aumentado
import java.util.ArrayList;
import java.util.regex.*;
import javax.swing.*;
//segundo aumentado

import java.lang.Boolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Personas;

import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class AlumnoInscrito implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

   // int iId_estudiante = 0; int iGestion = 0; int iPeriodo = 0;
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    

    String sId_estudiante = request.getParameter("id_estudiante");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
  modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);


//el otro
    String sNombres = cliente.getNombres();
    modelo.put("usuario", sNombres);


  //el otro
    /*try {
      iId_estudiante = Integer.parseInt(sId_estudiante);
      iGestion = Integer.parseInt(sGestion);
      iPeriodo = Integer.parseInt(sPeriodo);
    }
    catch(Exception e) {
      return new ModelAndView("reportesAcademicos/certificadoAlumnoInscrito/Entrada", "cliente", cliente);      
    }

    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
*/

/*anterior
    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    if (datosEstudiante == null) {
      return new ModelAndView("Error", "mensaje", "El RU que ingreso no existe");
    }
*/
if (Integer.parseInt(sId_estudiante) > 0) {
       //Busco datos del Estudiante
      Estudiantes datosEst = new Estudiantes();
      datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
	  //datosEst.setIns_sede(cliente.getId_almacen());
      datosEst = this.mi.getEstBuscarEstudianteNombres(datosEst);
	  if (datosEst==null){
	  return new ModelAndView("Error", "mensaje", "El Estudiante no pertenece a su Area. Verique");
	  }
      modelo.put("datosEst",datosEst);

      System.out.println ("MOSTRANDO AVER QUE SALE "  +datosEst.getPrograma());
      //SACO EL id_persona 
      int iId_persona = datosEst.getId_persona();
      System.out.println("VER ID-PERSONA---->" + iId_persona);
      //fin de la sacada       

   
     //para cortar la carrera
     String sCarrera = datosEst.getPrograma();
     String[] sCortarcadena = sCarrera.split("\\(");
     String sCarreracortada = sCortarcadena[0];
    
     modelo.put ("sCarreracortada",sCarreracortada); 
     //fin de la cortada jajaja

      //Verificar Si la carrera es Anual o Semestral
      //si el id_periodo de fcl_programas  es  2 la carrera es anual     1 es semestral
      //buscamos el periodo
       Programas  buscarPeriodo = new Programas();
       buscarPeriodo.setId_programa(datosEst.getId_programa());
       buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);
       modelo.put("id_periodo",Integer.toString(buscarPeriodo.getId_periodo()));
          
       System.out.println("el periodo que tiene fcl_programas---->" + buscarPeriodo.getId_periodo());
     
    
     //PARA ANUAL
    if (buscarPeriodo.getId_periodo()==2)
    {
 //Verificando si tiene matricula para el periodo y gestion actual        
      Estudiantes datosMatricula = new Estudiantes();
      datosMatricula.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosMatricula.setGestion(Integer.parseInt(sGestion));    
      datosMatricula.setPeriodo(1);  //----> OJO estatico el periodo para anual    
      datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
      
      if (datosMatricula == null){
        return new ModelAndView ("Aviso", "mensaje", "El estudiante con R.U." +sId_estudiante+ "no esta matriculado para la gestion" +sGestion+ "y periodo"+sPeriodo);
     }
      if("B".equals(datosMatricula.getId_estado())){
        return new ModelAndView ("Aviso", "mensaje", "La matricula del estudiante con R.U." +sId_estudiante+ "esta bloqueada");
   }
}
//para SEMESTRAL
 else {

     //Verificando si tiene matricula para el periodo y gestion actual        
      Estudiantes datosMatricula = new Estudiantes();
      datosMatricula.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosMatricula.setGestion(Integer.parseInt(sGestion));    
      datosMatricula.setPeriodo(Integer.parseInt(sPeriodo));    
      datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
      
      if (datosMatricula == null){
        return new ModelAndView ("Aviso", "mensaje", "El estudiante con R.U." +sId_estudiante+ "no esta matriculado para la gestion" +sGestion+ "y periodo"+sPeriodo);
     }
      if("B".equals(datosMatricula.getId_estado())){
        return new ModelAndView ("Aviso", "mensaje", "La matricula del estudiante con R.U." +sId_estudiante+ "esta bloqueada");
     }
 
 
} 
 
 
     //BUSCAR GRADO ACADEMICO
     Libretas datosGrados = new Libretas();
     datosGrados.setId_programa(datosEst.getId_programa());
     datosGrados.setId_plan(datosEst.getId_plan());
     datosGrados = this.mi.getBuscarGradoAcademicoPrograma(datosGrados);
     modelo.put("datosGrados", datosGrados);
    
    
    //SACAMOS FECHA DE INGRESO A LA UAB
      Estudiantes ingresoU = new Estudiantes();
       ingresoU.setId_persona((iId_persona));
     // ingresoU.setId_persona(Integer.parseInt(sId_persona));
      ingresoU = this.mi.getListarIngresoUAB(ingresoU);
      modelo.put("ingresoU",ingresoU);
    
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

      //Sacamos el formato de la hora
      formatoFecha.setCampo("formato_hora");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoFecha));  
      
      //aumentado  VER si es periodo_siguiente o gestion_siguiente VA  A SER mi, si es periodo, gestion VA A SER coimata 
      //Sacamos el formato de la gestion actual o siguiente hay que revisar shiiiiiiiiiiiiii
    
//  Abm GP = new Abm();
       
  //    GP.setCampo("gestion");
    //  GP.setCodigo("coimata");
      //modelo.put("gestion", this.mi.getDibBuscarParametro(GP));  
     
      
      //Sacamos el formato del periodo actual o siguiente hay que revisar shiiiiiiiiiiiiii
     // Abm XX = new Abm();
      //GP.setCampo("periodo");
      //GP.setCodigo("coimata");
      //modelo.put("periodo", this.mi.getDibBuscarParametro(GP));  
          
      return new ModelAndView("reportesAcademicos/certificadoAlumnoInscrito/SalidaImpresionCertificado", modelo);      
    }
    else{
      return new ModelAndView("Aviso","mensaje","No ingreso el R.U. del estudiante");
    }  
    
  }
}






















