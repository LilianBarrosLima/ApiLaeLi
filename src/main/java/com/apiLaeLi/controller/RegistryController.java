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
//__________________________________Historic Screen________________________________________	
//------------------------------Registry by day-------------------------------------------
	//http://localhost:8080/api/registros/usuario/dia/1
	@GetMapping(value = "/usuario/dia/{employee_id}")
	public ResponseEntity<Collection<Registry>> findRegistriesByDay(@PathVariable(value = "employee_id")Integer employee_id) {
		Collection <Registry> registriesByDay = registryService.findByEmployeeIdByDay(employee_id);
		return ResponseEntity.ok().body(registriesByDay);		
	}			
//------------------------------Registry by month------------------------------------------	
	//http://localhost:8080/api/registros/usuario/mes/1
	@GetMapping(value = "/usuario/mes/{employee_id}")
	public ResponseEntity<Collection<Registry>> findByEmployeeIdByMonth(@PathVariable(value = "employee_id")Integer employee_id) {
		Collection <Registry> registriesByMonth = registryService.findByEmployeeIdByMonth(employee_id);
		return ResponseEntity.ok().body(registriesByMonth);
	}	
//-------------------------------Worked hours---HH:mm--------------------------------------	
	//http://localhost:8080/api/registros/horas/1
	@GetMapping(value = "/horas/{employee_id}")
	public ResponseEntity<String> showHours(@PathVariable(value = "employee_id")Integer employee_id) {
		Collection <Registry> registriesByDay = registryService.findByEmployeeIdByDay(employee_id);
		long registryDay = registriesByDay.size();	
		Registry[] registries = registriesByDay.toArray(new Registry[0]);		
		long total_minutes = 0;	 
	    Date start_point = null, end_point = null;	
	    
	    for(int i = 0; i < registryDay; i++) {	
	    	if (registryDay % 2 == 0) { //case have input and output 
		        if(i % 2 == 0) {//the first, input, the even		        
		        	start_point = registries[i].getPointRegistry();
		        }else{//output
		        	end_point = registries[i].getPointRegistry();
		        	long diff = end_point.getTime() - start_point.getTime();
		            total_minutes += TimeUnit.MILLISECONDS.toMillis(diff);			            	
		        }		      
		    }else{ //Odd - doesn't have output  
		    	if (registryDay > 2) {//3 or + registries
	    			do {	    			
	    				if(i % 2 == 0) {//it means that is the first, input, the even		        
	    		        	start_point = registries[i].getPointRegistry();
	    		        }else{//output
	    		        	end_point = registries[i].getPointRegistry();
	    		        	long diff = end_point.getTime() - start_point.getTime();
	    		            total_minutes += TimeUnit.MILLISECONDS.toMillis(diff);
	    		        } 			    				
	    			}while(registryDay <= (registryDay-1));   	
		    	}else if (registryDay == 0){//if doesn't have anyone registries
		    		total_minutes = 0;
	    		}else {//just 1 registry
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
	
//---------------------------------Minutes by day-----Long--------------------------------	
	//http://localhost:8080/api/registros/minutos/1
	@GetMapping(value = "/minutos/{employee_id}")
	public ResponseEntity<Long> findDaylyHours(@PathVariable(value = "employee_id")Integer employee_id) {
		Collection <Registry> registriesByDay = registryService.findByEmployeeIdByDay(employee_id);
		long registryDay = registriesByDay.size();	
		Registry[] registries = registriesByDay.toArray(new Registry[0]);		
		long total_minutes = 0;	 
	    Date start_point = null, end_point = null;	
	    
	    for(int i = 0; i < registryDay; i++) {	
	    	if (registryDay % 2 == 0) { //case have input and output  
		        if(i % 2 == 0) {//the first, input, the even		        
		        	start_point = registries[i].getPointRegistry();
		        }else{//output
		        	end_point = registries[i].getPointRegistry();
		        	long diff = end_point.getTime() - start_point.getTime();
		        	total_minutes += TimeUnit.MILLISECONDS.toMinutes(diff);		        	
		        }		      
		    }else{ //Odd - doesn't have output   
	    		if (registryDay > 2) {//3 or +
	    			do {	    			
	    				if(i % 2 == 0) {//the first, input, the even		        
	    		        	start_point = registries[i].getPointRegistry();
	    		        }else{//output
	    		        	end_point = registries[i].getPointRegistry();
	    		        	long diff = end_point.getTime() - start_point.getTime();
	    		            total_minutes += TimeUnit.MILLISECONDS.toMinutes(diff);
	    		        } 			    				
	    			}while(registryDay <= (registryDay-1));	 	    		
	    		}else {//0 or 1 registry
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
//____________________________________Home Screen__________________________________________		
		//btn registry point and daily registries.
		//http://localhost:8080/api/registros/usuario/dia/1
		//http://localhost:8080/api/registros/incluir
//_________________________________________________________________________________________
//------------------------------------Message-------------------------------------------GET
	//http://localhost:8080/api/registros/mensagem/1		
	@GetMapping(value = "/mensagem/{employee_id}")
	public ResponseEntity <String> registriesMessage(@PathVariable(value = "employee_id")Integer employee_id) {
		Collection <Registry> registriesByDay = registryService.findByEmployeeIdByDay(employee_id);
		long registryDay = registriesByDay.size();
		if (registriesByDay.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty! There isn't registries today!"); 
		}else if (!(registryDay % 2 == 0)) {
			return ResponseEntity.status(HttpStatus.OK).body("You must registry point today yet!"); 
		}else {
			return ResponseEntity.ok("Ok");
		}
	}		
//--------------------------Find registry by employee id----------------------GET employee_id
	//http://localhost:8080/api/registros/usuario/1
	@GetMapping(value = "/usuario/{employee_id}") 
	public List<Registry> findRegistryByemployee_id (@PathVariable(value = "employee_id")Integer employee_id){
		return registryService.findByemployee_id(employee_id);
	}	
//----------------------------------All registry-----------------------------------------GET		
	//http://localhost:8080/api/registros/
	@GetMapping("/")
	public ResponseEntity <List<Registry>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(registryService.findAll());
	}
//----------------------------------Find registry by id--------------------------------GET id	
	//http://localhost:8080/api/registros/1
	@GetMapping(value = "/{id}")
	public ResponseEntity <Object> findRegistryById(@PathVariable(value = "id") Integer id){
		Optional<Registry> registryOptional = registryService.findRegistryById(id);
		if (!registryOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registry not found!"); 
		}
		return ResponseEntity.status(HttpStatus.OK).body(registryOptional.get());
	}
//----------------------------------Delete registry-----------------------------------DELETE		
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
//----------------------------------Edit registry-----------------------------------------PUT		
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
//----------------------------------Add registry-----------------------------------------POST	
	//http://localhost:8080/api/registros/incluir
	@PostMapping(value = "/incluir")
	public ResponseEntity <Object> saveRegistry(@RequestBody @Valid RegistryDto registryDto){		
		var registry = new Registry();
		BeanUtils.copyProperties(registryDto, registry); 
		return ResponseEntity.status(HttpStatus.CREATED).body(registryService.saveRegistry(registry));		
	}	
	
	
}
