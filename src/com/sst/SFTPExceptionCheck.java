package com.sst;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.sap.aii.mapping.api.*;

//=========================================================================================================================================================================================================================================
public class SFTPExceptionCheck
{
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public ArrayList<SFTPParam> DeleteFilesSFTP(String SFTPHOST, int SFTPPORT, String SFTPUSER, String SFTPPASS, String Path, String LocationType, int RetainDays, int MinFiles, String FileName) {
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
	/*
	 * This is just a Dummy Class it will check for Exception ie. if We enter wrong directory which 
	 * is not present in sftp server or wrong server Id which is not present in Value mapping table.
	 * Also, if we maintain wrong directory in second sequence of the table in ECC
	 * it will delete all the files from first(1st sequence) directory but no response will be sent back to ECC because 
	 * Of the exception raised from this class and it will terminates as an error in Mapping 	
	 */
		ArrayList<SFTPParam> filesDeleted = new ArrayList<SFTPParam>();
		try {
		SFTPParam res = new SFTPParam();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(Path);
			ChannelSftp sftp = (ChannelSftp) channel;
			sftp.cd(Path);			
			Vector<String> files = sftp.ls(FileName);
			for (int i = 0; i < files.size(); i++) {				 
					Object obj = files.elementAt(i);
					if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
				
					}
				}			
		
				channelSftp.disconnect();
				session.disconnect(); 
			} catch (JSchException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (SftpException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		return filesDeleted;
	
		
	
	}
}

//************************************************************************************************************************************
