package cicc.quickfix.client.jmeter;


import cicc.quickfix.client.FixInitiatorApplication;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import quickfix.*;


public class AbstractJmeterClient extends AbstractJavaSamplerClient {
    SessionSettings settings = new SessionSettings();
    FixInitiatorApplication application = new FixInitiatorApplication();
    SessionID sessionID = new SessionID("FIX.4.2", "INBOUND8", "HKOMSUAT");

    @Override
    public void setupTest(JavaSamplerContext context) {
        if (JMeterContextService.getContext().getVariables().getIteration()==1) {


            try {
                settings = new SessionSettings("src/main/resources/quickfix.properties");
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

            System.out.println("******setupTest******");
        }
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult sampleResult = new SampleResult();
        try {

            sampleResult.sampleStart();

                // 发消息
                try {
                    Thread.sleep(1000);
                    application.sendNewOrderRequestByTxt(sessionID, sampleResult.getResultFileName());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            sampleResult.sampleEnd();
            sampleResult.setSuccessful(true);
            sampleResult.setResponseMessage("OK");
            sampleResult.setResponseCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            sampleResult.sampleEnd();

            sampleResult.setSuccessful(false);
            sampleResult.setResponseCode("500");
        }
        sampleResult.setDataType(SampleResult.TEXT);
        return sampleResult;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {

        System.out.println("******teardownTest******");
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        return arguments;
    }


}
