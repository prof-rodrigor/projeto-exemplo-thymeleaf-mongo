package br.ufpb.dcx.rodrigor.projetos.participante.model;

import org.bson.types.ObjectId;

public class Participante {
    private ObjectId id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private CategoriaParticipante categoria;

    // Getters and Setters

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public CategoriaParticipante getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaParticipante categoriaParticipante) {
        this.categoria = categoriaParticipante;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", categoria=" + categoria +
                '}';
    }
}