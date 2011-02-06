package net.anotheria.access.storage.dlp;

/**
 * This configuration object defines the behaviour of a DualLinkPersistenceService. 
 * @author another
 *
 */
public class DualLinkPersistenceServiceConfig {
	
	/**
	 * If true the service will move all objects found by the 'old' worker to the 'new' worker.
	 */
	private boolean migrateOnRead;
	
	/**
	 * If true the service will delete the objects on the old worker after migration
	 */
	private boolean deleteUponMigration;
	
	/**
	 * Reads 
	 */
	private boolean readFromBoth;
	
	private boolean migrateOnWrite;

	public boolean isMigrateOnRead() {
		return migrateOnRead;
	}

	public void setMigrateOnRead(boolean migrateOnRead) {
		this.migrateOnRead = migrateOnRead;
	}

	public boolean isDeleteUponMigration() {
		return deleteUponMigration;
	}

	public void setDeleteUponMigration(boolean deleteUponMigration) {
		this.deleteUponMigration = deleteUponMigration;
	}

	public boolean isReadFromBoth() {
		return readFromBoth;
	}

	public void setReadFromBoth(boolean readFromBoth) {
		this.readFromBoth = readFromBoth;
	}

	public boolean isMigrateOnWrite() {
		return migrateOnWrite;
	}

	public void setMigrateOnWrite(boolean migrateOnWrite) {
		this.migrateOnWrite = migrateOnWrite;
	}
	
	
	public static DualLinkPersistenceServiceConfig onTheFlyMigration(){
		DualLinkPersistenceServiceConfig config = new DualLinkPersistenceServiceConfig();
		
		config.setDeleteUponMigration(true);
		config.setMigrateOnRead(true);
		config.setMigrateOnWrite(false);
		config.setReadFromBoth(true);
		
		return config;
	}
	
	public static DualLinkPersistenceServiceConfig readOnly(){
		DualLinkPersistenceServiceConfig config = new DualLinkPersistenceServiceConfig();
		
		config.setDeleteUponMigration(false);
		config.setMigrateOnRead(false);
		config.setMigrateOnWrite(false);
		config.setReadFromBoth(true);
		return config;
	}

	public static DualLinkPersistenceServiceConfig newWorkerOnlyMode(){
		DualLinkPersistenceServiceConfig config = new DualLinkPersistenceServiceConfig();
		
		config.setDeleteUponMigration(false);
		config.setMigrateOnRead(false);
		config.setMigrateOnWrite(false);
		config.setReadFromBoth(false);
		return config;
	}
}