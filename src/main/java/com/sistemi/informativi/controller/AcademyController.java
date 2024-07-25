package com.sistemi.informativi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.sistemi.informativi.entity.Academy;
import com.sistemi.informativi.service.AcademyService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.validation.Valid;

/*
 * L'annotation @RequestMapping consente di identificare l'intero WSP con un SUPERPATH
 * In pratica tutte le operazioni esposte dal servzio avranno un prefisso comune di URI: /rest/api/acedemies
 * 
 * Buona pratica: mettere i nomi  al plurale
 */

/*
 * @CrossOrigin è una Annotation Spring che consente di rendere disponibile il Web Service Provider
 * ad applicazioni esterne (remote)
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/api/academies")
public class AcademyController {
	
	// DI
	private AcademyService academyService;

	public AcademyController(AcademyService academyService) {
		
		this.academyService = academyService;
	}

	// https://localhost:8080/rest/api/academies (GET)
	@GetMapping
	public List<Academy> getAcademies(){
		
		/*
		 * Il Bean Jackson Object Mapper (eseguito di default da Spring Boot dietro le quinte) sta  in ascolto 
		 * sul ritorno di ogni singolo metodo del RestController ed è in grado di convertire ogni Oggetto 
		 * in un JSON da restituire al consumer
		 */
		return academyService.getAcademies();
	}
	
	/*
	 * L'annotation @PathVariable comunica al Jackson Object Mapper di convertire una variabile contenuta
	 * nella url http in una variabile Java
	 */
	// https://localhost:8080/rest/api/academies (GET)
	@GetMapping("/code/{code}")
	public Academy getAcademyByCode(@PathVariable String code){
		
		/*
		 * Il Bean Jackson Object Mapper (eseguito di default da Spring Boot dietro le quinte) sta in ascolto 
		 * sul ritorno di ogni singolo metodo del RestController ed è in grado di convertire ogni Oggetto 
		 * in un JSON da restituire al consumer
		 */
		return academyService.getAcademyByCode(code);
	
	}
	
	/*
	 * L'annotation @Valid chiede al Jackson Object Mapper (che è quello che deve poi modificare il JSON 
	 * in Oggetto) di controllare all'interno del JSON che arriva dal consumer se sono rispettate le eventuali 
	 * regole di validazione impostate nel sistema del WSP. Nel caso in cui venga violata qualche regola di 
	 * validazione viene restituita la seguente eccezione: MethodNotArgumentValidException
	 */
	@PostMapping
	public Academy saveAcademy(@Valid @RequestBody Academy academy) {
		
		return academyService.saveOrUpdateAcademy(academy);
	}
	
	@PutMapping
	public Academy updateAcademy(@RequestBody Academy academy) {
		
		return academyService.saveOrUpdateAcademy(academy);
	}
	
	@DeleteMapping("/code/{code}")
	public Map<String,Boolean> removeAcademy(@PathVariable String code) {
		
		return academyService.removeAcademy(code);
	}
	
	@GetMapping("/hateoas/code/{code}")
    public EntityModel<Academy> getAcademyByCodeHateoas(@PathVariable String code)  {

        Academy academy = academyService.getAcademyByCode(code);
        
        /*
         * 1. Occorre inizializzare una variabile di tipo EntityModel che consentirà di fare l'attach 
         * di uno o più link all'interno del JSON di risposta
         * 
         * Si invoca il metodo .of passandogli in input academy in questo caso
         */
        EntityModel<Academy> resource = EntityModel.of(academy);

        /*
         * 2. Occorre inizializzare una variabile di tipo WebMvcLinkBuilder che costruisce fisicamente un 
         * HYPERLINK all'interno del JSON di risposta
         * 
         * e fare un riferimento preciso all'interno del link sull'operazione che si può consumare
         * in base al click effettuato
         * 
         * Cosa deve contenere questo link cliccabile lo dobbiamo specificare noi
         * getAcademies per avere tutte le academies
         */
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAcademies());

        /*
         *  3. E' preferibile associare all'hyperlink una label che faccia capire al consumer l'operazione
         *  che si consumerà al click sul link
         */
        resource.add(linkTo.withRel("all-academies"));

        return resource;
    }
	
	@GetMapping(value="/xml", produces=MediaType.APPLICATION_XML_VALUE)
	public List<Academy> getXmlAcademies(){
		
		return academyService.getAcademies();
	}
	
	@PostMapping("/custom/response")
	public ResponseEntity<?> customSaveAcademy(@RequestBody Academy academy) {

		academyService.saveOrUpdateAcademy(academy);
		
		/*
		 * Viene costruito un Oggetto HashMap che poi verrà utilizzato come contenuto del JSON
		 */
		Map<String, String> responseMap = new HashMap<>();
	    responseMap.put("save academy operation", "ok");
	    
	    /*
	     * Viene istanziato il costruttore dell'API ResponseEntity che prende in input sia l'Oggetto che rappresenta
	     * il contenuto (customizzabile) sia uno status code ad esso associato (diverso da quello di default)
	     *
	     * Ha senso utlizzare l'API ResponseEntity quando si intende restituire un JSON con uno status code 
	     * associato diverso da quello di defualt specificato dell'Architettura REST
	     */
	    return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
	}
}
