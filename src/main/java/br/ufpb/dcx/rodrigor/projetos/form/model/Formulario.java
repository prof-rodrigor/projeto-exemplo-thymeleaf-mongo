package br.ufpb.dcx.rodrigor.projetos.form.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Formulario {

    private Map<String,Campo> campos = new LinkedHashMap<>();
    private String nome;
    private final String id;

    private PersistenciaFormulario persistencia;

    public Formulario( String id, String nome){
        this.nome = nome;
        this.id = id;
    }

    public void addCampo(Campo campo){
        campos.put(campo.getId(), campo);
    }

    public Campo getCampo(String id){
        return campos.get(id);
    }

    public List<Campo> getCampos(){
        return List.copyOf(campos.values());
    }

    public String getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setPersistencia(PersistenciaFormulario persistencia) {
        this.persistencia = persistencia;
    }

    @Override
    public String toString() {
        return "Formulario{" +
                "campos=" + campos +
                ", nome='" + nome + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public void persistir() {
        if(persistencia != null){
            persistencia.persistir(this);
        }

    }
}
