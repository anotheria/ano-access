package net.anotheria.access.storage.dlp;

import java.io.File;

import org.apache.log4j.Logger;

public class DualLinkPersistenceWorkerConfig {
	
	private static Logger log = Logger.getLogger(DualLinkPersistenceWorkerConfig.class);
	
	private String extension;
	private String defPath;
	
	public DualLinkPersistenceWorkerConfig(String anExtension, String aDefPath){
		extension = anExtension;
		defPath = aDefPath;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	protected String calculateDirPath(String userId){
		String fragments[] = getFragmentation(userId, 5,2);
		String path = getDefPath(userId);
		for (int i=0; i<fragments.length-1; i++){
			path += fragments[i];
			if (i<fragments.length-2)
				path += File.separator;				
		}
		return path;
	}

	protected String calculateFilePath(String userId){
		String filePath = calculateDirPath(userId)+File.separator+userId+getExtension();
		log.debug("Calculated file path for "+userId+" is "+filePath); 
		return filePath;
	}
	
	protected String getDefPath(){
		return defPath;
	}

	protected String getDefPath(@SuppressWarnings("unused") String aUserId){
		return getDefPath();		
	}
	
	public static void main(String a[]){
		System.out.println("OLD "+new DualLinkPersistenceWorkerConfig(".vip", "/work/data/vip/").calculateFilePath("2571458"));
		System.out.println("NEW "+new DualLinkPersistenceWorkerConfig(".vip", "/work/vip/data/").calculateFilePath("2571458"));
	}

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
