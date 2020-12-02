package com.sst;

import com.sap.aii.mapping.value.api.IFResponse;
import com.sap.aii.mapping.value.api.ValueMappingException;
import com.sap.aii.mapping.value.api.XIVMFactory;
import com.sap.aii.mapping.value.api.XIVMService;

//======================================================================================================================
public class Lookup {
//======================================================================================================================

//----------------------------------------------------------------------------------------------------------------------
	public static String GetSFTPConValue(String sourceContext, String sourceAgency, String sourceScheme, String 
			targetContext, String targetAgency, String targetScheme, String keyValue) 
	throws Exception { 
//----------------------------------------------------------------------------------------------------------------------
			String retValue = new String(); 
			try { 
				com.sap.aii.mapping.value.api.IFIdentifier sourceIdentifier = XIVMFactory.newIdentifier(sourceAgency, sourceScheme); 
				com.sap.aii.mapping.value.api.IFIdentifier targetIdentifier = XIVMFactory.newIdentifier(targetAgency,targetScheme); 
				com.sap.aii.mapping.value.api.IFRequest mRequest = XIVMFactory.newRequest(sourceIdentifier,targetIdentifier, keyValue); 
				try { 
					IFResponse mResponse = XIVMService.executeMapping(mRequest); 
					if(mResponse.hasTargetValues()) { 
						retValue = mResponse.getSingleTargetValue(); 
					} else { 
						String exceptionMessage = new String("LookupValueNotFound:"); 
						exceptionMessage = exceptionMessage+" "+sourceAgency+" "+sourceScheme+" "+targetAgency+" "+targetScheme+" "+keyValue; 
						return exceptionMessage; 
					} 
				} catch(ValueMappingException e) { 
					String exceptionMessage = e.getMessage(); 
					e.printStackTrace(); 
					throw new Exception(exceptionMessage); 
				}
			} catch(Exception e) { 
				String exceptionMessage = e.getMessage(); 
				e.printStackTrace(); 
				throw new Exception(exceptionMessage); 
			} 
			return retValue;
		}
}
//**********************************************************************************************************************