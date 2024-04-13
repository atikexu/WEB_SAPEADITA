package com.sapeadita.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.jboss.aerogear.security.otp.Totp;

public class Utils implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String active="0";

	public static String obtenerCodigo(String usuario) {
		String regex = "[^a-zA-Z]";
		usuario = usuario.replaceAll(regex, "");
		Totp generator = new Totp(usuario);
		return generator.now();
	}
	
	public static Date obtenerFechaHoraParseada(String fecha) {
		try {
			if (!isNullOrEmpty(fecha)) {			
				SimpleDateFormat formatDate = new SimpleDateFormat("YY-MM-dd hh:mm:ss");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(formatDate.parse(fecha));
				Calendar cal = Calendar.getInstance();
				calendar.set(Calendar.YEAR, cal.get(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MONTH, cal.get(Calendar.MONTH));
				calendar.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
	        	calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
	        	calendar.set(Calendar.SECOND, cal.get(Calendar.SECOND));			
	        	return calendar.getTime();
			}			
		} catch(Exception ex) {
//			LOGGER.error(ex);
		}
		return null;
	}
	
	public static boolean isNullOrEmpty(String campo) { 
	    return campo == null || campo.trim().length() == 0;
	}
	
	public static String  fechaFormatWeb(String fecha) {
		String fechaHoraString = fecha;//"2023-07-20 18:32:00-05";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
        LocalDateTime localDateTime = LocalDateTime.parse(fechaHoraString, formatter);
        ZoneId zonaHoraria = ZoneId.of("America/New_York"); // Por ejemplo, cambiar a la zona horaria deseada
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zonaHoraria);
        String fechaHoraFormateada = zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		return fechaHoraFormateada;
	}
	
	public static String  fechaFormatWebNew(String fecha) {
		String fechaHoraString = fecha;//"2023-07-20 18:32:00-05";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
        LocalDateTime localDateTime = LocalDateTime.parse(fechaHoraString, formatter);
        localDateTime = localDateTime.withSecond(0);
        ZoneId zonaHoraria = ZoneId.of("America/New_York"); // Por ejemplo, cambiar a la zona horaria deseada
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zonaHoraria);
        String fechaHoraFormateada = zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		return fechaHoraFormateada;
	}
	
	public static String  fechaFormatWeb() {
		Date fechaActual = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssXXX");
        TimeZone zonaHoraria = TimeZone.getTimeZone("GMT-05:00"); // Por ejemplo, cambiar a la zona horaria deseada
        formatter.setTimeZone(zonaHoraria);
        String fechaActualFormateada = formatter.format(fechaActual);
        System.out.println(fechaActualFormateada);
        return fechaActualFormateada;
	}
	
	public static String quitarSaltos(String cadena) {
		  return cadena.replaceAll("\\r\\n|\\r|\\n", " "); 
		}

}
