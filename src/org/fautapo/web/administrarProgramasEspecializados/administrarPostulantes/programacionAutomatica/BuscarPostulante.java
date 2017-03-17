package org.fautapo.web.administrarProgramasEspecializados.administrarPostulantes.programacionAutomatica;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class BuscarPostulante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Para wayka
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);
   
    Postulantes datosPostulante;
    //Recuperando variables del jsp
    //String sId_postulante = request.getParameter("id_postulante");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    int iResultadoMatricula=0;
    
    //Recuperamos de tramites
    int iId_tramite = cliente.getInt(request, "id_tramite");
    modelo.put("id_tramite", Integer.toString(iId_tramite));
    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_postulante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    String sId_postulante = tramite.getValores();
    
    if ("".equals(sId_postulante))
    {  
      modelo.put("gestion", sGestion);
      modelo.put("periodo", sPeriodo);
      return new ModelAndView("Error", "mensaje", "El tramite no esta ingresando el codigo del postulante");
      //return new ModelAndView("administrarProgramasEspecializados/administrarPostulantes/programacionAutomatica/BuscarPostulante", modelo);
    }
    
    if(!"".equals(sId_postulante))
    {
      //Sacando los datos del postulante    
      datosPostulante = new Postulantes();
      try {
        datosPostulante.setId_postulante(Integer.parseInt(sId_postulante));
      }
      catch(Exception e) {
        return new ModelAndView("Error","mensaje","El registro de postulantes es un dato de tipo entero ");
      }
      datosPostulante = this.mi.getPstBuscarPostulante(datosPostulante);
      modelo.put("datosPostulante",datosPostulante);
      if(datosPostulante == null) {
        return new ModelAndView("Aviso","mensaje","No existe el registro del postulante"+ sId_postulante);
      }
      
      //Sacando los datos personales del Postulante encontrado
      //Postulantes datosPersona = new Personas();
      datosPostulante.setId_persona(datosPostulante.getId_persona());
      Postulantes datosPersona = this.mi.getPstBuscarPersona(datosPostulante);
      modelo.put("datosPersona", datosPersona);
      //Sacando el programa en que esta el postulante
      Programas datosPrograma = new Programas();
      datosPrograma.setId_programa(datosPostulante.getId_programa());
      datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
      modelo.put("datosPrograma", datosPrograma);
      
      //Verificamos si ya existe programado el postulante
      Postulantes buscarPst = new Postulantes();
      buscarPst.setId_postulante(datosPostulante.getId_postulante());
      buscarPst.setGestion(datosPostulante.getGestion());
      buscarPst.setPeriodo(datosPostulante.getPeriodo());
      List lProgramacionPstVerificar = this.mi.getListarPstMateriasProgramadas(buscarPst);
      if(lProgramacionPstVerificar.size() > 0) {
        return new ModelAndView("Error","mensaje","El postulante con R.U.P."+ sId_postulante + "ya realizo programacion de materias");
      }
      //Buscamos el periodo
      Programas buscarPeriodo= new Programas();
      buscarPeriodo.setId_programa(datosPostulante.getId_programa());
      buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);
      modelo.put("id_periodo", Integer.toString(buscarPeriodo.getId_periodo()));
      
      //Sacando los parametros de programacion de prg_detalles
      Programas parametro = new Programas();
      parametro.setId_programa(datosPostulante.getId_programa());
      parametro.setId_plan(datosPostulante.getId_plan());
      parametro.setGestion(datosPostulante.getGestion());
      parametro.setPeriodo(datosPostulante.getPeriodo());
      List lParametros = this.mi.getPrgBuscarDetalles(parametro);
     
    
      if(lParametros.size() == 0)
      {
        String sMensaje="No existen parámetros de programación";
        modelo.put("gestion", sGestion);
        modelo.put("periodo", sPeriodo);
        modelo.put("mensaje", sMensaje);
        //return new ModelAndView("inscripcionMaterias/postulantes/ListarProgramacionMaterias", modelo);      
	return new ModelAndView("administrarProgramasEspecializados/administrarPostulantes/programacionAutomatica/ListarProgramacionMaterias", modelo);
      }
      else{
        //Listamos las materia programadas  para el postulante
        Materias programacion = new Materias();
        programacion.setId_postulante(Integer.parseInt(sId_postulante));
        //programacion.setGestion(Integer.parseInt(sGestion));
        //programacion.setPeriodo(Integer.parseInt(sPeriodo));
	programacion.setGestion(datosPostulante.getGestion());
        programacion.setPeriodo(datosPostulante.getPeriodo());
        List lMaterias = this.mi.getPstPrgListarProgramacionMateriasAut(programacion);
        for (int i = 0; i < lMaterias.size(); i++) {
          Materias materia = (Materias) lMaterias.get(i);
          if (materia.getCupo_restante() > 0) {
    	    programacion.setId_materia(materia.getId_materia());
 	    programacion.setId_modelo_ahorro(0);
	    //Sacando el id_grupo minimo
	    materia = this.mi.getDptoListarMateriaGrupoMinimo(programacion);
	    int iId_grupo = materia.getId_grupo();
	    //System.out.println("EL ID GRUPO ENTERO ES  -->"+i+"-->"+iId_grupo );
	    materia.setId_grupo(materia.getId_grupo());
	    //System.out.println("EL ID GRUPO ES  -->"+i+"-->"+materia.getId_grupo() );
	    //Recuperamos Id_materia
	    materia.setId_materia(programacion.getId_materia());
	    System.out.println("EL ID MATERIA ES  -->"+i+"-->"+materia.getId_materia() );
	    lMaterias.set(i, materia);
          }
	  else {
	     //VERIFICA SI EXISTE CUPO en todos los paralelos habilitados
	    return new ModelAndView("Error","mensaje","ATENCION EXISTE UNA MATERIA QUE NO TIENE CUPO. RECLAME AL ENCARGADO");
	  }
        }
	//Registramos LA programacion AUTOMATICAMENTE DEACUERDO AL ID_GRUPO E ID_MATERIA
	int iContador=0;
	if (lMaterias.size() > 0) {
	  Programas programacionIns = new Programas();
	  for(int j=0;j<lMaterias.size(); j++) {
	    Materias materiaIns = (Materias) lMaterias.get(j);
	    programacionIns.setId_postulante(datosPostulante.getId_postulante());
	    //System.out.println("EL ID  POSTULANTE A REGISTRAS ES  -->"+j+"-->"+programacionIns.getId_postulante() );
	    programacionIns.setId_materia(materiaIns.getId_materia());
	    //System.out.println("EL ID MATERIA A REGISTRAS ES  -->"+j+"-->"+ programacionIns.getId_materia() );
	    programacionIns.setId_grupo(materiaIns.getId_grupo());
	    //System.out.println("EL ID GRUPO A REGISTRAR ES  -->"+j+"-->"+programacionIns.getId_grupo() );
	    programacionIns.setId_modelo_ahorro(0);
	    programacionIns.setGestion(datosPostulante.getGestion());
	    //System.out.println("LA GESSTION A REGISTRAR ES   -->"+j+"-->"+programacionIns.getGestion() );
	    programacionIns.setPeriodo(datosPostulante.getPeriodo());
	    //System.out.println("EL PERIDO A REGISTRAR ES  -->"+j+"-->"+programacionIns.getPeriodo() );
	    programacionIns.setId_rol(cliente.getId_rol());	       //CRCB
	    programacionIns.setUlt_usuario(cliente.getId_usuario());
            int iResultadoProg=this.mi.setPstProgramacionMateria(programacionIns);
	    if(iResultadoProg==1){
	      iContador++;
	    }
	  }
	  //Verificando si realizo todos los registros. Por Verdad Matricular al postulante
	  if(lMaterias.size() == iContador){
	    Postulantes datosMatricula = new Postulantes();
	    datosMatricula.setId_postulante(datosPostulante.getId_postulante());
	    datosMatricula.setGestion(datosPostulante.getGestion());
	    datosMatricula.setPeriodo(datosPostulante.getPeriodo());
	    datosMatricula.setId_rol(cliente.getId_rol());	       //CRCB
	    datosMatricula.setUlt_usuario(cliente.getId_usuario());
	    iResultadoMatricula = this.mi.setPstRegistrarMatricula(datosMatricula);
	    
	    if(iResultadoMatricula > 0){
	      //Sacando el resultado de la programacion
	      List lProgramacionPst = this.mi.getListarPstMateriasProgramadas(datosMatricula);
	      modelo.put("lProgramacionPst",lProgramacionPst);
	      datosMatricula.setId_matricula(iResultadoMatricula);
	      datosMatricula = this.mi.getPstBuscarMatriculaPostulante(datosMatricula);
	      modelo.put("datosMatricula",datosMatricula);
	    }  
	  }      
        }
	//FIN PROGRAMACION AUTOMATICA
	
	modelo.put("lMaterias",lMaterias);
        modelo.put("gestion", sGestion);
        modelo.put("periodo", sPeriodo);
	return new ModelAndView("administrarProgramasEspecializados/administrarPostulantes/programacionAutomatica/ListarProgramacionMaterias", modelo);
      }
    }
    
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    return new ModelAndView("administrarProgramasEspecializados/administrarPostulantes/BuscarPostulante", modelo);
    
    
  }
}
