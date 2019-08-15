package com.sly.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sly.cursomc.domain.Categoria;
import com.sly.cursomc.domain.Cliente;
import com.sly.cursomc.dto.ClienteDTO;
import com.sly.cursomc.dto.ClienteNewDTO;
import com.sly.cursomc.services.ClienteService;;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	/*
	@RequestMapping(method = RequestMethod.GET)
	public String listar() {
		return "REST está funcionando";
	}]*/
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	// RequestBody transforma o JSOn em um objeto automaticamente	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDto(objDto);
		obj = service.insert(obj);
		// O HTTP Exige uma resposta 201 e uma URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id ) {
		Cliente obj = service.fromDto(objDto);
		obj.setId(id);
		obj = service.update(obj);
		// HTTP 204 - Success no content
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Cliente obj, @PathVariable Integer id ) {
        service.delete(id);
		// HTTP 204 - Success no content
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		// Converter um objeto em outro
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Cliente> list = service.findPage(page,linesPerPage,orderBy,direction);
		// Converter um objeto em outro
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
	
	/*
	public List<Cliente> listar() {
		
		Cliente cat1 = new Cliente(1, "Escritório");
		Cliente cat2 = new Cliente(2, "Informática");
		
		List<Cliente> lista = new ArrayList<>();
		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}
*/    
}
