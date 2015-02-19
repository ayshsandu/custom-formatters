package org.wso2.carbon.customFormatters;

import org.apache.axiom.om.OMOutputFormat;
import org.apache.axis2.transport.http.ApplicationXMLFormatter;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.OutputStream;

/*
* This class extends default org.apache.axis2.transport.http.ApplicationXMLFormatter
* */
public class CustomXMLFormatter extends org.apache.axis2.transport.http.ApplicationXMLFormatter {

    private static String TRANSFORM_APOS = "TRANSFORM_APOS";
    /*
    * This method Overrides the default writeTo method depending on the property "TRANSFORM_APOS"
     * if "TRANSFORM_APOS" property is set to true this method will replace ' to &apos;
     * otherwise super.writeTo() method is invoked
     * */
    @Override
    public void writeTo(MessageContext messageContext, OMOutputFormat format,
                        OutputStream outputStream, boolean preserve) throws AxisFault {
        Boolean isTransformApos = messageContext.getProperty(TRANSFORM_APOS) != null ? (Boolean) messageContext.
                getProperty(TRANSFORM_APOS) : false;
        if(isTransformApos){
            try {
                String message = (String) messageContext.getEnvelope().getBody().toString();
                if (message != null) {
                    String output = StringUtils.replace(message, "'", "&apos;");
                    byte[] bytes = output.getBytes("UTF-8");
                    outputStream.write(bytes);

                }
            } catch (IOException e) {
                throw AxisFault.makeFault(e);
            }
        }else{
            super.writeTo(messageContext,format, outputStream, preserve);
        }

    }

}
