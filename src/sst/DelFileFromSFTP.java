package com.sst;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
public class DelFileFromSFTP 
//=========================================================================================================================================================================================================================================
{
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public ArrayList<SFTPParam> DeleteFilesSFTP(String SFTPHOST, int SFTPPORT, String SFTPUSER, String SFTPPASS, String Path, String LocationType, int RetainDays, int MinFiles, String FileName) {
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		

		
		ArrayList<SFTPParam> Files = new ArrayList<SFTPParam>();
		ArrayList<SFTPParam> DelFile = new ArrayList<SFTPParam>();
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
			Vector<ChannelSftp.LsEntry> files = channelSftp.ls(FileName);
			for(ChannelSftp.LsEntry entry : files) 
				{
				if(!entry.getAttrs().isDir())
					{
					res = new SFTPParam();
					res.setZEFILENAME(entry.getFilename());
					res.setZECRDT(sdf.parse(entry.getAttrs().getMtimeString()));
					Files.add(res);			//All Filenames and it's Creation date is stored here from Directory
					}
				}
			
			for(int i=0 ; i<Files.size() ; i++)
			{
				res = new SFTPParam();
				SFTPParam file = Files.get(i);
				long dif=(Calendar.getInstance().getTimeInMillis()-file.ZECRDT.getTime())/(1000*1*60*60*24);
				if(dif >= RetainDays)
				{
					res.setZEFILENAME(file.ZEFILENAME);
					res.setZECRDT(file.ZECRDT);
					DelFile.add(res);				// Files older than n days(Retain Days)alone are stored here so that it can be deleted 
				}
			}
			
			//This will Sort Files based on created date, So that Old files delete first
			Collections.sort(DelFile, new Comparator<SFTPParam>(){
				   public int compare(SFTPParam parm1, SFTPParam parm2){
				      return parm2.getZECRDT().compareTo(parm1.getZECRDT());
				   }
			});
			for(int i=MinFiles ; i<DelFile.size() ; i++)
				{
					res = new SFTPParam();
					SFTPParam del = DelFile.get(i);
					res.setZEFILENAME(del.ZEFILENAME);
					res.setZECRDT(del.ZECRDT);
					filesDeleted.add(res);
					sftp.rm(del.ZEFILENAME);
				}
			
				channelSftp.disconnect();
				session.disconnect(); 
			} catch (JSchException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (SftpException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (ParseException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		return filesDeleted;
	}
}

//************************************************************************************************************************************
