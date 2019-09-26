package com.sly.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sly.cursomc.domain.Cidade;
import com.sly.cursomc.domain.Cliente;
import com.sly.cursomc.domain.Endereco;
import com.sly.cursomc.domain.enums.TipoCliente;
import com.sly.cursomc.dto.ClienteDTO;
import com.sly.cursomc.dto.ClienteNewDTO;
import com.sly.cursomc.respositories.CidadeRepository;
import com.sly.cursomc.respositories.ClienteRepository;
import com.sly.cursomc.respositories.EnderecoRepository;
import com.sly.cursomc.services.exceptions.DateIntegrityException;
import com.sly.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	// Instanciar automaticamente
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;	
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto Nao Ecnontrado Id:" + id + 
				", Tipo: " + Cliente.class.getName()));
	}

 
    @Transactional
	public  Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	//Sobrecarga
	public Cliente fromDto(ClienteNewDTO objDto) {
		
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		 
		
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if (objDto.getTelefone2()!=null ) {
			cli.getTelefones().add(objDto.getTelefone2());			
		}
		if (objDto.getTelefone3()!=null ) {
			cli.getTelefones().add(objDto.getTelefone3());			
		}
		
		return cli;
	}
	
	
	private void updateData(Cliente newCliDB, Cliente obj) {
		newCliDB.setNome(obj.getNome());
		newCliDB.setEmail(obj.getEmail());
	}
 
}
