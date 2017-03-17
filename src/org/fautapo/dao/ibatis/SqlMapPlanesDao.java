package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.PlanesDao;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Modelos_ahorros;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapPlanesDao extends SqlMapClientDaoSupport implements PlanesDao {

  public List getPrgListarPlanes(Programas programa) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPrgListarPlanes", programa);
  }

  public List getFclListarPlanes(Facultades facultad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getFclListarPlanes", facultad);
  }

  public List getUnvListarPlanes(Universidades universidad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getUnvListarPlanes", universidad);
  }
  
  public List getUnvGrdListarPlanes(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getUnvGrdListarPlanes", plan);
  }
  
  public List getPlnListarMateriasNivel(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPlnListarMateriasNivel", plan);
  }

  public int getPlnListarNroNiveles(Planes plan) throws DataAccessException {
    return ((Integer) getSqlMapClientTemplate().queryForObject("getPlnListarNroNiveles", plan)).intValue();
  }

  public List getPlnListarMateriasRequisitos(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPlnListarMateriasRequisitos", plan);
  }
   
  public List getPlnListarMateriasNroRequisitos(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPlnListarMateriasNroRequisitos", plan);
  }

  //ADMINISTRAR HORARIOS
  public List getListarPlanProgramaModeloAhorro(Modelos_ahorros materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPlanProgramaModeloAhorro", materia);
  }
  
  public Planes getMtrBuscarPlanPrograma(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getMtrBuscarPlanPrograma", plan);
  }
  //FIN ADMINISTRAR HORARIOS
  
  //PGRAMACIONES COMO ESTUDIANTE
  public List getMncListarMenciones(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMncListarMenciones", plan);
  }
 
  public int setEstRegistrarMencionEstudiante(Planes plan) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setEstRegistrarMencionEstudiante", plan);
    return i.intValue();
  }
  //FIN PGRAMACIONES COMO ESTUDIANTE
 
  // MI SEGUNDA PARTE
  public List getListarTiposGrados() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposGrados", null);
  }
  
  public Planes getBuscarTiposGrados(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getBuscarTiposGrados", plan);
  }
  
  public List getListarPrgPlanesActual(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPrgPlanesActual", plan);
  }  
 
  //  FIN MI SEGUNDA PARTE
  // INICIO - Convalidacion Automatica
  public List getListarMateriasPlanGrupo(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPlanGrupo", plan);
  }
   
  public List getListarMateriasPlanGrupoCantidad(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPlanGrupoCantidad", plan);
  }
   
  public List getListarMateriasPlan(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPlan", plan);
  }
   
  public List getListarMateriasPlanAnterior(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPlanAnterior", plan);
  }
   
  public List getListarMateriasPlanAnterior2(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPlanAnterior2", plan);
  }
   
  public List getListarMateriasPlanConvalidado(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPlanConvalidado", plan);
  }
   
  public int setRegistrarMtrPlan(Planes plan) throws DataAccessException {
    return ((Integer) getSqlMapClientTemplate().queryForObject("setRegistrarMtrPlan", plan)).intValue();
  }
  
  public int setEliminarMtrPlan(Planes plan) throws DataAccessException {
    return ((Integer) getSqlMapClientTemplate().queryForObject("setEliminarMtrPlan", plan)).intValue();
  }
  
  public Planes getMncBuscarMencion(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getMncBuscarMencion", plan);
  }

  public Planes getBuscarMateriaPlan(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getBuscarMateriaPlan", plan);
  }
  // FIN - Convalidacion Automatica

  //INICIO- Admin Planes de Estudio
  public List getListarMateriasPlanRequisitos(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPlanRequisitos", plan);
  }

  public List getListarMateriasElectivasPlan(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasElectivasPlan", plan);
  }

  public List getListarMateriasPlanMencion(Planes plan) throws DataAccessException {
    System.out.println("id_programa---------"+plan.getId_mencion());
    System.out.println("id_plan---------"+plan.getId_plan());
    System.out.println("id_tipo_grado---------"+plan.getId_tipo_grado());
    System.out.println("id_mencion---------"+plan.getId_mencion());
    return getSqlMapClientTemplate().queryForList("getListarMateriasPlanMencion", plan);
  }

  public List getListarMateriasRequisitos(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasRequisitos", plan);
  }
   
  public List getListarMateriasNoRequisitos(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasNoRequisitos", plan);
  }

  public List getListarMateriasConvalidadas(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasConvalidadas", plan);
  }
   
  public List getListarMateriasNoConvalidadas(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasNoConvalidadas", plan);
  }

  public List getListarMateriasNoPlan(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasNoPlan", plan);
  }

  public List getPlnListarTiposMaterias() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPlnListarTiposMaterias", null);
  }

  public Planes getPlnBuscarTipoMateria(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getPlnBuscarTipoMateria", plan);
  }

  public Planes getBuscarPrgPlan(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getBuscarPrgPlan", plan);
  }

  public Planes getBuscarPrgPlan2(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getBuscarPrgPlan2", plan);
  }
 

  public Planes getBuscarMtrPlan(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getBuscarMtrPlan", plan);
  }

  public int setModificarMtrPlan(Planes plan) throws DataAccessException {
    return ((Integer) getSqlMapClientTemplate().queryForObject("setModificarMtrPlan", plan)).intValue();
  }
  //FIN - Admin Planes de Estudio
  
  public List getListarPrgPlanesVestibulares() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPrgPlanesVestibulares", null);
  }
  
  public List getListarPrgPlanesUniversitarios() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPrgPlanesUniversitarios", null);
  }
  
  public Planes getBuscarMaxPrgPlanActual(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getBuscarMaxPrgPlanActual", plan);
  }
  
  public List getListarMateriasPlanTipoGrado(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPlanTipoGrado", plan);
  }
  
  //Convalidacion Manual
  public List getListarTiposConvalidaciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposConvalidaciones", null);
  }
  
  public Planes getBuscarTipoConvalidacion(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getBuscarTipoConvalidacion", plan);
  }
  
  public int setRegistrarConvalidacionManual(Planes plan) throws DataAccessException {
    return ((Integer) getSqlMapClientTemplate().queryForObject("setRegistrarConvalidacionManual", plan)).intValue();
  }
  
  public int setRegistrarDetallesConvalidacionManual(Planes plan) throws DataAccessException {
    return ((Integer) getSqlMapClientTemplate().queryForObject("setRegistrarDetallesConvalidacionManual", plan)).intValue();
  }
  //Fin Convalidacion Manual
  
  //Autorizar Convalidacion Manual
  public List getListarConvalidacionManualPrograma(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarConvalidacionManualPrograma", plan);
  }
  
  public Planes getBuscarConvalidacionManual(Planes plan) throws DataAccessException {
    return (Planes) getSqlMapClientTemplate().queryForObject("getBuscarConvalidacionManual", plan);
  }
  
  public List getListarCnvDetallesConvalidacion(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCnvDetallesConvalidacion", plan);
  }
  
  public List getListarNotasCruceCnvDetalles(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarNotasCruceCnvDetalles", plan);
  } 
  
  public int setRegistrarEstNotasConvalidacionManual(Planes plan) throws DataAccessException {
    return ((Integer) getSqlMapClientTemplate().queryForObject("setRegistrarEstNotasConvalidacionManual", plan)).intValue();
  }
  
  public int setEliminarCnvDetalle(Planes plan) throws DataAccessException {
    return ((Integer) getSqlMapClientTemplate().queryForObject("setEliminarCnvDetalle", plan)).intValue();
  }
  //Fin Autorizar Convalidacion Manual

}