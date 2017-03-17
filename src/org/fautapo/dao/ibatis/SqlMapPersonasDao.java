package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.PersonasDao;
import org.fautapo.domain.Personas;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-23
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-23
*/

public class SqlMapPersonasDao extends SqlMapClientDaoSupport implements PersonasDao {

  public Personas getBuscarPersonaUsuario(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getBuscarPersonaUsuario", persona);
  }

  public Personas getBuscarPersona(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getBuscarPersona", persona);
  }

  // INICIO - MI
  public Personas getPrsBuscarPersona(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getPrsBuscarPersona", persona);
  }
  // FIN - MI
  
  //Inicio MI segunda Parte
  
   //Listar Paises
  public List getListarPaises() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPaises", null);
  }
  
  public List getListarDepartamentos(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarDepartamentos",  persona);
  }
  
  public List getListarProvincias(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarProvincias",  persona);
  }
  
  public List getListarLocalidades(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarLocalidades",  persona);
  }
  
  public List getListarLocalidadesTodas() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarLocalidadesTodas", null);
  }
  
  //Listar Tipos
  public List getListarTiposSexos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposSexos", null);
  }
  
  public List getListarTiposEstadosCiviles() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposEstadosCiviles", null);
  }
  
  public List getListarTiposEmpresasTelef() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposEmpresasTelef", null);
  }
  
  public List getListarTiposEstudiantes() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposEstudiantes", null);
  }
  
  public Personas getBuscarTipoEstudiante(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getBuscarTipoEstudiante", persona);
  }
  
  public List getListarTiposGraduaciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposGraduaciones", null);
  }
  
  public List getListarTiposInstituciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposInstituciones", null);
  }
  
  public List getListarColegiosTipoIns(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarColegiosTipoIns", persona);
  }
  
  public List getListarTiposTurnos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposTurnos", null);
  }
  
  public List getListarTiposProblemasRol(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposProblemasRol", persona);
  }
  
  //Registrar
  public int setRegistrarPersona(Personas persona) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarPersona", persona);
    return i.intValue();
  }
  
   public int setRegistrarPrsColegio(Personas persona) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarPrsColegio", persona);
    return i.intValue();
  }
  
  public int setRegistrarPrsClasificacion(Personas persona) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarPrsClasificacion", persona);
    return i.intValue();
  }
  
  public int setRegistrarPrsDocumentos(Personas persona) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarPrsDocumentos", persona);
    return i.intValue();
  }
  
  public int setRegistrarPrsCompromisos(Personas persona) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarPrsCompromisos", persona);
    return i.intValue();
  }
  
  public List getListarPrsDocumentosPersona(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPrsDocumentosPersona", persona);
  }
  
  public Personas getBuscarTipoClasificacionPersona(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getBuscarTipoClasificacionPersona", persona);
  }
  
  public List getListarTiposCompromisos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposCompromisos", null);
  }
  
  public Personas getBuscarPersonaColegio(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getBuscarPersonaColegio", persona);
  }
  //Persona
  public List getPrsListarPersonasDip(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPrsListarPersonasDip", persona);
  }
  
  //Persona items
  public List getListarItemsPersonasDip(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarItemsPersonasDip", persona);
  }
  
  public Personas getBuscarItemPersona(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getBuscarItemPersona", persona);
  }
  
  public Personas getBuscarItemsUsuario(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getBuscarItemsUsuario", persona);
  }
 
  //Listar PrsCompromisos 
  public List getListarPrsCompromisosPersona(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPrsCompromisosPersona", persona);
  }
  
  public List getListarPrsDocumentosClasificacion(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPrsDocumentosClasificacion", persona);
  }

  public int getBuscarPrsDocumentacionCompleta(Personas persona) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getBuscarPrsDocumentacionCompleta", persona);
  return i.intValue();
  }  
	
  //Fin Listar PrsCompromisos 
  //Fin MI segunda Parte

  public Personas getMiBuscarCompromiso(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getMiBuscarCompromiso", persona);
  }

  public int getMiPrsNroCompromisos(Personas persona) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getMiPrsNroCompromisos", persona);
    return i.intValue();
  }
  
  //Listar Personas
  public List getListarPersonas(Personas persona) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPersonas", persona);
  }

//INICIO - METODOS ADICIONADOS POR LA UAP
  public Personas getEstBuscarEstudianteDocente(Personas persona) throws DataAccessException {
    return (Personas) getSqlMapClientTemplate().queryForObject("getEstBuscarEstudianteDocente", persona);
  }

  // Listado de Curso de Preparatoria de Ingles Estudiantes Otros
  public List  getListarCursoPreInglesOtros(Personas persona) throws DataAccessException{
    return getSqlMapClientTemplate().queryForList("getListarCursoPreInglesOtros", persona);
  }

  public List  getListarCursoPsicoOtros(Personas persona) throws DataAccessException{ 
    return getSqlMapClientTemplate().queryForList("getListarCursoPsicoOtros", persona);
  }
//FIN - METODOS ADICIONADOS POR LA UAP

}