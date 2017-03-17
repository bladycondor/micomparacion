package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.HorariosDao;
import org.fautapo.domain.Horarios;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapHorariosDao extends SqlMapClientDaoSupport implements HorariosDao {


 public List getListarDias() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarDias", null);
  }

  public List getListarHorario(Horarios horario) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarHorario", horario);
  }

  public List getListarAulasDisponibles(Horarios horario) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarAulasDisponibles", horario);
  }

  public void setHrsLimpiarHorarioMateria(Horarios horario) throws DataAccessException {
    getSqlMapClientTemplate().queryForObject("setHrsLimpiarHorarioMateria", horario);
  }

  public int setHrsRegistrarHorarioAula(Horarios horario) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setHrsRegistrarHorarioAula", horario);
    return i.intValue();
  }


}