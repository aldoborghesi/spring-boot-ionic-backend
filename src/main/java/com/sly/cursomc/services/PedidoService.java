package com.sly.cursomc.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sly.cursomc.domain.Pedido;
import com.sly.cursomc.respositories.PedidoRepository;
import com.sly.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	// Instanciar automaticamente
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto Nao Ecnontrado Id:" + id + 
				", Tipo: " + Pedido.class.getName()));
	}

}
