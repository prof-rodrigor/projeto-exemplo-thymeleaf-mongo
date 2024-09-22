package br.ufpb.dcx.rodrigor.projetos.form.model;

public class ResultadoValidacao {

    private final String mensagem;
    private boolean ok;

    public ResultadoValidacao(String mensagem) {
        this.mensagem = mensagem;
        this.ok = false;
    }

    public ResultadoValidacao() {
        this.mensagem = "";
        this.ok = true;
    }

    public ResultadoValidacao(boolean ok) {
        this.mensagem = "";
        this.ok = ok;
    }

    public String getMensagem() {
        return mensagem;
    }

    public boolean ok() {
        return ok;
    }
}
