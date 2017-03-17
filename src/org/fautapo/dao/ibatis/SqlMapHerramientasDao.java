package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.HerramientasDao;
import org.fautapo.domain.Herramientas;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapHerramientasDao extends SqlMapClientDaoSupport implements HerramientasDao {

  public List getListarCombosPagina(Herramientas herramienta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCombosPagina", herramienta);
  }

}