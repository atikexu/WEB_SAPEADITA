package com.sapeadita.service;

import com.sapeadita.bean.UsuarioBean;

public interface LoginService {
	public UsuarioBean verificarUsuario(UsuarioBean usuario) throws Exception;
	public UsuarioBean verificarUsuarioXUser(String usuario) throws Exception;
	public UsuarioBean registrarUsuario(UsuarioBean usuario) throws Exception;
	public UsuarioBean actualizarEstadoValidacionUsuario(UsuarioBean usuario) throws Exception;
	public UsuarioBean verificarUsuarioXUserXCodigo(String usuario, String codigo) throws Exception;
	public UsuarioBean verificarUsuarioXUserXEmail(String usuario, String email) throws Exception;
	public UsuarioBean actualizarContraUsuario(UsuarioBean usuario) throws Exception;
}
