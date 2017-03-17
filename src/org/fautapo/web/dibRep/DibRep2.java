package org.fautapo.web.dibRep;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class DibRep2 implements Controller {

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
    String sql_variables="";
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
    int _id_usuario = cliente.getId_usuario();
    
    String sId_consulta = request.getParameter("c");

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
    for(int i=0; i< tablas_dibrep.size(); i++){
      aux = (Abm) tablas_dibrep.get(i);
      if (i==0) {
        sql_tablas=aux.getTabla() + " " + aux.getAlias();  
        sql_estados=aux.getAlias() + ".id_estado<>\'X\'";  
      } else {
        sql_tablas=sql_tablas + ", " + aux.getTabla() +" "+ aux.getAlias() ;  
        sql_estados=sql_estados + " AND " + aux.getAlias() +".id_estado<>\'X\'" ;  
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
    String header = "";

    //MARCA LOS CAMPOS PARA LA SUBTOTALES
    String suma_st[] = request.getParameterValues("sumar_st");
    String suma = "";    



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
	
          //PARA LOS ENCABEZADOS DE EL REPORTE                            ---------------------------- ERRRORR DE MIERA SOLO EN ESTO
	  if(headers != null) {
	    for(int j=0; j< headers.length; j++) {
	      if(headers[j].equals(sCampo)) {
	        headers[j] = Integer.toString(j);
	        break;
	      }
	    }
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
	        if(suma.equals(""))
	          suma = Integer.toString(i);
	        else
	          suma = suma + ":" + Integer.toString(i);;
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











  //LISTA A LOS QUE ENTRAN A MOSTRAR CON COUNT()
    String sql_campos1 = sql_campos + sql_campos_sum;
    t =true;
    String contar_reg = request.getParameter("contar_reg");
    if (contar_reg!=null){
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
    for(int i=0; i< tablas_dibrep.size(); i++) {
      abm = (Abm) tablas_dibrep.get(i);
      
      List campoForaneos = this.mi.getListarForaneosTabla(abm);
      
      if (campoForaneos != null){
        for (int j=0; j < campoForaneos.size(); j++) {
          aux = (Abm) campoForaneos.get(j);
          Abm buscarCampo = this.mi.getBuscarCampo(aux);
	  aux1.setTabla(aux.getTabla_foranea());
	  Abm buscarTabla1 = this.mi.getBuscarTabla1(aux1);
	  if (buscarTabla1 != null) {
	    if ((abm.getId_tabla() != buscarTabla1.getId_tabla())&&(!abm.getPadre())) {
              for (int k=0; k < tablas_dibrep.size(); k++){
	        Abm aux2 = new Abm();
	        aux2 = (Abm) tablas_dibrep.get(k);
	        if ((buscarTabla1.getId_tabla()==aux2.getId_tabla())&&(!aux2.getPadre())){
		  aux.setAlias(aux2.getAlias());
		  if ("".equals(sql_condiciones)) {
	            sql_condiciones = abm.getAlias()+ "." + buscarCampo.getCampo() + "=" + aux.getAlias() +"."+aux.getId_campo_foraneo() + " ";
	          } else {
	            sql_condiciones = sql_condiciones + " AND " + abm.getAlias()+ "." + buscarCampo.getCampo() + "=" + aux.getAlias() +"."+aux.getId_campo_foraneo() + " ";
	          }
		  break;
		}
	      }
	    } else {
	      if(!abm.getPadre()){
                for (int k=0; k < tablas_dibrep.size(); k++){
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
        if(valor_campo != null) {
          valor_campo = valor_campo.trim();
	  if (valor_campo.equals("")){
	  } else {
	    if(!valor_campo.equals("0")) {
	      if("".equals(sql_condiciones)) {
                sql_condiciones = abm.getAlias() + "." + aux.getCampo() + " " + condicion_campo +" '" + valor_campo + "'::" + aux.getTipo_dato();	    
	      } else {
                sql_condiciones = sql_condiciones + " AND " + abm.getAlias() + "." + aux.getCampo() + " " + condicion_campo + " '" + valor_campo + "'::" + aux.getTipo_dato();            	    
	      }  
            }
	  }
	}
      }
    }


    //LISTA A LOS QUE SERAN CAMPOS VARIABLES
    String variables[] = request.getParameterValues("variables");
    if (variables != null) {
        for(int i=0; i< variables.length ; i++) {
	 String vari[] = variables[i].split("_");
	 abm.setId_campo(Integer.parseInt(vari[1]));    
	 Abm buscarCampo = this.mi.getBuscarCampo(abm);
	 Abm buscarTabla = this.mi.getBuscarTabla(buscarCampo);
	 if ("".equals(sql_variables)) {
	   sql_variables= variables[i];  
	 } else {
	   sql_variables = sql_variables + ":" + variables[i];  
	 } 
       }
     }
    String limite = request.getParameter("limite");
    limite = limite.trim();
    try {
    int i = Integer.parseInt(limite);
    sql_limite=" LIMIT " + limite;      

    } catch (Exception ex){
    }
    
    if(!sql_total.equals("")){
      sql_total ="SELECT (" + sql_total + ")::text as valores FROM " + sql_tablas + " WHERE " + sql_estados; 
      if (!"".equals(sql_condiciones)){
        sql_total = sql_total + " AND " + sql_condiciones;
        if(!sql_variables.equals("")){
	  sql_total = sql_total + " ## ";
	}
      } else {
        if(!sql_variables.equals("")){
	  sql_total = sql_total + " ### ";
	}
      }
      sql_total = sql_total + ";";
      aux.setSql(sql_total);
      aux.setEtiquetas(etiquetas_total);
    }
    sql = "SELECT " + sql_campos2 + " FROM " + sql_tablas + " WHERE " + sql_estados;

    if (!"".equals(sql_condiciones)){
      sql = sql + " AND " + sql_condiciones;
      if (!sql_variables.equals("")){
      sql = sql + " ## ";
      }
    } else { 
      if (!sql_variables.equals("")){
        sql = sql + " ### ";
      }
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
    
    abm.setSql(sql);
    System.out.println("guardar SQL:" + abm.getSql());

    abm.setEtiquetas(etiquetas);


    abm.setId_campos(sql_variables);
    int resultado = 0;

    String glosa[] = request.getParameterValues("glosa");
    if (glosa!=null) {
      abm.setGlosa(true);    
    } else {
      abm.setGlosa(false);
    }
    
    String descripcion = request.getParameter("descripcion");
    descripcion = descripcion.trim();
    if(descripcion.equals(""))
      return new ModelAndView("Aviso", "mensaje","Falta colocar la descripción");
    
    abm.setDescripcion(descripcion);
    abm.setUlt_usuario(_id_usuario);
    if (headers!=null)
      for (int kk=0;kk<headers.length;kk++){
      if (header.equals(""))
        header = headers[kk];
      else 
        header = header + ":" + headers[kk];
      }
    abm.setCabezas(header);
    abm.setSumas(suma);
    
    if (sId_consulta.equals("")) 
      if (!sql_campos2.equals("")){
        resultado = this.mi.setInsertarConsulta(abm);
        aux.setId_consulta(resultado);
        if (!sql_total.equals("")){
          System.out.println("guardar sql_total sql:'" + aux.getSql() + "' id_consulta:" + aux.getId_consulta()+ " Etiquetas:" + aux.getEtiquetas());          
          int resultado1 = this.mi.setInsertarConsultaTotales(aux);
        }
        return new ModelAndView("Aviso", "mensaje","se grabo correctamente Su Consulta Nro=" + resultado);
      } else {
        return new ModelAndView("Aviso", "mensaje","No tiene Datos bien definidos");
      }
    else
      if (!sql_campos2.equals("")){
        abm.setId_consulta(Integer.parseInt(sId_consulta));
        resultado = this.mi.setModificarConsulta(abm);
        aux.setId_consulta(Integer.parseInt(sId_consulta));
        if (!sql_total.equals("")){
          System.out.println("guardar sql_total sql:'" + aux.getSql() + "' id_consulta:" + aux.getId_consulta()+ " Etiquetas:" + aux.getEtiquetas());          
          int resultado1 = this.mi.setInsertarConsultaTotales(aux);
        }
        return new ModelAndView("Aviso", "mensaje","se modificó correctamente Su Consulta Nro=" + resultado);
      } else {
        return new ModelAndView("Aviso", "mensaje","No tiene Datos bien definidos");
      }
  }
}

    