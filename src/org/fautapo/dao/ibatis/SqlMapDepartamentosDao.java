package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.DepartamentosDao;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Universidades;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapDepartamentosDao extends SqlMapClientDaoSupport implements DepartamentosDao {

  public Departamentos getDptBuscarDepartamento(Departamentos departamento) throws DataAccessException {
    return (Departamentos) getSqlMapClientTemplate().queryForObject("getDptBuscarDepartamento", departamento);
  }

  public List getFclListarDepartamentos(Facultades facultad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getFclListarDepartamentos", facultad);
  }

  public List getUnvListarDepartamentos(Universidades universidad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getUnvListarDepartamentos", universidad);
  }

}