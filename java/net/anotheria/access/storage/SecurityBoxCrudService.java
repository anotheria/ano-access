package net.anotheria.access.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import net.anotheria.access.impl.SecurityBox;
import net.anotheria.anoprise.dualcrud.CrudService;
import net.anotheria.anoprise.dualcrud.CrudServiceException;
import net.anotheria.anoprise.dualcrud.ItemNotFoundException;
import net.anotheria.anoprise.dualcrud.Query;

import org.apache.log4j.Logger;



public class SecurityBoxCrudService implements CrudService<SecurityBox>{

	private SecurityBoxStorageConfig config;
	private static Logger log = Logger.getLogger(SecurityBoxCrudService.class);
	
	protected SecurityBoxCrudService(SecurityBoxStorageConfig aConfig){
		config = aConfig;
	}
	
	@Override
	public void delete(SecurityBox box) throws CrudServiceException{
		String owner = box.getOwnerId();
		File f = getFile(owner);
		if (log.isDebugEnabled())
			log.info("Deleting file "+f.getAbsolutePath()+" for user "+owner);
		if (!f.exists())
			return;
		if (!f.delete()){
			throw new CrudServiceException("Deletion of file "+f.getAbsolutePath()+" failed.");			
		}
	}

	@Override
	public SecurityBox read(String owner)  throws CrudServiceException{
		File targetFile = getFile(owner);
		ObjectInputStream oIn = null; 
		if (!targetFile.exists())
			throw new ItemNotFoundException(owner);
		try{
			synchronized(this){
				oIn = new ObjectInputStream(new FileInputStream(targetFile));
				@SuppressWarnings("unchecked") SecurityBox ret = (SecurityBox)oIn.readObject();
				oIn.close();
				return ret;
			}
		}catch(IOException e){
			log.warn("loadFile, targetFile=" + targetFile.getAbsolutePath(), e);
			throw new CrudServiceException("io problems", e);
		}catch(ClassNotFoundException e){
			log.warn("loadFile, targetFile=" + targetFile.getAbsolutePath(), e);
			throw new CrudServiceException("Load failed, because class not found: "+e.getMessage());
		}finally{
			try{
				if (oIn!=null)
					oIn.close();
			}catch(Exception ignored){
			}
		}
	}

	@Override
	public SecurityBox save(SecurityBox saveable) throws CrudServiceException{
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
			throw new CrudServiceException("io problems", e);
		}
		return saveable;
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

	@Override
	public SecurityBox create(SecurityBox t) throws CrudServiceException {
		return save(t);
	}

	@Override
	public SecurityBox update(SecurityBox t) throws CrudServiceException {
		return save(t);
	}

	@Override
	public boolean exists(SecurityBox t) throws CrudServiceException {
		throw new UnsupportedOperationException("yet unimplemented");
	}

	@Override
	public List<SecurityBox> query(Query q) throws CrudServiceException {
		throw new UnsupportedOperationException("yet unimplemented");
	}
	
	
}
