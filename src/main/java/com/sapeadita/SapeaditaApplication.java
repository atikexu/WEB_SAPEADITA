package com.sapeadita;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SapeaditaApplication {

	public static void main(String[] args) {
		
		
//		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                // Aquí puedes llamar al método que deseas ejecutar cada 5 segundos
//                System.out.println("Método ejecutado en HILOS");
//            }
//        };
//
//        // Programa la tarea para que se ejecute cada 5 segundos
//        scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
//		
//		
//		
		
		
		
		SpringApplication.run(SapeaditaApplication.class, args);
	}

}
