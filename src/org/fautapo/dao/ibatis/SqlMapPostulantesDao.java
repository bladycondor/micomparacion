package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.PostulantesDao;
import org.fautapo.domain.Postulantes;

public class SqlMapPostulantesDao extends SqlMapClientDaoSupport implements PostulantesDao {
  
  public Postulantes getPstBuscarPostulante(Postulantes postulante) throws DataAccessException {
    return (Postulantes) getSqlMapClientTemplate().queryForObject("getPstBuscarPostulante", postulante);
  }
  
  public Postulantes getPstBuscarPostulantePrograma(Postulantes postulante) throws DataAccessException {
    return (Postulantes) getSqlMapClientTemplate().queryForObject("getPstBuscarPostulantePrograma", postulante);
  } 
  
  public List getPstListarPostulantesNombres(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarPostulantesNombres", postulante);
  }  
  
  public List getPstListarPostulantesDip(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarPostulantesDip", postulante);
  }
  
  //Mi segunda parte
  //Registra Postulante
    // Listar - Jojo
  public List getMiListarPostulantesDip(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPostulantesDip", postulante);
  }

  public List getMiListarPostulantesNombre(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPostulantesNombre", postulante);
  }
  // Listar - Jojo
  //CRCB
  public int setMiRegistrarPstPersona(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setMiRegistrarPstPersona", postulante);
    return i.intValue();
  }
  
  public int setMiRegistrarPostulante(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setMiRegistrarPostulante", postulante);
    return i.intValue();
  }
  
  // aqui
  public int setMiRegistrarPostulanteC(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setMiRegistrarPostulanteC", postulante);
    return i.intValue();
  }
//
  public int setPstRegistrarDocumentos(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setPstRegistrarDocumentos", postulante);
    return i.intValue();
  }
  
  
  public Postulantes getPstBuscarPersona(Postulantes postulante) throws DataAccessException {
    return (Postulantes) getSqlMapClientTemplate().queryForObject("getPstBuscarPersona", postulante);
  }
  
  public List getListarTiposDocumentos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposDocumentos", null);
  }
  
  public List getListarTiposDocumentosClasificacionVigente(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposDocumentosClasificacionVigente", postulante);
  }
  
  public List getListarTiposClasificaciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposClasificaciones", null);
  }  
  
  public List getListarTiposClasificacionesPost() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposClasificacionesPost", null);
  }  

  public int setPstRegistrarMatricula(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setPstRegistrarMatricula", postulante);
    return i.intValue();
  }
  
  public List getListarPstMateriasProgramadas(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPstMateriasProgramadas", postulante);
  }
  
  public Postulantes getPstBuscarPostulanteNombres(Postulantes postulante) throws DataAccessException {
    return (Postulantes) getSqlMapClientTemplate().queryForObject("getPstBuscarPostulanteNombres", postulante);
  }
  
  public Postulantes getPstBuscarPostulanteNombresSede(Postulantes postulante) throws DataAccessException {
    return (Postulantes) getSqlMapClientTemplate().queryForObject("getPstBuscarPostulanteNombresSede", postulante);
  }
  
  public Postulantes getPstBuscarMatriculaPostulante(Postulantes postulante) throws DataAccessException {
    return (Postulantes) getSqlMapClientTemplate().queryForObject("getPstBuscarMatriculaPostulante", postulante);
  }
  
  public List getMiListarPstDipGestionPeriodo(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPstDipGestionPeriodo", postulante);
  }
  
  public List getMiListarPstNombreGestionPeriodo(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPstNombreGestionPeriodo", postulante);
  }

  public List getMiListarPstProgramaGestionPeriodo(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPstProgramaGestionPeriodo", postulante);
  }
  
  public List getMiListarPstProgramaGestionPeriodoSede(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPstProgramaGestionPeriodoSede", postulante);
  }
  //Fin REgistra Postulante
     public List getMiListarPstPsaGestionPeriodo(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPstPsaGestionPeriodo", postulante);
  }
   public List getRepAsistenciapostulantepsa(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepAsistenciapostulantepsa", postulante);
  }
 public int setRegistrarasignacion(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarasignacion", postulante);
    return i.intValue();
  }
  public int setPstModificarAsistenciaPostulante(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setPstModificarAsistenciaPostulante", postulante);
    return i.intValue();
  }
  public List getRepAsistenciapostulantepsaci(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepAsistenciapostulantepsaci", postulante);
  }
   public List getDctListarPostulantespsasoloid(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarPostulantespsasoloid", postulante);
  }
  public List getNroPostulantesPsa(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getNroPostulantesPsa", postulante);
  }
  public List getMiListarPstAprobadoDipGestionPeriodo(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPstAprobadoDipGestionPeriodo", postulante);
  }
  
  public List getMiListarPstAprobadoNombreGestionPeriodo(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPstAprobadoNombreGestionPeriodo", postulante);
  }
  
  public int setPstModificarEstadoPostulante(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setPstModificarEstadoPostulante", postulante);
    return i.intValue();
  }
  
  //Pst PErsonas
  public List getPstListarPersonasNombre(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarPersonasNombre", postulante);
  }
  
  public List getPstListarPersonasDip(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarPersonasDip", postulante);
  }
  
  public List getMiListarPostulantesPorPersona(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPostulantesPorPersona", postulante);
  }
  
  public int setRegistrarPstPrsColegio(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarPstPrsColegio", postulante);
    return i.intValue();
  }
  
  public Postulantes getBuscarPstPersonaColegio(Postulantes postulante) throws DataAccessException {
    return (Postulantes) getSqlMapClientTemplate().queryForObject("getBuscarPstPersonaColegio", postulante);
  }

  // inicio JOJO
  public Postulantes getMiPrsBuscarPostulante(Postulantes postulante) throws DataAccessException {
    return (Postulantes) getSqlMapClientTemplate().queryForObject("getMiPrsBuscarPostulante", postulante);
  }

  public void setPstRegistrarPrograma(Postulantes postulante) throws DataAccessException {
    getSqlMapClientTemplate().queryForObject("setPstRegistrarPrograma", postulante);
  }
  // fin JOJO
  //Estadistica
  public List getNroPstInscritosHabilitados(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getNroPstInscritosHabilitados", postulante);
  }
 
  public List getNroPstInscritosHabilitadosTipoAdmision(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getNroPstInscritosHabilitadosTipoAdmision", postulante);
  } 
  
  //Programa anterios postulante
  public int setPstRegistrarProgramaAnterior(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setPstRegistrarProgramaAnterior", postulante);
    return i.intValue();
  }

  //Listado de Postulantes Inscritos
  public List getPstListarInscritosPorProgramaTipoAdmision(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarInscritosPorProgramaTipoAdmision", postulante);
  }

  //Listado de Postulantes Aprobados
  public List getPstListarAprobadosPorProgramaTipoAdmision(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarAprobadosPorProgramaTipoAdmision", postulante);
  }

  //Listado de Postulantes Reprobados
  public List getPstListarReprobadosPorProgramaTipoAdmision(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarReprobadosPorProgramaTipoAdmision", postulante);
  }
 
 //Buscar Postulantes
 public List getMiListarPostulantesDipTipoAdm(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPostulantesDipTipoAdm", postulante);
  }

  public List getMiListarPostulantesNombreTipoAdm(Postulantes postulante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMiListarPostulantesNombreTipoAdm", postulante);
  } 

//INICIO - METODOS ADICIONADOS POR LA UAP
 //Insertar pst_personas para tramites
  public int setMiRegistrarPstPersonaTrn(Postulantes postulante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setMiRegistrarPstPersonaTrn", postulante);
    return i.intValue();
  }
//FIN - METODOS ADICIONADOS POR LA UAP

}