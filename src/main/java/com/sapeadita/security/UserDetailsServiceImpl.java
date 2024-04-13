package com.sapeadita.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sapeadita.bean.UsuarioBean;
import com.sapeadita.dao.LoginDao;
import com.sapeadita.util.Utils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	public static Logger log = LogManager.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	@Lazy
	private LoginDao loginDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inicio loadUserByUsername");
		Utils.active="1";
		UsuarioBean usuarioBean = new UsuarioBean();
		try {
			usuarioBean = loginDao.verificarUsuarioXUser(username);
//			log.info("user "+usuarioBean.getNombre());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("Error 1");
			e.printStackTrace();
		}
		UserBuilder builder = null;
		if(usuarioBean != null) {
			
			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(usuarioBean.getPassword());
			builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
//			log.info("pass "+usuarioBean.getPassword());
		}else {
			Utils.active="2";
			throw new UsernameNotFoundException("Usuario incorrecto");
			
		}
		
		log.info("Fin loadUserByUsername");
		return builder.build();
	}

}
