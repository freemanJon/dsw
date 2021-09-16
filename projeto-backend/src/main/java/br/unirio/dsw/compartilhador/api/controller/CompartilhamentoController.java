package br.unirio.dsw.compartilhador.api.controller;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unirio.dsw.compartilhador.api.model.Compartilhamento;
import br.unirio.dsw.compartilhador.api.model.ItemCompartilhado;
import br.unirio.dsw.compartilhador.api.model.TipoItemCompartilhado;
import br.unirio.dsw.compartilhador.api.model.Usuario;
import br.unirio.dsw.compartilhador.api.repository.CompartilhamentoRepository;
import br.unirio.dsw.compartilhador.api.repository.ItemCompartilhadoRepository;
import br.unirio.dsw.compartilhador.api.repository.UsuarioRepository;
import br.unirio.dsw.compartilhador.api.utils.spring.ControllerResponse;
import br.unirio.dsw.compartilhador.api.utils.spring.PageDTO;
import br.unirio.dsw.compartilhador.api.utils.spring.ResponseData;
import lombok.Data;

/**
 * Controlador com as ações de compartilhamentos
 * 
 * @author User
 */
@RestController
@RequestMapping("/api/compartilhamento")
@CrossOrigin(origins = "*")
public class CompartilhamentoController
{
	private static final Logger log = LoggerFactory.getLogger(ItemCompartilhadoController.class);

	@Autowired
	private UsuarioRepository usuarioRepositorio;

	@Autowired
	private CompartilhamentoRepository compartilhamentoRepositorio;
	
	@Autowired
	private ItemCompartilhadoRepository itemRepositorio;
	
	/**
	 * Ação que cria um novo compartilhamento
	 */
	@PostMapping(value = "/novo")
	public ResponseEntity<ResponseData> novo(@RequestBody NovoCompartilhamentoDTO dto, BindingResult result)
	{
		log.info("Criando um compartilhamento: {}", dto.toString());
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (username == null)
			return ControllerResponse.fail("nome", "Não há um usuário logado no sistema.");

        Usuario usuarioRemetente = usuarioRepositorio.findByEmail(username);
        Usuario usuarioDestinatario = usuarioRepositorio.findByEmail(dto.getEmailDestinatario());
        
		if (usuarioRemetente == null)
			return ControllerResponse.fail("nome", "Não foi possível recuperar os dados do usuário a partir das credenciais.");
		
		if (usuarioDestinatario == null)
			return ControllerResponse.fail("nome", "Não foi possível encontrar o usuário destinatario.");
		
		Optional<ItemCompartilhado> item = itemRepositorio.findById(dto.getIdItem());
		
		if (item == null)
			return ControllerResponse.fail("item", "Não existe item no sistema.");
		
        Compartilhamento compartilhamento = new Compartilhamento();
        compartilhamento.setDataInicio(dto.getDataInicio());
        compartilhamento.setDataTermino(dto.getDataTermino());
        compartilhamento.setUsuario(usuarioDestinatario);
        compartilhamento.setItem(item.get());
        compartilhamentoRepositorio.save(compartilhamento);
		return ControllerResponse.success();
	}
	
	@GetMapping(value = "/{id}/{acao}")
	public ResponseEntity<ResponseData> mudarStatus(@PathVariable("id") long id, @PathVariable("acao") String acao)
	{
		log.info("Alterando status do compartilhamento: {}", id);
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (username == null)
			return ControllerResponse.fail("Não há um usuário logado no sistema.");

        Usuario usuario = usuarioRepositorio.findByEmail(username);

		if (usuario == null)
			return ControllerResponse.fail("Não foi possível recuperar os dados do usuário a partir das credenciais.");

		Optional<Compartilhamento> compartilhamento = compartilhamentoRepositorio.findById(id);
		
		if (!compartilhamento.isPresent())
			return ControllerResponse.fail("O compartilhado não foi encontrado.");
		
		switch(acao) {
			case "rejeitar":
				if(compartilhamento.get().getUsuario().getId() == usuario.getId()) {
					compartilhamento.get().setCanceladoDono(false);
			        compartilhamento.get().setCanceladoUsuario(false);
			        compartilhamento.get().setAceito(false);
			        compartilhamento.get().setRejeitado(true);
				}
				else {
					return ControllerResponse.fail("O usuario nao faz parte do compartilhamento.");
				}
		        break;
		        
			case "aceitar":
				if(compartilhamento.get().getUsuario().getId() == usuario.getId()) {
					compartilhamento.get().setCanceladoDono(false);
			        compartilhamento.get().setCanceladoUsuario(false);
			        compartilhamento.get().setAceito(true);
			        compartilhamento.get().setRejeitado(false);
				}
				else {
					return ControllerResponse.fail("O usuario nao faz parte do compartilhamento.");
				}
		        break;
		        
			case "cancelarUsuario":
				if(compartilhamento.get().getUsuario().getId() == usuario.getId()) {
					compartilhamento.get().setCanceladoDono(false);
			        compartilhamento.get().setCanceladoUsuario(true);
			        compartilhamento.get().setAceito(false);
			        compartilhamento.get().setRejeitado(false);
				}
				else {
					return ControllerResponse.fail("O usuario nao faz parte do compartilhamento.");
				}
		        break;
		        
			case "cancelarDono":
				if(compartilhamento.get().getItem().getUsuario().getId() == usuario.getId()) {
					compartilhamento.get().setCanceladoDono(true);
			        compartilhamento.get().setCanceladoUsuario(false);
			        compartilhamento.get().setAceito(false);
			        compartilhamento.get().setRejeitado(false);
				}
				else {
					return ControllerResponse.fail("O usuario nao eh dono do item que esta sendo compartilhado.");
				}
		        break;
		}
		
		compartilhamentoRepositorio.save(compartilhamento.get());
		return ControllerResponse.success();
	}
	
	/**
	 * Ação que recupera lista de compartilhamentos
	 */
	@GetMapping(value = "/lista")
	public ResponseEntity<ResponseData> lista(@RequestParam int page, @RequestParam int per_page)
	{
		log.info("Listando compartilhamentos destinatario");
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (username == null)
			return ControllerResponse.fail("Não há um usuário logado no sistema.");

        Usuario usuario = usuarioRepositorio.findByEmail(username);
        Page<Compartilhamento> compartilhamentos;
		if (usuario == null)
			return ControllerResponse.fail("Não foi possível recuperar os dados do usuário a partir das credenciais.");
		
		Pageable pageable = PageRequest.of(page-1, per_page);
		compartilhamentos = compartilhamentoRepositorio.findByUsuarioId(usuario.getId(), pageable);
		PageDTO<CompartilhamentoDTO> result = new PageDTO<CompartilhamentoDTO>(compartilhamentos.getTotalElements(), page, per_page);
		
		compartilhamentos.forEach(compartilhamento -> {
			CompartilhamentoDTO dto = new CompartilhamentoDTO();
			dto.setId(compartilhamento.getId());
			dto.setNome(compartilhamento.getItem().getNome());
			dto.setNome_usuario(compartilhamento.getItem().getUsuario().getNome());
			dto.setStatus(this.RetornaStatusAtual(compartilhamento));
			dto.setData_inicio(compartilhamento.getDataInicio());
			dto.setData_termino(compartilhamento.getDataTermino());
			result.add(dto);
		});
		
		return ControllerResponse.success(result);
	}
	
	@GetMapping(value = "/abertos")
	public ResponseEntity<ResponseData> contarCompartilhamentoAberto()
	{
		log.info("Listando compartilhamentos destinatario");
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (username == null)
			return ControllerResponse.fail("Não há um usuário logado no sistema.");

        Usuario usuario = usuarioRepositorio.findByEmail(username);
   
		if (usuario == null)
			return ControllerResponse.fail("Não foi possível recuperar os dados do usuário a partir das credenciais.");
		
		long countAbertos = compartilhamentoRepositorio.contarCompartilhamentosAbertos(usuario.getId());
		ResponseTotalAbertos totalAbertos = new ResponseTotalAbertos();
		totalAbertos.setTotal(countAbertos);
		return ControllerResponse.success(totalAbertos);
	}
	
	private String RetornaStatusAtual(Compartilhamento compartilhamento) {
		
		if(compartilhamento.isAceito()) {
			return "Aceito";
		}
		else if(compartilhamento.isCanceladoUsuario()) {
			return "Cancelado pelo usuario";
		}
		else if(compartilhamento.isCanceladoDono()) {
			return "Cancelado pelo dono";
		}
		else if(compartilhamento.isRejeitado()) {
			return "Rejeitado";
		}
		else {
			return "Aberto";
		}
	}
}

/**
 * Formulário para registrar um novo item compartilhado
 * 
 * @author User
 */
@Data class NovoCompartilhamentoDTO
{
	private String emailDestinatario;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
	private LocalDate dataInicio;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
	private LocalDate dataTermino;
	private long idItem;
}

@Data class CompartilhamentoDTO
{
	private long id;
	
	private String nome;
	
	private String nome_usuario;
	
	private String status;
	
	private LocalDate data_inicio;
	
	private LocalDate data_termino;
}

@Data class ResponseTotalAbertos
{
	private long total;
}

/**
 * Classe que representa filtro para listar determinados itens 
 * 
 * @author User
 */
@Data class RequestListaCompartilhamentosDTO
{
	private int page;
	private int per_page;
}
