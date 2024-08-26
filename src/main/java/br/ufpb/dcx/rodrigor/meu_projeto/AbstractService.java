package br.ufpb.dcx.rodrigor.meu_projeto;

import br.ufpb.dcx.rodrigor.meu_projeto.db.MongoDBConnector;

public class AbstractService {

    protected final MongoDBConnector mongoDBConnector;

    public AbstractService(MongoDBConnector mongoDBConnector) {
        this.mongoDBConnector = mongoDBConnector;
    }

    public MongoDBConnector getMongoDBConnector() {
        return mongoDBConnector;
    }
}
