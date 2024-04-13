package com.sapeadita.service;

import java.util.List;

import com.sapeadita.bean.ProductoBean;

public interface ProductoService {
	public List<ProductoBean> listarProducto(ProductoBean productoBean) throws Exception;
	public ProductoBean obtenerProductoXId(ProductoBean productoBean) throws Exception;
	public ProductoBean registrarProducto(ProductoBean productoBean) throws Exception;
	public ProductoBean actualizarProducto(ProductoBean productoBean) throws Exception;
	public List<ProductoBean> listarProductosXCategoria(ProductoBean productoBean) throws Exception;
}
