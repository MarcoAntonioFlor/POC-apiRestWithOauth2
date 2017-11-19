package br.com.algaworks.algamoney.resources;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaworks.algamoney.event.ResourceCreatedEvent;
import br.com.algaworks.algamoney.model.Pessoa;
import br.com.algaworks.algamoney.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher; 
	
	@PostMapping
	public ResponseEntity<?> salvarPessoa(@RequestBody Pessoa pessoa, HttpServletResponse response){
		
		Optional<Pessoa> pessoaSalva = pessoaService.salvar(pessoa);
		if(pessoaSalva.isPresent()){
			publisher.publishEvent(new ResourceCreatedEvent(this, response, pessoaSalva.get().getCodigo()));
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva.get());
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPessoa(@PathVariable Long codigo){
		Optional<Pessoa> pessoa = pessoaService.buscaPorIdentificador(codigo);
		if(pessoa.isPresent()){
			return ResponseEntity.ok(pessoa.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{/codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPessoa(@PathVariable Long codigo){
		pessoaService.removerPessoaPorId(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable final Long codigo, @Valid @RequestBody final Pessoa pessoa){
		Optional<Pessoa> pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva.get());
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo){
		pessoaService.atualizarPropriedadeAtiva(codigo, ativo);
	}
	 
}
