package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.EnlacesDao;
import org.fautapo.domain.Enlaces;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapEnlacesDao extends SqlMapClientDaoSupport implements EnlacesDao {

  public List getListarEnlaces(Enlaces enlace) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarEnlaces", enlace);
  }

  public Enlaces getBuscarEnlace(Enlaces enlace) throws DataAccessException {
    return (Enlaces) getSqlMapClientTemplate().queryForObject("getBuscarEnlaces", enlace);
  }

  // INICIO Combustible \\
  public Enlaces getEnlBuscarEnlace(Enlaces enlace) throws DataAccessException {
    return (Enlaces) getSqlMapClientTemplate().queryForObject("getEnlBuscarEnlace", enlace);
  }
  // FIN Combustible \\

  // INICIO - MI 
  //Listar estados por tabla
  public List getStdListarEstadosTabla(Enlaces enlace) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getStdListarEstadosTabla", enlace);
  }
  // FIN - MI 

}