package br.ufpb.dcx.rodrigor.projetos.participante.services;

import br.ufpb.dcx.rodrigor.projetos.participante.model.CategoriaParticipante;
import br.ufpb.dcx.rodrigor.projetos.participante.model.Participante;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.net.HttpURLConnection.*;

public class ParticipanteService {

    private static final Logger logger = LogManager.getLogger(ParticipanteService.class);

    private final String host;

    public ParticipanteService(String host) {
        this.host = host;
    }

    public List<Participante> listarParticipantesPorCategoria(CategoriaParticipante categoriaParticipante) {
        String url = host + "/v1/participantes";
        if (categoriaParticipante != null) {
            url += "?categoria=" + categoriaParticipante.name();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HTTP_OK) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.body(), new TypeReference<List<Participante>>() {});
            } else if (response.statusCode() == HTTP_NOT_FOUND) {
                return new LinkedList<>();
            } else {
                logger.error("Erro ao recuperar participantes: {}", response.statusCode());
                throw new RuntimeException("Erro ao recuperar participantes: " + response.statusCode());
            }
        } catch (Exception e) {
            logger.error("Erro ao recuperar participantes", e);
            throw new RuntimeException("Erro ao recuperar participantes", e);
        }
    }

    public List<Participante> listarProfessores() {
        return listarParticipantesPorCategoria(CategoriaParticipante.PROFESSOR);
    }

    public List<Participante> listarParticipantes() {
        return this.listarParticipantesPorCategoria(null);
    }


    public Optional<Participante> buscarParticipantePorId(String string) {
        String url = host + "/v1/participantes/" + string;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HTTP_OK) {
                ObjectMapper mapper = new ObjectMapper();
                return Optional.of(mapper.readValue(response.body(), Participante.class));
            } else if (response.statusCode() == HTTP_NOT_FOUND) {
                return Optional.empty();
            } else {
                logger.error("Erro ao recuperar participante: {}", response.statusCode());
                throw new RuntimeException("Erro ao recuperar participante: " + response.statusCode());
            }
        } catch (Exception e) {
            logger.error("Erro ao recuperar participante", e);
            throw new RuntimeException("Erro ao recuperar participante", e);
        }
    }
}