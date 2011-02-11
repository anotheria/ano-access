package net.anotheria.access.storage;

import java.io.File;

import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.ConfigureMe;

@ConfigureMe(allfields=true)
public class SecurityBoxStorageConfig {
	
	private static Logger log = Logger.getLogger(SecurityBoxStorageConfig.class);
	
	private static final SecurityBoxStorageConfig INSTANCE = new SecurityBoxStorageConfig();
	
	private String extension;
	
	private String defPath;
	
	private SecurityBoxStorageConfig() {
		try{
			ConfigurationManager.INSTANCE.configure(this);
		}catch (IllegalArgumentException e) {
			log.error("Config file not found. Initializtion failed.", e);
        }
	}
	
	public static SecurityBoxStorageConfig getInstance() {
		return INSTANCE;
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
	
	public String getDefPath(){
		return defPath;
	}

	public void setDefPath(String defPath) {
		this.defPath = defPath;
	}

	protected String getDefPath(@SuppressWarnings("unused") String aUserId){
		return getDefPath();		
	}
	
	public static void main(String a[]){
//		System.out.println("OLD "+new SecurityBoxStorageConfig(".vip", "/work/data/vip/").calculateFilePath("2571458"));
//		System.out.println("NEW "+new SecurityBoxStorageConfig(".vip", "/work/vip/data/").calculateFilePath("2571458"));
		
		System.out.println("OLD "+ SecurityBoxStorageConfig.INSTANCE.calculateFilePath("2571458"));
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
