package com.pas.nfl.business;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.exception.PASSystemException;
import com.pas.util.IPropertyCollection;
import com.pas.util.IPropertyReader;
import com.pas.util.PropertyReaderFactory;

/**
 * Title: BusinessFactory
 * 
 * Description: 
 * 
 * Copyright: Copyright (c) 2006
 * Company: Lincoln Life
 * 
 */
public class NFLBusinessFactory
{

    private static Map<String, INFLBusiness> businessServiceMap = new HashMap<String, INFLBusiness>();
    
    private static Logger log = LogManager.getLogger(NFLBusinessFactory.class);
	
	
	public static void initialize()throws PASSystemException
	{
		String blockName = "initialize :: ";
		log.debug(blockName + "In");

		IPropertyReader pr = PropertyReaderFactory.getPropertyReader();
		IPropertyCollection ipc = pr.getCollection("BusinessSettings.BusinessSetting");
		INFLBusiness appService; 
	
		while (ipc.hasNext())
		{
			ipc.next();
			String serviceName = ipc.getProperty("name");
			String daoRef = ipc.getProperty("DAORef");
			String serviceClassName = ipc.getProperty("class");
		
			if (log.isDebugEnabled())
			{
				log.debug("++++++++");
				log.debug("service Name =" + serviceName );
				log.debug("Service Class Name =" + serviceClassName );
				log.debug("DAO Reference Name =" + daoRef );
				log.debug("++++++++");
			}
			
			try
			{
				appService = createAppServiceInstance(serviceName, serviceClassName, daoRef);
				businessServiceMap.put(serviceName, appService);
			}
			catch(PASSystemException lfge)
			{
				log.error( "Service name :: " + serviceName +" seems incorrect");
				log.error( "Service Class name :: " + serviceClassName );
				log.error( "Service DAO Ref name :: " + daoRef );
				lfge.logException(log);
			}
						
		}

		log.debug(blockName + "Out");
	}

    /**
     * This method provides handle to IBusiness. All clients need to
     * pass busines implementation name
     *
     * @param String  business implementation name
     * @return IBusiness handle to Business implementation
     * @throws BusinessException
     */
    public static INFLBusiness getBusinessInstance(String businessName) throws PASSystemException
    {
        String methodName = "getBusinessInstance::";
        log.debug(methodName + "in");
        
        INFLBusiness iBusiness = businessServiceMap.get(businessName);
    
        if (iBusiness == null)
        {
			log.error(methodName + "Exception occurred while returning IBusiness handle");
			log.error(methodName + "Please pass valid requested business name to method getBusinessInstance" );
			log.error(methodName + "Also make sure you are not passing null or empty string");
			log.error(methodName + "Requested Business Name = " + businessName);
            throw new PASSystemException("Failed to obtain business instance");
        }

        log.debug(methodName + "out");
        return iBusiness;
    }
    
	@SuppressWarnings("unchecked")
	private static INFLBusiness createAppServiceInstance(String serviceName, 
		String serviceClassName, String daoRef) throws PASSystemException
	{
		
		INFLBusiness businessRef=null;
		String methodName = "createAppServiceInstance:: ";
		log.debug(methodName + "in");
		
		try
		{
		  Class cls = Class.forName(serviceClassName);
		  Class partypes[] = new Class[2];
		  partypes[0] = Class.forName("java.lang.String");
		  partypes[1] = Class.forName("java.lang.String");
		  Constructor ct = cls.getConstructor(partypes);
		  Object arglist[] = new Object[2];
		  arglist[0] = serviceName;
		  arglist[1] = daoRef;
		  businessRef = (INFLBusiness) ct.newInstance(arglist);
		}
		catch (Exception e)
		{
		   throw new PASSystemException("Error in creating business class instance using Reflection" , e);
		}
		log.debug(methodName + "out");
		return businessRef;
	}
}

