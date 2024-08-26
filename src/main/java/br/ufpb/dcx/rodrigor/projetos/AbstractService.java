package br.ufpb.dcx.rodrigor.projetos;

import br.ufpb.dcx.rodrigor.projetos.db.MongoDBRepository;

public class AbstractService {

    protected final MongoDBRepository mongoDBRepository;

    public AbstractService(MongoDBRepository mongoDBRepository) {
        this.mongoDBRepository = mongoDBRepository;
    }

    public MongoDBRepository getMongoDBConnector() {
        return mongoDBRepository;
    }
}
