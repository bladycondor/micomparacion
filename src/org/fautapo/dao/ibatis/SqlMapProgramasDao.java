package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.ProgramasDao;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Usuarios;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapProgramasDao extends SqlMapClientDaoSupport implements ProgramasDao {

  public Programas getPrgBuscarPrograma(Programas programa) throws DataAccessException {
    return (Programas) getSqlMapClientTemplate().queryForObject("getPrgBuscarPrograma", programa);
  }

  public List getFclListarProgramas(Facultades facultad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getFclListarProgramas", facultad);
  }

  public List getUnvListarProgramas(Universidades universidad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getUnvListarProgramas", universidad);
  }
  
  public List getUnvListarProgramasPost(Universidades universidad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getUnvListarProgramasPost", universidad);
  }

  //AQUI EST PROGRAMACIONES 
 public Programas getPrdBuscarPrgPeriodo(Programas programa) throws DataAccessException {
    return (Programas) getSqlMapClientTemplate().queryForObject("getPrdBuscarPrgPeriodo", programa);
  }
  
 public List getPrgBuscarDetalles(Programas programa) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPrgBuscarDetalles", programa);
  }
  
 public Programas getMdlBuscarMateriaAhorro(Programas programa) throws DataAccessException {
    return (Programas) getSqlMapClientTemplate().queryForObject("getMdlBuscarMateriaAhorro", programa);
  }
  
 public Programas setEstProgramacionMateria(Programas programa) throws DataAccessException {
    return (Programas) getSqlMapClientTemplate().queryForObject("setEstProgramacionMateria", programa);
  }    
 
  
 public int setPstProgramacionMateria(Programas programa) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setPstProgramacionMateria", programa);
    return i.intValue();
  }       

 public List getTpsListarProgramaciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTpsListarProgramaciones", null);
 }
  
 public Programas getTpsBuscarProgramacion(Programas programa) throws DataAccessException {
    return (Programas) getSqlMapClientTemplate().queryForObject("getTpsBuscarProgramacion", programa);
 } 

  //ADMINISTRAR HORARIOS
  public List getListarProgramasAcceso(Usuarios usuario) throws DataAccessException {
     return getSqlMapClientTemplate().queryForList("getListarProgramasAcceso", usuario);
  }  
  
 public List getMtrListarPlanesPrograma(Programas programa) throws DataAccessException {
     return getSqlMapClientTemplate().queryForList("getMtrListarPlanesPrograma", programa);
  }  
  //FIN ADMINISTRAR HORARIOS  
  
 //PROGRAMACIONES COMO ESTUDIANTE
 public int getBuscarNivelMaximoPlanesEst(Programas programa) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getBuscarNivelMaximoPlanesEst", programa);
    return i.intValue();
 }
 
 public List getZchListarChoqueMaterias(Programas programa) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getZchListarChoqueMaterias", programa);
 }
 
 public List getZchListarChoquePeriodos(Programas programa) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getZchListarChoquePeriodos", programa);
 }
 
 public List setEstListarProgramarMaterias(Programas programa) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("setEstListarProgramarMaterias", programa);
 }
 //FIN PROGRAMACIONES COMO ESTUDIANTE
 //RETIRO ADICION DE MATERIAS
 public List getEstListarProgramacionesEstudiante(Programas programa) throws DataAccessException {
   return getSqlMapClientTemplate().queryForList("getEstListarProgramacionesEstudiante", programa);
 }
 
 public List setEstPrgRetirarProgramacionesMaterias(Programas programa) throws DataAccessException {
   return getSqlMapClientTemplate().queryForList("setEstPrgRetirarProgramacionesMaterias", programa);
 }
 
  public List setEstPrgRegistrarListarCambiarGrupos(Programas programa) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("setEstPrgRegistrarListarCambiarGrupos", programa);
  }
  //FIN RETIRO ADICION DE MATERIAS
 
  //MI SEGUNDA PARTE
  public List getListarTiposAdmisiones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposAdmisiones", null);
  }
 
  public List getListarTiposAdmisionesPost() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposAdmisionesPost", null);
  }

  public List getListarTiposAdmisionesPrograma(Programas programa) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposAdmisionesPrograma", programa);
  }
  public List getListarTiposAdmisionesProgramaLiberacion(Programas programa) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposAdmisionesProgramaLiberacion", programa);
  }
 
  //Buscar programa de estudiante
  public Programas getPrgBuscarProgramaEstudiante(Programas programa) throws DataAccessException {
    return (Programas) getSqlMapClientTemplate().queryForObject("getPrgBuscarProgramaEstudiante", programa);
  }
  //fin Buscar programa de estudiante
 
  public List getListarGradosProgramas(Programas programa) throws DataAccessException { 
    return getSqlMapClientTemplate().queryForList("getListarGradosProgramas", programa);
  }

 // FIN MI SEGUNDA PARTE
 

}