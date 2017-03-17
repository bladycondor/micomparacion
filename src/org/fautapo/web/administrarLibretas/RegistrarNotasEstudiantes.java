package org.fautapo.web.administrarLibretas;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.*;

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

public class RegistrarNotasEstudiantes implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    
    String sId_tipo_nota_s     = "";  //AUXILIAR 
    sId_tipo_nota_s            = request.getParameter("id_tipo_nota_s");
    String sNro_nota_s         = request.getParameter("nro_nota_s");
    String sId_programa        = request.getParameter("id_programa");
    String sId_tipo_grado      = request.getParameter("id_tipo_grado"); 
    //String sId_materia         = request.getParameter("id_materia");
    //String sId_grupo           = request.getParameter("id_grupo");  
    //String sGrupo              = request.getParameter("grupo");  
    //String sId_departamento    = request.getParameter("id_departamento");
    //String sId_fase            = request.getParameter("id_fase");
    //String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    //String sTipo_evaluacion    = request.getParameter("tipo_evaluacion");
    //String sId_modelo_ahorro   = request.getParameter("id_modelo_ahorro");
    //String sGestion            = request.getParameter("gestion");
    //String sPeriodo            = request.getParameter("periodo"); 
    
    //modelo.put("gestion", sGestion);   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    //modelo.put("periodo", sPeriodo);
    //modelo.put("id_docente",Integer.toString(cliente.getId_usuario()));
    //modelo.put("id_grupo", sId_grupo);
    //modelo.put("grupo", sGrupo);
    //modelo.put("id_fase", sId_fase);
    //modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    //modelo.put("tipo_evaluacion", sTipo_evaluacion);
    //modelo.put("id_modelo_ahorro", sId_modelo_ahorro);
    //modelo.put("nro_nota", sNro_nota_s);
    //modelo.put("id_tipo_nota_s", sId_tipo_nota_s);
    //modelo.put("id_departamento", sId_departamento);        
    
    modelo.put("id_tipo_grado", sId_tipo_grado);    
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_rol",Integer.toString(cliente.getId_rol()));
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

    //Convertimos a entero datos de entrada
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
    

    //Sacamos la fase actual según la asignacion del docente
    Libretas buscarFase = new Libretas();
    buscarFase.setId_fase(datosAsignacion.getId_fase());
    buscarFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    buscarFase.setId_departamento(datosAsignacion.getId_departamento());
    buscarFase.setGestion(datosAsignacion.getGestion());
    buscarFase.setPeriodo(datosAsignacion.getPeriodo());
    Libretas datosFase = this.mi.getLbrBuscarFase(buscarFase);
    if(datosFase.getFase() == null) {
      return new ModelAndView("Aviso","mensaje", "No se puede encontrar la fase.");
    }
    //Verificando si la fase es mayor a 100
    if(datosFase.getId_fase() >= 100) {
      return new ModelAndView("Error","mensaje", "No esta permitido administrar notas para la fase ::&nbsp;"+datosFase.getFase());
    }
    modelo.put("fase", datosFase.getFase());
    
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
    
    //Sacamos el tipo de nota que se esta calificando
    String sTipos[];
    sTipos = new String[2];
    sTipos = sId_tipo_nota_s.split(":");
    String sId_lbr_tipo_nota = sTipos[0];
    String sCantidad = sTipos[1];
    modelo.put("id_lbr_tipo_nota", sId_lbr_tipo_nota);
    modelo.put("cantidad", sCantidad);
    
    Libretas datosTipoNota = new Libretas();
    datosTipoNota.setId_lbr_tipo_nota(Integer.parseInt(sId_lbr_tipo_nota));
    datosTipoNota = this.mi.getLbrBuscarTipoNota(datosTipoNota);
    int iId_tipo_nota = datosTipoNota.getId_tipo_nota();
    int iCantidad = Integer.parseInt(sCantidad);
    modelo.put("id_tipo_nota", Integer.toString(iId_tipo_nota));
    modelo.put("tipo_nota", datosTipoNota.getTipo_nota());
    
    //GUARDANDO LAS NOTAS DE LOS ESTUDIANTES, EVALUACION REGULAR
    //Recuperamos las notas de los estudiantes
    if (("1".equals(sId_tipo_grado)) && (!"2".equals(sId_tipo_evaluacion))) {
      Enumeration e = request.getParameterNames();
      while(e.hasMoreElements()){
        String sName = (String)e.nextElement();      //Recuperando el nombre del objeto (Ej. de la caja de texto)
        String sNota = request.getParameter(sName);   //Recuperando el valor del objeto (Ej. de la caja de texto)
	System.out.println(" sNota ---Reg--> " + sNota);
        String sDato = sName.substring(0,4); 
        if ("nota".equals(sDato)){
          String sElementos[];
          sElementos = new String[2];
          sElementos = sName.split(":");
          String sNombre = sElementos[0];
          String sId_estudiante = sElementos[1];
	  int iId_estudiante = Integer.parseInt(sId_estudiante);
	  System.out.println("ID_ESTUDIANTE_ENTRA-->" + Integer.toString(iId_estudiante));
	  int iNro_nota = Integer.parseInt(sNro_nota_s);
	  
	  FormatosNum formatoNum = new FormatosNum();
	  String iNota = formatoNum.parseDecimal(Double.parseDouble(sNota), 7);        
          //Registramos las notas de los estudiantes
	  Libretas insertar = new Libretas();
	  insertar.setId_estudiante(iId_estudiante);
	  insertar.setId_grupo(datosAsignacion.getId_grupo());
	  insertar.setId_materia(datosAsignacion.getId_materia());
	  insertar.setId_departamento(buscarMateria.getId_departamento());
	  insertar.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
	  insertar.setId_fase(datosAsignacion.getId_fase());
	  insertar.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
	  insertar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	  insertar.setGestion(datosAsignacion.getGestion());
	  insertar.setPeriodo(datosAsignacion.getPeriodo());
	  insertar.setNro_nota(iNro_nota);
	  insertar.setNota(Double.parseDouble(iNota));
	  insertar.setId_rol(cliente.getId_rol());     //CRCB
	  insertar.setUlt_usuario(cliente.getId_usuario());
	  System.out.println("ID_ESTUDIANTE_REGISTRAR-->" + Integer.toString(insertar.getId_estudiante()));
	  System.out.println("ID_GRUPO_REGISTRAR-->" + Integer.toString(insertar.getId_grupo()));
	  System.out.println("ID_MATERIA_REGISTRAR-->" + Integer.toString(insertar.getId_materia()));
	  System.out.println("ID_DEPARTAMENTO_REGISTRAR-->" + Integer.toString(insertar.getId_departamento()));
	  System.out.println("ID_MODELO_AHORRO_REGISTRAR-->" + Integer.toString(insertar.getId_modelo_ahorro()));
	  System.out.println("ID_FASE_REGISTRAR-->" + Integer.toString(insertar.getId_fase()));
	  System.out.println("ID_TIPO_NOTA_REGISTRAR-->" + Integer.toString(insertar.getId_tipo_nota()));
	  System.out.println("ID_TIPO_EVALUACION_REGISTRAR-->" + Integer.toString(insertar.getId_tipo_evaluacion()));
	  System.out.println("GESTION_REGISTRA-->" + Integer.toString(insertar.getGestion()));
	  System.out.println("PERIODO_REGISTRAR-->" + Integer.toString(insertar.getPeriodo()));
	  System.out.println("NRO_NOTA_REGISTRAR-->" + Integer.toString(insertar.getNro_nota()));
	  System.out.println("NOTA_REGISTRAR-->" + insertar.getNota());
	  System.out.println("ID_ROL_REGISTRAR-->" + Integer.toString(insertar.getId_rol()));
	  System.out.println("ULT_USUARIO_REGISTRAR-->" + Integer.toString(insertar.getUlt_usuario()));
	  int iValor = this.mi.setEstInsertarNotaEstudianteFase(insertar); //ESTA HACE LOS CUATRO PASOS
        }
      }
      //SACAMOS LAS NOTAS DE LOS ESTUDIANTES PARA LA CONFIRMACION
      if (sId_tipo_nota_s != null) {
        //Sacando la lista de estudiantes programados a la materia
        Libretas datosEstProg = new Libretas();
        datosEstProg.setId_materia(datosAsignacion.getId_materia());
        datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
        datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro()); 
        datosEstProg.setGestion(datosAsignacion.getGestion());
        datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
	datosEstProg.setId_fase(datosAsignacion.getId_fase());
	datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
     
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
	  int _iNota_final = this.mi.getEstSumarNotasEstudianteEvalRegular(datosNotas);
	  System.out.println("NOTA_FINAL----->" + Integer.toString(_iNota_final));
	  
	  //Sacamos la nota de aprobacion de la fase 
          Libretas datosFase2 = new Libretas();
          datosFase2.setId_fase(datosAsignacion.getId_fase());
          datosFase2.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	  datosFase2.setId_departamento(datosAsignacion.getId_departamento());
	  datosFase2.setGestion(datosAsignacion.getGestion());
          datosFase2.setPeriodo(datosAsignacion.getPeriodo());
          Libretas buscarFase2 = this.mi.getLbrBuscarFase(datosFase2);
          modelo.put("nota_aprobacion", Double.toString(buscarFase2.getNota_aprobacion()));
	  
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
    }
    
    //GUARDANDO LAS NOTAS DE LOS ESTUDIANTES, EVALUACION CONTINUA
    //Recuperamos las notas de los estudiantes
    if (("1".equals(sId_tipo_grado)) && ("2".equals(sId_tipo_evaluacion))) {
      Enumeration e = request.getParameterNames();
      while( e.hasMoreElements()){
        String sName = (String)e.nextElement();      //Recuperando el nombre del objeto (Ej. de la caja de texto)
        String sNota = request.getParameter(sName);   //Recuperando el valor del objeto (Ej. de la caja de texto)
        String sDato = sName.substring(0,4); 
        if ("nota".equals(sDato)){
          String sElementos[];
          sElementos = new String[2];
          sElementos = sName.split(":");
          String sNombre = sElementos[0];
          String sId_estudiante = sElementos[1];
	  int iId_estudiante = Integer.parseInt(sId_estudiante);
	  System.out.println("id_estudiante222--------->" + Integer.toString(iId_estudiante));
	  int iNro_nota = Integer.parseInt(sNro_nota_s);
	  
	  FormatosNum formatoNum = new FormatosNum();
	  String iNota = formatoNum.parseDecimal(Double.parseDouble(sNota), 7);        
	  //int iNota = Integer.parseInt(sNota);
	
          //PRIMERO: Verificamos si ya existe una nota de cada estudiante.
          //SEGUNDO: Si existe la nota => modificar
	  //TERCERO: Si no existe la nota => insertar
	  //CUARTO : Insertar la nota calculada de la fase actual
	  Libretas insertar = new Libretas();
	  insertar.setId_estudiante(iId_estudiante);
	  insertar.setId_grupo(datosAsignacion.getId_grupo());
	  insertar.setId_materia(datosAsignacion.getId_materia());
	  insertar.setId_departamento(buscarMateria.getId_departamento());
	  insertar.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
	  insertar.setId_fase(datosAsignacion.getId_fase());
	  insertar.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
	  insertar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	  insertar.setGestion(datosAsignacion.getGestion());
	  insertar.setPeriodo(datosAsignacion.getPeriodo());
	  insertar.setNro_nota(iNro_nota);
	  insertar.setNota(Double.parseDouble(iNota));
	  insertar.setId_rol(cliente.getId_rol());     //CRCB
	  insertar.setUlt_usuario(cliente.getId_usuario());
	  int iValor = this.mi.setEstInsertarNotaEstudianteFase(insertar); //ESTA HACE LOS CUATRO PASOS
        }
      }
      //SACAMOS LAS NOTAS DE LOS ESTUDIANTES PARA LA CONFIRMACION
      if (sId_tipo_nota_s != null) {
        //Sacando la lista de estudiantes programados a la materia
        Libretas datosEstProg = new Libretas();
        datosEstProg.setId_materia(datosAsignacion.getId_materia());
        datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
        datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro()); 
        datosEstProg.setGestion(datosAsignacion.getGestion());
        datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
	datosEstProg.setId_fase(datosAsignacion.getId_fase());
	datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
     
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
	  System.out.println("NOTA_FINAL----->" + Integer.toString(_iNota_final));
	  
	  //Sacamos la fase minima de la materia
          Libretas datosFase1 = new Libretas();
          datosFase1.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
          datosFase1.setId_programa(Integer.parseInt(sId_programa));
	  datosFase1.setGestion(datosAsignacion.getGestion());
	  datosFase1.setPeriodo(datosAsignacion.getPeriodo());
          int iMin_fase = this.mi.getLbrBuscarFaseMinima(datosFase1);

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
    }
    
    //GUARDANDO LAS NOTAS DE LOS POSTULANTES
    //Recuperamos las notas de los postulantes
    if ("2".equals(sId_tipo_grado)) {
      Enumeration e = request.getParameterNames();
      while( e.hasMoreElements()){
        String sName = (String)e.nextElement();      //Recuperando el nombre del objeto (Ej. de la caja de texto)
        String sNota = request.getParameter(sName);   //Recuperando el valor del objeto (Ej. de la caja de texto)
        String sDato = sName.substring(0,4); 
        if ("nota".equals(sDato)){
          String sElementos[];
          sElementos = new String[2];
          sElementos = sName.split(":");
          String sNombre = sElementos[0];
          String sId_postulante = sElementos[1];
	  System.out.println("id_postulante---------> " + sId_postulante);
	  int iId_postulante = Integer.parseInt(sId_postulante);
	  int iNro_nota = Integer.parseInt(sNro_nota_s);
	  
	  FormatosNum formatoNum = new FormatosNum();
	  String iNota = formatoNum.parseDecimal(Double.parseDouble(sNota), 7);        
	  //int iNota = Integer.parseInt(sNota);
	
          //PRIMERO: Verificamos si ya existe una nota de cada postulante.
          //SEGUNDO: Si existe la nota => modificar
	  //TERCERO: Si no existe la nota => insertar
	  //CUARTO : Insertar la nota calculada de la fase actual
	  Libretas insertar = new Libretas();
	  insertar.setId_postulante(iId_postulante);
	  insertar.setId_grupo(datosAsignacion.getId_grupo());
	  insertar.setId_materia(datosAsignacion.getId_materia());
	  insertar.setId_departamento(buscarMateria.getId_departamento());
	  insertar.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
	  insertar.setId_fase(datosAsignacion.getId_fase());
	  insertar.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
	  insertar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	  insertar.setGestion(datosAsignacion.getGestion());
	  insertar.setPeriodo(datosAsignacion.getPeriodo());
	  insertar.setNro_nota(iNro_nota);
	  insertar.setNota(Double.parseDouble(iNota));
	  insertar.setId_rol(cliente.getId_rol());   //CRCB
	  insertar.setUlt_usuario(cliente.getId_usuario());
	  int iValor = this.mi.setPstInsertarNotaPostulanteFase(insertar); //ESTA HACE LOS CUATRO PASOS
        }
      }
      //SACAMOS LAS NOTAS DE LOS POSTULANTES PARA LA CONFIRMACION
      if (sId_tipo_nota_s != null) {
        //Sacamos el tipo de nota que estamos calificando
        /*Libretas datosTipoNota = new Libretas();
        datosTipoNota.setId_tipo_nota(iId_tipo_nota);
        Libretas buscarTipoNota = this.mi.getMiBuscarTipoNota(datosTipoNota);
        modelo.put("tipo_nota", buscarTipoNota.getTipo_nota());
	*/
        //Sacando la lista de postulantes programados a la materia
        Libretas datosPstProg = new Libretas();
        datosPstProg.setId_materia(datosAsignacion.getId_materia());
        datosPstProg.setId_grupo(datosAsignacion.getId_grupo());
        datosPstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro()); 
        datosPstProg.setGestion(datosAsignacion.getGestion());
        datosPstProg.setPeriodo(datosAsignacion.getPeriodo());
        List lPostulantes = this.mi.getPstBuscarPostulantesProgramados(datosPstProg);
     
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
    return new ModelAndView("administrarLibretas/RegistrarNotasEstudiantes", modelo);
  }
}