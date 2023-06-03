package cicc.quickfix.client.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
//import org.example.OMSClient;
import org.slf4j.Logger;

import java.io.Serializable;

public class OMSJmeterClient extends AbstractJavaSamplerClient implements Serializable {
    private static final long serialVersionUID = 5986604070792870240L;


    @Override
    public void setupTest(JavaSamplerContext context) {
        System.out.println("******setupTest******");
    }



    @Override
    public Arguments getDefaultParameters() {
        return super.getDefaultParameters();
    }

    @Override
    protected Logger getNewLogger() {
        return super.getNewLogger();
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        return null;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        super.teardownTest(context);
    }
}
