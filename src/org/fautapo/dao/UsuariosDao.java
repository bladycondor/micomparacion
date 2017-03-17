package org.fautapo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.fautapo.domain.Usuarios;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-22
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-29
*/

public interface UsuariosDao {

  Usuarios getBuscarUsuario(Usuarios usuario) throws DataAccessException;  
  List getListarUsuarios(Usuarios usuario) throws DataAccessException;
  List getListarUsuariosUbicacionOrganica(Usuarios usuario) throws DataAccessException;

  int getVerificarUsuario(Usuarios usuario) throws DataAccessException;
  int setRegistrarNuevaClave(Usuarios usuario) throws DataAccessException;
  
  int setRegistrarUsuario(Usuarios usuario) throws DataAccessException;
  int setEliminarUsuario(Usuarios usuario) throws DataAccessException;
  
}