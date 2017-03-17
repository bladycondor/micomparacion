package org.fautapo.web.programarPostulantesPsa;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import org.fautapo.domain.Abm;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.lang.String;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Asistenciapsa;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

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

    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");
    //Si dio volver recuperamos los datos
	  String sFecha = request.getParameter("fecha");
	  System.out.println("La fecha del examen  -->"+sFecha);
    String sHora = request.getParameter("hora");
	 System.out.println("La hora del examen  -->"+sHora);
	String sLugar = request.getParameter("lugar");
	 System.out.println("El lugar del examen  -->"+sLugar);
	//String sNromaquinas = request.getParameter("nro_maquinas");
    String sId_facultad = request.getParameter("id_facultad");
    String sId_programa = request.getParameter("id_programa");
	 String sNromaquinas = cliente.getString(request, "nro_maquinas");
    List listaPostulantes;
	int iResultadoAsignacion=0;
	
	
//Sacamos el formato de la fecha	
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    
	
    Postulantes postulante = new Postulantes();
    postulante.setGestion(Integer.parseInt(sGestion));
    postulante.setPeriodo(Integer.parseInt(sPeriodo));
	

    List lTransacciones = this.mi.getRepAsistenciapostulantepsa(postulante);
    if ((!"".equals(sGestion)) && (!"".equals(sPeriodo)) ) {
      //postulante.setId_programa(Integer.parseInt(sId_programa));
      listaPostulantes = this.mi.getMiListarPstPsaGestionPeriodo(postulante);
      modelo.put("listaPostulantes", listaPostulantes);
	  
	// esto esta agregado para realizar la asignacion del centro de computo a los postulantes segun el nro de maquinas habilitadas  
	 // List designaciones = this.mi.getDctListarAsignacionDocenteMateriaFuncionsintitular(asigna);
 
List postulanteid=this.mi.getDctListarPostulantespsasoloid(postulante);
int postulantesregistrados=0;
 modelo.put("postulanteid", postulanteid);
 modelo.put("lTransacciones", lTransacciones);
 //if(sFecha<){
	try{
//Asistenciapsa asistencia=new Asistenciapsa();
		
		for (int i = 0; i < (Integer.parseInt(sNromaquinas)-2); i++) {
     int inro_memo=0; int iId_memo=0;
	 int id_asigna=0;
	 //if (postulanteid.get(i)==null) {
	  //  return new ModelAndView("Error","mensaje","No existen mas postulantes para asignar.");
		
      //  return new ModelAndView("habilitarPostulantes/Entrada", "cliente", cliente);
    //  }else {
	 Postulantes memos = (Postulantes) postulanteid.get(i);
	 Asistenciapsa asistencia=new Asistenciapsa();
	// if(postulanteid.get(i)!=null){
		
	  
	/*SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/MM/dd");
		Date fechaactual=new Date();
		try{
		 fechaactual=formatoDeFecha.parse(sFecha);}
		catch(ParseException e){
			System.out.println(e.getMessage());
		}*/
//System.out.println("fechaaaaaaaaaaaaa"+formatoDeFecha.format(sFecha));
      asistencia.setId_postulante(memos.getId_postulante());
	  System.out.println("El id_postulante es dct -->"+Integer.toString(memos.getId_postulante()));
	  asistencia.setFecha(sFecha);
	  System.out.println("La fec_inicio del examen  -->"+asistencia.getFecha());
	  asistencia.setHora(sHora);
	  System.out.println("La hora del examen  -->"+asistencia.getHora());
	  asistencia.setLugar(sLugar);
	  System.out.println("El lugar del examen  -->"+asistencia.getLugar());
	  asistencia.setNro_maquinas(i+1);
	  System.out.println("El numero de maquina del examen  -->"+asistencia.getNro_maquinas());
	  
	  asistencia.setId_rol(cliente.getId_rol());
      asistencia.setUlt_usuario(cliente.getId_usuario());
      
	  iResultadoAsignacion = this.mi.setRegistrarasignacion(asistencia);
	  //}
	 postulantesregistrados=(asistencia.getNro_maquinas());
		} }catch(ClassCastException e){
	System.out.println("Error");
									}
	catch(IndexOutOfBoundsException e){
	return new ModelAndView("Aviso","mensaje","Se registraron: "+postulantesregistrados+" Postulantes. No existen mas postulantes para asignar.");
									}
	

// no permitir ingresar un examen en fecha atrasada
//}else {}	
//Programas programa = new Programas();
     // programa.setId_programa(Integer.parseInt(sId_programa));
    //  programa = this.mi.getPrgBuscarPrograma(programa);
     // modelo.put("programa", programa);
    }
    else
      return new ModelAndView("Error","mensaje","Faltan datos");
    
    // FIN esto esta agregado para realizar la asignacion del centro de computo a los postulantes segun el nro de maquinas habilitadas 
    //modelo.put("listaPostulantes", listaPostulantes);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    //modelo.put("id_facultad", sId_facultad);
   // modelo.put("id_programa", sId_programa);
    
    return new ModelAndView("programarPostulantesPsa/ListarPostulantes", modelo);
	
	//return new ModelAndView("programarPostulantesPsa/postulantexasistenciaLista/ListarCondiciones", modelo);
  }
}