package com.sapeadita.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.sapeadita.bean.MensajeProgramadoBean;
import com.sapeadita.dao.MensajeProgramadoDao;
import com.sapeadita.mapper.MensajeProgramadoMapper;
import com.sapeadita.util.Utils;

@Repository
public class MensajeProgramadoDaoImpl implements MensajeProgramadoDao{

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<MensajeProgramadoBean> listarMensajeProgramado(MensajeProgramadoBean mensajeProgramadoBean) throws Exception {
		String sql="select * from sapeadita.mensaje_programado where estado='2' order by fecha_programada desc";
		List<MensajeProgramadoBean> mensajes= jdbcTemplate.query(sql,new MensajeProgramadoMapper());
		return mensajes;
	}
	
	@Override
	public MensajeProgramadoBean obtenerMensajeProgramadoXId(MensajeProgramadoBean mensajeProgramadoBean) throws Exception {
		MensajeProgramadoBean mensaje = new MensajeProgramadoBean();
		String sql="select * from sapeadita.mensaje_programado where id_mensaje=? and estado='1'";
		List<MensajeProgramadoBean> buscar= jdbcTemplate.query(sql,new MensajeProgramadoMapper(),mensajeProgramadoBean.getIdMensaje());
		if(buscar.isEmpty()) {
			mensaje = null;
		}else {
			mensaje = buscar.get(0);
		}
		return mensaje;
	}
	
	@Override
	public MensajeProgramadoBean registrarMensajeProgramado(MensajeProgramadoBean mensajeProgramadoBean) throws Exception {
		String sql = "insert into sapeadita.mensaje_programado(titulo, correos, celulares, id_usuario, estado, fecha_programada, fecha_creacion,  usuario_creacion, mensaje) values(?,?,?,?,?,?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dateTime = sdf.parse(mensajeProgramadoBean.getFechaProgramada());
        long timeInMillis = dateTime.getTime();
        Timestamp timestamp = new Timestamp(timeInMillis);
        Date dateHoy = new Date();
        java.sql.Date dateModi = new java.sql.Date(dateHoy.getTime());
        
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, mensajeProgramadoBean.getTitulo());
                ps.setString(2, mensajeProgramadoBean.getCorreos());
                ps.setString(3, mensajeProgramadoBean.getCelulares());
                ps.setInt(4, mensajeProgramadoBean.getIdUsuario());
                ps.setString(5, mensajeProgramadoBean.getEstado());
                ps.setTimestamp(6, timestamp);
                ps.setDate(7, dateModi);
                
                ps.setString(8, mensajeProgramadoBean.getUsuarioCreacion());
//                ps.setString(10, mensajeProgramadoBean.getUsuarioModificacion());
                ps.setString(9, mensajeProgramadoBean.getMensaje());
                return ps;
            }
        }, holder);
        if (holder.getKeys().size() > 1) {
        	Long newId2 = (Long) holder.getKeys().get("id_mensaje");
        	mensajeProgramadoBean.setIdMensaje(newId2.intValue());
        }
        return mensajeProgramadoBean;
	}

	@Override
	public MensajeProgramadoBean actualizarMensajeProgramado(MensajeProgramadoBean mensajeProgramadoBean) throws Exception {
		String sql = "update sapeadita.mensaje_programado "
				+ "set titulo=?, correos=?, celulares=?, estado=? , fecha_programada=?, fecha_modificacion=?, usuario_modificacion=?, mensaje=? where id_mensaje=? and id_usuario=?";
        KeyHolder holder = new GeneratedKeyHolder();

//        LocalDateTime fecha = LocalDateTime.parse(mensajeProgramadoBean.getFechaProgramada());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dateTime = sdf.parse(mensajeProgramadoBean.getFechaProgramada());
        long timeInMillis = dateTime.getTime();
        Timestamp timestamp = new Timestamp(timeInMillis);
        Date dateHoy = new Date();
        java.sql.Date dateModi = new java.sql.Date(dateHoy.getTime());
        
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, mensajeProgramadoBean.getTitulo());
                ps.setString(2, mensajeProgramadoBean.getCorreos());
                ps.setString(3, mensajeProgramadoBean.getCelulares());
//                ps.setInt(4, mensajeProgramadoBean.getIdUsuario());
                ps.setString(4, mensajeProgramadoBean.getEstado());
//                ps.setDate(5, Utils.obtenerFechaHoraParseada(mensajeProgramadoBean.getFechaProgramada()));
                ps.setTimestamp(5, timestamp);
//                ps.setString(7, mensajeProgramadoBean.getFechaCreacion());
                ps.setDate(6, dateModi);
//                ps.setString(9, mensajeProgramadoBean.getUsuarioCreacion());
                ps.setString(7, mensajeProgramadoBean.getUsuarioModificacion());
                ps.setString(8, mensajeProgramadoBean.getMensaje());
                ps.setInt(9, mensajeProgramadoBean.getIdMensaje());
                ps.setInt(10, mensajeProgramadoBean.getIdUsuario());
                return ps;
            }
        }, holder);
        return mensajeProgramadoBean;
	}
	
	@Override
	public List<MensajeProgramadoBean> listarMensajeProgramadoXUsuario(MensajeProgramadoBean mensajeProgramadoBean) throws Exception {
		String sql="";
		sql="select * from sapeadita.mensaje_programado p  "
				+ "where p.id_usuario = ? order by estado asc, fecha_programada asc";
//			+ "where p.id_usuario = ? and estado = '1'";
		
		
		List<MensajeProgramadoBean> mensajes= jdbcTemplate.query(sql,new MensajeProgramadoMapper(),mensajeProgramadoBean.getIdUsuario());
		return mensajes;
	}
	
	
	@Override
	public MensajeProgramadoBean actualizarEstado() throws Exception {
		MensajeProgramadoBean mensajeProgramadoBean = new MensajeProgramadoBean();
	    Connection connection = null;
	    PreparedStatement ps = null;
	    try {
//	        String sql = "update sapeadita.mensaje_programado "
//	                + "set estado=? where estado = '1' and fecha_programada <= now()";
	        
	        String sql = "update sapeadita.mensaje_programado "
	                + "set estado=? where estado = '1' and fecha_programada <= CURRENT_TIMESTAMP - INTERVAL '5 hours'";
	        
	        connection = jdbcTemplate.getDataSource().getConnection();
	        ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, "2");
	        ps.executeUpdate();
	        return mensajeProgramadoBean;
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (ps != null) {
	            ps.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }
	}
	
	@Override
	public MensajeProgramadoBean actualizarEstadoEnviado(Integer idMensaje) throws Exception {
		MensajeProgramadoBean mensajeProgramadoBean = new MensajeProgramadoBean();
	    Connection connection = null;
	    PreparedStatement ps = null;
	    try {
	        String sql = "update sapeadita.mensaje_programado "
	                + "set estado=? where id_mensaje = ?";
	        connection = jdbcTemplate.getDataSource().getConnection();
	        ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, "0");
	        ps.setInt(2, idMensaje);
	        ps.executeUpdate();
	        return mensajeProgramadoBean;
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (ps != null) {
	            ps.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }
	}

}
