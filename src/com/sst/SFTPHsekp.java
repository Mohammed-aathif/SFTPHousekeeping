package com.sst;

import java.io.InputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.sap.aii.mapping.api.AbstractTrace;
import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationConstants;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;
import com.sap.aii.mapping.api.StreamTransformationException;




//======================================================================================================================
public class SFTPHsekp extends AbstractTransformation  {
//======================================================================================================================
	final AbstractTrace trace = null;
	
//----------------------------------------------------------------------------------------------------------------------
	public void transform(TransformationInput in, TransformationOutput out) throws StreamTransformationException {
//----------------------------------------------------------------------------------------------------------------------
	try {
	InputStream inStream = in.getInputPayload().getInputStream(); 
	ArrayList<SFTPParam> rqstParam = GetRqstParam(inStream); 
	String strRespXML = new ProcessRqst().DoFileHsekp(rqstParam);
	out.getOutputPayload().getOutputStream().write(strRespXML.getBytes());
	} catch (Exception e) {		
		getTrace().addDebugMessage(e.getMessage());
		getTrace().addDebugMessage("Inside creation XML");
		e.printStackTrace();
		throw new StreamTransformationException(e.getMessage());
	} 
	} 
//----------------------------------------------------------------------------------------------------------------------
	ArrayList<SFTPParam> GetRqstParam(InputStream inStream) {
//----------------------------------------------------------------------------------------------------------------------
		RqstXMLHandler rqstHndlr = new RqstXMLHandler( );
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(rqstHndlr);
            saxParser.parse(inStream, rqstHndlr);
            return rqstHndlr.GetRqstParam( );
        } catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
        }
        
	} 
}
//**********************************************************************************************************************

