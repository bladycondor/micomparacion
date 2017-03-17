package org.fautapo.dao.ibatis;

import java.util.*;
import java.io.*;


import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.CurriculumDao;
import org.fautapo.domain.*;



public class SqlMapCurriculumDao extends SqlMapClientDaoSupport implements CurriculumDao {

  public List cvGetListarRubrosPersona(Curriculum curriculum) throws DataAccessException { return getSqlMapClientTemplate().queryForList("cvGetListarRubrosPersona", curriculum);}
  public List cvGetListarRubros(Curriculum curriculum) throws DataAccessException { return getSqlMapClientTemplate().queryForList("cvGetListarRubros", curriculum);}
  public List cvGetListarSubRubros(Curriculum curriculum) throws DataAccessException { return getSqlMapClientTemplate().queryForList("cvGetListarSubRubros", curriculum);}
  public int cvSetRegistrarCurriculum(Curriculum curriculum) throws DataAccessException {return ((Integer) getSqlMapClientTemplate().queryForObject("cvSetRegistrarCurriculum", curriculum)).intValue(); }
  //Adjuntos Dct
  public int setRegistrarDctAdjuntos(Curriculum curriculum) throws DataAccessException {return ((Integer) getSqlMapClientTemplate().queryForObject("setRegistrarDctAdjuntos", curriculum)).intValue(); }
  public List getListarAdjuntosDocente(Curriculum curriculum) throws DataAccessException { return getSqlMapClientTemplate().queryForList("getListarAdjuntosDocente", curriculum);}
  public int setEliminarDctAdjunto(Curriculum curriculum) throws DataAccessException {return ((Integer) getSqlMapClientTemplate().queryForObject("setEliminarDctAdjunto", curriculum)).intValue(); }

}



