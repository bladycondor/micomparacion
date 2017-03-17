CREATE or replace FUNCTION mi_grp_registrar_evaluacion(integer, integer, integer, integer, integer, integer, integer, integer, integer, integer, integer, integer, integer, integer) RETURNS integer AS $_$
declare
  _id_materia          alias for $1;
  _id_grupo            alias for $2;
  _id_modelo_ahorro    alias for $3;
  _id_tipo_nota        alias for $4;
  _id_fase             alias for $5;
  _id_departamento     alias for $6;
  _id_tipo_evaluacion  alias for $7;
  _id_tipo_docente     alias for $8;
  _gestion             alias for $9;
  _periodo             alias for $10;
  _cantidad            alias for $11;
  _ponderacion         alias for $12;
  _id_rol              alias for $13;
  _ult_usuario         alias for $14;
begin

  PERFORM *
  FROM grp_evaluaciones 
  WHERE id_tipo_evaluacion = _id_tipo_evaluacion
   AND id_materia = _id_materia
   AND id_grupo = _id_grupo
   AND id_modelo_ahorro = _id_modelo_ahorro
   AND id_tipo_nota = _id_tipo_nota
   AND id_fase = _id_fase
   AND id_departamento = id_departamento
   AND id_tipo_evaluacion = _id_tipo_evaluacion
   AND id_tipo_docente = _id_tipo_docente
   AND gestion = _gestion
   AND periodo = _periodo
   AND id_estado = 'A';

  if found then
    UPDATE grp_evaluaciones 
    SET cantidad = _cantidad, 
        ponderacion = _ponderacion,
	id_rol = _id_rol,
	ult_usuario = _ult_usuario
    WHERE id_tipo_evaluacion = _id_tipo_evaluacion
     AND id_materia = _id_materia
     AND id_grupo = _id_grupo
     AND id_modelo_ahorro = _id_modelo_ahorro
     AND id_tipo_nota = _id_tipo_nota
     AND id_fase = _id_fase
     AND id_departamento = id_departamento
     AND id_tipo_evaluacion = _id_tipo_evaluacion
     AND id_tipo_docente = _id_tipo_docente
     AND gestion = _gestion
     AND periodo = _periodo
     AND id_estado = 'A';
     if found then
       return 1;
     end if;
  else
    INSERT INTO grp_evaluaciones(id_materia, id_grupo, id_modelo_ahorro, id_tipo_nota, id_fase, id_departamento, 
                                 id_tipo_evaluacion, id_tipo_docente, gestion, periodo, cantidad, ponderacion, 
  			         id_rol, ult_usuario)
    VALUES(_id_materia, _id_grupo, _id_modelo_ahorro, _id_tipo_nota, _id_fase, _id_departamento, _id_tipo_evaluacion,
           _id_tipo_docente, _gestion, _periodo, _cantidad, _ponderacion, _id_rol, _ult_usuario);
     if found then
       return 1;
     end if;   
  end if;
  return 0;
end;
$_$ LANGUAGE plpgsql;
