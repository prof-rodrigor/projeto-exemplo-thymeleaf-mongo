package br.ufpb.dcx.rodrigor.projetos.projeto.controllers;

import br.ufpb.dcx.rodrigor.projetos.coordenador.service.CoordenadorService;
import br.ufpb.dcx.rodrigor.projetos.projeto.model.Projeto;
import br.ufpb.dcx.rodrigor.projetos.projeto.services.ProjetoService;
import io.javalin.http.Context;

import java.time.LocalDate;

public class ProjetoController {
    private final ProjetoService projetoService = new ProjetoService();
    private final CoordenadorService coordenadorService = new CoordenadorService();

    public void listarProjetos(Context ctx) {
        ctx.attribute("projetos", projetoService.listarProjetos());
        ctx.render("/projetos/lista_projetos.html");
    }

    public void mostrarFormulario(Context ctx) {
        ctx.render("/projetos/form_projeto.html");
    }

    public void adicionarProjeto(Context ctx) {
        Projeto projeto = new Projeto();
        projeto.setNome(ctx.formParam("nome"));
        projeto.setDescricao(ctx.formParam("descricao"));
        projeto.setDataInicio(LocalDate.parse(ctx.formParam("dataInicio")));
        projeto.setDataEncerramento(LocalDate.parse(ctx.formParam("dataEncerramento")));

        projetoService.adicionarProjeto(projeto);
        ctx.redirect("/projetos");
    }

    public void removerProjeto(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        projetoService.removerProjeto(id);
        ctx.redirect("/projetos");
    }
}