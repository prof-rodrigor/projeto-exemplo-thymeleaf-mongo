package br.ufpb.dcx.rodrigor.projetos;

import br.ufpb.dcx.rodrigor.projetos.db.MongoDBConnector;

public class AbstractService {

    protected final MongoDBConnector mongoDBConnector;

    public AbstractService(MongoDBConnector mongoDBConnector) {
        this.mongoDBConnector = mongoDBConnector;
    }

    public MongoDBConnector getMongoDBConnector() {
        return mongoDBConnector;
    }
}
