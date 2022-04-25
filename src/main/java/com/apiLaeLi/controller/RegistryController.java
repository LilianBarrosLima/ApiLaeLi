package com.apiLaeLi.controller;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiLaeLi.dtos.RegistryDto;
import com.apiLaeLi.entities.Registry;
import com.apiLaeLi.service.RegistryService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/registros")
public class RegistryController {
	final RegistryService registryService;
	
	public RegistryController(RegistryService registryService){	
	
	this.registryService = registryService;		
}				
	
//---------Historico------Ver todos registros por id do usuario por dia --------------------
	//http://localhost:8080/api/registros/usuario/dia/1
	@GetMapping(value = "/usuario/dia/{user_id}")
	public ResponseEntity<Collection<Registry>> findRegistriesByDay(@PathVariable(value = "user_id")Integer user_id) {
		Collection <Registry> registriesByDay = registryService.findByUserIdByDay(user_id);
		return ResponseEntity.ok().body(registriesByDay);		
	}			
//---------Historico------Ver todos registros por id do usuario por mes----------------------	
	//http://localhost:8080/api/registros/usuario/mes/1
	@GetMapping(value = "/usuario/mes/{user_id}")
	public ResponseEntity<Collection<Registry>> findByUserIdByMonth(@PathVariable(value = "user_id")Integer user_id) {
		Collection <Registry> registriesByMonth = registryService.findByUserIdByMonth(user_id);
		return ResponseEntity.ok().body(registriesByMonth);
	}	
//---------Historico------------------Mostra as horas trabalhadas---HH:mm-----------------	
	//http://localhost:8080/api/registros/horas/1
	@GetMapping(value = "/horas/{user_id}")
	public ResponseEntity<String> showHours(@PathVariable(value = "user_id")Integer user_id) {
		Collection <Registry> registriesByDay = registryService.findByUserIdByDay(user_id);
		long registryDay = registriesByDay.size();	
		Registry[] registries = registriesByDay.toArray(new Registry[0]);		
		long total_minutes = 0;	 
	    Date start_point = null, end_point = null;	
	    
	    for(int i = 0; i < registryDay; i++) {	
	    	if (registryDay % 2 == 0) { //se tiver entrada e saida 
		        if(i % 2 == 0) {//quer dizer q é o primeiro do par, a entrada		        
		        	start_point = registries[i].getPointRegistry();
		        }else{//saida
		        	end_point = registries[i].getPointRegistry();
		        	long diff = end_point.getTime() - start_point.getTime();
		            total_minutes += TimeUnit.MILLISECONDS.toMillis(diff);			            	
		        }		      
		    }else{ //Impar - se tiver somente entrada  
		    	if (registryDay > 2) {//3 ou +
	    			do {	    			
	    				if(i % 2 == 0) {//quer dizer q é o primeiro do par, a entrada		        
	    		        	start_point = registries[i].getPointRegistry();
	    		        }else{//saida
	    		        	end_point = registries[i].getPointRegistry();
	    		        	long diff = end_point.getTime() - start_point.getTime();
	    		            total_minutes += TimeUnit.MILLISECONDS.toMillis(diff);
	    		        } 			    				
	    			}while(registryDay <= (registryDay-1));   	
		    	}else if (registryDay == 0){//se não tiver entrada
		    		total_minutes = 0;
	    		}else {//se tiver somente uma entrada
	    			start_point = registries[i].getPointRegistry();	 
	    			Calendar cal = Calendar.getInstance();
	    			Date now = cal.getTime();
	    			long diff = now.getTime() - start_point.getTime();
    		        total_minutes += TimeUnit.MILLISECONDS.toMillis(diff); 	    			
	    		}
		    }
		}
	    
	    long minutes  = ( total_minutes / 60000 ) % 60;     // 60000   = 60 * 1000
        long hours    = total_minutes / 3600000;
	    String showH =  String.format("%02d:%02d", hours, minutes);
	    	    
	    if (total_minutes < (480*60000)) {
    		System.out.println("Your working hours are 8 hours by day. You worked just " + showH + ", please justify."); 
       	}
		return ResponseEntity.ok().body(showH);
	} 
	
//---------Historico------Calcula qtd de minutos por dia-----Long--------------	
	//http://localhost:8080/api/registros/minutos/1
	@GetMapping(value = "/minutos/{user_id}")
	public ResponseEntity<Long> findDaylyHours(@PathVariable(value = "user_id")Integer user_id) {
		Collection <Registry> registriesByDay = registryService.findByUserIdByDay(user_id);
		long registryDay = registriesByDay.size();	
		Registry[] registries = registriesByDay.toArray(new Registry[0]);		
		long total_minutes = 0;	 
	    Date start_point = null, end_point = null;	
	    
	    for(int i = 0; i < registryDay; i++) {	
	    	if (registryDay % 2 == 0) { //se tiver entrada e saida //caminho feliz 
		        if(i % 2 == 0) {//quer dizer q é o primeiro do par, a entrada		        
		        	start_point = registries[i].getPointRegistry();
		        }else{//saida
		        	end_point = registries[i].getPointRegistry();
		        	long diff = end_point.getTime() - start_point.getTime();
		        	total_minutes += TimeUnit.MILLISECONDS.toMinutes(diff);		        	
		        }		      
		    }else{ //Impar - se tiver entrada  e nao saida 
	    		if (registryDay > 2) {//3 ou +
	    			do {	    			
	    				if(i % 2 == 0) {//quer dizer q é o primeiro do par, a entrada		        
	    		        	start_point = registries[i].getPointRegistry();
	    		        }else{//saida
	    		        	end_point = registries[i].getPointRegistry();
	    		        	long diff = end_point.getTime() - start_point.getTime();
	    		            total_minutes += TimeUnit.MILLISECONDS.toMinutes(diff);
	    		        } 			    				
	    			}while(registryDay <= (registryDay-1));	 	    		
	    		}else {//0 ou 1 pontos - se tiver somente uma entrada
	    			System.out.println("You must justify!");
		    		start_point = registries[i].getPointRegistry();	 
		    		Calendar cal = Calendar.getInstance();
		    		Date now = cal.getTime();
		    		long diff = now.getTime() - start_point.getTime();
	    		    total_minutes += TimeUnit.MILLISECONDS.toMinutes(diff); 
		    	}     			      
		    }		    	
        }    	
		return ResponseEntity.ok().body(total_minutes);	 		
	}
//---------------------------------------Tela Home-------------------------------------	
		//Mostra o botao resgistrar ponto e os registros do dia.
		//http://localhost:8080/api/registros/usuario/dia/1
		//http://localhost:8080/api/registros/incluir
//------------------------------------Mensagens-------------------------------------------------
	//http://localhost:8080/api/registros/mensagem/1		
	@GetMapping(value = "/mensagem/{user_id}")
	public ResponseEntity <String> registriesMessage(@PathVariable(value = "user_id")Integer user_id) {
		Collection <Registry> registriesByDay = registryService.findByUserIdByDay(user_id);
		long registryDay = registriesByDay.size();
		if (registriesByDay.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty! There isn't registries today!"); 
		}else if (!(registryDay % 2 == 0)) {
			return ResponseEntity.status(HttpStatus.OK).body("You must registry point today yet!"); 
		}else {
			return ResponseEntity.ok("Ok");
		}
	}		
//--------------------------Ver todos registros por id do usuario------------------------findByuser_id----
	//http://localhost:8080/api/registros/usuario/1
	@GetMapping(value = "/usuario/{user_id}") 
	public List<Registry> findRegistryByuser_id (@PathVariable(value = "user_id")Integer user_id){
		return registryService.findByuser_id(user_id);
	}	
//----------------------------------Ver todos registros-----------------------------------------GET		
	//http://localhost:8080/api/registros/
	@GetMapping("/")
	public ResponseEntity <List<Registry>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(registryService.findAll());
	}
//----------------------------------Ver registros por id--------------------------------------------GET id	
	//http://localhost:8080/api/registros/1
	@GetMapping(value = "/{id}")
	public ResponseEntity <Object> findRegistryById(@PathVariable(value = "id") Integer id){
		Optional<Registry> registryOptional = registryService.findRegistryById(id);
		if (!registryOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registry not found!"); 
		}
		return ResponseEntity.status(HttpStatus.OK).body(registryOptional.get());
	}
//----------------------------------Deletar registros-----------------------------------------DELETE		
	//http://localhost:8080/api/registros/apagar/1
	@DeleteMapping(value = "/apagar/{id}")
	public ResponseEntity <Object> deleteRegistry(@PathVariable(value = "id") Integer id){
		Optional<Registry> registryOptional = registryService.findRegistryById(id);
		if (!registryOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registry not found!"); 
		}
		registryService.deleteRegistry(registryOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Registry deleted!");
	}
//----------------------------------Editar registros-----------------------------------------PUT		
	//http://localhost:8080/api/registros/editar/1
	@PutMapping(value = "/editar/{id}")
	public ResponseEntity <Object> updateRegistry(@PathVariable(value = "id") Integer id,
												@RequestBody @Valid RegistryDto registryDto){
		
		Optional<Registry> registryOptional = registryService.findRegistryById(id);
		if (!registryOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registry not found!"); 
		}		
		var registry = new Registry(); 	
		BeanUtils.copyProperties(registryDto, registryOptional);
		registry.setId(registryOptional.get().getId());
		return ResponseEntity.status(HttpStatus.OK).body(registryService.saveRegistry(registry));
	}	
//----------------------------------Incluir registros/ponto-----------------------------------------POST	
	//http://localhost:8080/api/registros/incluir
	@PostMapping(value = "/incluir")
	public ResponseEntity <Object> saveRegistry(@RequestBody @Valid RegistryDto registryDto){		
		var registry = new Registry();
		BeanUtils.copyProperties(registryDto, registry); //converte registryDto para registry para salvar
		return ResponseEntity.status(HttpStatus.CREATED).body(registryService.saveRegistry(registry));		
	}		
}
