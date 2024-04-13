package com.sapeadita.bean;

import java.io.Serializable;

public class MensajeProgramadoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer idMensaje;
	private String titulo;
	private String correos;
	private String celulares;
	private Integer idUsuario;
	private String estado;
	private String fechaProgramada;
	private String fechaCreacion;
	private String fechaModificacion;
	private String usuarioCreacion;
	private String usuarioModificacion;
	private String mensaje;
	private String fechaProgramadaFormat;
	
	private String anioView;
	private String mesView;
	private String diasView;
	private String horasView;
	private String minutosView;
	private String segundosView;
	
	@Override
	public String toString() {
		return "MensajeProgramadoBean [idMensaje=" + idMensaje + ", titulo=" + titulo + ", correos=" + correos
				+ ", celulares=" + celulares + ", idUsuario=" + idUsuario + ", estado=" + estado + ", fechaProgramada="
				+ fechaProgramada + ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion
				+ ", usuarioCreacion=" + usuarioCreacion + ", usuarioModificacion=" + usuarioModificacion + ", mensaje="
				+ mensaje + ", fechaProgramadaFormat=" + fechaProgramadaFormat + "]";
	}
	
	public String getFechaProgramadaFormat() {
		return fechaProgramadaFormat;
	}

	public void setFechaProgramadaFormat(String fechaProgramadaFormat) {
		this.fechaProgramadaFormat = fechaProgramadaFormat;
	}

	public Integer getIdMensaje() {
		return idMensaje;
	}
	public void setIdMensaje(Integer idMensaje) {
		this.idMensaje = idMensaje;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCorreos() {
		return correos;
	}
	public void setCorreos(String correos) {
		this.correos = correos;
	}
	public String getCelulares() {
		return celulares;
	}
	public void setCelulares(String celulares) {
		this.celulares = celulares;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaProgramada() {
		return fechaProgramada;
	}
	public void setFechaProgramada(String fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAnioView() {
		return anioView;
	}

	public void setAnioView(String anioView) {
		this.anioView = anioView;
	}

	public String getMesView() {
		return mesView;
	}

	public void setMesView(String mesView) {
		this.mesView = mesView;
	}

	public String getDiasView() {
		return diasView;
	}

	public void setDiasView(String diasView) {
		this.diasView = diasView;
	}

	public String getHorasView() {
		return horasView;
	}

	public void setHorasView(String horasView) {
		this.horasView = horasView;
	}

	public String getMinutosView() {
		return minutosView;
	}

	public void setMinutosView(String minutosView) {
		this.minutosView = minutosView;
	}

	public String getSegundosView() {
		return segundosView;
	}

	public void setSegundosView(String segundosView) {
		this.segundosView = segundosView;
	}
	
	
}
