package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.ProveidosDao;
import org.fautapo.domain.Proveidos;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-27
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-27
*/

public class SqlMapProveidosDao extends SqlMapClientDaoSupport implements ProveidosDao {

  public int setRegistrarProveido(Proveidos proveido) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarProveido", proveido);
    return i.intValue();
  }

  public Proveidos getBuscarUltimoProveido(Proveidos proveido) throws DataAccessException {
    return (Proveidos) getSqlMapClientTemplate().queryForObject("getBuscarUltimoProveido", proveido);
  }

  public String getBuscarUltimoProveido2(Proveidos proveido) throws DataAccessException {
    String cadena = (String) getSqlMapClientTemplate().queryForObject("getBuscarUltimoProveido2", proveido);
    return cadena;
  }

  public List getListarProveidosHistoricos(Proveidos proveido) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarProveidosHistoricos", proveido);
  }

  public Proveidos getBuscarProveido(Proveidos proveido) throws DataAccessException {
    return (Proveidos) getSqlMapClientTemplate().queryForObject("getBuscarProveido", proveido);
  }

  public Proveidos getBuscarProveidoCorresp(Proveidos proveido) throws DataAccessException {
    return (Proveidos) getSqlMapClientTemplate().queryForObject("getBuscarProveidoCorresp", proveido);
  }

}