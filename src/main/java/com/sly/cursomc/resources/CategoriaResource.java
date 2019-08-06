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
import com.sly.cursomc.dto.CategoriaDTO;
import com.sly.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	/*
	@RequestMapping(method = RequestMethod.GET)
	public String listar() {
		return "REST está funcionando";
	}]*/
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	// RequestBody transforma o JSOn em um objeto automaticamente	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
		Categoria obj = service.fromDto(objDto);
		obj = service.insert(obj);
		// O HTTP Exige uma resposta 201 e uma URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id ) {
		Categoria obj = service.fromDto(objDto);
		obj.setId(id);
		obj = service.update(obj);
		// HTTP 204 - Success no content
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Categoria obj, @PathVariable Integer id ) {
        service.delete(id);
		// HTTP 204 - Success no content
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		// Converter um objeto em outro
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction);
		// Converter um objeto em outro
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
	
	/*
	public List<Categoria> listar() {
		
		Categoria cat1 = new Categoria(1, "Escritório");
		Categoria cat2 = new Categoria(2, "Informática");
		
		List<Categoria> lista = new ArrayList<>();
		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}
*/    
}
