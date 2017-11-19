package br.com.algaworks.algamoney.resources;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaworks.algamoney.dto.LancamentoFilter;
import br.com.algaworks.algamoney.event.ResourceCreatedEvent;
import br.com.algaworks.algamoney.exceptionHandler.MensagemErro;
import br.com.algaworks.algamoney.model.Lancamento;
import br.com.algaworks.algamoney.service.LancamentoService;
import br.com.algaworks.algamoney.service.exception.PessoaInexistenteException;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/filtro")
	public ResponseEntity<Page<Lancamento>> filtroLancamento(LancamentoFilter lancamentoFilter, Pageable pageable){
		Page<Lancamento> lancamentos = lancamentoService.filtrar(lancamentoFilter, pageable);
		if(lancamentos.getContent().isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(lancamentos);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> salvarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Optional<Lancamento> lancamentoSalvo = lancamentoService.salvar(lancamento);
		HttpStatus status = HttpStatus.CONFLICT;
		if(lancamentoSalvo.isPresent()){
			publisher.publishEvent(new ResourceCreatedEvent(this, response, lancamentoSalvo.get().getCodigo()));
			status = HttpStatus.CREATED;
   		}
		return ResponseEntity.status(status).build();
	}
	
	@GetMapping
	public ResponseEntity<List<Lancamento>> listarLancamentos(){
		List<Lancamento> lancamentos = lancamentoService.listar();
		if(lancamentos.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(lancamentos);	
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarLancamento(@PathVariable Long codigo){
		Optional<Lancamento> lancamento = lancamentoService.buscarLancamento(codigo);
		if(lancamento.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(lancamento.get());	
	}		
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLancamento(@PathVariable Long codigo){
		lancamentoService.removerLancamentoPorIdentificador(codigo);
	}
	
	@ExceptionHandler(PessoaInexistenteException.class)
	public ResponseEntity<Object> handlerPessoaInexistenteException(PessoaInexistenteException ex){
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<MensagemErro> erros = Arrays.asList(new MensagemErro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}
