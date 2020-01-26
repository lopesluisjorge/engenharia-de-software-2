package br.edu.ifma.es2.transportadora.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifma.es2.transportadora.controller.form.FreteForm;
import br.edu.ifma.es2.transportadora.entity.Frete;
import br.edu.ifma.es2.transportadora.service.FreteService;

@RestController
@RequestMapping("/fretes")
public class FreteController {

    @Autowired
    private FreteService freteService;

    @GetMapping("/{id}")
    public ResponseEntity<Frete> buscarPorId(@PathVariable Long id) {
        var frete = freteService.buscaPorId(id);

        return ResponseEntity.ok(frete);
    }

    @PostMapping
    public ResponseEntity<Frete> cadastrar(@Valid @RequestBody FreteForm freteForm) {
        var freteSalvo = freteService.cadastrar(freteForm);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(freteSalvo)
                .toUri();

        return ResponseEntity.created(uri).body(freteSalvo);
    }

}
