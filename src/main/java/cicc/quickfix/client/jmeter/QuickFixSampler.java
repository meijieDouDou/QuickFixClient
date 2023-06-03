package cicc.quickfix.client.jmeter;

import cicc.quickfix.client.FixInitiatorApplication;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

/**
 * @author fit2cloudzhao
 * @date 2022/8/2 21:45
 * @description:
 */
public class QuickFixSampler extends AbstractSampler implements TestStateListener {

    Logger log = LoggerFactory.getLogger(QuickFixSampler.class);


    SessionSettings settings ;
    FixInitiatorApplication application = new FixInitiatorApplication();


    SessionID sessionID = new SessionID("FIX.4.2", "INBOUND8", "HKOMSUAT");

    private static final String SERVER_IP = "server_ip";
    private static final String PORT = "port";

    private static final String PARAM = "request_param";

    private static final String FILENAME = "fileName";

    private static final String requstMsg = "requstMsg";


    public QuickFixSampler() {
        setName("QuickFixSampler sampler");
    }

    @Override
    public SampleResult sample(Entry entry) {
        try {
            settings = new SessionSettings("quickfix.properties");
//            settings = new SessionSettings("quickfix.properties");
            settings.setString("SocketConnectHost",getServerIp());
            settings.setString("SocketConnectPort",getPort().toString());


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

        SampleResult sampleResult = new SampleResult();
        try{
            sampleResult.sampleStart();

            try {
                if("NewOrder".equals(getParam())){
                    System.out.println(getParam());
                    application.sendNewOrderRequestByTxt(sessionID,getFileName());


                }else if("cancelOrder".equals(getParam())){
                    System.out.println(getParam());
                    application.cancelOrderRequestByTxt(sessionID,getFileName());

                }else {
                    System.out.println("请正确输入参数OrderType");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            sampleResult.setSuccessful(true);
            sampleResult.setResponseMessage("OK");
            sampleResult.setResponseData(("请求过程: "+application.getRequstMsg().toString()+"\n"+"请求结果: "+application.getResponMsg().toString()).getBytes());
            sampleResult.setResponseCode("200");
            sampleResult.sampleEnd();
        } catch (Exception e) {
            e.printStackTrace();
            sampleResult.setSuccessful(false);
            sampleResult.setResponseCode("500");
            sampleResult.setResponseData(("请求失败: ").getBytes());
            sampleResult.sampleEnd();
        }
        sampleResult.setDataType(SampleResult.TEXT);

        return sampleResult;
    }




    public FixInitiatorApplication getApplication() {
        return application;
    }

    public void setApplication(FixInitiatorApplication application) {
        this.application = application;
    }

    public String getServerIp() {
        return getPropertyAsString(SERVER_IP);
    }

    public void setServerIp(String serverIp) {
        setProperty(SERVER_IP, serverIp);
    }

    public String getRequstMsg() {
        return getPropertyAsString(requstMsg);
    }

    public void setSRequstMsg(String requstMsg) {
        setProperty(requstMsg, requstMsg);
    }

    public Integer getPort() {
        return getPropertyAsInt(PORT);
    }




    public void setPort(Integer port) {
        setProperty(PORT, port);
    }

    public void setParam(String param) {
        setProperty(PARAM, param);
    }

    public void setFileName(String fileName) {
        setProperty(FILENAME, fileName);
    }


    public String getParam() {
        return getPropertyAsString(PARAM);
    }

    public String getFileName() {
        return getPropertyAsString(FILENAME);
    }

    @Override
    public void testStarted() {

    }

    @Override
    public void testStarted(String s) {

    }

    @Override
    public void testEnded() {
        this.testEnded("local");
    }

    @Override
    public void testEnded(String s) {

    }

}
