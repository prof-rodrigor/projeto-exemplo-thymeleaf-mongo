// src/main/java/br/ufpb/dcx/rodrigor/projetos/login/UsuarioService.java
package br.ufpb.dcx.rodrigor.projetos.login;

import br.ufpb.dcx.rodrigor.projetos.AbstractService;
import br.ufpb.dcx.rodrigor.projetos.db.MongoDBRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService extends AbstractService {

    private final MongoCollection<Document> usuariosCollection;

    public UsuarioService(MongoDBRepository mongoDBRepository) {
        super(mongoDBRepository);
        MongoDatabase database = mongoDBRepository.getDatabase("projetos");
        this.usuariosCollection = database.getCollection("usuarios");
    }

    public void cadastrarUsuario(Usuario usuario) {
        if (buscarUsuarioPorLogin(usuario.getLogin()) != null) {
            throw new IllegalArgumentException("Já existe um usuário com este login.");
        }
        Document doc = usuarioToDocument(usuario);
        usuariosCollection.insertOne(doc);
        usuario.setId(doc.getObjectId("_id").toString());
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        for (Document doc : usuariosCollection.find()) {
            usuarios.add(documentToUsuario(doc));
        }
        return usuarios;
    }

    public void removerUsuario(String id) {
        usuariosCollection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    public Usuario buscarUsuarioPorLogin(String login) {
        Document doc = usuariosCollection.find(new Document("login", login)).first();
        if (doc == null) {
            return null;
        }
        return documentToUsuario(doc);
    }

    private Document usuarioToDocument(Usuario usuario) {
        String hashedSenha = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        return new Document("login", usuario.getLogin())
                .append("nome", usuario.getNome())
                .append("senha", hashedSenha);
    }

    private Usuario documentToUsuario(Document doc) {
        return new Usuario(
                doc.getObjectId("_id").toString(),
                doc.getString("login"),
                doc.getString("nome"),
                doc.getString("senha")
        );
    }
}