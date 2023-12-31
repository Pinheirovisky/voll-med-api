package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

  @Autowired
  PacienteRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
    var paciente = new Paciente(dados);
    repository.save(paciente);

    var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(paciente.getId()).toUri();

    return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
  }

  @GetMapping
  public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
    var pacientes = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);

    return ResponseEntity.ok(pacientes);
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoPaciente dados) {
    Paciente paciente = repository.getReferenceById(id);
    paciente.atualizarInformacoes(dados);

    return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Object> excluir(@PathVariable Long id) {
    Paciente paciente = repository.getReferenceById(id);
    paciente.excluir();

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
    Paciente paciente = repository.getReferenceById(id);

    return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
  }
}