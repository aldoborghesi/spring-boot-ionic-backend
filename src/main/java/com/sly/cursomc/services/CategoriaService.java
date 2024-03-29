package com.sly.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sly.cursomc.domain.Categoria;
import com.sly.cursomc.domain.Cliente;
import com.sly.cursomc.dto.CategoriaDTO;
import com.sly.cursomc.respositories.CategoriaRepository;
import com.sly.cursomc.services.exceptions.DateIntegrityException;
import com.sly.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	// Instanciar automaticamente
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto Nao Ecnontrado Id:" + id + 
				", Tipo: " + Categoria.class.getName()));
	}

	public  Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		//Recupera no Database
		Categoria newCatDB = find(obj.getId());
	    updateData(newCatDB, obj);
		return repo.save(newCatDB);
	}

	
	public void delete(Integer id) {
		find(id);
		try  {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DateIntegrityException("Categoria tem produtos, não permitido");
			
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
		
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDto(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	private void updateData(Categoria newCatDB, Categoria obj) {
		newCatDB.setNome(obj.getNome());
		
	}
}
