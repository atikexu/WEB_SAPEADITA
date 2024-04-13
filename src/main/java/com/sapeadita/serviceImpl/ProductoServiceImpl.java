package com.sapeadita.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapeadita.bean.ProductoBean;
import com.sapeadita.dao.ProductoDao;
import com.sapeadita.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	ProductoDao productoDao;

	@Override
	public List<ProductoBean> listarProducto(ProductoBean productoBean) throws Exception {
		return productoDao.listarProducto(productoBean);
	}

	@Override
	public ProductoBean obtenerProductoXId(ProductoBean productoBean) throws Exception {
		return productoDao.obtenerProductoXId(productoBean);
	}

	@Override
	public ProductoBean registrarProducto(ProductoBean productoBean) throws Exception {
		return productoDao.registrarProducto(productoBean);
	}

	@Override
	public ProductoBean actualizarProducto(ProductoBean productoBean) throws Exception {
		return productoDao.actualizarProducto(productoBean);
	}
	
	@Override
	public List<ProductoBean> listarProductosXCategoria(ProductoBean productoBean) throws Exception {
		return productoDao.listarProductosXCategoria(productoBean);
	}
}
