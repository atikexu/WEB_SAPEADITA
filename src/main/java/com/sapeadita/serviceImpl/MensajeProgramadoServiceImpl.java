package com.sapeadita.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapeadita.bean.MensajeProgramadoBean;
import com.sapeadita.dao.MensajeProgramadoDao;
import com.sapeadita.service.MensajeProgramadoService;

@Service
public class MensajeProgramadoServiceImpl implements MensajeProgramadoService{
	
	@Autowired
	MensajeProgramadoDao mensajeDao;

	@Override
	public List<MensajeProgramadoBean> listarMensajeProgramado(MensajeProgramadoBean mensajeBean) throws Exception {
		return mensajeDao.listarMensajeProgramado(mensajeBean);
	}

	@Override
	public MensajeProgramadoBean obtenerMensajeProgramadoXId(MensajeProgramadoBean mensajeBean) throws Exception {
		return mensajeDao.obtenerMensajeProgramadoXId(mensajeBean);
	}

	@Override
	public MensajeProgramadoBean registrarMensajeProgramado(MensajeProgramadoBean mensajeBean) throws Exception {
		return mensajeDao.registrarMensajeProgramado(mensajeBean);
	}

	@Override
	public MensajeProgramadoBean actualizarMensajeProgramado(MensajeProgramadoBean mensajeBean) throws Exception {
		return mensajeDao.actualizarMensajeProgramado(mensajeBean);
	}
	
	@Override
	public List<MensajeProgramadoBean> listarMensajeProgramadoXUsuario(MensajeProgramadoBean mensajeBean) throws Exception {
		return mensajeDao.listarMensajeProgramadoXUsuario(mensajeBean);
	}

	@Override
	public MensajeProgramadoBean actualizarEstado() throws Exception {
		return mensajeDao.actualizarEstado();
	}
	
	@Override
	public MensajeProgramadoBean actualizarEstadoEnviado(Integer idMensaje) throws Exception {
		return mensajeDao.actualizarEstadoEnviado(idMensaje);
	}
}
