package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.DibwaykaDao;
import org.fautapo.domain.Dibwayka;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapDibwaykaDao extends SqlMapClientDaoSupport implements DibwaykaDao {

  public List getListarCamposProcesoWK(Dibwayka dibwayka) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposProcesoWK", dibwayka);
  }

  public List getListarComboWK(Dibwayka dibwayka) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarComboWK",dibwayka);
  }

  public int setCrearTablasDibWK(Dibwayka dibwayka) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setCrearTablasDibWK", dibwayka);
    return i.intValue();
  }

  public List getListarCamposDibWK(Dibwayka dibwayka) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposDibWK",dibwayka);
  }

  public Dibwayka getBuscarTablaDibWK(Dibwayka dibwayka) throws DataAccessException {
    return (Dibwayka) getSqlMapClientTemplate().queryForObject("getBuscarTablaDibWK", dibwayka);
  }
 
 public Dibwayka getBuscarCampoDibWK(Dibwayka dibwayka) throws DataAccessException {
    return (Dibwayka) getSqlMapClientTemplate().queryForObject("getBuscarCampoDibWK", dibwayka);
 }

 public Dibwayka getBuscarTuplaDibWK(Dibwayka dibwayka) throws DataAccessException {
    return (Dibwayka) getSqlMapClientTemplate().queryForObject("getBuscarTuplaDibWK", dibwayka);
 }


  public int setInsertarConsultaDibWK(Dibwayka dibwayka) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setInsertarConsultaDibWK", dibwayka);
    return i.intValue();
  }

  public List getListarCondicionesConsultaDibWK(Dibwayka dibwayka) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCondicionesConsultaDibWK", dibwayka);
  }

  public List getListarConsultasDibWK() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarConsultasDibWK", null);
  }

  public int setBorrarConsultaDibWK(Dibwayka dibwayka) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setBorrarConsultaDibWK", dibwayka);
    return i.intValue();
  }

  public int setModificarConsultaDibWK(Dibwayka dibwayka) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setModificarConsultaDibWK", dibwayka);
    return i.intValue();
  }

  public List getConsultaCondicionDibWK(Dibwayka dibwayka) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getConsultaCondicionDibWK", dibwayka);
  }

  public Dibwayka getBuscarConsultaDibWK(Dibwayka dibwayka) throws DataAccessException {
    return (Dibwayka) getSqlMapClientTemplate().queryForObject("getBuscarConsultaDibWK", dibwayka);
  }

}