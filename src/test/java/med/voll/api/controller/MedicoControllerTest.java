package med.voll.api.controller;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.mocks.DadosMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

    @Test
    @DisplayName("Deveria cadastrar um medico apos passar os dados corretamente")
    @WithMockUser
    void cadastrar() throws Exception {

        var medico = DadosMock.dadosMedico("Medico", "medico3@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var endereco = new Endereco(medico.endereco());

        var response = mvc.perform(
                        post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJson.write(medico).getJson())
                )
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var medicoId = Long.parseLong(response.getContentAsString().split(",\"nome\"")[0].replaceAll("\\D+", ""));
        var medicoDetalhado = new DadosDetalhamentoMedico(
                medicoId, medico.nome(), medico.email(), medico.crm(), medico.telefone(), medico.especialidade(), endereco);

        var jsonEsperado = dadosDetalhamentoMedicoJson.write(medicoDetalhado).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}