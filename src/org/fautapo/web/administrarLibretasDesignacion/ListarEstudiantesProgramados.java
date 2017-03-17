package org.fautapo.web.administrarLibretasDesignacion;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;   
import org.fautapo.domain.FormatosNum;   
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-08
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-08
 */

public class ListarEstudiantesProgramados implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String sId_tipo_nota_s = "";
    String sId_programa        = request.getParameter("id_programa");
    String sId_modelo_ahorro   = request.getParameter("id_modelo_ahorro");
    sId_tipo_nota_s            = request.getParameter("id_tipo_nota_s");
    String sNro_nota_s         = request.getParameter("nro_nota_s");
    String sBandera            = request.getParameter("bandera");
    String sId_tipo_grado      = request.getParameter("id_tipo_grado");
    String sAvanzado           = request.getParameter("avanzado");
    //String sId_materia         = request.getParameter("id_materia");
    //String sId_grupo           = request.getParameter("id_grupo");  
    //String sGrupo              = request.getParameter("grupo");  
    //String sId_fase            = request.getParameter("id_fase");
    //String sTipo_evaluacion    = request.getParameter("tipo_evaluacion");
    //String sMateria            = request.getParameter("materia");
    //String sPrograma           = request.getParameter("programa");    
    //String sGestion            = request.getParameter("gestion");
    //String sPeriodo            = request.getParameter("periodo"); 
    //String sId_departamento    = request.getParameter("id_departamento");    
    
    //Nuevo
    int iId_asignacion = cliente.getInt(request,"id_asignacion");
    //Buscamos la asignacion docente
    Asignaciones buscarAsignacion = new Asignaciones();
    buscarAsignacion.setId_asignacion(iId_asignacion);
    Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocente(buscarAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
    if(datosAsignacion == null)
      return new ModelAndView("Error", "mensaje", "No se encontr&oacute; la asignaci&oacute;n docente para la materia");
    String sId_tipo_evaluacion = Integer.toString(datosAsignacion.getId_tipo_evaluacion());
    
    if (("".equals(sId_tipo_nota_s))) {
      String mensaje = "No selecciono ningun items";
      modelo.put("mensaje",mensaje);
      return new ModelAndView("Error",modelo);
    }
    
    //modelo.put("gestion", sGestion);   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    //modelo.put("periodo", sPeriodo);
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_docente",Integer.toString(cliente.getId_usuario()));
    modelo.put("id_rol",Integer.toString(cliente.getId_rol()));    
    modelo.put("id_tipo_grado", sId_tipo_grado);
    modelo.put("nro_nota", sNro_nota_s);
    modelo.put("id_tipo_nota_s", sId_tipo_nota_s);
    modelo.put("nro_nota_s", sNro_nota_s);
    //modelo.put("id_grupo", sId_grupo);
    //modelo.put("grupo", sGrupo);
    //modelo.put("id_fase", sId_fase);
    //modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    //modelo.put("tipo_evaluacion", sTipo_evaluacion);
    //modelo.put("id_modelo_ahorro", sId_modelo_ahorro);
    //modelo.put("id_materia", sId_materia);
    //modelo.put("materia", sMateria);
    //modelo.put("id_programa", sId_programa);
    //modelo.put("programa", sPrograma);
    //modelo.put("avanzado", sAvanzado);
    //modelo.put("id_departamento", sId_departamento);    
    
    //Convertimos a entero los datos necesarios
    //int iGestion = Integer.parseInt(sGestion);
    //int iPeriodo = Integer.parseInt(sPeriodo);
    //int iId_materia = Integer.parseInt(sId_materia);
    //int iId_fase = Integer.parseInt(sId_fase);    
    //int iId_tipo_evaluacion = Integer.parseInt(sId_tipo_evaluacion);    
    //int iId_grupo = Integer.parseInt(sId_grupo);    
    //int iId_modelo_ahorro = Integer.parseInt(sId_modelo_ahorro);
    
    //Sacamos el programa 
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    Programas buscarPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("id_programa", sId_programa);
    modelo.put("programa", buscarPrograma.getPrograma());

    /*
    //Sacamos datos de la materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(iId_materia);
    Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("materia", buscarMateria.getMateria());
    modelo.put("sigla", buscarMateria.getSigla());
    modelo.put("id_departamento", Integer.toString(buscarMateria.getId_departamento()));
    modelo.put("id_materia", sId_materia);
    */
    
    //Sacamos datos de la materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(datosAsignacion.getId_materia());
    Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("sigla", buscarMateria.getSigla());
    modelo.put("id_materia", Integer.toString(buscarMateria.getId_materia()));
    modelo.put("materia", buscarMateria.getMateria());
    //Verificamos si el departamento de la materia coincide con la asignacion
    if(datosAsignacion.getId_departamento() != buscarMateria.getId_departamento())
      return new ModelAndView("Error", "mensaje","Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
    

    //Verificamos si es una materia con modelo ahorro
    //Buscamos la materia modelo_ahorro
    Asignaciones datos = new Asignaciones();
    if (datosAsignacion.getId_modelo_ahorro() > 0) {
      datos.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
      datos.setId_materia(datosAsignacion.getId_materia());
      datos.setId_programa(datosPrograma.getId_programa());
      datos.setGestion(datosAsignacion.getGestion());
      datos.setPeriodo(datosAsignacion.getPeriodo());
      List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
      Asignaciones aux = new Asignaciones();
      for (int i = 0; i < materiaAhorro.size(); i++) {
        aux = (Asignaciones)materiaAhorro.get(i);
        modelo.put("materia_modelo_ahorro", aux.getModelo_ahorro());
      }
    }
    modelo.put("id_modelo_ahorro", Integer.toString(datosAsignacion.getId_modelo_ahorro()));
    
    //Sacamos la fase actual según la asignacion del docente
    Libretas buscarFase = new Libretas();
    buscarFase.setId_fase(datosAsignacion.getId_fase());
    buscarFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    buscarFase.setId_departamento(datosAsignacion.getId_departamento());
    buscarFase.setGestion(datosAsignacion.getGestion());
    buscarFase.setPeriodo(datosAsignacion.getPeriodo());
    Libretas datosFase = this.mi.getLbrBuscarFase(buscarFase);
    if(datosFase.getFase() == null) {
      return new ModelAndView("Aviso","mensaje", "No existen mas fases.");
    }
    modelo.put("fase", datosFase.getFase());
    
    
    //Sacamos la definicion de items de la materia
    Libretas datosEvaluacion = new Libretas();
    datosEvaluacion.setId_materia(datosAsignacion.getId_materia());
    datosEvaluacion.setId_grupo(datosAsignacion.getId_grupo());
    datosEvaluacion.setGestion(datosAsignacion.getGestion());
    datosEvaluacion.setPeriodo(datosAsignacion.getPeriodo());
    datosEvaluacion.setId_departamento(buscarMateria.getId_departamento());
    datosEvaluacion.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosEvaluacion.setId_fase(datosAsignacion.getId_fase());
    datosEvaluacion.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    List lEvaluacion = this.mi.getGrpListarEvaluacionDefinida(datosEvaluacion);
    modelo.put("listaItems", lEvaluacion);
    
    //Sacamos las notas de los estudiantes
    if (sId_tipo_nota_s != null) {
      String elementos[];
      elementos = new String[2];
      elementos = sId_tipo_nota_s.split(":");
      String sId_lbr_tipo_nota = elementos[0];
      String sCantidad = elementos[1];
      modelo.put("cantidad", sCantidad);
      //modelo.put("cantidad", sCantidad);
      int iCantidad = Integer.parseInt(sCantidad);  
      System.out.println("cantidad----l--->" + Integer.toString(iCantidad));
      System.out.println("id_lbr_tipo_nota----l--->" + sId_lbr_tipo_nota);
      //Sacamos el tipo de nota que estamos calificando
      Libretas datosTipoNota = new Libretas();
      datosTipoNota.setId_lbr_tipo_nota(Integer.parseInt(sId_lbr_tipo_nota));
      datosTipoNota = this.mi.getLbrBuscarTipoNota(datosTipoNota);
      int iId_tipo_nota = datosTipoNota.getId_tipo_nota();
      modelo.put("id_tipo_nota", Integer.toString(iId_tipo_nota));
      modelo.put("tipo_nota", datosTipoNota.getTipo_nota());

      //Sacando la lista de estudiantes programados a la materia, evaluacion regular
      if (("1".equals(sId_tipo_grado)) && (!"2".equals(sId_tipo_evaluacion))) {
        System.out.println("entro al if de id_tipo_grado=1 y id_tipo_evaluacion=1----");
        Libretas datosEstProg = new Libretas();
        datosEstProg.setId_materia(datosAsignacion.getId_materia());
        datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
        datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro()); 
        datosEstProg.setGestion(datosAsignacion.getGestion());
        datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
	datosEstProg.setId_fase(datosAsignacion.getId_fase());
	datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	datosEstProg.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
	System.out.println("Id_MATERIA -->"+ Integer.toString(datosEstProg.getId_materia()));
	System.out.println("Id_GRUPO -->"+ Integer.toString(datosEstProg.getId_grupo()));
	System.out.println("Id_MODELO_AHORRO -->"+ Integer.toString(datosEstProg.getId_modelo_ahorro()));
	System.out.println("GESTION -->"+ Integer.toString(datosEstProg.getGestion()));
	System.out.println("PERIODO -->"+ Integer.toString(datosEstProg.getPeriodo()));
	System.out.println("Id_FAS -->"+ Integer.toString(datosEstProg.getId_fase()));
	System.out.println("Id_TIPO_EVALUCACION -->"+ Integer.toString(datosEstProg.getId_tipo_evaluacion()));
	System.out.println("Id_tipo_nota -->"+ Integer.toString(datosTipoNota.getId_tipo_nota()));
        List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
        System.out.println("lista de estudiantes----->" + lEstudiantes.size());
	
	Libretas libreta = new Libretas();
        for (int i=0; i<lEstudiantes.size(); i++) {
          libreta = (Libretas)lEstudiantes.get(i);
          int iId_estudiante = libreta.getId_estudiante();
          //Obtenemos las notas de cada uno de los estudiantes por la FASE que ingreso
	  Libretas datosNotas = new Libretas();
	  datosNotas.setId_estudiante(iId_estudiante);
	  datosNotas.setId_grupo(datosAsignacion.getId_grupo());
	  datosNotas.setId_materia(datosAsignacion.getId_materia());
	  datosNotas.setId_tipo_nota(datosTipoNota.getId_tipo_nota());   //Esta es para el metodo getEstListarNotaEstudiante
	  datosNotas.setId_fase(datosAsignacion.getId_fase());
	  datosNotas.setGestion(datosAsignacion.getGestion());
	  datosNotas.setPeriodo(datosAsignacion.getPeriodo());
	  datosNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	  datosNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
	  int _iNota_final = this.mi.getEstSumarNotasEstudianteEvalRegular(datosNotas);  //Contiene la suma de la nota del Estudiante por fase
	  modelo.put("nota_final" , Integer.toString(_iNota_final));
          System.out.println("id_estudiante----->" + iId_estudiante);
	  System.out.println("NOTA_FINAL----->" + Integer.toString(_iNota_final));
	  
	  //Sacamos la nota de aprobacion de la fase
          Libretas datosFase2 = new Libretas();
          datosFase2.setId_fase(datosAsignacion.getId_fase());
          datosFase2.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	  datosFase2.setId_departamento(datosAsignacion.getId_departamento());
	  datosFase2.setGestion(datosAsignacion.getGestion());
          datosFase2.setPeriodo(datosAsignacion.getPeriodo());
          Libretas buscarFase2 = this.mi.getLbrBuscarFase(datosFase2);
	  
	  FormatosNum formatoNum = new FormatosNum();
	  String nNota_aprobacion = formatoNum.parseDecimal(buscarFase2.getNota_aprobacion(), 7);        
	  System.out.println("_iNota_final-->"+Integer.toString(_iNota_final));
	  if (_iNota_final == 0) {
            List lNotasEst = this.mi.getEstListarNotasEstudiante(datosNotas);
            libreta.setNotas(lNotasEst);
            lEstudiantes.set(i, libreta);
	    modelo.put("numItems", lNotasEst);
	  }
        }
        PagedListHolder lNotas = new PagedListHolder(lEstudiantes);
        lNotas.setPageSize(lNotas.getNrOfElements());      	
        modelo.put("listaNotas", lNotas);
      }

      //Sacando la lista de estudiantes programados a la materia, evaluacion continua
      if (("1".equals(sId_tipo_grado)) && ("2".equals(sId_tipo_evaluacion))) {
        System.out.println("entro al if de id_tipo_grado=1 y id_tipo_evaluacion=2----");
        Libretas datosEstProg = new Libretas();
        datosEstProg.setId_materia(datosAsignacion.getId_materia());
        datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
        datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro()); 
        datosEstProg.setGestion(datosAsignacion.getGestion());
        datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
	//datosEstProg.setId_fase(datosAsignacion.getId_fase());
	datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
        System.out.println("lista de estudiantes--2--->" + lEstudiantes.size());	
	
	Libretas libreta = new Libretas();
        for (int i=0; i<lEstudiantes.size(); i++){
          libreta = (Libretas)lEstudiantes.get(i);
          int iId_estudiante = libreta.getId_estudiante();
          //Obtenemos las notas de cada uno de los estudiantes
	  Libretas datosNotas = new Libretas();
	  datosNotas.setId_estudiante(iId_estudiante);
	  datosNotas.setId_grupo(datosAsignacion.getId_grupo());
	  datosNotas.setId_materia(datosAsignacion.getId_materia());
	  datosNotas.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
	  datosNotas.setId_fase(datosAsignacion.getId_fase());
	  datosNotas.setGestion(datosAsignacion.getGestion());
	  datosNotas.setPeriodo(datosAsignacion.getPeriodo());
	  datosNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	  datosNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
	  int _iNota_final = this.mi.getEstSumarNotasEstudianteEvalContinua(datosNotas);
	  modelo.put("nota_final" , Integer.toString(_iNota_final));
	  System.out.println("NOTA_FINAL----->" + Integer.toString(_iNota_final));
	  
	  //Sacamos la fase minima de la materia
          Libretas datosFase1 = new Libretas();
          datosFase1.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
          datosFase1.setId_programa(Integer.parseInt(sId_programa));
	  datosFase1.setGestion(datosAsignacion.getGestion());
	  datosFase1.setPeriodo(datosAsignacion.getPeriodo());
          int iMin_fase = this.mi.getLbrBuscarFaseMinima(datosFase1);
	  
	  //Sacamos la fase maxima de la materia
          int iMax_fase = this.mi.getLbrBuscarFaseMaxima(datosFase1);
	  modelo.put("id_fase_max", Integer.toString(iMax_fase));
	  
	  //Sacamos la nota de aprobacion de la fase inicial
          Libretas datosFase2 = new Libretas();
          datosFase2.setId_fase(iMin_fase);
          datosFase2.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	  datosFase2.setId_departamento(datosAsignacion.getId_departamento());
	  datosFase2.setGestion(datosAsignacion.getGestion());
          datosFase2.setPeriodo(datosAsignacion.getPeriodo());
          Libretas buscarFase2 = this.mi.getLbrBuscarFase(datosFase2);
          
	  modelo.put("nota_aprobacion", Double.toString(buscarFase2.getNota_aprobacion()));
	  
	  if (_iNota_final == 0) {
            List lNotasEst = this.mi.getEstListarNotasEstudiante(datosNotas);
	    
            for (int j=lNotasEst.size(); j<iCantidad; j++){
	      System.out.println("entro al for de lNOtasEst----->");
              Libretas aux = new Libretas();
	      //if (_iNota_final == 2){ 
                aux.setNota(0);
	      /*}
	      if (_iNota_final != 2){ 
                aux.setNota(_iNota_final);
	      }	*/
     	      aux.setNro_nota(j+1);
	      lNotasEst.add(aux);
	    }
	    
            libreta.setNotas(lNotasEst);
            lEstudiantes.set(i, libreta);   
	    modelo.put("numItems", lNotasEst);
	  }
        }
        PagedListHolder lNotas = new PagedListHolder(lEstudiantes);
        lNotas.setPageSize(lNotas.getNrOfElements());      	
        modelo.put("listaNotas", lNotas);
      }
      
      //Para vestibulares
      if ("2".equals(sId_tipo_grado)) {
        Libretas datosPstProg = new Libretas();
        datosPstProg.setId_materia(datosAsignacion.getId_materia());
        datosPstProg.setId_grupo(datosAsignacion.getId_grupo());
        datosPstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro()); 
        datosPstProg.setGestion(datosAsignacion.getGestion());
        datosPstProg.setPeriodo(datosAsignacion.getPeriodo());
        List lPostulantes = this.mi.getPstBuscarPostulantesProgramados(datosPstProg);
	System.out.println("lista de postulantes----->" + lPostulantes.size());	
	
	Libretas libreta = new Libretas();
        for (int i=0; i<lPostulantes.size(); i++){
          libreta = (Libretas)lPostulantes.get(i);
          int iId_postulante = libreta.getId_postulante();
          //Obtenemos las notas de cada uno de los postulantes
	  Libretas datosNotas = new Libretas();
	  datosNotas.setId_postulante(iId_postulante);
	  datosNotas.setId_grupo(datosAsignacion.getId_grupo());
	  datosNotas.setId_materia(datosAsignacion.getId_materia());
	  datosNotas.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
	  datosNotas.setId_fase(datosAsignacion.getId_fase());
	  datosNotas.setGestion(datosAsignacion.getGestion());
	  datosNotas.setPeriodo(datosAsignacion.getPeriodo());
	
          List lNotasPst = this.mi.getPstListarNotasPostulante(datosNotas);
          for (int j=lNotasPst.size(); j<iCantidad; j++){
            Libretas aux = new Libretas();
            aux.setNota(0);
     	    aux.setNro_nota(j+1);
	    lNotasPst.add(aux);
	  }
          libreta.setNotas(lNotasPst);
          lPostulantes.set(i, libreta);   
	  modelo.put("numItems", lNotasPst);
        }
        PagedListHolder lNotas = new PagedListHolder(lPostulantes);
        lNotas.setPageSize(lNotas.getNrOfElements());      	
        modelo.put("listaNotas", lNotas);
      }
      
    } 
    
    modelo.put("id_tipo_evaluacion", Integer.toString(datosAsignacion.getId_tipo_evaluacion()));

    return new ModelAndView("administrarLibretasDesignacion/ListarEstudiantesProgramados", modelo);
  }
}