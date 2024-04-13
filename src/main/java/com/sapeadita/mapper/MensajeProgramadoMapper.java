package com.sapeadita.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sapeadita.bean.MensajeProgramadoBean;

public class MensajeProgramadoMapper implements RowMapper<MensajeProgramadoBean>{
	@Override
	public MensajeProgramadoBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		MensajeProgramadoBean mensajeProgramadoBean = new MensajeProgramadoBean();
		mensajeProgramadoBean.setIdMensaje(rs.getInt("id_mensaje"));
		mensajeProgramadoBean.setTitulo(rs.getString("titulo"));
		mensajeProgramadoBean.setCorreos(rs.getString("correos"));
		mensajeProgramadoBean.setCelulares(rs.getString("celulares"));
		mensajeProgramadoBean.setIdUsuario(rs.getInt("id_usuario"));
		mensajeProgramadoBean.setEstado(rs.getString("estado"));
		mensajeProgramadoBean.setFechaProgramada(rs.getString("fecha_programada"));
		mensajeProgramadoBean.setFechaCreacion(rs.getString("fecha_creacion"));
		mensajeProgramadoBean.setFechaModificacion(rs.getString("fecha_modificacion"));
		mensajeProgramadoBean.setUsuarioCreacion(rs.getString("usuario_creacion"));
		mensajeProgramadoBean.setUsuarioModificacion(rs.getString("usuario_modificacion"));
		mensajeProgramadoBean.setMensaje(rs.getString("mensaje"));
		return mensajeProgramadoBean;
	}
}
