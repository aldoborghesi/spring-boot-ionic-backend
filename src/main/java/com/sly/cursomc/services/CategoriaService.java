package com.sly.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sly.cursomc.domain.Categoria;
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
	    find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try  {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DateIntegrityException("Categoria tem produtos, não permitido");
			
		}
	}
}
