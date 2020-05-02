package com.clientes.clientesapirest.domain.service;

import org.springframework.stereotype.Service;

import com.clientes.clientesapirest.domain.exception.NegocioException;
import com.clientes.clientesapirest.domain.model.Cliente;
import com.clientes.clientesapirest.domain.repository.ClienteRepository;

@Service
public class ClienteService {

	ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este cpf.");
		}

		return clienteRepository.save(cliente);
	}

	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}

}
