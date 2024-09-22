package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorTexto implements ValidadorCampo {

    private int min;
    private int max;

    public ValidadorTexto(int min, int max){
        this.min = min;
        this.max = max;
    }

    @Override
    public ResultadoValidacao validarCampo(String valor) {
        if(valor.length() < min || valor.length() > max){
            return new ResultadoValidacao( "O valor deve ter entre " + min + " e " + max + " caracteres");
        }
        return new ResultadoValidacao();
    }
}
