package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.CalendariosDao;
import org.fautapo.domain.Calendarios;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapCalendariosDao extends SqlMapClientDaoSupport implements CalendariosDao {

  public List getListarCalendarios(Calendarios calendario) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCalendarios", calendario);
  }

}