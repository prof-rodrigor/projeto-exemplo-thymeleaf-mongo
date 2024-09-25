package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorSenha implements ValidadorCampo {

    private int min;
    private int max;

    public ValidadorSenha(int min, int max){
        this.min = min;
        this.max = max;
    }


    @Override
    public ResultadoValidacao validarCampo(String valor) {
        if(valor.length() < min){
            return new ResultadoValidacao("Senha muito curta");
        }
        if(valor.length() > max){
            return new ResultadoValidacao("Senha muito longa");
        }
        String senhaRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$%&@()!_-])(?=\\S+$).{8,300}$";
        if(!valor.matches(senhaRegex)){
            return new ResultadoValidacao("Senha inválida");
        }
        return new ResultadoValidacao();
    }
}
