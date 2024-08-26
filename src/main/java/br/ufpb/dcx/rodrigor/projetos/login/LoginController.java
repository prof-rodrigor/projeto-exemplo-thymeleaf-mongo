package br.ufpb.dcx.rodrigor.projetos.login;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    // Usuário de exemplo para autenticação
    private final Usuario usuarioExemplo = new Usuario("admin", "Administrador", "admin");

    public void mostrarPaginaLogin(Context ctx) {
        String teste = ctx.queryParam("teste");
        if(teste != null){
            throw new RuntimeException("Erro de teste a partir do /login?teste=1");
        }

        ctx.render("login.html");
    }

    public void processarLogin(Context ctx) {
        String login = ctx.formParam("login");
        String senha = ctx.formParam("senha");

        if (usuarioExemplo.getLogin().equals(login) && usuarioExemplo.getSenha().equals(senha)) {
            ctx.sessionAttribute("usuario", usuarioExemplo);
            logger.info("Usuário '{}' autenticado com sucesso.", login);
            ctx.redirect("/area-interna");
        } else {
            logger.warn("Tentativa de login falhou para o usuário: {}", login);
            ctx.redirect("/login");
        }
    }

    public void logout(Context ctx) {
        ctx.sessionAttribute("usuario", null);
        ctx.redirect("/login");
    }
}