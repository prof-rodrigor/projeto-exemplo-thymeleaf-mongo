package br.ufpb.dcx.rodrigor.projetos.coordenador.service;

import br.ufpb.dcx.rodrigor.projetos.coordenador.model.Coordenador;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoordenadorService {
    private final Map<String, Coordenador> coordenadores = new LinkedHashMap<>();

    public CoordenadorService() {
        // Adicionando alguns coordenadores de exemplo
        adicionarCoordenador(new Coordenador("234233", "Rodrigo Rebouças", "rodrigor@dcx.ufpb.br"));
        adicionarCoordenador(new Coordenador("233322", "Peter Parker", "peter@homemaranha.com"));
    }

    public List<Coordenador> listarCoordenadores() {
        return coordenadores.values().stream().toList();
    }

    public void adicionarCoordenador(Coordenador coordenador) {
        coordenadores.put(coordenador.getMatricula(), coordenador);
    }

    public Coordenador getCoordenador(String matricula) {
        return coordenadores.get(matricula);
    }
}