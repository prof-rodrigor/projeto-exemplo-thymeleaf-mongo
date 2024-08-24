package br.ufpb.dcx.rodrigor.projetos.projeto.model;

import br.ufpb.dcx.rodrigor.projetos.coordenador.model.Coordenador;

import java.time.LocalDate;

public class Projeto {
    private Long id;
    private String nome;
    private String descricao;
    private Coordenador coordenador;
    private LocalDate dataInicio;
    private LocalDate dataEncerramento;

    public Projeto(Long id, String nome, String descricao, Coordenador coordenador, LocalDate dataInicio, LocalDate dataEncerramento) {
        this.id = id;
        this.nome = nome;
        this.coordenador = coordenador;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataEncerramento = dataEncerramento;
    }

    public Projeto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Coordenador getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Coordenador coordenador) {
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