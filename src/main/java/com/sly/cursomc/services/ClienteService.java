package com.sly.cursomc.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sly.cursomc.domain.Cliente;
import com.sly.cursomc.respositories.ClienteRepository;
import com.sly.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	// Instanciar automaticamente
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto Nao Ecnontrado Id:" + id + 
				", Tipo: " + Cliente.class.getName()));
	}

}
