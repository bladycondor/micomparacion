package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.CategoriasDao;
import org.fautapo.domain.Categorias;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapCategoriasDao extends SqlMapClientDaoSupport implements CategoriasDao {

  public List getListarCategorias(Categorias categoria) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCategorias", categoria);
  }

}