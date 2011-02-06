package net.anotheria.access.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;


public abstract class AbstractFSPersistenceService{
	
	protected static Logger log;

	private String lock;
	
	protected AbstractFSPersistenceService(){
		this.lock = "MyLockObject_"+getClass().getName();
		log = Logger.getLogger(this.getClass());
	}
	
	protected abstract String getPathSegment();
	protected abstract String getExtension();
	
		
	protected String getDefPath(){
		return "/work/data"+getPathSegment();		
	} 

	protected String getDefPath(@SuppressWarnings("unused") String aUserId){
		return getDefPath();		
	}

	private String calculateDirPath(String userId){
		String fragments[] = getFragmentation(userId,5,2);
		String path = getDefPath(userId);
		for (int i=0; i<fragments.length-1; i++){
			path += fragments[i];
			if (i<fragments.length-2)
				path += File.separator;				
		}
		return path;
	}
	
	private String calculateFilePath(String userId){
		String filePath = calculateDirPath(userId)+File.separator+userId+getExtension();
		log.debug("Calculated file path for "+userId+" is "+filePath); 
		return filePath;
	}

	/**
	 * Returns the file where the data for the given user id has to be stored.
	 * @param userId
	 * @return
	 */
	protected File getFile(String userId){
		File dir = new File(calculateDirPath(userId));
		dir.mkdirs();
		return new File(calculateFilePath(userId));
	}
	
	/**
	 * Saves an object to disk.
	 * @param objectToSave
	 * @throws Exception
	 */
	protected void saveFile(IFSSaveable objectToSave) throws Exception{
		File targetFile = getFile(objectToSave.getUserId());
		log.debug("saving object "+objectToSave);
		ObjectOutputStream oOut = null;
		try{
			synchronized(this.lock){
				oOut = new ObjectOutputStream(new FileOutputStream(targetFile));
				oOut.writeObject(objectToSave);
				oOut.close();
			}
		}catch(IOException e){
			log.warn("saveFile, targetFile=" + targetFile.getAbsolutePath(), e);
			try{
				if (oOut!=null)
					oOut.close();
			}catch(Exception ignored){}
			throw e;
		}
	}
	
	/**
	 * Loads a file from stream.
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected Object loadFile(String userId)  throws Exception{
		File targetFile = getFile(userId);
		ObjectInputStream oIn = null; 
		if (!targetFile.exists())
			throw createFileNotFoundException(userId);
		try{
			synchronized(this.lock){
				oIn = new ObjectInputStream(new FileInputStream(targetFile));
				Object ret = oIn.readObject();
				oIn.close();
				return ret;
			}
		}catch(IOException e){
			log.warn("loadFile, targetFile=" + targetFile.getAbsolutePath(), e);
			throw e;
		}finally{
			try{
				if (oIn!=null)
					oIn.close();
			}catch(Exception ignored){
			}
		}
	}
	
	public abstract Exception createFileNotFoundException(String owner);
	
	public String[] getFragmentation(String userId, int fragmentationDepth, int fragmentLength) {
		String s = userId;
		//first ensure our string is long enough.
		while (s.length()<fragmentationDepth*fragmentLength)
			s = "0"+s;
		
		int singleLength = fragmentLength;
		String ret[] = new String[fragmentationDepth];
		for (int i=0; i<fragmentationDepth; i++){
			String fragment = s.substring(i*singleLength, i*singleLength+singleLength);
			ret[i] = fragment;
		}
		 
		return ret;
	}
		

}