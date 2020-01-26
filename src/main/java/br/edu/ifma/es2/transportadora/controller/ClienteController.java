package br.edu.ifma.es2.transportadora.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifma.es2.transportadora.controller.dto.ClienteDto;
import br.edu.ifma.es2.transportadora.controller.form.ClienteForm;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.service.ClienteService;

@RestController
@RequestMapping("/clientes")
class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPor(@PathVariable Long id) {
        var cliente = clienteService.buscarPorId(id);

        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<ClienteDto> salva(@Valid @RequestBody ClienteForm form) {
        var cliente = form.converte();
        var clienteSalvo = clienteService.salva(cliente);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteSalvo)
                .toUri();

        return ResponseEntity.created(uri).body(new ClienteDto(clienteSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> atualiza(@PathVariable Long id,
            @Valid @RequestBody ClienteForm clienteForm) {
        try {
            var cliente = clienteForm.converte();
            var clienteAtualizado = clienteService.atualiza(id, cliente);
            return ResponseEntity.ok(new ClienteDto(clienteAtualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
