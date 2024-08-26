package br.ufpb.dcx.rodrigor.projetos.participante.services;

import br.ufpb.dcx.rodrigor.projetos.AbstractService;
import br.ufpb.dcx.rodrigor.projetos.db.MongoDBConnector;
import br.ufpb.dcx.rodrigor.projetos.participante.model.CategoriaParticipante;
import br.ufpb.dcx.rodrigor.projetos.participante.model.Participante;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class ParticipanteService extends AbstractService {

    private final MongoCollection<Document> collection;

    public ParticipanteService(MongoDBConnector mongoDBConnector) {
        super(mongoDBConnector);
        MongoDatabase database = mongoDBConnector.getDatabase("projetos");
        this.collection = database.getCollection("participantes");
    }

    public List<Participante> listarParticipantesPorCategoria(CategoriaParticipante categoriaParticipante) {
        List<Participante> participantes = new LinkedList<>();
        for (Document doc : collection.find(eq("categoria", categoriaParticipante.name()))) {
            participantes.add(documentToParticipante(doc));
        }
        return participantes;
    }

    public List<Participante> listarProfessores() {
        return listarParticipantesPorCategoria(CategoriaParticipante.PROFESSOR);
    }

    public List<Participante> listarParticipantes() {
        List<Participante> participantes = new LinkedList<>();
        for (Document doc : collection.find()) {
            participantes.add(documentToParticipante(doc));
        }
        return participantes;
    }



    public Optional<Participante> buscarParticipantePorId(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        return Optional.ofNullable(doc).map(ParticipanteService::documentToParticipante);
    }

    public void adicionarParticipante(Participante participante) {
        Document doc = participanteToDocument(participante);
        collection.insertOne(doc);
        participante.setId(doc.getObjectId("_id"));
    }

    public void atualizarParticipante(Participante participanteAtualizado) {
        Document doc = participanteToDocument(participanteAtualizado);
        collection.replaceOne(eq("_id", new ObjectId(participanteAtualizado.getId().toString())), doc);
    }

    public void removerParticipante(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }

    public static Participante documentToParticipante(Document doc) {
        Participante participante = new Participante();
        participante.setId(doc.getObjectId("_id"));
        participante.setNome(doc.getString("nome"));
        participante.setSobrenome(doc.getString("sobrenome"));
        participante.setEmail(doc.getString("email"));
        participante.setTelefone(doc.getString("telefone"));
        participante.setCategoria(CategoriaParticipante.valueOf(doc.getString("categoria")));
        return participante;
    }

    public static Document participanteToDocument(Participante participante) {
        Document doc = new Document();
        if (participante.getId() != null) {
            doc.put("_id", participante.getId());
        }
        doc.put("nome", participante.getNome());
        doc.put("sobrenome", participante.getSobrenome());
        doc.put("email", participante.getEmail());
        doc.put("telefone", participante.getTelefone());
        doc.put("categoria", participante.getCategoria().name());
        return doc;
    }
}