package br.ufpb.dcx.rodrigor.projetos.projeto.services;

import br.ufpb.dcx.rodrigor.meu_projeto.AbstractService;
import br.ufpb.dcx.rodrigor.meu_projeto.db.MongoDBConnector;
import br.ufpb.dcx.rodrigor.projetos.participante.model.Participante;
import br.ufpb.dcx.rodrigor.projetos.participante.services.ParticipanteService;
import br.ufpb.dcx.rodrigor.projetos.projeto.model.Projeto;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class ProjetoService extends AbstractService {

    private final MongoCollection<Document> collection;
    private final ParticipanteService participanteService;

    private static final Logger logger = LogManager.getLogger();

    public ProjetoService(MongoDBConnector mongoDBConnector, ParticipanteService participanteService) {
        super(mongoDBConnector);
        this.participanteService = participanteService;
        MongoDatabase database = mongoDBConnector.getDatabase("projetos");
        this.collection = database.getCollection("projetos");
    }



    public List<Projeto> listarProjetos() {
        List<Projeto> projetos = new ArrayList<>();
        for (Document doc : collection.find()) {
            projetos.add(documentToProjeto(doc));
        }
        return projetos;
    }

    public Optional<Projeto> buscarProjetoPorId(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        return Optional.ofNullable(doc).map(this::documentToProjeto);
    }

    public void adicionarProjeto(Projeto projeto) {
        Document doc = projetoToDocument(projeto);
        collection.insertOne(doc);
        projeto.setId(doc.getObjectId("_id").toString());
    }

    public void atualizarProjeto(Projeto projetoAtualizado) {
        Document doc = projetoToDocument(projetoAtualizado);
        collection.replaceOne(eq("_id", new ObjectId(projetoAtualizado.getId())), doc);
    }

    public void removerProjeto(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }

    public Projeto documentToProjeto(Document doc) {
        Projeto projeto = new Projeto();
        projeto.setId(doc.getObjectId("_id").toString());
        projeto.setNome(doc.getString("nome"));
        projeto.setDescricao(doc.getString("descricao"));
        projeto.setDataInicio(doc.getDate("dataInicio").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        projeto.setDataEncerramento(doc.getDate("dataEncerramento").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        ObjectId coordenadorId = doc.getObjectId("coordenador");
        if(coordenadorId == null) {
            logger.warn("Projeto '{}' não possui coordenador", projeto.getNome());
        }
        if (coordenadorId != null) {
            Participante coordenador = participanteService.buscarParticipantePorId(coordenadorId.toString())
                    .orElse(null);
            projeto.setCoordenador(coordenador);
        }

        return projeto;
    }

    public Document projetoToDocument(Projeto projeto) {
        Document doc = new Document();
        if (projeto.getId() != null) {
            doc.put("_id", new ObjectId(projeto.getId()));
        }
        doc.put("nome", projeto.getNome());
        doc.put("descricao", projeto.getDescricao());
        doc.put("dataInicio", java.util.Date.from(projeto.getDataInicio().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
        doc.put("dataEncerramento", java.util.Date.from(projeto.getDataEncerramento().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));

        if (projeto.getCoordenador() != null) {
            doc.put("coordenador", new ObjectId(String.valueOf(projeto.getCoordenador().getId())));
        }

        return doc;
    }
}