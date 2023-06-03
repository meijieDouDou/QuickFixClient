package cicc.quickfix.client.jmeter;


import cicc.quickfix.client.FixInitiatorApplication;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import quickfix.*;


public class MSJmeterFixClient2 extends AbstractJavaSamplerClient {

    SessionSettings settings ;
    FixInitiatorApplication application = new FixInitiatorApplication();
    SessionID sessionID = new SessionID("FIX.4.2", "INBOUND8", "HKOMSUAT");


    private static final String host = "host";
    private static final String port = "port";



    @Override
    public void setupTest(JavaSamplerContext context) {
        String SocketConnectHost = context.getParameter(host);
        String SocketConnectPort = context.getParameter(port);

        try {
            settings = new SessionSettings("quickfix.properties");
            settings.setString("SocketConnectHost","10.83.15.34");
            settings.setString("SocketConnectPort","9000");
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

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult sampleResult = new SampleResult();
        try{
            sampleResult.sampleStart();

            try {application.sendNewOrderRequestByTxt(sessionID,javaSamplerContext.getParameter("message.log"));
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
        Arguments param = new Arguments();
        param.addArgument(host, "请输入ip");
        param.addArgument(port, "请输入端口");
        param.addArgument("fileName","请指定文件名");
        param.addArgument("ordertype","创建订单输入：NewOrder，取消订单输入：cancelOrder");
        return param;
    }
}
