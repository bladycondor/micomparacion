package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.AbmDao;
import org.fautapo.domain.Abm;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapAbmDao extends SqlMapClientDaoSupport implements AbmDao {

  public List getListarTablas() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTablas", null);
  }

  public Abm getBuscarTabla(Abm abm) throws DataAccessException {
    return (Abm) getSqlMapClientTemplate().queryForObject("getBuscarTabla", abm);
  }

  public List getListarCamposTabla(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposTabla", abm);
  }

  public int setEjecutarConsulta(Abm abm) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setEjecutarConsulta", abm);
    return i.intValue();
  }

  public List getEjecutarListado(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEjecutarListado", abm);
  }

  //  INICIO JOJO  \\
  public List getEjecutarListado2(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEjecutarListado2", abm);
  }

  public String getDibContadorClasico(Abm abm) throws DataAccessException {
    System.out.println("tabla-----"+abm.getTabla());
    System.out.println("condicion-----"+abm.getCondicion());
    return (String) getSqlMapClientTemplate().queryForObject("getDibContadorClasico", abm);
  }

  public String getDibBuscarParametro(Abm abm) throws DataAccessException {
    return (String) getSqlMapClientTemplate().queryForObject("getDibBuscarParametro", abm);
  }

  public List getListarRegistros(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarRegistros", abm);
  }

  public int setInsertarDatos(Abm abm) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setInsertarDatos", abm);
    return i.intValue();
  }

  public List getListarCombos(Abm abm) throws DataAccessException {
    System.out.println("tabla_foranea----"+abm.getTabla_foranea());
    System.out.println("campo----"+abm.getCampo());
    System.out.println("condicion----"+abm.getCondicion());
    return getSqlMapClientTemplate().queryForList("getListarCombos", abm);
  }

  public Abm getBuscarForanea(Abm abm) throws DataAccessException {
    return (Abm) getSqlMapClientTemplate().queryForObject("getBuscarForanea", abm);
  }

  public Abm getBuscarCampoTabla(Abm abm) throws DataAccessException {
    return (Abm) getSqlMapClientTemplate().queryForObject("getBuscarCampoTabla", abm);
  }

  public int getContarDependientes(Abm abm) throws DataAccessException {
    return ((Integer) getSqlMapClientTemplate().queryForObject("getContarDependientes", abm)).intValue();
  }

    //  INICIO huaica  \\
  public List getListarCamposTablaActividad(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposTablaActividad", abm);
  }

  public List getEjecutarListado3(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEjecutarListado3", abm);
  }

  public List getListarRegistrosActividad(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarRegistrosActividad", abm);
  }
    //  FIN huaica  \\

    //  INICIO combustible  \\
  public List getEnlListarCamposTabla(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEnlListarCamposTabla", abm);
  }

  public List getEnlEjecutarListado(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEnlEjecutarListado", abm);
  }

  public List getEnlListarRegistros(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEnlListarRegistros", abm);
  }
    //  FIN combustible  \\

  //  FIN JOJO  \\

  public List getListarCamposCondicion(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposCondicion", abm);
  }

  public Abm getBuscarCampo(Abm abm) throws DataAccessException {
    return (Abm) getSqlMapClientTemplate().queryForObject("getBuscarCampo", abm);
  }

  public List getListarForaneosTabla(Abm abm) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarForaneosTabla", abm);
  }

  public Abm getBuscarTabla1(Abm abm) throws DataAccessException {
    return (Abm) getSqlMapClientTemplate().queryForObject("getBuscarTabla1", abm);
  }

  public int setInsertarConsulta(Abm abm) throws DataAccessException {
     Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setInsertarConsulta", abm);
    return i.intValue();
  }

  public Abm getBuscarConsulta(Abm abm) throws DataAccessException {
    return (Abm) getSqlMapClientTemplate().queryForObject("getBuscarConsulta", abm);
  }

  public int setInsertarConsultaTotales(Abm abm) throws DataAccessException {
     Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setInsertarConsultaTotales", abm);
    return i.intValue();
  }

  public Abm getBuscarConsultaTotales(Abm abm) throws DataAccessException {
    return (Abm) getSqlMapClientTemplate().queryForObject("getBuscarConsultaTotales", abm);
  }

  public List getListarConsultas() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarConsultas", null);
  }

  public Abm getBuscarCampoTabla1(Abm abm) throws DataAccessException {
    return (Abm) getSqlMapClientTemplate().queryForObject("getBuscarCampoTabla1", abm);
  }

  public int setBorrarConsulta(Abm abm) throws DataAccessException {
     Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setBorrarConsulta", abm);
    return i.intValue();
  }

  public int setModificarConsulta(Abm abm) throws DataAccessException {
     Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setModificarConsulta", abm);
    return i.intValue();
  }
  
  public String setDibInsertarRegistro(Abm abm) throws DataAccessException {
        return (String) getSqlMapClientTemplate().queryForObject("setDibInsertarRegistro", abm);
  }
  
}