package br.ufpb.dcx.rodrigor.projetos;

import br.ufpb.dcx.rodrigor.projetos.db.MongoDBRepository;
import br.ufpb.dcx.rodrigor.projetos.login.LoginController;
import br.ufpb.dcx.rodrigor.projetos.login.UsuarioController;
import br.ufpb.dcx.rodrigor.projetos.login.UsuarioService;
import br.ufpb.dcx.rodrigor.projetos.participante.controllers.ParticipanteController;
import br.ufpb.dcx.rodrigor.projetos.participante.services.ParticipanteService;
import br.ufpb.dcx.rodrigor.projetos.projeto.controllers.ProjetoController;
import br.ufpb.dcx.rodrigor.projetos.projeto.services.ProjetoService;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Consumer;

public class App {
    private static final Logger logger = LogManager.getLogger();

    private static final int PORTA_PADRAO = 8000;

    //Propriedades do application.properties:
    private static final String PROP_PORTA_SERVIDOR = "porta.servidor";
    private static final String PROP_MONGODB_CONNECTION_STRING = "mongodb.connectionString";


    private final Properties propriedades;
    private MongoDBRepository mongoDBRepository = null;

    public App() {
        this.propriedades = carregarPropriedades();
    }

    public void iniciar() {
        Javalin app = inicializarJavalin();
        configurarPaginasDeErro(app);
        configurarRotas(app);

        // Lidando com exceções não tratadas
        app.exception(Exception.class, (e, ctx) -> {
            logger.error("Erro não tratado", e);
            ctx.status(500);
        });
    }

    private void configurarPaginasDeErro(Javalin app) {
        app.error(404, ctx -> ctx.render("erro_404.html"));
        app.error(500, ctx -> ctx.render("erro_500.html"));
    }

    private Javalin inicializarJavalin() {
        int porta = obterPortaServidor();

        logger.info("Iniciando aplicação na porta {}", porta);

        Consumer<JavalinConfig> configConsumer = this::configureJavalin;

        return Javalin.create(configConsumer).start(porta);
    }

    private void configureJavalin(JavalinConfig config) {
        TemplateEngine templateEngine = configurarThymeleaf();

        config.events(event -> {
            event.serverStarting(() -> {
                mongoDBRepository = inicializarMongoDB();
                config.appData(Keys.MONGO_DB.key(), mongoDBRepository);
                registrarServicos(config, mongoDBRepository);
            });
            event.serverStopping(() -> {
                if (mongoDBRepository == null) {
                    logger.error("MongoDBConnector não deveria ser nulo ao parar o servidor");
                } else {
                    mongoDBRepository.close();
                    logger.info("Conexão com o MongoDB encerrada com sucesso");
                }
            });
        });
        config.staticFiles.add(staticFileConfig -> {
            staticFileConfig.directory = "/public";
            staticFileConfig.location = Location.CLASSPATH;
        });
        config.fileRenderer(new JavalinThymeleaf(templateEngine));

    }

    private void registrarServicos(JavalinConfig config, MongoDBRepository mongoDBRepository) {
        ParticipanteService participanteService = new ParticipanteService(mongoDBRepository);
        config.appData(Keys.PROJETO_SERVICE.key(), new ProjetoService(mongoDBRepository, participanteService));
        config.appData(Keys.PARTICIPANTE_SERVICE.key(), participanteService);
        config.appData(Keys.USUARIO_SERVICE.key(), new UsuarioService(mongoDBRepository));
    }


    private void configurarRotas(Javalin app) {
        LoginController loginController = new LoginController();
        app.get("/", ctx -> ctx.redirect("/login"));
        app.get("/login", loginController::mostrarPaginaLogin);
        app.post("/login", loginController::processarLogin);
        app.get("/logout", loginController::logout);

        app.get("/area-interna", ctx -> {
            if (ctx.sessionAttribute("usuario") == null) {
                ctx.redirect("/login");
            } else {
                ctx.render("area_interna.html");
            }
        });

        ProjetoController projetoController = new ProjetoController();
        app.get("/projetos", projetoController::listarProjetos);
        app.get("/projetos/novo", projetoController::mostrarFormulario);
        app.post("/projetos", projetoController::adicionarProjeto);
        app.get("/projetos/{id}/remover", projetoController::removerProjeto);

        ParticipanteController participanteController = new ParticipanteController();
        app.get("/participantes", participanteController::listarParticipantes);
        app.get("/participantes/novo", participanteController::mostrarFormularioCadastro);
        app.post("/participantes", participanteController::adicionarParticipante);
        app.get("/participantes/{id}/remover", participanteController::removerParticipante);


        // Rotas para o controlador de usuário
        UsuarioController usuarioController = new UsuarioController();
        app.get("/usuarios", usuarioController::listarUsuarios);
        app.get("/usuarios/novo", usuarioController::mostrarFormularioCadastro);
        app.post("/usuarios/cadastrar", usuarioController::cadastrarUsuario);
        app.get("/usuarios/signup", usuarioController::mostrarFormulario_signup);
        app.get("/usuarios/{id}/remover", usuarioController::removerUsuario);

    }



    private int obterPortaServidor() {
        if (propriedades.containsKey(PROP_PORTA_SERVIDOR)) {
            try {
                return Integer.parseInt(propriedades.getProperty(PROP_PORTA_SERVIDOR));
            } catch (NumberFormatException e) {
                logger.error("Porta definida no arquivo de propriedades não é um número válido: '{}'", propriedades.getProperty(PROP_PORTA_SERVIDOR));
                System.exit(1);
            }
        } else {
            logger.info("Porta não definida no arquivo de propriedades, utilizando porta padrão {}", PORTA_PADRAO);
        }
        return PORTA_PADRAO;
    }

    private TemplateEngine configurarThymeleaf() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    private MongoDBRepository inicializarMongoDB() {
        String connectionString = propriedades.getProperty(PROP_MONGODB_CONNECTION_STRING);
        logger.info("Lendo string de conexão ao MongoDB a partir do application.properties");
        if (connectionString == null) {
            logger.error("O string de conexão ao MongoDB não foi definido no arquivo /src/main/resources/application.properties");
            logger.error("Defina a propriedade '{}' no arquivo de propriedades", PROP_MONGODB_CONNECTION_STRING);
            System.exit(1);
        }

        logger.info("Conectando ao MongoDB");
        MongoDBRepository db = new MongoDBRepository(connectionString);
        if (db.conectado("config")) {
            logger.info("Conexão com o MongoDB estabelecida com sucesso");
        } else {
            logger.error("Falha ao conectar ao MongoDB");
            System.exit(1);
        }
        return db;
    }

    private Properties carregarPropriedades() {
        Properties prop = new Properties();
        try (InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if(input == null){
                logger.error("Arquivo de propriedades /src/main/resources/application.properties não encontrado");
                logger.error("Use o arquivo application.properties.examplo como base para criar o arquivo application.properties");
                System.exit(1);
            }
            prop.load(input);
        } catch (IOException ex) {
            logger.error("Erro ao carregar o arquivo de propriedades /src/main/resources/application.properties", ex);
            System.exit(1);
        }
        return prop;
    }

    public static void main(String[] args) {
        try {
            new App().iniciar();
        } catch (Exception e) {
            logger.error("Erro ao iniciar a aplicação", e);
            System.exit(1);
        }
    }
}