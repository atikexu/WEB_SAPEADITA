package com.sapeadita.service;

import java.util.List;

import com.sapeadita.bean.MensajeProgramadoBean;

public interface MensajeProgramadoService {
	public List<MensajeProgramadoBean> listarMensajeProgramado(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	public MensajeProgramadoBean obtenerMensajeProgramadoXId(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	public MensajeProgramadoBean registrarMensajeProgramado(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	public MensajeProgramadoBean actualizarMensajeProgramado(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	public List<MensajeProgramadoBean> listarMensajeProgramadoXUsuario(MensajeProgramadoBean mensajeProgramadoBean) throws Exception;
	
	public MensajeProgramadoBean actualizarEstado() throws Exception;
	public MensajeProgramadoBean actualizarEstadoEnviado(Integer idMensaje) throws Exception;
}
