package br.ufpb.dcx.rodrigor.projetos.projeto.services;

import br.ufpb.dcx.rodrigor.projetos.coordenador.service.CoordenadorService;
import br.ufpb.dcx.rodrigor.projetos.projeto.model.Projeto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjetoService {
    private List<Projeto> projetos = new ArrayList<>();
    private Long ultimoId = 1L;

    private CoordenadorService coordenadorService = new CoordenadorService();

    public ProjetoService() {
        // Adicionando alguns projetos de exemplo
        Projeto p1 = new Projeto(ultimoId++, "Projeto Exemplo 1", "Descrição do projeto 1", coordenadorService.getCoordenador("234233"), LocalDate.now(), LocalDate.now().plusMonths(6));
        projetos.add(p1);
        projetos.add(new Projeto(ultimoId++, "Projeto Exemplo 2", "Descrição do projeto 2",coordenadorService.getCoordenador("233322"),  LocalDate.now().minusMonths(2), LocalDate.now().plusMonths(4)));
    }

    public List<Projeto> listarProjetos() {
        return projetos;
    }

    public Optional<Projeto> buscarProjetoPorId(Long id) {
        return projetos.stream().filter(projeto -> projeto.getId().equals(id)).findFirst();
    }

    public void adicionarProjeto(Projeto projeto) {
        projeto.setId(ultimoId++);
        projetos.add(projeto);
    }

    public void atualizarProjeto(Projeto projetoAtualizado) {
        projetos.replaceAll(projeto -> projeto.getId().equals(projetoAtualizado.getId()) ? projetoAtualizado : projeto);
    }

    public void removerProjeto(Long id) {
        projetos.removeIf(projeto -> projeto.getId().equals(id));
    }
}