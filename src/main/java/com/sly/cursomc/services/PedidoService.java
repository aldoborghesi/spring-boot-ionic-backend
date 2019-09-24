package com.sly.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sly.cursomc.domain.ItemPedido;
import com.sly.cursomc.domain.PagamentoComBoleto;
import com.sly.cursomc.domain.Pedido;
import com.sly.cursomc.domain.enums.EstadoPagamento;
import com.sly.cursomc.respositories.ItemPedidoRepository;
import com.sly.cursomc.respositories.PagamentoRepository;
import com.sly.cursomc.respositories.PedidoRepository;
import com.sly.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	// Instanciar automaticamente
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;	
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itpedrepo;
	
	@Autowired 
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto Nao Ecnontrado Id:" + id + 
				", Tipo: " + Pedido.class.getName()));
	}
// no metod Insert 
// ao inves de produtoRepository usar o produtoService.find
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			//Casting
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());			
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.00);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		itpedrepo.saveAll(obj.getItens());
		
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
		
	}
}
