package org.fautapo.web.listarDibRep;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.fautapo.web.listarDibRep.ListarConsultaPDF;
import org.fautapo.web.listarDibRep.ListarConsultaExcel;

public class ListarAbmGeneral1 implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
   Map modelo = new HashMap();
   String datos[][];
   String sql="";
   String sql_total="";

    //CONTROL DE SESION
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) {return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente.");}

    Instituciones institucion = new Instituciones();
    //institucion.setId_institucion(cliente.id_institucion);
    institucion.setId_institucion(1); //--------------------------ESTATICO
    institucion = this.mi.getBuscarInstitucion(institucion);
    if(institucion !=null){
      modelo.put("institucion",institucion.getInstitucion());
      modelo.put("logo",institucion.getLogo());
    }
   String _nombres = cliente.getNombres();

   String glosa_texto = request.getParameter("glosa_texto");
   modelo.put("glosa_texto",glosa_texto);

   String sRuta = request.getParameter("ruta");
   modelo.put("ruta",sRuta);

   int id_consulta = Integer.parseInt(request.getParameter("c"));
   modelo.put("id_consulta",Integer.toString(id_consulta));
   Abm abm = new Abm();
   Abm abm1 = new Abm();

   String valorTotales[] = null;
   String valor_total[] = null;

   abm.setId_consulta(id_consulta);

   Abm buscarConsulta = this.mi.getBuscarConsulta(abm);
   modelo.put("consulta", buscarConsulta);
   

   Abm buscarTotales = this.mi.getBuscarConsultaTotales(abm);
   modelo.put("consultaTotal", buscarTotales);
   
   String sOpcionTotal = request.getParameter("op_total");
   modelo.put("opcionTotal",sOpcionTotal);

   String sPaginacion = request.getParameter("paginacion");
   modelo.put("paginacion",sPaginacion);

   String headers[] = null;
   String suma_st[] = null;
   try {
     headers = buscarConsulta.getCabezas().split(":");
   } 
   catch(Exception e){}

   try {
     suma_st = buscarConsulta.getSumas().split(":");    
   } 
   catch(Exception e){}   
   //if(headers != null)
    modelo.put("headers", headers);
    
   //if(suma_st != null)
    modelo.put("suma_st", suma_st);

   if (buscarConsulta != null){
     String valor [] = buscarConsulta.getEtiquetas().split(":");
     
     modelo.put("valor",valor);
     modelo.put("titulo",buscarConsulta.getTitulo());
     modelo.put("descripcion",buscarConsulta.getDescripcion());

     sql = buscarConsulta.getConsulta();
     List listarCamposCondicion = this.mi.getListarCamposCondicion(abm); 
     
     if (listarCamposCondicion.size() != 0) {
       sql="";
       String sql1 = "";
       String valorCampo;
       List lCampos = new ArrayList();
       for (int i = 0; i < listarCamposCondicion.size(); i++) {
         Abm aux = (Abm)listarCamposCondicion.get(i);
         valorCampo = request.getParameter(aux.getCampo());
         Abm aux1 = this.mi.getBuscarTabla(aux);
	 String tipo_dato = aux.getTipo_dato();
      	 aux.setValores("");
         if (!"0".equals(valorCampo)) {
	   valorCampo = valorCampo.trim();
	   if (!"".equals(valorCampo)) {
	     if (sql1.equals("")) {
	       if ("dfecha".equals(tipo_dato)||"dfecha2".equals(tipo_dato)) {
	  	 String valorCampo1 = request.getParameter(aux.getCampo()+"1");//
  	         sql1 = aux.getAlias()+"." + aux.getCampo() + " BETWEEN _cadena_fecha('" + valorCampo1 + "')::" + aux.getTipo_dato() + " AND _cadena_fecha('" + valorCampo + "')::" + aux.getTipo_dato();
      	         aux.setValores(valorCampo1 + " - " + valorCampo);
	       } else {
	         sql1 = aux.getAlias()+"." + aux.getCampo() + "='" + valorCampo + "'::" + aux.getTipo_dato() ;
                 String sCombo = request.getParameter(aux.getCampo() + valorCampo);
		 if ( (sCombo != null) && (!sCombo.equals("")) ) {
      	           aux.setValores(sCombo);
		 }
		 else  {
      	           aux.setValores(valorCampo);
		 }
	       }       	        
	     } else {
	       if("dfecha".equals(tipo_dato)||"dfecha2".equals(tipo_dato)) {
		 String valorCampo1 = request.getParameter(aux.getCampo()+"1");//	     
	         sql1 = sql1 + " AND " + aux.getAlias()+"." + aux.getCampo() + " BETWEEN _cadena_fecha('" + valorCampo1 + "')::" + aux.getTipo_dato() + " AND _cadena_fecha('" + valorCampo + "')::" + aux.getTipo_dato();
      	         aux.setValores(valorCampo1 + " - " + valorCampo);
	       } else {
	         sql1 = sql1 + " AND " + aux.getAlias()+"." + aux.getCampo() + "='" + valorCampo + "'::" + aux.getTipo_dato();    
                 String sCombo = request.getParameter(aux.getCampo() + valorCampo);
		 if ( (sCombo != null) && (!sCombo.equals("")) ) {
      	           aux.setValores(sCombo);
		 } else {
      	           aux.setValores(valorCampo);
		 }
	       }	   
	     }

	   }
	 }
         lCampos.add(aux);	  	 
       }
       
       modelo.put("condicion", lCampos);
       //Llenar
        
       try {  
         String sql2[] =buscarConsulta.getConsulta().split("###");
         if(!sql1.equals("")){
           sql = sql2[0] + " AND "+ sql1 + sql2[1] ;
	 } else {
	   sql = sql2[0] + sql2[1] ;
	 }
       } 
       catch(Exception ex) {
         String sql2[] =buscarConsulta.getConsulta().split("##");
         if(!sql1.equals("")){
           sql = sql2[0] + " AND "+ sql1 + sql2[1] ;
         } 
	 else {
	   sql = sql2[0] + sql2[1] ;
	 }
       }
       
      try {
         Abm aux = new Abm(); 
         valorTotales = buscarTotales.getEtiquetas().split(":");
         modelo.put("etiquetas_total", valorTotales);
	 //sql_total = buscarTotales.getConsulta();  
	 String sql_totales2[]=buscarTotales.getConsulta().split("###");
         if(!sql1.equals("")){
           sql_total = sql_totales2[0] + " AND "+ sql1 + sql_totales2[1];
	 } 
	 else {
	   sql_total = sql_totales2[0] + sql_totales2[1];
	 }

         abm1.setSql(sql_total);
	 System.out.println("## sql_total =" + abm1.getSql());
	 List valores_total = this.mi.getEjecutarListado(abm1);
         Abm aux1 = new Abm();
	 aux1 = (Abm)valores_total.get(0);
	 valor_total = aux1.getValores().split("#~~#");
       } 
       catch(Exception ex) {
         try {
           Abm aux = new Abm(); 
           valorTotales = buscarTotales.getEtiquetas().split(":");
           modelo.put("etiquetas_total", valorTotales);
	 
	   String sql_totales2[] = buscarTotales.getConsulta().split("##");
           if(!sql1.equals("")){
             sql_total = sql_totales2[0] + " AND "+ sql1 + sql_totales2[1] ;
           } 
	   else {
	     sql_total = sql_totales2[0] + sql_totales2[1] ;
	   }

           abm1.setSql(sql_total);
           System.out.println("## sql_total =" + abm1.getSql());
	   List valores_total = this.mi.getEjecutarListado(abm1);
           Abm aux1 = new Abm();
	   aux1 = (Abm)valores_total.get(0);
	   valor_total = aux1.getValores().split("#~~#");
	 } 
	 catch(Exception e){
           System.out.println("no entro a ninguna:" +e.getMessage());
	 }
       }
       abm.setSql(sql);
     } 
     else {
       abm.setSql(sql);
       try {
         Abm aux = new Abm();        
         valorTotales = buscarTotales.getEtiquetas().split(":");
         modelo.put("etiquetas_total", valorTotales);

         sql_total = buscarTotales.getConsulta();  
         aux.setSql(sql_total);
         System.out.println("sql_total:" + aux.getSql());
         List valores_total = this.mi.getEjecutarListado(aux);
         aux = (Abm)valores_total.get(0);
         valor_total = aux.getValores().split("#~~#");
       } 
       catch (Exception ex){}
     }
     System.out.println("sql=" + abm.getSql());

     String aIzq_der[] = new String[valor.length];
     for (int i=0; i<valor.length;i++) {
      aIzq_der[i] = "0"; 
     }
     String aCant_dec[] = new String[valor.length];
     for (int i=0; i<valor.length;i++) {
        aCant_dec[i] = "0"; 
     }
       try {
         modelo.put("sql",abm.getSql());
         List valores = this.mi.getEjecutarListado(abm);
         datos = new String[valores.size()][valor.length];
         for (int i=0; i<valores.size(); i++) { 
           abm = (Abm)valores.get(i);
           String valor1[]= abm.getValores().split("#~~#");
           for (int j=0; j<valor1.length;j++) {
             datos[i][j] = valor1[j];
             for (int k=0; k<aIzq_der.length;k++) {	   
	       if (k==j){
	         try {
	           //aIzq_der[k] =String.valueOf(Float.valueOf(aIzq_der[k]).floatValue() + Float.valueOf(valor1[j]).floatValue());
	           float v = Float.valueOf(valor1[j]).floatValue();
	           aIzq_der[k] = String.valueOf(Integer.parseInt(aIzq_der[k]) + 1);
	           //System.out.println(aIzq_der[k]);
	         } catch (Exception e1) {}
	       }
             }
           }
         }

         for(int k=0;k<aIzq_der.length;k++)
         if (!aIzq_der[k].equals("0")) {
           String sDato=datos[0][k];
           String aDato[]=sDato.split("\\.");
           try{aCant_dec[k]=Integer.toString(aDato[1].length());}catch(Exception ep){}
         }

         modelo.put("cant_dec", aCant_dec);
         modelo.put("izq_der", aIzq_der);     
         modelo.put("valores", valores);      
         modelo.put("datos",datos);
       } catch(Exception e) {
         System.out.println("ERROR: " + e.getMessage());
         return new ModelAndView("Aviso", "mensaje","no hay datos definidos datos para Listar");
       }
    }  
    modelo.put("valor_total", valor_total);

    String boton = request.getParameter("op");
    if ("PDF".equals(boton)) { 
      
      String a = "esto es una valor que se manda de otra clase";
      ListarConsultaPDF objeto = new ListarConsultaPDF();
      objeto.a = a;
      
      return new ModelAndView(objeto, modelo);
    
    } else
      if ("EXCEL".equals(boton)) {
        String a = "hola esto es prueba";
        ListarConsultaExcel objeto = new ListarConsultaExcel();
        objeto.a = a;
      
        return new ModelAndView(objeto, modelo);
      }
      else
        return new ModelAndView("listarDibRep/ListarConsulta", modelo);
  }
//INICIO LDJ
  static String separar_miles(String s, String Separador, String Decimal){
    if (s.length() > 0){
      String sDecimal;
      String sEntero;
      if (s.indexOf('.') == -1) s += ".00"; else if((s.length() - s.indexOf('.')) == 2) s += "0";
      sDecimal = s.substring(s.indexOf('.'));
      sDecimal = Decimal + sDecimal.charAt(1) + sDecimal.charAt(2);
      sEntero = s.substring(0, s.indexOf('.'));
      s = "";
      if (sEntero.length() > 3) {
        s = sEntero.substring(0, sEntero.length() % 3);
        sEntero = sEntero.substring(sEntero.length() % 3);
        if (s.length() > 0) s += Separador;
        while (sEntero.length() > 3){
          s += sEntero.substring(0, 3) + Separador;
          sEntero = sEntero.substring(3);
        }
      }
      s+= sEntero + sDecimal;
    }
    else s = "0" + Decimal + "00";
    return s;    
  } 
//fin LDJ
}