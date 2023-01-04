package com.pas.nfl.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.dao.BaseDBDAO;
import com.pas.dao.IDBDAO;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.SystemException;
import com.pas.util.IPropertyCollection;
import com.pas.util.IPropertyReader;
import com.pas.util.PropertyReaderFactory;

/**
 * Title: NFLDAOFactory
 */
public class NFLDAOFactory
{
    private static Map<String, BaseDBDAO> daos = new HashMap<String, BaseDBDAO>();
   
    private static Logger log = LogManager.getLogger(NFLDAOFactory.class);
    
	@SuppressWarnings("unchecked")
	public static void initialize() throws PASSystemException {

		String blockName = "staticDAOblock :: ";
        log.debug(blockName + "In");
        
        IPropertyReader pr = PropertyReaderFactory.getPropertyReader();
		IPropertyCollection ipc = pr.getCollection("DAOSettings.DAO");
				 
		while (ipc.hasNext())
		{
			ipc.next();
			String daoName = ipc.getProperty("DAOname");
			
			Class cls;
			Method meth;
			Class parameterTypes[] = new Class[0];
			Object arglist[] = new Object[0];
			BaseDBDAO daoInstance = null;
			
			try
			{
				cls = Class.forName("com.pas.nfl.dao." + daoName);
				meth = cls.getMethod("getDAOInstance", parameterTypes);
				daoInstance = (BaseDBDAO)meth.invoke(cls, arglist);
			}
			catch (ClassNotFoundException e)
			{
				log.error("ClassNotFoundException encountered");
		        log.error("Requested DAO Name = " + daoName);
				e.printStackTrace();
			}
			catch (SecurityException e)
			{
				log.error("SecurityException encountered");
		        log.error("Requested DAO Name = " + daoName);
				e.printStackTrace();
			}
			catch (NoSuchMethodException e)
			{				
				log.error("NoSuchMethodException encountered");
		        log.error("Requested DAO Name = " + daoName);
				e.printStackTrace();
			}
			catch (IllegalArgumentException e)
			{				
				log.error("IllegalArgumentException encountered");
		        log.error("Requested DAO Name = " + daoName);
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{				
				log.error("IllegalAccessException encountered");
		        log.error("Requested DAO Name = " + daoName);
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				log.error("InvocationTargetException encountered");
		        log.error("Requested DAO Name = " + daoName);
				e.printStackTrace();
			}
						
			if (daoInstance != null)
			   daos.put(daoName, daoInstance);
		}
			
            		
        log.debug(blockName + "out");		
	}

    /**
     * Provides handle to requested DAO 
     * @param DAOName
     * @return IDAO
     */
    public static IDBDAO getDAOInstance(String DAOName) throws DAOException, SystemException
    {
        String methodName = "getDAOInstance :: ";
        log.debug(methodName + "In");

        IDBDAO dao = null;

        try
        {
            dao = daos.get(DAOName);
        }
        catch (Exception e)
        {
            log.error(methodName + "Exception occurred while returning IDBDAO handle");
            log.error(methodName + "Please pass valid requested dao name to method getDAOInstance");
            log.error(methodName + "Also make sure you are not passing null or empty string");
            log.error(methodName + "Requested DAO Name = " + DAOName);
			log.error("Make sure DAO is set up in the XML file dao_def.xml");
            throw new SystemException("Failed to obtain DAO instance");
        }
        log.debug(methodName + "out");

        return dao;
    }
}
