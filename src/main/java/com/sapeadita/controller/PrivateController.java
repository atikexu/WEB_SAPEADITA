package com.sapeadita.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sapeadita.exceptions.FTPErrors;
import com.sapeadita.service.CategoriaService;
import com.sapeadita.service.FTPService;
import com.sapeadita.service.LoginService;
import com.sapeadita.service.MensajeProgramadoService;
import com.sapeadita.service.ProductoService;
import com.sapeadita.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sapeadita.bean.CategoriaBean;
import com.sapeadita.bean.MensajeProgramadoBean;
import com.sapeadita.bean.ProductoBean;
import com.sapeadita.bean.RequestWhatsappBean;
import com.sapeadita.bean.ResponseWhatsappBean;
import com.sapeadita.bean.SubCategoriaBean;
import com.sapeadita.bean.UsuarioBean;

@Controller
@RequestMapping("/private")
public class PrivateController {
	public static Logger log = LogManager.getLogger(PrivateController.class);
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CategoriaService categoriaService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	MensajeProgramadoService mensajeProgramadoService;
	
	@Autowired
    private FTPService ftpService;
	
	private final Environment env;

    @Autowired
    public PrivateController(Environment env) {
        this.env = env;
    }
	
	@GetMapping("/principal")
	public String inicio(Authentication auth, HttpSession session, Model model) throws Exception {
		log.info("Inicio principal al hacer login");
		
		if(auth==null) {
			return "redirect:/login/login";
		}
		String username = auth.getName();
//		List<CategoriaBean> categorias = categoriaService.listarCategoria(new CategoriaBean());
//		List<SubCategoriaBean> subcategorias = categoriaService.listarSubCategoria(new SubCategoriaBean());
//		List<ProductoBean> productos = productoService.listarProducto(new ProductoBean());
//		List<MensajeProgramadoBean> mensajeProgramado = mensajeProgramadoService.listarMensajeProgramado(new MensajeProgramadoBean());
		
		if(session.getAttribute("usuario")==null) {
			UsuarioBean usuario1 = loginService.verificarUsuarioXUser(username);
			usuario1.setPassword(null);
			session.setAttribute("usuario", usuario1);
		}
		UsuarioBean usuario = loginService.verificarUsuarioXUser(username);
		MensajeProgramadoBean mensajeProgramadoBean = new MensajeProgramadoBean();
		mensajeProgramadoBean.setIdUsuario(usuario.getIdUsuario());
		List<MensajeProgramadoBean> mensajeProgramado = mensajeProgramadoService.listarMensajeProgramadoXUsuario(mensajeProgramadoBean);
		
		List<MensajeProgramadoBean> mensajeProgramadoFinal = new ArrayList<>();
		Date date = new Date();
//		MensajeProgramadoBean mensaje = new MensajeProgramadoBean();
		if(mensajeProgramado.size()>0) {
			
			for (MensajeProgramadoBean mensajeProgramadoBean2 : mensajeProgramado) {
//				mensaje = mensajeProgramado.get(0);
				
				mensajeProgramadoBean2.setFechaProgramadaFormat(Utils.fechaFormatWeb(mensajeProgramadoBean2.getFechaProgramada()));
				
				System.out.println("MENSAJE "+mensajeProgramadoBean2.getMensaje().replaceAll("\\r\\n|\\r|\\n", ""));
				mensajeProgramadoBean2.setMensaje(Utils.quitarSaltos(mensajeProgramadoBean2.getMensaje()));
//				Date dateProg = mensaje.getFechaProgramada();
				System.out.println("DATEPROG "+mensajeProgramadoBean2.getFechaProgramada());
				System.out.println("DATE "+date);
				
				LocalDateTime fechaHoraActual = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
				LocalDateTime fechaHoraComparar = LocalDateTime.parse(mensajeProgramadoBean2.getFechaProgramada(), formatter);
				System.out.println("La fecha1 "+fechaHoraComparar);
				fechaHoraComparar = fechaHoraComparar.minusHours(5);
				System.out.println("La fecha2 "+fechaHoraComparar);
//				LocalDateTime fechaHoraComparar5 = fechaHoraComparar.plusHours(5);
//				if (fechaHoraActual.isAfter(fechaHoraComparar)) {
//				    System.out.println("La fecha y hora actual es posterior a la fecha de comparación.");
//				} else 
					
//				if (fechaHoraActual.isBefore(fechaHoraComparar)) {
				    System.out.println("La fecha y hora actual es anterior a la fecha de comparación.");
//				    System.out.println("La fecha y hora actual es anterior a la fecha de comparación."+ fechaHoraComparar-fechaHoraActual);
//				    Integer fechaYear = fechaHoraComparar.getYear()-fechaHoraActual.getYear();
//				    Integer fechaMonth = fechaHoraComparar.getMonthValue()-fechaHoraActual.getMonthValue();
//				    Integer fechaDay = fechaHoraComparar.getDayOfMonth()-fechaHoraActual.getDayOfMonth();
//				    Integer fechaHour = fechaHoraComparar.getHour()-fechaHoraActual.getHour();
//				    Integer fechaMinute = fechaHoraComparar.getMinute()-fechaHoraActual.getMinute();
//				    Integer fechaSecond = fechaHoraComparar.getSecond()-fechaHoraActual.getSecond();
//				    
				    Duration duracion = Duration.between(fechaHoraActual, fechaHoraComparar);
				    long dias = fechaHoraComparar.getDayOfMonth();
//				    long horas = duracion.toHours();
//				    long minutos = duracion.toMinutesPart();
//				    long segundos = duracion.toSecondsPart();
				    long horas = fechaHoraComparar.getHour();
				    long minutos = fechaHoraComparar.getMinute();
				    long segundos = fechaHoraComparar.getSecond();
				    
				    model.addAttribute("anioView", fechaHoraComparar.getYear());
				    model.addAttribute("mesView", fechaHoraComparar.getMonthValue());
				    model.addAttribute("diasView", dias);
					model.addAttribute("horasView", horas);
					model.addAttribute("minutosView", minutos);
					model.addAttribute("segundosView", segundos);
					
					mensajeProgramadoBean2.setAnioView(fechaHoraComparar.getYear()+"");
					mensajeProgramadoBean2.setMesView(fechaHoraComparar.getMonthValue()+"");
					mensajeProgramadoBean2.setDiasView(dias+"");
					mensajeProgramadoBean2.setHorasView(horas+"");
					mensajeProgramadoBean2.setMinutosView(minutos+"");
					mensajeProgramadoBean2.setSegundosView(segundos+"");
					mensajeProgramadoFinal.add(mensajeProgramadoBean2);
					
					

					// Objeto que quieres convertir a JSON
//					List<MensajeProgramadoBean> objeto = new Arra;

					
					
			}
			
		
//			} else {
//				model.addAttribute("anioView", fechaHoraActual.getYear());
//			    model.addAttribute("mesView", fechaHoraActual.getMonthValue());
//			    model.addAttribute("diasView", fechaHoraActual.getDayOfMonth());
//				model.addAttribute("horasView", fechaHoraActual.getHour());
//				model.addAttribute("minutosView", fechaHoraActual.getMinute());
//				model.addAttribute("segundosView", fechaHoraActual.getSecond()+10);
//			}
		}
//		else {
//			String fechaHoy = Utils.fechaFormatWeb();
//			model.addAttribute("fechaProgramadaFormat", Utils.fechaFormatWebNew(fechaHoy));
			
//			mensaje.setTitulo("Sin programación");
//			mensaje.setCelulares("");
//			mensaje.setFechaProgramada("no tiene programación");
//			mensaje.setIdUsuario(usuario.getIdUsuario());
//			mensajeProgramado.add(mensaje);
//		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(mensajeProgramadoFinal);
		
		String fechaHoy = Utils.fechaFormatWeb();
		model.addAttribute("fechaProgramadaFormat", Utils.fechaFormatWebNew(fechaHoy));
//		model.addAttribute("editarProg", mensaje);
//		ProductoBean producto = new ProductoBean();
		
//		model.addAttribute("categorias", categorias);
//		model.addAttribute("subcategorias", subcategorias);
//		model.addAttribute("productos", productos);
		model.addAttribute("producto", new ProductoBean());
//		model.addAttribute("categoria", new CategoriaBean());
		model.addAttribute("mensajeProgramado", mensajeProgramadoFinal);
		model.addAttribute("mensajeProgramadoJson", json);
		model.addAttribute("nombre", "nombre");
		model.addAttribute("useruser", usuario.getIdUsuario());
		
		
		
		
		
		
		
		return "private/principal";
	}
	
	@PostMapping("/principal")
	public String buscarProductoCategoria(Authentication auth, HttpSession session, @ModelAttribute CategoriaBean categoria, BindingResult result, Model model, RedirectAttributes rm) throws Exception {
		log.info("Inicio principal buscarProductoCategoria");
		String username = auth.getName();
//		List<CategoriaBean> categorias = categoriaService.listarCategoria(new CategoriaBean());
//		ProductoBean prod = new ProductoBean();
//		prod.setIdCategoria(categoria.getIdCategoria());
//		if(categoria.getIdCategoria()!=9) {
//			prod.setIdSubCategoria(0);
//		}
//		else {
//			prod.setIdSubCategoria(categoria.getIdCategoria());
//		}
		
		
//		List<ProductoBean> productos = productoService.listarProductosXCategoria(prod);
//		List<SubCategoriaBean> subcategorias = categoriaService.listarSubCategoria(new SubCategoriaBean());
		
		if(session.getAttribute("usuario")==null) {
			UsuarioBean usuario = loginService.verificarUsuarioXUser(username);
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);
		}
//		model.addAttribute("categorias", categorias);
//		model.addAttribute("subcategorias", subcategorias);
//		model.addAttribute("productos", productos);
//		model.addAttribute("producto", new ProductoBean());
//		model.addAttribute("categoria", categoria);
		return "private/principal";
	}
	
	@GetMapping("/grabarProducto")
	public String mostrarProducto(Authentication auth, HttpSession session, Model model) throws Exception {
		log.info("Inicio mostrarProducto");
		String username = auth.getName();
//		List<CategoriaBean> categorias = categoriaService.listarCategoria(new CategoriaBean());
//		List<ProductoBean> productos = productoService.listarProducto(new ProductoBean());
//		List<SubCategoriaBean> subcategorias = categoriaService.listarSubCategoria(new SubCategoriaBean());
		if(session.getAttribute("usuario")==null) {
			UsuarioBean usuario = loginService.verificarUsuarioXUser(username);
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);
		}
//		model.addAttribute("categorias", categorias);
//		model.addAttribute("subcategorias", subcategorias);
//		model.addAttribute("productos", productos);
//		model.addAttribute("producto", new ProductoBean());
//		model.addAttribute("categoria", new CategoriaBean());
		return "private/principal";
	}
	
	@PostMapping("/grabarMensaje")
	public String grabarProducto(@ModelAttribute MensajeProgramadoBean mensaje,
			BindingResult result, Model model, RedirectAttributes rm, Authentication auth) {
		log.info("Inicio grabarMensaje");
		String username = auth.getName();
		MensajeProgramadoBean mensajeProgramadoBean = null;
		
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(mensaje.getFechaProgramada(), inputFormatter);
        
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateTime.format(outputFormatter);
        mensaje.setFechaProgramada(formattedDate);
        mensaje.setMensaje(Utils.quitarSaltos(mensaje.getMensaje()));
		
		try {
			if(mensaje.getIdMensaje()==null || mensaje.getIdMensaje().equals("")) {
				mensaje.setFechaCreacion(""+LocalDate.now());
				mensaje.setUsuarioCreacion(username);
				mensajeProgramadoBean = mensajeProgramadoService.registrarMensajeProgramado(mensaje);
			}else {
				mensaje.setFechaModificacion(""+LocalDate.now());
				mensaje.setUsuarioModificacion(username);
				mensajeProgramadoBean = mensajeProgramadoService.actualizarMensajeProgramado(mensaje);
			}
			
			UsuarioBean usuario = loginService.verificarUsuarioXUser(username);
			MensajeProgramadoBean mensajeProgramadoBean1 = new MensajeProgramadoBean();
			mensajeProgramadoBean1.setIdUsuario(usuario.getIdUsuario());
			List<MensajeProgramadoBean> mensajeProgramado = mensajeProgramadoService.listarMensajeProgramadoXUsuario(mensajeProgramadoBean1);
			
			
			Date date = new Date();
			if(mensajeProgramado.size()>0) {
				MensajeProgramadoBean mensaje1 = mensajeProgramado.get(0);
				
				model.addAttribute("editarProg", mensaje1);
//				Date dateProg = mensaje.getFechaProgramada();
				System.out.println("DATEPROG "+mensaje1.getFechaProgramada());
				System.out.println("DATE "+date);
				
				LocalDateTime fechaHoraActual = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
				LocalDateTime fechaHoraComparar = LocalDateTime.parse(mensaje1.getFechaProgramada(), formatter);
				System.out.println("La fecha1 "+fechaHoraComparar);
				fechaHoraComparar = fechaHoraComparar.minusHours(5);
				System.out.println("La fecha2 "+fechaHoraComparar);

			    Duration duracion = Duration.between(fechaHoraActual, fechaHoraComparar);
			    long dias = fechaHoraComparar.getDayOfMonth();
			    long horas = fechaHoraComparar.getHour();
			    long minutos = fechaHoraComparar.getMinute();
			    long segundos = fechaHoraComparar.getSecond();
			    
			    model.addAttribute("anioView", fechaHoraComparar.getYear());
			    model.addAttribute("mesView", fechaHoraComparar.getMonthValue());
			    model.addAttribute("diasView", dias);
				model.addAttribute("horasView", horas);
				model.addAttribute("minutosView", minutos);
				model.addAttribute("segundosView", segundos);
				
				ObjectMapper objectMapper = new ObjectMapper();

				// Objeto que quieres convertir a JSON
//				List<MensajeProgramadoBean> objeto = new Arra;

				
				String json = objectMapper.writeValueAsString(mensajeProgramado);

			}
			
			model.addAttribute("producto", new ProductoBean());
			model.addAttribute("mensajeProgramado", mensajeProgramado);
			model.addAttribute("nombre", "nombre");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("Fin grabarProducto");
		return "redirect:/private/principal";
	}
	
	@GetMapping("/ayuda")
	public String ayuda(Authentication auth, HttpSession session, Model model) throws Exception {
		log.info("Inicio ayuda");
		
		if(auth==null) {
			return "redirect:/login/login";
		}
		String username = auth.getName();

		if(session.getAttribute("usuario")==null) {
			UsuarioBean usuario1 = loginService.verificarUsuarioXUser(username);
			usuario1.setPassword(null);
			session.setAttribute("usuario", usuario1);
		}
		UsuarioBean usuario = loginService.verificarUsuarioXUser(username);
		MensajeProgramadoBean mensajeProgramadoBean = new MensajeProgramadoBean();
		mensajeProgramadoBean.setIdUsuario(usuario.getIdUsuario());

		
		return "private/ayuda";
	}
	
	@PostConstruct
    public void init() {
		scheduler.scheduleAtFixedRate(this::scheduledMethod, 0, 10, TimeUnit.SECONDS);
    }
	
	public void scheduledMethod() {
        // Aquí puedes poner el código que deseas ejecutar periódicamente
		String miVariable = env.getProperty("url.whatsapp");
//        System.out.println("Método ejecutado "+miVariable);
        try {
			mensajeProgramadoService.actualizarEstado();
			
			MensajeProgramadoBean mensajeProgramadoBean = new MensajeProgramadoBean();
			List<MensajeProgramadoBean> listMessage = mensajeProgramadoService.listarMensajeProgramado(mensajeProgramadoBean);
			
			
			
			for (MensajeProgramadoBean message : listMessage) {
				String url = miVariable;//"http://localhost:3001/lead";        
		        String[] listNumber = message.getCelulares().split(",");
		        for(String number : listNumber) {
		        	String requestBody = "{\"message\":\""+message.getMensaje()+"\",\"phone\":\"51"+number+"\"}";
		        	CloseableHttpClient httpClient = HttpClients.createDefault();
		        	HttpPost httpPost = new HttpPost(url);
		        	StringEntity requestEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
		            httpPost.setEntity(requestEntity);
		            HttpResponse response = httpClient.execute(httpPost);
		            HttpEntity responseEntity = response.getEntity();
		            String responseJson = EntityUtils.toString(responseEntity);
		            System.out.println(responseJson+" NUMBER: "+number);
		            httpClient.close();
//		        	Gson gson = new Gson();
//
//		            // Crea un objeto con los datos deseados
//		        	RequestWhatsappBean dataObject = new RequestWhatsappBean();
//		            dataObject.setMessage(message.getMensaje());
//		            dataObject.setPhone(number);
//
//		            // Convierte el objeto a formato JSON
//		            String json = gson.toJson(dataObject);
//		            
//		            ResponseWhatsappBean response =  restTemplate.postForObject(url, json, ResponseWhatsappBean.class);
//		            System.out.println("Respuesta del servicio web: " + response);

		        }
		        mensajeProgramadoService.actualizarEstadoEnviado(message.getIdMensaje());
//		        String response = restTemplate.postForObject(url, requestPayload, String.class);

		        // Manejar la respuesta obtenida
//		        System.out.println("Respuesta del servicio web: " + response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}
