package br.ufpb.dcx.rodrigor.projetos.participante.controllers;

import br.ufpb.dcx.rodrigor.projetos.Keys;
import br.ufpb.dcx.rodrigor.projetos.participante.model.CategoriaParticipante;
import br.ufpb.dcx.rodrigor.projetos.participante.model.Participante;
import br.ufpb.dcx.rodrigor.projetos.participante.services.ParticipanteService;
import io.javalin.http.Context;

import java.net.HttpURLConnection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParticipanteController {


    private static final Logger logger = LogManager.getLogger(ParticipanteController.class);


    public ParticipanteController() {
    }

    public void listarParticipantes(Context ctx) {
        ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
        try {
            String categoriaParam = ctx.queryParam("categoria");
            CategoriaParticipante categoria = categoriaParam != null ? CategoriaParticipante.valueOf(categoriaParam) : null;
            List<Participante> participantes = participanteService.listarParticipantesPorCategoria(categoria);
            ctx.attribute("participantes", participantes);
            ctx.render("/participantes/lista_participantes.html");
        } catch (Exception e) {
            logger.error("Erro ao recuperar participantes", e);
            ctx.status(HttpURLConnection.HTTP_INTERNAL_ERROR).result("Erro ao recuperar participantes: " + e.getMessage());
        }
    }



}