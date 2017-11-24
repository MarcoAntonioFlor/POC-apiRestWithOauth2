package br.com.algaworks.algamoney.resources;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaworks.algamoney.event.ResourceCreatedEvent;
import br.com.algaworks.algamoney.model.Categoria;
import br.com.algaworks.algamoney.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/categorias")
@Api(value="categorias", description="Operações sobre as categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ApiOperation(value = "Lista todas as categorias", response = Categoria.class, responseContainer="List")
	@GetMapping
	public ResponseEntity<?> listarCategorias() {
		List<Categoria> listarCategorias = categoriaService.listar();
		if (listarCategorias.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(listarCategorias);
	}

	@PostMapping
	public ResponseEntity<?> salvaCategoria(@RequestBody @Validated Categoria categoria, HttpServletResponse response) {
		Optional<Categoria> categoriaSalva = categoriaService.salvar(categoria);
		if (categoriaSalva.isPresent()) {
			publisher.publishEvent(new ResourceCreatedEvent(this, response, categoriaSalva.get().getCodigo()));
			return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscaPorId(@PathVariable("codigo") final Long id) {
		return categoriaService.buscaPorIdentificador(id)
				.map(categoria -> ResponseEntity.ok(categoria))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{codigo}")
	public void removerCategoria(@PathVariable("codigo") final Long id) {
		categoriaService.remover(id);
	}
}
