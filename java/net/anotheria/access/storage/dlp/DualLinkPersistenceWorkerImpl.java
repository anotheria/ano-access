package net.anotheria.access.storage.dlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.anotheria.access.storage.IFSSaveable;

import org.apache.log4j.Logger;



public class DualLinkPersistenceWorkerImpl<T extends IFSSaveable> implements IDualLinkPersistenceWorker<T>{

	private IPersistenceNotFoundExceptionFactory<? extends PersistedObjectNotFoundException> exceptionFactory;
	private DualLinkPersistenceWorkerConfig config;
	private static Logger log = Logger.getLogger(DualLinkPersistenceWorkerImpl.class);
	
	protected DualLinkPersistenceWorkerImpl(DualLinkPersistenceWorkerConfig aConfig, IPersistenceNotFoundExceptionFactory<? extends PersistedObjectNotFoundException> anExceptionFactory){
		config = aConfig;
		exceptionFactory = anExceptionFactory;
	}
	
	@Override
	public void delete(String owner) throws PersistenceException{
		File f = getFile(owner);
		if (log.isDebugEnabled())
			log.info("Deleting file "+f.getAbsolutePath()+" for user "+owner);
		if (!f.exists())
			return;
		if (!f.delete()){
			throw new PersistenceException("Deletion of file "+f.getAbsolutePath()+" failed.");			
		}
	}

	@Override
	public T load(String owner)  throws PersistenceException{
		File targetFile = getFile(owner);
		ObjectInputStream oIn = null; 
		if (!targetFile.exists())
			throw exceptionFactory.createNotFoundException(owner);
		try{
			synchronized(this){
				oIn = new ObjectInputStream(new FileInputStream(targetFile));
				@SuppressWarnings("unchecked") T ret = (T)oIn.readObject();
				oIn.close();
				return ret;
			}
		}catch(IOException e){
			log.warn("loadFile, targetFile=" + targetFile.getAbsolutePath(), e);
			throw new PersistenceException(e);
		}catch(ClassNotFoundException e){
			log.warn("loadFile, targetFile=" + targetFile.getAbsolutePath(), e);
			throw new PersistenceException("Load failed, because class not found: "+e.getMessage());
		}finally{
			try{
				if (oIn!=null)
					oIn.close();
			}catch(Exception ignored){
			}
		}
	}

	@Override
	public void save(T saveable) throws PersistenceException{
		File targetFile = getFile(saveable.getUserId());
		if (log.isDebugEnabled())
			log.debug("saving object "+saveable);
		ObjectOutputStream oOut = null;
		try{
			synchronized(this){
				oOut = new ObjectOutputStream(new FileOutputStream(targetFile));
				oOut.writeObject(saveable);
				oOut.close();
			}
		}catch(IOException e){
			log.warn("saveFile, targetFile=" + targetFile.getAbsolutePath(), e);
			try{
				if (oOut!=null)
					oOut.close();
			}catch(Exception ignored){}
			throw new PersistenceException(e);
		}
	}


	/**
	 * Returns the file where the data for the given user id has to be stored.
	 * @param userId
	 * @return
	 */
	protected File getFile(String userId){
		File dir = new File(config.calculateDirPath(userId));
		dir.mkdirs();
		return new File(config.calculateFilePath(userId));
	}
	
	
}
