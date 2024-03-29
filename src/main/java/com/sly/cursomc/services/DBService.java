package com.sly.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sly.cursomc.domain.Categoria;
import com.sly.cursomc.domain.Cidade;
import com.sly.cursomc.domain.Cliente;
import com.sly.cursomc.domain.Endereco;
import com.sly.cursomc.domain.Estado;
import com.sly.cursomc.domain.ItemPedido;
import com.sly.cursomc.domain.Pagamento;
import com.sly.cursomc.domain.PagamentoComBoleto;
import com.sly.cursomc.domain.PagamentoComCartao;
import com.sly.cursomc.domain.Pedido;
import com.sly.cursomc.domain.Produto;
import com.sly.cursomc.domain.enums.EstadoPagamento;
import com.sly.cursomc.domain.enums.Perfil;
import com.sly.cursomc.domain.enums.TipoCliente;
import com.sly.cursomc.respositories.CategoriaRepository;
import com.sly.cursomc.respositories.CidadeRepository;
import com.sly.cursomc.respositories.ClienteRepository;
import com.sly.cursomc.respositories.EnderecoRepository;
import com.sly.cursomc.respositories.EstadoRepository;
import com.sly.cursomc.respositories.ItemPedidoRepository;
import com.sly.cursomc.respositories.PagamentoRepository;
import com.sly.cursomc.respositories.PedidoRepository;
import com.sly.cursomc.respositories.ProdutoRepository;

@Service
public class DBService {


	@Autowired	
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void instatiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		Categoria cat3 = new Categoria(null,"Cama mesa e banho");
		Categoria cat4 = new Categoria(null,"Eletronicos");
		Categoria cat5 = new Categoria(null,"Jardinagem");
		Categoria cat6 = new Categoria(null,"Decoração");
		Categoria cat7 = new Categoria(null,"Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 55.00);
		Produto p4 = new Produto(null, "Mesa de Escritorio", 155.00);
		Produto p5 = new Produto(null, "Toalha", 45.00);
		Produto p6 = new Produto(null, "Colcha", 15.00);
		Produto p7 = new Produto(null, "TV Tela Plana", 1005.00);
		Produto p8 = new Produto(null, "Roçadeira", 455.00);		
		Produto p9 = new Produto(null, "Abajour", 155.00);
		Produto p10 = new Produto(null, "Pendente", 255.00);		
		Produto p11 = new Produto(null, "Shampoo", 5.00);

		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4));
		cat3.getProdutos().addAll(Arrays.asList(p5,p6));
		cat4.getProdutos().addAll(Arrays.asList(p1,p2,p3,p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9,p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));		
		
		p1.getCategorias().addAll(Arrays.asList(cat1,cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1,cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));
		
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
				
		Cidade c1   = new Cidade(null, "Uberlândia", est1);
		Cidade c2   = new Cidade(null, "Campinas",   est2);
		Cidade c3   = new Cidade(null, "Sumaré",     est2);
		
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Salomão", "aldo@slyinfo.com", "12322456788", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("4444411","33213333"));
		
		Cliente cli2 = new Cliente(null, "Aldo", "aldoborghesi@gmail.com", "97954918904", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli2.addPerfil(Perfil.ADMIN);
		cli1.getTelefones().addAll(Arrays.asList("332277","997766888"));
		
		
		Endereco end1 = new Endereco(null, "Rua Maceio", "100", "Fundos", "centro", "68000-000", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua Netuno", "222", "", "Comerci", "77000-000", cli1, c2);
		Endereco end3 = new Endereco(null, "Rua Duque Caxias", "333", "", "Centro", "860111-190", cli2, c2);

		cli1.getEnderecos().addAll(Arrays.asList(end1,end2));
		cli2.getEnderecos().addAll(Arrays.asList(end3));
	
		clienteRepository.saveAll(Arrays.asList(cli1,cli2));
		enderecoRepository.saveAll(Arrays.asList(end1,end2,end3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("30/10/2018 10:32"), cli1, end2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 100:32"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		//Associar os pedidos aos itens Pedidos
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		//Associar os itens pedido aos Produto
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip2));
		p3.getItens().addAll(Arrays.asList(ip3));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
		
		
	}
}
