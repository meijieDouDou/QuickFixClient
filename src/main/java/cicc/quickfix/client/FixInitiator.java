package cicc.quickfix.client;

import cicc.quickfix.client.jmeter.QuickFixSampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

import java.io.IOException;
import java.sql.SQLOutput;

public class FixInitiator {

    Logger log = LoggerFactory.getLogger(FixInitiator.class);


    public static SocketInitiator initiator;
    public static SessionSettings settings;
    public static FixInitiatorApplication application;

    public static SocketInitiator getInitiator() {
        return initiator;
    }

    public static void setInitiator(SocketInitiator initiator) {
        FixInitiator.initiator = initiator;
    }

    public static SessionSettings getSettings() {
        return settings;
    }

    public static void setSettings(SessionSettings settings) {
        FixInitiator.settings = settings;
    }

    public static FixInitiatorApplication getApplication() {
        return application;
    }

    public static void setApplication(FixInitiatorApplication application) {
        FixInitiator.application = application;
    }




    public FixInitiator() {
        log.info("sdfsdfsdfs========================");
        try {
            settings = new SessionSettings("src/main/resources/quickfix.properties");
        }catch (ConfigError configError){
            System.out.println("Warning: config error! " + configError);
        }

        application = new FixInitiatorApplication();

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        try {
            initiator = new SocketInitiator(application, storeFactory, settings, logFactory,messageFactory);
        }catch (ConfigError configError){
            System.out.println("Warning: config error! " + configError);
        }
    }

    public void startServer(){
        try {
            initiator.start();
        }catch (ConfigError configError){
            configError.printStackTrace();
        }
    }

    public void stopServer(){
        initiator.stop();
    }

    public static void main(String[] args) {
        FixInitiator fixInitiator = new FixInitiator();
        fixInitiator.startServer();

        SessionID sessionID = new SessionID("FIX.4.2", "INBOUND8", "HKOMSUAT");

        // 发消息
        try {
            Thread.sleep(5000);
            application.cancelOrderRequestByTxt(sessionID,"message.log");
            Thread.sleep(5000);
            fixInitiator.stopServer();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
