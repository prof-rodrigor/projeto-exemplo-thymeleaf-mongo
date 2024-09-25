package br.ufpb.dcx.rodrigor.projetos.usuario.service;

import br.ufpb.dcx.rodrigor.projetos.form.model.Formulario;
import br.ufpb.dcx.rodrigor.projetos.form.model.PersistenciaFormulario;
import br.ufpb.dcx.rodrigor.projetos.login.Usuario;
import br.ufpb.dcx.rodrigor.projetos.login.UsuarioService;

public class PersistenciaUsuario implements PersistenciaFormulario {

    private UsuarioService usuarioService;

    private PersistenciaUsuario(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @Override
    public void persistir(Formulario formulario) {
        String nome = formulario.getCampo("nome").getValor();
        String email = formulario.getCampo("email").getValor();
        String senha = formulario.getCampo("senha").getValor();
        usuarioService.cadastrarUsuario(new Usuario(null, email, nome, senha));
    }
}
