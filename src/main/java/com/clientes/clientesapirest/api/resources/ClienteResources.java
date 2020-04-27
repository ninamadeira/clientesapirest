package com.clientes.clientesapirest.api.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.clientesapirest.domain.model.Cliente;
import com.clientes.clientesapirest.domain.repository.ClienteRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Clientes")
public class ClienteResources {
	@Autowired
	private ClienteRepository clienteRepository;

	@ApiOperation(value = "Retorna uma lista de Clientes")
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		return clienteRepository.findAll();

	}
	
	@ApiOperation(value = "Retorna um Cliente")
	@GetMapping("/clientes/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		return clienteRepository.findById(clienteId).map(cliente -> ResponseEntity.ok().body(cliente))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation(value = "Salva um Cliente")
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@ApiOperation(value = "Atualiza um Cliente")
	@PutMapping("/clientes/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody @Valid Cliente cliente) {
		return clienteRepository.findById(clienteId).map(clienteRegistro -> {
			clienteRegistro.setNome(cliente.getNome());
			clienteRegistro.setEmail(cliente.getEmail());
			clienteRegistro.setFone(cliente.getFone());
			clienteRegistro.setCpf(cliente.getCpf());
			Cliente clienteAtualizado = clienteRepository.save(clienteRegistro);
			return ResponseEntity.ok().body(clienteAtualizado);
		}).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Deleta um Cliente")
	@DeleteMapping("/clientes/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}

		clienteRepository.deleteById(clienteId);;

		return ResponseEntity.noContent().build();
	}

}
