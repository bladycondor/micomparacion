package org.fautapo.web.administrarTiposNotas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Enlaces;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04_03
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-03
*/



public class RegistrarTiposNotas implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    
    
    //Recuperando datos 
    String sBoton = request.getParameter("boton");    
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_departamento = request.getParameter("id_departamento");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    int iId_facultad = cliente.getInt(request, "id_facultad");
    modelo.put("id_facultad", Integer.toString(iId_facultad));
    
    //Buscamos Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(iId_facultad);
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("facultad",datosFacultad);
    if(datosFacultad ==null)
      return new ModelAndView("Error", "mensaje", "No existe la facultad");
    
    if(("".equals(sId_departamento))||("".equals(sId_tipo_evaluacion))){
      return new ModelAndView("Aviso","mensaje", "Seleccione los datos requeridos");
    }
    int iId_departamento=Integer.parseInt(sId_departamento); 
    int iId_tipo_evaluacion = Integer.parseInt(sId_tipo_evaluacion);
    int iGestion = Integer.parseInt(sGestion);
    int iPeriodo = Integer.parseInt(sPeriodo);
    

    String sId_fase = request.getParameter("id_fase");
    String sId_fase_ant = request.getParameter("id_fase_ant");
    if (sId_fase_ant == null){
      sId_fase_ant = "--";
    }
    
    //Sacamos datos del FCLDepartamento
    Libretas buscar = new Libretas();
    buscar.setId_departamento(iId_departamento);
    Departamentos datosFclDepartamento = this.mi.getDptBuscarDepartamento(buscar);
    modelo.put("id_departamento", sId_departamento);
    modelo.put("fcldepartamento", datosFclDepartamento.getDepartamento());

    //Buscando datos del tipo de evaluacion
    buscar.setId_tipo_evaluacion(iId_tipo_evaluacion);
    Libretas datosTipoEvaluacion = this.mi.getTpsBuscarTipoEvaluacion(buscar);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    modelo.put("tipo_evaluacion", datosTipoEvaluacion.getTipo_evaluacion());
    
    
    //Listar Tipos Notas
    List lTiposNotas = this.mi.getListarTiposNotas();
    modelo.put("lTiposNotas", lTiposNotas);
   
    //Listando las fases de acuerdo al tipo de evaluacion y al id_departamento elegido
    buscar.setGestion(iGestion);  
    buscar.setPeriodo(iPeriodo);  
    List lListarFases = this.mi.getLbrListarFases(buscar);
    modelo.put("lListarFases", lListarFases);      
    
    if ("--".equals(sId_fase)){
      sId_fase = null;
    }

    //Listando los tipos de notas de la fase seleccionada
    if (sId_fase != null ){
      Libretas listartiponota = new Libretas();
      listartiponota.setId_fase(Integer.parseInt(sId_fase));
      listartiponota.setId_departamento(iId_departamento);
      listartiponota.setId_tipo_evaluacion(iId_tipo_evaluacion);
      listartiponota.setGestion(iGestion);  
      listartiponota.setPeriodo(iPeriodo);
      List lListarTiposNotas = this.mi.getLbrListarTiposNotasFase(listartiponota);
      modelo.put("lListarTiposNotas", lListarTiposNotas);
      modelo.put("id_fase_s", sId_fase);
      
    }    
    
    //Listando los estado de la tabla _estados para la tabla lbr_tipos_notas
    String tabla ="lbr_tipos_notas";
    Enlaces estados =new Enlaces();
    estados.setTabla(tabla);
    List lListarEstados= this.mi.getStdListarEstadosTabla(estados);
    modelo.put("lListarEstados",lListarEstados);
    String sAux = "0";
    String sId_tipo_nota = request.getParameter("id_tipo_nota");
    if ((sId_fase != null)&&(sId_fase_ant != "--")){
      if (sId_fase.equals(sId_fase_ant)){  
        if (("Crear".equals(sBoton))||("0".equals(sId_tipo_nota))){
          sAux ="2";
          String sCid_tipo_nota_s = "0";
	  modelo.put("id_fase_s",sId_fase);
          modelo.put("id_tipo_nota_s",sCid_tipo_nota_s);        
        }
        else{           
          modelo.put("id_tipo_nota_s",sId_tipo_nota);	
            if (sId_tipo_nota != null){
              int iId_tipo_nota = Integer.parseInt(sId_tipo_nota);
              if (iId_tipo_nota != 0){      
                sAux = "1";
	        //Obtenemos los datos del tipo de nota elegido      
		Libretas tiponota = new Libretas();        
                tiponota.setId_tipo_nota(iId_tipo_nota);
                Libretas buscarTipoNota = this.mi.getLbrBuscarTipoNota(tiponota);
	        if (buscarTipoNota != null){
                  modelo.put("id_tipo_nota_s",Integer.toString(buscarTipoNota.getId_tipo_nota()));
                  modelo.put("tipo_nota",buscarTipoNota.getTipo_nota());
                  modelo.put("estado",buscarTipoNota.getEstado());
                  modelo.put("id_estado",buscarTipoNota.getId_estado());
	        }
              }
            }
        }
      
        if (("Modificar".equals(sBoton))||("Aceptar".equals(sBoton))){    
          int iId_fase = Integer.parseInt(sId_fase);
          int iId_tipo_nota = Integer.parseInt(sId_tipo_nota);	
	  String sId_estado = request.getParameter("id_estado");
	  String sTipo_nota;
	  try {
	    sTipo_nota = request.getParameter("tipo_nota");
	  } catch (Exception e){
	    sTipo_nota = null;
	  }
	  if ("".equals(sTipo_nota)){
	    sAux = "3";
	    modelo.put("mensaje", "Inserte datos en Tipo Nota");
	  }
	  else{	
	    if("Modificar".equals(sBoton)){
	      sAux="3";
	      Libretas modificar = new Libretas();
	      modificar.setId_tipo_nota(iId_tipo_nota);
	      modificar.setId_fase(iId_fase);
	      modificar.setId_departamento(iId_departamento);
	      modificar.setId_tipo_evaluacion(iId_tipo_evaluacion);
              modificar.setId_estado(sId_estado);
	      modificar.setGestion(iGestion);
	      modificar.setPeriodo(iPeriodo);
	      modificar.setTipo_nota(sTipo_nota);
	      modificar.setId_rol(cliente.getId_rol());  //CRCB
	      modificar.setUlt_usuario(cliente.getId_usuario());
	      int iValor = this.mi.setLbrModificarTipoNota(modificar);	    
	      if(iValor == 1){
	        modelo.put("mensaje", "Se registro la modificación");
	      }
	      else{
	        modelo.put("mensaje", "No se registro la modificación ");
	      }	
	    }
	    if("Aceptar".equals(sBoton)){
	      sAux = "3";
	      Libretas nuevo = new Libretas();
	      nuevo.setId_fase(iId_fase);
	      nuevo.setId_departamento(iId_departamento);
              nuevo.setId_tipo_evaluacion(iId_tipo_evaluacion);  
              nuevo.setId_estado(sId_estado);
	      nuevo.setGestion(iGestion);
	      nuevo.setPeriodo(iPeriodo);
	      nuevo.setTipo_nota(sTipo_nota);
	      nuevo.setId_rol(cliente.getId_rol());  //CRCB
	      nuevo.setUlt_usuario(cliente.getId_usuario());
	      int iValor = this.mi.setLbrInsertarTipoNota(nuevo);
	      if(iValor == 1){
	        modelo.put("mensaje", "Se registro el tipo de nota");
	      }
	      else{
	        modelo.put("mensaje", "No se registro el tipo de nota");
	      }	
	    }
	  }
	
	  Libretas listartiponota = new Libretas();
          listartiponota.setId_fase(Integer.parseInt(sId_fase));
          listartiponota.setId_departamento(iId_departamento);
          listartiponota.setId_tipo_evaluacion(iId_tipo_evaluacion);
          listartiponota.setGestion(iGestion);  
          listartiponota.setPeriodo(iPeriodo);
          List lListarTiposNotas = this.mi.getLbrListarTiposNotasFase(listartiponota);
          modelo.put("lListarTiposNotas", lListarTiposNotas);
          modelo.put("id_fase_s", sId_fase);
        
        }
      }
      else{ 
        sAux = "2";
      }
      
    }
    else{
      sAux = "0";
    }
     
    modelo.put("sAux",sAux);
    modelo.put("cliente", cliente);
    modelo.put("gestion", sGestion);    
    modelo.put("periodo", sPeriodo);

    return new ModelAndView("administrarTiposNotas/RegistrarTiposNotas", modelo);
  }
}
