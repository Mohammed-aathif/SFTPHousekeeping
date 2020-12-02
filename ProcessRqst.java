package com.sst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sap.aii.mapping.api.StreamTransformationException;

//======================================================================================================================
public class ProcessRqst {
//======================================================================================================================
	String strRespXML = null;
	
//----------------------------------------------------------------------------------------------------------------------
	String DoFileHsekp(ArrayList<SFTPParam> rqstParam) throws StreamTransformationException{
//----------------------------------------------------------------------------------------------------------------------

		ArrayList<SFTPParam> del =new ArrayList<SFTPParam>() ;
		ArrayList<SFTPParam> respParam = new ArrayList<SFTPParam>();
		SFTPParam res = new SFTPParam();
		Date myDate = new Date();
		Lookup lookup = new Lookup();
		DelFileFromSFTP Delete = new DelFileFromSFTP();
		SFTPExceptionCheck exc = new SFTPExceptionCheck();
		String strHostRcvrAgency="";
        String strPortRcvrAgency="";
        String strPassRcvrAgency="";
        String strUserRcvrAgency="";
        String strKeyValue="";
		int MinFiles = 0;
		
		//Start of changes,Before deleting files this will check for exception 
		
		if (rqstParam != null) 
		{
		  for(SFTPParam sftpExc : rqstParam) 
		  {          	
		     strKeyValue = Integer.toString(sftpExc.ZESERVERID); 
		     strHostRcvrAgency = "Server_"+strKeyValue+"_host";
		     strPortRcvrAgency = "Server_"+strKeyValue+"_port";
		     strPassRcvrAgency = "Server_"+strKeyValue+"_"+sftpExc.ZEACCTNAME;
		     strUserRcvrAgency = sftpExc.ZEACCTNAME;	
		     MinFiles =sftpExc.ZEMINFILES;
		     
		     System.out.println("Serverid: "+strKeyValue+"\nHost: "+strHostRcvrAgency+"\nPort: "+strPortRcvrAgency+"\nPassword: "+strPassRcvrAgency+"\nUser : "+strUserRcvrAgency+"\n");
		     
		     String strHostname;
			try {
			strHostname = Lookup.GetSFTPConValue("", "SFTPConnection", "Server", "", strHostRcvrAgency, "Hostname", strKeyValue);
		    int strPort = Integer.parseInt(Lookup.GetSFTPConValue("", "SFTPConnection", "Server", "", strPortRcvrAgency, "Port", strKeyValue));
			String	strPass = Lookup.GetSFTPConValue("", "SFTPConnection", "Server", "", strPassRcvrAgency, "Password", strKeyValue);
			del.addAll(exc.DeleteFilesSFTP(strHostname, strPort, strUserRcvrAgency, strPass, sftpExc.ZEFILEPATH, "SFTP", sftpExc.ZERETAINDAYS, MinFiles, sftpExc.ZEFILENAME));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}          
		  }
		}
	
		//End of changes, Before deleting files this will check for exception 		
		if (rqstParam != null) 
		{
		  for(SFTPParam sftp : rqstParam) 
		  {          	
		     strKeyValue = Integer.toString(sftp.ZESERVERID); 
		     strHostRcvrAgency = "Server_"+strKeyValue+"_host";
		     strPortRcvrAgency = "Server_"+strKeyValue+"_port";
		     strPassRcvrAgency = "Server_"+strKeyValue+"_"+sftp.ZEACCTNAME;
		     strUserRcvrAgency = sftp.ZEACCTNAME;	
		     MinFiles =sftp.ZEMINFILES;
		     
		     System.out.println("Serverid: "+strKeyValue+"\nHost: "+strHostRcvrAgency+"\nPort: "+strPortRcvrAgency+"\nPassword: "+strPassRcvrAgency+"\nUser : "+strUserRcvrAgency+"\n");
		     
		     String strHostname;
			try {
			strHostname = Lookup.GetSFTPConValue("", "SFTPConnection", "Server", "", strHostRcvrAgency, "Hostname", strKeyValue);
		    int strPort = Integer.parseInt(Lookup.GetSFTPConValue("", "SFTPConnection", "Server", "", strPortRcvrAgency, "Port", strKeyValue));
			String	strPass = Lookup.GetSFTPConValue("", "SFTPConnection", "Server", "", strPassRcvrAgency, "Password", strKeyValue);
			del.addAll(Delete.DeleteFilesSFTP(strHostname, strPort, strUserRcvrAgency, strPass, sftp.ZEFILEPATH, "SFTP", sftp.ZERETAINDAYS, MinFiles, sftp.ZEFILENAME));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}          
			 for(int i=0 ; i<del.size() ; i++)
		               {
		            	res = new SFTPParam();
		            	SFTPParam Del = del.get(i);
		            	res.setZESERVERID(Integer.parseInt(strKeyValue));
		            	res.setZEACCTNAME(strUserRcvrAgency);
		           		res.setZEFILENAME(Del.ZEFILENAME);
		           		res.setZESQN(sftp.ZESQN);
		           		res.setZEFILEPATH(sftp.ZEFILEPATH);
		           		res.setZECRDT((Del.ZECRDT));
		           		res.setZEMSG(sftp.ZEMSG);
		           		
		           		respParam.add(res);
		               }
		               del.clear(); 
		            }
            	}      
	
		return BuildRespXML(respParam);
	}

//----------------------------------------------------------------------------------------------------------------------
	String BuildRespXML(ArrayList<SFTPParam> respParam) {
//----------------------------------------------------------------------------------------------------------------------		
		String strRespXML = null;	
		strRespXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		strRespXML = strRespXML+"<ns0:MT_SFTP_Hsekp_Resp xmlns:ns0=\"http://services.sst.com/sftphsekp\">";
		if(respParam!=null)
		{
		for(SFTPParam resp : respParam)
			{
				strRespXML = strRespXML+"<SFTP_Hsekp_Result>";
				strRespXML = strRespXML+"<ZESERVERID>"+resp.ZESERVERID+"</ZESERVERID>";
				strRespXML = strRespXML+"<ZESQN>"+resp.ZESQN+"</ZESQN>";
				strRespXML = strRespXML+"<ZEFILEPATH>"+resp.ZEFILEPATH+"</ZEFILEPATH>";
				strRespXML = strRespXML+"<ZEFILENAME>"+resp.ZEFILENAME+"</ZEFILENAME>";
				new SimpleDateFormat("yyyyMMdd").format(resp.ZECRDT);
				//strRespXML = strRespXML+"<ZECRDT>"+resp.ZECRDT+"</ZECRDT>";
				strRespXML = strRespXML+"<ZECRDT>"+new SimpleDateFormat("yyyy-MM-dd").format(resp.ZECRDT)+"</ZECRDT>";
				strRespXML = strRespXML+"<ZEMSG>"+"Successfully Deleted"+"</ZEMSG>";
				strRespXML = strRespXML+"</SFTP_Hsekp_Result>";
			}
		}
		strRespXML= strRespXML+"</ns0:MT_SFTP_Hsekp_Resp>";
		
		return strRespXML;
	}
}

//**********************************************************************************************************************
