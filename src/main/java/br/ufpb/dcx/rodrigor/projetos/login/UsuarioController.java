// src/main/java/br/ufpb/dcx/rodrigor/projetos/login/UsuarioController.java
package br.ufpb.dcx.rodrigor.projetos.login;

import br.ufpb.dcx.rodrigor.projetos.Keys;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UsuarioController {



    public void mostrarFormularioCadastro(Context ctx) {
        ctx.render("/login/formulario_usuario.html");
    }


    public void mostrarFormulario_signup(Context ctx) {
        ctx.render("/login/formulario_signup.html");
    }

    public void cadastrarUsuario(Context ctx) {
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());
        String nome = ctx.formParam("nome");
        String email = ctx.formParam("login");
        String senha = ctx.formParam("senha");

        boolean signup = (ctx.formParam("signup") != null);
        String formSignup = "/login/formulario_signup.html";
        String formCadastro = "/login/formulario_usuario.html";

        if(usuarioService.buscarUsuarioPorLogin(email) != null){
            ctx.attribute("erro", "Já existe um usuário com o email cadastrado:"+email);
            ctx.render(signup?formSignup:formCadastro);
            return;
        }

        usuarioService.cadastrarUsuario(new Usuario(null, email, nome, senha));
        if(signup){
            ctx.attribute("info", "Usuário cadastrado com sucesso!");
            ctx.render("/login/login.html");
        }else{
            ctx.redirect("/usuarios");
        }
    }

    public void listarUsuarios(Context ctx) {
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        ctx.attribute("usuarios", usuarios);
        ctx.render("/login/lista_usuarios.html");
    }

    public void removerUsuario(@NotNull Context context) {  // Fix: Add @NotNull annotation
        UsuarioService usuarioService = context.appData(Keys.USUARIO_SERVICE.key());
        String id = context.pathParam("id");
        usuarioService.removerUsuario(id);
        context.redirect("/usuarios");

    }
}