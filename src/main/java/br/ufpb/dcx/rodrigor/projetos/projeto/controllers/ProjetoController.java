package br.ufpb.dcx.rodrigor.projetos.projeto.controllers;

import br.ufpb.dcx.rodrigor.projetos.Keys;
import br.ufpb.dcx.rodrigor.projetos.participante.model.CategoriaParticipante;
import br.ufpb.dcx.rodrigor.projetos.participante.model.Participante;
import br.ufpb.dcx.rodrigor.projetos.participante.services.ParticipanteService;
import br.ufpb.dcx.rodrigor.projetos.projeto.model.Projeto;
import br.ufpb.dcx.rodrigor.projetos.projeto.services.ProjetoService;
import io.javalin.http.Context;

import java.time.LocalDate;

public class ProjetoController {

    public void listarProjetos(Context ctx) {
        ProjetoService projetoService = ctx.appData(Keys.PROJETO_SERVICE.key());
        ctx.attribute("projetos", projetoService.listarProjetos());
        ctx.render("/projetos/lista_projetos.html");
    }

    public void mostrarFormulario(Context ctx) {
        ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
        ctx.attribute("professores", participanteService.listarProfessores());
        ctx.render("/projetos/form_projeto.html");
    }

    public void adicionarProjeto(Context ctx) {
        ProjetoService projetoService = ctx.appData(Keys.PROJETO_SERVICE.key());
        ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());

        Projeto projeto = new Projeto();
        projeto.setNome(ctx.formParam("nome"));
        projeto.setDescricao(ctx.formParam("descricao"));
        projeto.setDataInicio(LocalDate.parse(ctx.formParam("dataInicio")));
        projeto.setDataEncerramento(LocalDate.parse(ctx.formParam("dataEncerramento")));

        String coordenadorId = ctx.formParam("coordenador");
        Participante coordenador = participanteService.buscarParticipantePorId(coordenadorId)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        if (coordenador.getCategoria() != CategoriaParticipante.PROFESSOR) {
            throw new IllegalArgumentException("Somente professores podem ser coordenadores.");
        }

        projeto.setCoordenador(coordenador);
        projetoService.adicionarProjeto(projeto);
        ctx.redirect("/projetos");
    }

    public void removerProjeto(Context ctx) {
        ProjetoService projetoService = ctx.appData(Keys.PROJETO_SERVICE.key());
        String id = ctx.pathParam("id");
        projetoService.removerProjeto(id);
        ctx.redirect("/projetos");
    }
}