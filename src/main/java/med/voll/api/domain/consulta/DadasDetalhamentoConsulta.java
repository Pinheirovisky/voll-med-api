package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadasDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
}
