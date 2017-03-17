package org.fautapo.dao.ibatis;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.AsignacionesDao;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Materias;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-07
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-07
*/

public class SqlMapAsignacionesDao extends SqlMapClientDaoSupport implements AsignacionesDao {

  public Asignaciones getDctBuscarAsignacionDocente(Asignaciones asignacion) throws DataAccessException {
    return (Asignaciones) getSqlMapClientTemplate().queryForObject("getDctBuscarAsignacionDocente", asignacion);
  }
  public Asignaciones getDctBuscarAsignacionDocentemaslafuncion(Asignaciones asignacion) throws DataAccessException {
    return (Asignaciones) getSqlMapClientTemplate().queryForObject("getDctBuscarAsignacionDocentemaslafuncion", asignacion);
  }
    
  public Asignaciones getDctBuscarAsignacionDocenteDesignacion(Asignaciones asignacion) throws DataAccessException {
    return (Asignaciones) getSqlMapClientTemplate().queryForObject("getDctBuscarAsignacionDocenteDesignacion", asignacion);
  }
  
  public List getMtrBuscarMateriaAhorro(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMtrBuscarMateriaAhorro", asignacion);
  }
 //Alex
  public List getDctListarAsignacionDocente(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocente", asignacion);
  }
 
  public List getMtrListarMateriaAhorro(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMtrListarMateriaAhorro", asignacion);
  }

  public Asignaciones getDctBuscarAsignacionDocenteMateria(Asignaciones asignacion) throws DataAccessException {
    return (Asignaciones) getSqlMapClientTemplate().queryForObject("getDctBuscarAsignacionDocenteMateria", asignacion);
  }
 //Fin Alex 
 
  public int setRegistrarAsignacionDocente(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarAsignacionDocente", asignacion);
    return i.intValue();
  }
   //para sacar el numero del siguiente memo
   public int getTrnBuscarSiguienteNroMemo(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getTrnBuscarSiguienteNroMemo", asignacion);
    return i.intValue();
  }
  public int getTrnBuscaridMemo(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getTrnBuscaridMemo", asignacion);
    return i.intValue();
  }
  // Fin para sacar el numero del siguiente memo
  public int setRegistrarAsignacionDocentefac(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarAsignacionDocentefac", asignacion);
    return i.intValue();
  }
   public int setRegistrarMemo(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarMemo", asignacion);
    return i.intValue();
  }
  
    public int setRegistrarFaseResolucion(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarFaseResolucion", asignacion);
    return i.intValue();
  }
  public int setRegistrarFaseResolucionfac(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarFaseResolucionfac", asignacion);
    return i.intValue();
  }
  public int setRegistrarFaseResolucionuni(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarFaseResolucionuni", asignacion);
    return i.intValue();
  }
  
  public int setmostrarplan(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setmostrarplan", asignacion);
    return i.intValue();
  }
  
   public int setRegistrarRetrocederFaseResolucion(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarRetrocederFaseResolucion", asignacion);
    return i.intValue();
  }
  
  public Asignaciones getDctVerificarAsignacionDocenteGestion(Asignaciones asignacion) throws DataAccessException {
    return (Asignaciones) getSqlMapClientTemplate().queryForObject("getDctVerificarAsignacionDocenteGestion", asignacion);
  }
  
  public List getListarDocentesProgramados(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarDocentesProgramados", asignacion);
  }
  
  //Listar dct_asignacion por programa
  public List getDctListarAsignacionDocenteProgramaPlan(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteProgramaPlan", asignacion);
  }
  
  //Buscar docente
  public List getListarAsignacionDocenteTodas(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarAsignacionDocenteTodas", asignacion);
  }
  
  //Listar dct_asignacion por programa_plan_tipo_grado
  public List getDctListarAsignacionDocenteProgramaPlanTipoGrado(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteProgramaPlanTipoGrado", asignacion);
  }  
  
  //Cerrar libretas Por Dct Asignacion
  public List getListarMateriasCerrarLibretaDctAsignacion(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasCerrarLibretaDctAsignacion", materia);
  }
  
  //Eliminar Asignacion
  public int setEliminarAsignacionDocenteMateria(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setEliminarAsignacionDocenteMateria", asignacion);
    return i.intValue();
  }
  
  //Devuelve id fase resolucion
  public int setBuscar_id_fase_resolucion(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setBuscar_id_fase_resolucion", asignacion);
    return i.intValue();
  }
  
  public int setBuscar_id_fase_resolucionFinal(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setBuscar_id_fase_resolucionFinal", asignacion);
    return i.intValue();
  }

//INICIO - METODOS ADICIONADOS POR LA UAP
  //Asignacion Docente - Materia
  public List getDctListarAsignacionDocenteMateria(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteMateria", asignacion);
  }
   public List getDctListarAsignacionDocenteMateriaFuncion(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteMateriaFuncion", asignacion);
  }
   public List getDctListarAsignacionDocenteMateriaFuncionxid(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteMateriaFuncionxid", asignacion);
  }
  public List getDctListarAsignacionDocenteMateriaFuncionsintitular(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteMateriaFuncionsintitular", asignacion);
  }
   public List getDctListarAsignacionDocenteMateriaFuncionsintitularfinal(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteMateriaFuncionsintitularfinal", asignacion);
  }
     public List getDctListarNroAsignacionDocenteMateriaFuncionxid(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarNroAsignacionDocenteMateriaFuncionxid", asignacion);
  }
    public List getDctListarAsignacionDocenteMateriaFuncionsoloid(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteMateriaFuncionsoloid", asignacion);
  }
  public List getDctListarAsignacionDocenteMateriaFuncionparamemo(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteMateriaFuncionparamemo", asignacion);
  }
  public List getDctListarAsignacionDocenteMateriacontador(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteMateriacontador", asignacion);
  }
  public List getDctListarAsignacionDocenteMateriatc(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionDocenteMateriatc", asignacion);
  }


  //Asignacion Auxiliar - Materia
  public List getDctListarAsignacionAuxiliarMateria(Asignaciones asignacion) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarAsignacionAuxiliarMateria", asignacion);
  }

  //asignacion auxiliares de Docencia
  public int setRegistrarAsignacionAuxiliar(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarAsignacionAuxiliar", asignacion);
    return i.intValue();
  }

  public Asignaciones getDctBuscarAsignacionAuxiliar(Asignaciones asignacion) throws DataAccessException {
    return (Asignaciones) getSqlMapClientTemplate().queryForObject("getDctBuscarAsignacionAuxiliar", asignacion); 
  }
  
  public int setEliminarAsignacionAuxiliarMateria(Asignaciones asignacion) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setEliminarAsignacionAuxiliarMateria", asignacion);
    return i.intValue(); 
  }
//FIN - METODOS ADICIONADOS POR LA UAP

}
