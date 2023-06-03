package cicc.quickfix.client.jmeter;


import cicc.quickfix.client.FixInitiatorApplication;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import quickfix.*;


public class RunTestJmeterClient extends SetupJmeterClient {
    private static final String HOST = "HOST";
    private static final String PORT = "PORT";
    private String fileName;
    private String RequestMethod;


    @Override
    public void setupTest(JavaSamplerContext context) {
        String SocketConnectHost = context.getParameter(HOST);
        String SocketConnectPort = context.getParameter(PORT);
        settings.setString("SocketConnectHost",SocketConnectHost);
        settings.setString("SocketConnectPort",SocketConnectPort);
        System.out.println("******setupTest******");
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult sampleResult = new SampleResult();
        try{
            sampleResult.sampleStart();

            try {
                if("NewOrder".equals(javaSamplerContext.getParameter("RequestMethod"))){
                    System.out.println(javaSamplerContext.getParameter("RequestMethod"));
                    application.sendNewOrderRequestByTxt(sessionID,javaSamplerContext.getParameter("fileName"));

                }else if("cancelOrder".equals(javaSamplerContext.getParameter("RequestMethod"))){
                    System.out.println(javaSamplerContext.getParameter("RequestMethod"));
                    application.cancelOrderRequestByTxt(sessionID,javaSamplerContext.getParameter("fileName"));

                }else {
                    System.out.println("请正确输入参数RequestMethod");
                }

            } catch (Exception e) {
                    e.printStackTrace();
            }sampleResult.setSuccessful(true);
        } catch (Exception e) {
            e.printStackTrace();
            sampleResult.setSuccessful(false);
        } finally {
            sampleResult.sampleEnd();
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
        param.addArgument(HOST, "请输入ip");
        param.addArgument(PORT, "请输入端口");
        param.addArgument("fileName","请指定文件名");
        param.addArgument("RequestMethod","创建订单输入：NewOrder，取消订单输入：cancelOrder");
        return param;
    }
}
