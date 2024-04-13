package com.sapeadita.dao;

import java.util.List;

import com.sapeadita.bean.MensajeProgramadoBean;

public interface MensajeProgramadoDao {
	public abstract List<MensajeProgramadoBean> listarMensajeProgramado(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	public abstract MensajeProgramadoBean obtenerMensajeProgramadoXId(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	public abstract MensajeProgramadoBean registrarMensajeProgramado(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	public abstract MensajeProgramadoBean actualizarMensajeProgramado(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	public abstract List<MensajeProgramadoBean> listarMensajeProgramadoXUsuario(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	public abstract MensajeProgramadoBean actualizarEstado() throws Exception;
	public abstract MensajeProgramadoBean actualizarEstadoEnviado(Integer idMensaje) throws Exception;
}
