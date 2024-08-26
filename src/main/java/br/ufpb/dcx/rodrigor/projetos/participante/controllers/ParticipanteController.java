package br.ufpb.dcx.rodrigor.projetos.participante.controllers;

import br.ufpb.dcx.rodrigor.projetos.Keys;
import br.ufpb.dcx.rodrigor.projetos.participante.model.CategoriaParticipante;
import br.ufpb.dcx.rodrigor.projetos.participante.model.Participante;
import br.ufpb.dcx.rodrigor.projetos.participante.services.ParticipanteService;
import io.javalin.http.Context;

public class ParticipanteController {


    public ParticipanteController() {
    }

    public void listarParticipantes(Context ctx) {
        ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
        ctx.attribute("participantes", participanteService.listarParticipantes());
        ctx.render("/participantes/lista_participantes.html");
    }

    public void mostrarFormularioCadastro(Context ctx) {
        ctx.attribute("categorias", CategoriaParticipante.values());
        ctx.render("/participantes/formulario_participante.html");
    }

    public void adicionarParticipante(Context ctx) {
        ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
        Participante participante = new Participante();
        participante.setNome(ctx.formParam("nome"));
        participante.setSobrenome(ctx.formParam("sobrenome"));
        participante.setEmail(ctx.formParam("email"));
        participante.setTelefone(ctx.formParam("telefone"));
        System.out.println("categoria recebida:"+ctx.formParam("categoria"));
        System.out.println("categoria convertida:"+CategoriaParticipante.valueOf(ctx.formParam("categoria")));
        participante.setCategoria(CategoriaParticipante.valueOf(ctx.formParam("categoria")));

        participanteService.adicionarParticipante(participante);
        ctx.redirect("/participantes");
    }

    public void removerParticipante(Context ctx) {
        ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
        String id = ctx.pathParam("id");
        participanteService.removerParticipante(id);
        ctx.redirect("/participantes");
    }
}