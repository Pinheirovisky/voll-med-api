package med.voll.api.paciente;

import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
