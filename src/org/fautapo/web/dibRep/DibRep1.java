package org.fautapo.web.dibRep;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.math.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.fautapo.web.dibRep.ListarConsultaPDF;

public class DibRep1 implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //CONTROL DE SESION
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");

    Instituciones institucion = new Instituciones();
    //institucion.setId_institucion(cliente.id_institucion);
    institucion.setId_institucion(1); //--------------------------ESTATICO
    institucion = this.mi.getBuscarInstitucion(institucion);
    if(institucion !=null){
      modelo.put("institucion",institucion.getInstitucion());
      modelo.put("logo",institucion.getLogo());
    }

    String datos[][];
    Abm abm = new Abm();
    Abm aux = new Abm();
    Abm aux1 = new Abm();
    String id_tabla;
    String sql = "";
    String sql_tablas = "";
    String sql_estados = "";
    String sql_campos = "";
    String sql_campos_count = "";
    String sql_campos_sum = "";
    String sql_grupo = "";
    String sql_limite="";
    
    String sql_condiciones = "";
    String etiquetas  = "";
    String etiquetas_sum  = "";
    String etiquetas_count = "";
    String etiquetas_total = "";
    String sql_total = "";
    String sCampo_calculado = "";
    String sEtiqueta_calculado = "";
    String sSql_grupo_calculado = "";
    String sSql_count_sum = "";
    List tablas_dibrep = new ArrayList();
    boolean t = true;
    
    String _nombres = cliente.getNombres();

    String sPaginacion = request.getParameter("paginacion");
    modelo.put("paginacion",sPaginacion);

    String lista_tablas[] = null;
    String tt = request.getParameter("t");
    try {
      lista_tablas = tt.split(":");
    } catch (Exception ex) {
      lista_tablas = null;
    }
    //rescatar las tablas de la pagina anterior haciendo un recorrido de tablas y colocarlo en un array los que estan seleccionados
    for (int i=0; i<lista_tablas.length; i++) {
      String letra = String.valueOf((char)(97 + i));
      Abm dato_tabla=new Abm();
      dato_tabla.setId_tabla(Integer.parseInt(lista_tablas[i]));
      dato_tabla = this.mi.getBuscarTabla(dato_tabla);
      for (int j=i+1; j<lista_tablas.length; j++) {
        if(lista_tablas[i].equals(lista_tablas[j])){
	  boolean pp = true;
	  dato_tabla.setPadre(pp);
	  break;
	} else {
	  boolean pp = false;
	  dato_tabla.setPadre(pp);
	}
      }
	dato_tabla.setAlias(letra);
        tablas_dibrep.add(dato_tabla);
    }
    

    Abm auxx = new Abm();
    for (int i = 0; i< tablas_dibrep.size(); i++) {
    auxx=(Abm)tablas_dibrep.get(i);
    }

    //ALISTA LAS TABLAS PARA LA CONSULTA 
    for(int i=0; i< tablas_dibrep.size(); i++) {
      aux = (Abm) tablas_dibrep.get(i);
      if (i==0) {
        sql_tablas=aux.getTabla() + " " + aux.getAlias();
        sql_estados=aux.getAlias()+".id_estado<>\'X\'";
      } else {
        sql_tablas=sql_tablas + ", " + aux.getTabla() +" "+ aux.getAlias() ;  
        sql_estados=sql_estados + " AND " + aux.getAlias()+".id_estado<>\'X\'";
      }
    }

    ///NUEVO PARA HACERLO DE CERO
    //Poner los campos que saldran en un Reporte separados por ":" y adentro (campo|id_campo) ejemplo  "c|1"

    String sCampos_reporte = "";
    
    String id_campos[] = request.getParameterValues("id_campos");
    try {
      for(int i=0; i< id_campos.length; i++) {
        if(sCampos_reporte.equals(""))
          sCampos_reporte = "c#" + id_campos[i];  
        else
          sCampos_reporte = sCampos_reporte + ":c#" + id_campos[i];  
      }
    } catch (Exception ex){}
    
    String sumar[] = request.getParameterValues("sumar");
    try {
    for(int i=0; i< sumar.length; i++) {
      if(sCampos_reporte.equals(""))
        sCampos_reporte = "s#" + sumar[i];  
      else
        sCampos_reporte = sCampos_reporte + ":s#" + sumar[i];  
    }
    }catch (Exception ex) {}
    
    //FIN poner los campos dentro de una cadena sCampos_reporte
    
    
    //ORDEN muestra el orden que estan los campos y como se ordenara  
    String sOrdenar = "";
    for(int i=0; i< tablas_dibrep.size(); i++) {
      abm = (Abm) tablas_dibrep.get(i);
      abm.setPermiso("l");  
      List campos = this.mi.getListarCamposTabla(abm);
      for(int j=0; j < campos.size(); j++) {
        aux = (Abm) campos.get(j);
	String sNombre = abm.getAlias() + "_" + aux.getId_campo();
	String sValor_nombre = request.getParameter("orden_" + abm.getAlias() + "_" + aux.getId_campo());
        try {
	  sValor_nombre = sValor_nombre.trim();
	  if (!sValor_nombre.equals("")) {
            if (sOrdenar.equals(""))
	      sOrdenar = sNombre + "#" + sValor_nombre;  
	    else
	      sOrdenar = sOrdenar + ":" + sNombre + "#" + sValor_nombre; 
	  }
	} catch (Exception ex){}
      }
    }
    
    //entregando los campos donde se pondran para la consulta
    String aCampos_reporte[] = sCampos_reporte.split(":");
    String aCampos_reporte_aux[] = sCampos_reporte.split(":");
    String aCampos_reporte_aux1[] = sCampos_reporte.split(":");  




    if(!sOrdenar.equals("")) {
      String  aOrdenar[] = sOrdenar.split(":");    
      for(int i=0; i< aOrdenar.length; i++) {
        for(int j=i; j< aOrdenar.length; j++) {
          String sOrden1[] = aOrdenar[i].split("#");
          String sNombre1 = sOrden1[0];
          String sPosicion1 = sOrden1[1];

          String sOrden2[] = aOrdenar[j].split("#");
          String sNombre2 = sOrden2[0];
          String sPosicion2 = sOrden2[1];

	  int iPosicion1 = Integer.parseInt(sPosicion1);
	  int iPosicion2 = Integer.parseInt(sPosicion2);
	
          if (iPosicion1 > iPosicion2) {
            String valor = sNombre1 + "#" + sPosicion1;
	    aOrdenar[j] = valor;
            valor = sNombre2 + "#" + sPosicion2;	  
	    aOrdenar[i] = valor;
  	  }
        }  
      }
      
      //FIN ORDEN      
      int iContador = 0;
      for (int i=0; i< aOrdenar.length; i++) {
        String sOrden[] = aOrdenar[i].split("#");
        String sCampo1 = sOrden[0];
        String sPosicion = sOrden[1];
        for (int j=0; j< aCampos_reporte.length; j++) {
          String sOrden1[] = aCampos_reporte[j].split("#");
	  String tipo = sOrden1[0];
	  String sCampo2 = sOrden1[1];
          if(sCampo1.equals(sCampo2)){
            aCampos_reporte_aux1[iContador] = tipo + "#" + sCampo2;
	    iContador++;
	    aCampos_reporte_aux[j] = "x#" + sCampo2;
          }    
        }
      }

      for (int i=0; i< aCampos_reporte_aux.length; i++) {
        String sOrden1[] = aCampos_reporte_aux[i].split("#");
        String tipo = sOrden1[0];
        String sCampo2 = sOrden1[1];
        if(!tipo.equals("x")) {
          aCampos_reporte_aux1[iContador] = tipo + "#" + sCampo2;
	  iContador++;
        }    
      } 

    }


    //MARCA LOS CAMPOS PRINCIPALES
    String headers[] = request.getParameterValues("cabezas");


    //MARCA LOS CAMPOS PARA LA SUBTOTALES
    String suma_st[] = request.getParameterValues("sumar_st");
    

    //PONER LOS CAMPOS EN LAS CONSULTAS TANTOS LOS SIMPLES COMO LOS COMPUESTOS ej(sum(campo),count(*))
    if(!aCampos_reporte_aux1[0].equals("")) {
    for(int i=0; i< aCampos_reporte_aux1.length; i++) {
      String aOrden[] = aCampos_reporte_aux1[i].split("#");
      String tipo = aOrden[0];
      String sCampo = aOrden[1];
      if (tipo.equals("c")) {
          String valores_campo[] = sCampo.split("_"); 
          abm.setId_campo(Integer.parseInt(valores_campo[1]));
          Abm buscarCampo = this.mi.getBuscarCampo(abm);
	  //Abm buscarTabla = this.mi.getBuscarTabla(buscarCampo);
	  //continuara..............
	
          //PARA LOS ENCABEZADOS DE EL REPORTE
	  if(headers != null) {
	    for(int j=0; j< headers.length; j++) {
	      if(headers[j].equals(sCampo)) {
	        headers[j] = Integer.toString(i);//buscarCampo.getEtiqueta();
	        break;
	      }
	    }
            //ordenar cabezeras    
            for (int j=0;j<headers.length; j++) {
              for(int k=j;k<headers.length; k++) { 
                try {
                if(Integer.parseInt(headers[j])<Integer.parseInt(headers[k])){
                  String sApoyo=headers[j];
                  headers[j]=headers[k];
                  headers[k]=sApoyo;
                }
                }catch (Exception ee){}
              }
            }
	  }
          
          //PARA LOS SUBTOTALES DE LAS SUMAS EN EL JSP
	  if(suma_st != null) {
	    for(int j=0; j< suma_st.length; j++) {
	      if(suma_st[j].equals(sCampo)) {
	        suma_st[j] = Integer.toString(i);//buscarCampo.getEtiqueta();
	        break;
	      }
	    }    
	  }

          if (sql_campos.equals("")) {
            sql_campos = valores_campo[0] + "." + buscarCampo.getCampo();
            sql_grupo = valores_campo[0] + "." + buscarCampo.getCampo();
            etiquetas = buscarCampo.getEtiqueta();
          } else {
            sql_campos = sql_campos + "||'#~~#'||" + valores_campo[0] + "." + buscarCampo.getCampo();
            etiquetas = etiquetas + ":" + buscarCampo.getEtiqueta();
            if (sql_grupo.equals(""))
	      sql_grupo = valores_campo[0] + "." + buscarCampo.getCampo();
            else
	      sql_grupo = sql_grupo + ", " + valores_campo[0] + "." + buscarCampo.getCampo();	    
          }
      }
      
        if (tipo.equals("s")) {
	   String sumita[] = sCampo.split("_");
	   Abm abm_suma = new Abm();
	   abm_suma.setId_campo(Integer.parseInt(sumita[1]));
	   abm_suma.setAlias(sumita[0]);    
	   Abm buscarCampo = this.mi.getBuscarCampo(abm_suma);
	   Abm buscarTabla = this.mi.getBuscarTabla(buscarCampo);
	   if (sql_total.equals("")) {
	     sql_total = " sum(" + abm_suma.getAlias()+"."+ buscarCampo.getCampo() +")";  
	     etiquetas_total = "Suma " + buscarCampo.getEtiqueta();
	   } else {
	     sql_total =  sql_total +  "||'#~~#'||sum("+ abm_suma.getAlias()+"."+ buscarCampo.getCampo() +")";  
	     etiquetas_total = etiquetas_total + ":Suma " + buscarCampo.getEtiqueta();
	   }
	   
	   if ("".equals(sql_campos)) {
	      sql_campos = " sum("+ abm_suma.getAlias()+"."+ buscarCampo.getCampo() +")";  
	      etiquetas = "Suma " + buscarCampo.getEtiqueta();
	   } else {
	      sql_campos = sql_campos +"||'#~~#'||sum("+ abm_suma.getAlias()+"."+ buscarCampo.getCampo() +")";  
	      etiquetas = etiquetas  + ":Suma " + buscarCampo.getEtiqueta();
	   } 
        }
      }
     } 

    sCampo_calculado = request.getParameter("campo_calculado");
    sEtiqueta_calculado = request.getParameter("campo_calculado_etiqueta");
    
    if (!sCampo_calculado.equals("")) {
        String aCampo_calculado[] = sCampo_calculado.split("&");
        String sCampo_calculado1 = "";
        boolean ff= false;
      for(int i=0; i< aCampo_calculado.length; i++){
        if (ff) {
          if (sSql_grupo_calculado.equals(""))
            sSql_grupo_calculado = aCampo_calculado[i];
          else
	    sSql_grupo_calculado = sSql_grupo_calculado + ", " + aCampo_calculado[i];
        } 
        ff = !ff;      
	
        sCampo_calculado1 =sCampo_calculado1 + aCampo_calculado[i];       
      }

      sql_campos = sql_campos + "||'#~~#'||" + sCampo_calculado1;
      etiquetas = etiquetas + ":" + sEtiqueta_calculado;
    
      String aSql_count_sum[] = sCampo_calculado.split("sum");
      if(aSql_count_sum.length>1)
      sSql_count_sum = "*";          

      aSql_count_sum = sCampo_calculado.split("SUM");
      if(aSql_count_sum.length>1)
      sSql_count_sum = "*";          

      aSql_count_sum = sCampo_calculado.split("count");
      if(aSql_count_sum.length>1)
      sSql_count_sum = "*";          

      aSql_count_sum = sCampo_calculado.split("COUNT");
      if(aSql_count_sum.length>1)
      sSql_count_sum = "*";          
    }
    if (sql_campos.equals(""))
       return new ModelAndView("Aviso", "mensaje","no hay datos definidos datos para Listar");

    if(headers != null)
    modelo.put("headers", headers);
    
    if(suma_st != null)
    modelo.put("suma_st", suma_st);

    //LISTA A LOS QUE ENTRAN A MOSTRAR CON COUNT()
    String sql_campos1 = sql_campos;
    String contar_reg = request.getParameter("contar_reg");
    if (contar_reg!=null) {
      if(sql_total.equals("")) {
        sql_total ="count(*)";
        etiquetas_total="Cant.Reg.";
      } else {
       sql_total = "count(*)||'#~~#'||" + sql_total;
       etiquetas_total="Cant.Reg.:" + etiquetas_total;
      }
      if("".equals(sql_campos1)) {
        sql_campos_count = " count(*)";  
        etiquetas_count  = "Cant.Reg.";
      } else {
        sql_campos_count = "count(*)||'#~~#'||" + sql_campos_count;  
	etiquetas_count  = "Cant.Reg.:" + etiquetas_count;
      } 
    }
    
    etiquetas = etiquetas_count + etiquetas;
    String sql_campos2 = sql_campos_count + sql_campos1;

    sql_campos2 = "(" + sql_campos2 + ")::text as valores ";
    
    
    
    
    
    
    
    
    //esto saca los where que son las relaciones con foreign key de las tablas que estan relacionando;
    t = true;
    int t1 = 0;
    for(int i=0; i< tablas_dibrep.size(); i++){
      abm = (Abm) tablas_dibrep.get(i);
      
      List campoForaneos = this.mi.getListarForaneosTabla(abm);
      
      if (campoForaneos != null) {
        for (int j=0; j < campoForaneos.size(); j++) {
          aux = (Abm) campoForaneos.get(j);
          Abm buscarCampo = this.mi.getBuscarCampo(aux);
	  aux1.setTabla(aux.getTabla_foranea());
	  Abm buscarTabla1 = this.mi.getBuscarTabla1(aux1);
	  if (buscarTabla1 != null) {
	    if ((abm.getId_tabla() != buscarTabla1.getId_tabla())&&(!abm.getPadre())){
              for (int k=0; k < tablas_dibrep.size(); k++){
	        Abm aux2 = new Abm();
	        aux2 = (Abm) tablas_dibrep.get(k);
	        if ((buscarTabla1.getId_tabla()==aux2.getId_tabla())&&(!aux2.getPadre())){
		  aux.setAlias(aux2.getAlias());
		  if ("".equals(sql_condiciones)){
	            sql_condiciones = abm.getAlias()+ "." + buscarCampo.getCampo() + "=" + aux.getAlias() +"."+aux.getId_campo_foraneo() + " ";
	          } else {
	            sql_condiciones = sql_condiciones + " AND " + abm.getAlias()+ "." + buscarCampo.getCampo() + "=" + aux.getAlias() +"."+aux.getId_campo_foraneo() + " ";	    
	          }
		  break;
		}
	      }
	    } else {
	      if(!abm.getPadre()) {
                for (int k=0; k < tablas_dibrep.size(); k++) {
	          Abm aux2 = new Abm();
	          aux2 = (Abm) tablas_dibrep.get(k);
	          if ((buscarTabla1.getId_tabla()==aux2.getId_tabla())&&(aux2.getPadre())){
		    aux.setAlias(aux2.getAlias());
		    if(aux.getAlias()!=abm.getAlias()){
		      if ("".equals(sql_condiciones)){
	                sql_condiciones = abm.getAlias()+ "." + buscarCampo.getCampo() + "=" + aux.getAlias() +"."+aux.getId_campo_foraneo() + " ";
	              } else {
	                sql_condiciones = sql_condiciones + " AND " + abm.getAlias()+ "." + buscarCampo.getCampo() + "=" + aux.getAlias() +"."+aux.getId_campo_foraneo() + " ";	    
	              }		    
		    }  
		    break;		  
		  }
	        } 
	      }
	    }
	  }
        }
      }
    }



    // CONDICIONES QUE SE SACAN DE LOS CAMPOS 
    t = true;
    for(int i=0; i< tablas_dibrep.size(); i++) {
      abm = (Abm) tablas_dibrep.get(i);
      abm.setPermiso("l");  
      List campos = this.mi.getListarCamposTabla(abm);
      for(int j=0; j < campos.size(); j++) {
        aux = (Abm) campos.get(j);
	String valor_campo = request.getParameter("campo_" + abm.getAlias() + "_" + aux.getId_campo());
	String condicion_campo = request.getParameter("condicion_" + abm.getAlias() + "_" + aux.getId_campo());
        if (valor_campo != null) {
	valor_campo = valor_campo.trim();
	if (valor_campo.equals("")){
	} else {
	  if(!valor_campo.equals("0")) {
	    if("".equals(sql_condiciones)) {
              sql_condiciones = abm.getAlias() + "." + aux.getCampo() + condicion_campo +"'" + valor_campo + "'::" + aux.getTipo_dato();
	    } else {
              sql_condiciones = sql_condiciones + " AND " + abm.getAlias() + "." + aux.getCampo() + condicion_campo + "'" + valor_campo + "'::" + aux.getTipo_dato();            
	    }  
          }
	}
	}
      }
    }



    String sRuta = request.getParameter("ruta");
    String sDescripcion = request.getParameter("descripcion");
    sDescripcion = sDescripcion.replace("\r\n","");

    modelo.put("ruta", sRuta);   
    modelo.put("descripcion", sDescripcion);
    
    String limite = request.getParameter("limite");
    limite = limite.trim();
    try {
      int i = Integer.parseInt(limite);
      sql_limite=" LIMIT " + limite;      
    } catch (Exception ex){
    }
    

    if(!sql_total.equals("")) {
      sql_total ="SELECT (" + sql_total + ")::text as valores FROM " + sql_tablas + " WHERE " + sql_estados + " "; 
      if (!"".equals(sql_condiciones)) {
        sql_total = sql_total + " AND " + sql_condiciones;
      }
      sql_total = sql_total + ";";
    }
    
    
    
    sql = "SELECT " + sql_campos2 + " FROM " + sql_tablas + " WHERE " + sql_estados;

    if (!"".equals(sql_condiciones)){
      sql = sql + " AND " + sql_condiciones;
    }
    
      
    
    if (!"".equals(sql_grupo)&&(((!"".equals(sql_campos_count))||(!"".equals(etiquetas_total)))||(!"".equals(sSql_count_sum)))){
      if (!sSql_grupo_calculado.equals(""))
        sql = sql + " GROUP BY " + sql_grupo + ", " + sSql_grupo_calculado;
      else
        sql = sql + " GROUP BY " + sql_grupo;
    }
    
    if (!"".equals(sql_grupo)) {
      if (!sSql_grupo_calculado.equals(""))
        sql = sql + " ORDER BY " + sql_grupo +", " + sSql_grupo_calculado;
      else
        sql = sql + " ORDER BY " + sql_grupo;
    } else {
      if (!"".equals(sSql_grupo_calculado)) {
        sql = sql + " ORDER BY " + sSql_grupo_calculado;
      }
    }
    
    sql = sql + sql_limite;
    
    sql = sql + ";";

    
    modelo.put("sql", sql);
    System.out.println("sql=" + sql);

    if(!sql_total.equals("")) {
    aux.setSql(sql_total);
      try {
        String etiqueta_total[] = etiquetas_total.split(":");
	modelo.put("etiquetas_total", etiqueta_total); 
        List valores_total = this.mi.getEjecutarListado(aux);
        aux = (Abm)valores_total.get(0);
        String valor_total[]= aux.getValores().split("#~~#");
        modelo.put("valor_total", valor_total);
      } catch(Exception e) {

      }
    }

    abm.setSql(sql);
    String valor[] = etiquetas.split(":");
    modelo.put("valor",valor);

    String aIzq_der[] = new String[valor.length];
    for (int i=0; i<valor.length;i++) {
      aIzq_der[i] = "0"; 
    }

    String aCant_dec[] = new String[valor.length];
    for (int i=0; i<valor.length;i++) {
      aCant_dec[i] = "0"; 
    }

    try {
      List valores = this.mi.getEjecutarListado(abm);
      datos = new String[valores.size()][valor.length];
       for (int i=0; i<valores.size(); i++){ 
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
	     } catch (Exception e1) {	       
	     }
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
    
    String tipo_texto[] = request.getParameterValues("tipo_texto");
  
    String boton = request.getParameter("op");
    if ("PDF".equals(boton)) { 
      
      String a = "esto es una valor que se manda de otra clase";
      ListarConsultaPDF objeto = new ListarConsultaPDF();
      //objeto.a = a;
      return new ModelAndView(objeto, modelo);
    } else
      if ("EXCEL".equals(boton)) {
        String a = "hola esto es prueba";
        ListarConsultaExcel objeto = new ListarConsultaExcel();
        objeto.a = a;
      
        return new ModelAndView(objeto, modelo);
      }
    else
      return new ModelAndView("dibRep/ListarConsulta", modelo);
  }  
}
