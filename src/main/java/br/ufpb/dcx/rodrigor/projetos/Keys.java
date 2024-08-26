package br.ufpb.dcx.rodrigor.projetos;

import br.ufpb.dcx.rodrigor.projetos.db.MongoDBRepository;
import br.ufpb.dcx.rodrigor.projetos.participante.services.ParticipanteService;
import br.ufpb.dcx.rodrigor.projetos.projeto.services.ProjetoService;
import io.javalin.config.Key;

public enum Keys {
    MONGO_DB(new Key<MongoDBRepository>("mongo-db")),
    PROJETO_SERVICE(new Key<ProjetoService>("projeto-service")),
    PARTICIPANTE_SERVICE(new Key<ParticipanteService>("participante-service"))
    ;

    private final Key<?> k;

    <T> Keys(Key<T> key) {
        this.k = key;
    }

    public <T> Key<T> key() {
        @SuppressWarnings("unchecked")
        Key<T> typedKey = (Key<T>) this.k;
        return typedKey;
    }
}