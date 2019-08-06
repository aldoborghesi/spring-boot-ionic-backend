package com.sly.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sly.cursomc.domain.Cliente;
import com.sly.cursomc.dto.ClienteDTO;
import com.sly.cursomc.respositories.ClienteRepository;
import com.sly.cursomc.services.exceptions.DateIntegrityException;
import com.sly.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	// Instanciar automaticamente
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto Nao Ecnontrado Id:" + id + 
				", Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente obj) {
		//Recupera no Database
	    Cliente newCliDB = find(obj.getId());
	    updateData(newCliDB, obj);
		return repo.save(newCliDB);
	}
	
	public void delete(Integer id) {
		find(id);
		try  {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DateIntegrityException("Cliente tem pedidos, não permitido a exclusão");
			
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
		
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCliDB, Cliente obj) {
		newCliDB.setNome(obj.getNome());
		newCliDB.setEmail(obj.getEmail());
	}
}
