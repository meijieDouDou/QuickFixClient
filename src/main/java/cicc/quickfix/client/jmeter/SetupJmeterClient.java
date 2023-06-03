package cicc.quickfix.client.jmeter;

import cicc.quickfix.client.FixInitiatorApplication;
import com.kitfox.svg.A;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import quickfix.*;

public class SetupJmeterClient extends AbstractJavaSamplerClient {
    SessionSettings settings = new SessionSettings();
    FixInitiatorApplication application = new FixInitiatorApplication();
    SessionID sessionID = new SessionID("FIX.4.2", "INBOUND8", "HKOMSUAT");

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult sampleResult = new SampleResult();
        try {

            sampleResult.sampleStart();

            try {
                settings = new SessionSettings("quickfix.properties");
            } catch (ConfigError configError) {
                System.out.println("Warning: config error! " + configError);
            }

            MessageStoreFactory storeFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new FileLogFactory(settings);
            MessageFactory messageFactory = new DefaultMessageFactory();
            try {
                SocketInitiator initiator = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
                initiator.start();

            } catch (ConfigError e) {
                e.printStackTrace();
            }
        } catch (RuntimeError e) {
            e.printStackTrace();
            sampleResult.setSuccessful(false);
        }
        return sampleResult;
    }


}
