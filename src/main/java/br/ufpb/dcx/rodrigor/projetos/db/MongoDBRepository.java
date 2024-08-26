package br.ufpb.dcx.rodrigor.projetos.db;

import com.mongodb.MongoClientException;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;

public class MongoDBRepository {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(MongoDBRepository.class);
    private final MongoClient mongoClient;

    public MongoDBRepository(String connectionString) {
        this.mongoClient = MongoClients.create(connectionString);
    }

    public MongoDatabase getDatabase(String databaseName) {
        try {
            return mongoClient.getDatabase(databaseName);
        } catch (MongoException e) {
            logger.error("Failed to connect to MongoDB", e);
            return null;
        }
    }

    public boolean conectado(String databaseName) {
        if(mongoClient == null) {
            return false;
        }
        try {
            // Recuperando uma lista de coleções para verificar se a conexão está ativa
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.listCollectionNames().first();
            return true; // Conexão bem-sucedida
        } catch (MongoClientException e) {
            logger.error("Falha ao conectar ao MongoDB", e);
            return false;
        }
    }

    public void close() {
        mongoClient.close();
    }
}