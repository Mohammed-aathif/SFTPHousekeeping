package com.sst;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class RqstXMLHandler extends DefaultHandler {

    private String mCurrentTagName;
    private  SFTPParam sftp;
    
    private ArrayList<SFTPParam> rowparam = new ArrayList<SFTPParam>();
    
   
//==================================================================================================================================
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//==================================================================================================================================        
        this.mCurrentTagName = qName;
        if ("SFTP_Hsekp_Param".equals(this.mCurrentTagName)) {this.sftp = new SFTPParam();}
    }
 //==================================================================================================================================   
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
 //==================================================================================================================================   
        if ("ZESERVERID".equals(this.mCurrentTagName)) 
        {
        	String name = new String(ch, start, length);
            this.sftp.setZESERVERID(Integer.parseInt(name));
        }
        if ("ZESQN".equals(this.mCurrentTagName)) 
        {
            String sqn = new String(ch, start, length);
            this.sftp.setZESQN(Integer.parseInt(sqn));
        }
        if ("ZEACT".equals(this.mCurrentTagName)) 
        {
            String ZEACT = new String(ch, start, length);
            this.sftp.setZEACT(ZEACT);
        }
        if ("ZEFILEPATH".equals(this.mCurrentTagName)) 
        {
            String ZEFILEPATH = new String(ch, start, length);
            this.sftp.setZEFILEPATH(ZEFILEPATH);
        }
        if ("ZEFILENAME".equals(this.mCurrentTagName)) 
        {
            String ZEFILENAME = new String(ch, start, length);
            this.sftp.setZEFILENAME(ZEFILENAME);
        }
        if ("ZERETAINDAYS".equals(this.mCurrentTagName)) 
        {
            String ZERETAINDAYS = new String(ch, start, length);
            this.sftp.setZERETAINDAYS(Integer.parseInt(ZERETAINDAYS));
        }
        if ("ZEMINFILES".equals(this.mCurrentTagName)) 
        {
            String ZEMINFILES = new String(ch, start, length);
            this.sftp.setZEMINFILES(Integer.parseInt(ZEMINFILES));
        }
        if ("ZEACCTNAME".equals(this.mCurrentTagName)) 
        {
            String ZEACCTNAME = new String(ch, start, length);
            this.sftp.setZEACCTNAME(ZEACCTNAME);
        }
    }
//===================================================================================================================================   
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//==================================================================================================================================    
        if ("SFTP_Hsekp_Param".equals(qName)) {this.rowparam.add(this.sftp);this.sftp = null;}
        this.mCurrentTagName = null;
    } 
//===================================================================================================================================    
    public ArrayList<SFTPParam> GetRqstParam() {
//===================================================================================================================================    	
        return this.rowparam;
    }
}
//************************************************************************************************************************************