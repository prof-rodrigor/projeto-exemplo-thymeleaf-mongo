package br.ufpb.dcx.rodrigor.projetos.projeto.model;

import br.ufpb.dcx.rodrigor.projetos.participante.model.CategoriaParticipante;
import br.ufpb.dcx.rodrigor.projetos.participante.model.Participante;

import java.time.LocalDate;

public class Projeto {
    private String id;
    private String nome;
    private String descricao;
    private Participante coordenador;
    private LocalDate dataInicio;
    private LocalDate dataEncerramento;

    public Projeto(String id, String nome, String descricao, Participante coordenador, LocalDate dataInicio, LocalDate dataEncerramento) {
        this.id = id;
        this.nome = nome;
        this.coordenador = coordenador;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataEncerramento = dataEncerramento;
    }

    public Projeto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Participante getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Participante coordenador) {
        if(!coordenador.getCategoria().equals(CategoriaParticipante.PROFESSOR)) {
            throw new IllegalArgumentException("O coordenador deve ser um professor: "+coordenador);
        }
        this.coordenador = coordenador;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(LocalDate dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }
}