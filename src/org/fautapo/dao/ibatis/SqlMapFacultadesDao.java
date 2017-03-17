package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.FacultadesDao;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Universidades;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapFacultadesDao extends SqlMapClientDaoSupport implements FacultadesDao {

  public Facultades getFclBuscarFacultad(Facultades facultad) throws DataAccessException {
    return (Facultades) getSqlMapClientTemplate().queryForObject("getFclBuscarFacultad", facultad);
  }

  public List getUnvListarFacultades(Universidades universidad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getUnvListarFacultades", universidad);
  }

  public List getUnvListarFacultadesPost(Universidades universidad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getUnvListarFacultadesPost", universidad);
  }

}