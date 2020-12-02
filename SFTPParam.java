package com.sst;


import java.util.Date;
import java.util.Map;

import com.sap.aii.mapping.api.AbstractTrace;
import com.sap.aii.mapping.api.StreamTransformationConstants;

//======================================================================================================================
public class SFTPParam {
//======================================================================================================================
	public int ZESERVERID;
    public int ZESQN;
    public String  ZEACT;
    public String ZEFILEPATH;
    public String ZEFILENAME;
    public int ZERETAINDAYS;
    public int ZEMINFILES;
    public String ZEACCTNAME;
    public Date ZECRDT;
    public String ZEMSG;
    
//======================================================================================================================    
//Getters and setters    
//======================================================================================================================    
	public int getZESERVERID() {return ZESERVERID;}
	public void setZESERVERID(int zESERVERID) {ZESERVERID = zESERVERID;}
	public int getZESQN() {return ZESQN;}
	public void setZESQN(int zESQN) {ZESQN = zESQN;}
	public String getZEACT() {return ZEACT;}
	public void setZEACT(String zEACT) {ZEACT = zEACT;}
	public String getZEFILEPATH() {return ZEFILEPATH;}
	public void setZEFILEPATH(String zEFILEPATH) {ZEFILEPATH = zEFILEPATH;}
	public String getZEFILENAME() {return ZEFILENAME;}
	public void setZEFILENAME(String zEFILENAME) {ZEFILENAME = zEFILENAME;}
	public int getZERETAINDAYS() {return ZERETAINDAYS;}
	public void setZERETAINDAYS(int zERETAINDAYS) {ZERETAINDAYS = zERETAINDAYS;}
	public int getZEMINFILES() {return ZEMINFILES;}
	public void setZEMINFILES(int zEMINFILES) {ZEMINFILES = zEMINFILES;}
	public String getZEACCTNAME() {return ZEACCTNAME;}
	public void setZEACCTNAME(String zEACCTNAME) {ZEACCTNAME = zEACCTNAME;}
	public Date getZECRDT() {return ZECRDT;}
	public void setZECRDT(Date zECRDT) {ZECRDT = zECRDT;}
	public String getZEMSG() {return ZEMSG;}
	public void setZEMSG(String zEMSG) {ZEMSG = zEMSG;}
//----------------------------------------------------------------------------------------------------------------------
    public void clear() {
//----------------------------------------------------------------------------------------------------------------------
    	ZESERVERID = 0;
    	ZESQN = 0;
    	ZEACT = "";
    	ZEFILEPATH = "";
    	ZEFILENAME = "";
    	ZERETAINDAYS = 0;
    	ZEMINFILES = 0;
    	ZEACCTNAME = "";
    	ZECRDT = new Date();
    	ZEMSG = null;
    }
 //----------------------------------------------------------------------------------------------------------------------    
    public String toString() {
 //----------------------------------------------------------------------------------------------------------------------   	
        return this.ZESERVERID+" "+this.ZEACCTNAME+" "+this.ZEFILENAME+" "+this.ZEFILEPATH+" "+this.ZESQN ;
     }
}
//**********************************************************************************************************************
