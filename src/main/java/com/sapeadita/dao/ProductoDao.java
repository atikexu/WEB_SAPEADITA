package com.sapeadita.dao;

import java.util.List;

import com.sapeadita.bean.ProductoBean;

public interface ProductoDao {
	public abstract List<ProductoBean> listarProducto(ProductoBean productoBean) throws Exception;
	public abstract ProductoBean obtenerProductoXId(ProductoBean productoBean) throws Exception;
	public abstract ProductoBean registrarProducto(ProductoBean productoBean) throws Exception;
	public abstract ProductoBean actualizarProducto(ProductoBean productoBean) throws Exception;
	public abstract List<ProductoBean> listarProductosXCategoria(ProductoBean productoBean) throws Exception;
}
