package net.anotheria.access.storage.persistence.impl;

import net.anotheria.access.impl.SecurityBox;
import net.anotheria.access.storage.persistence.SecurityBoxPersistenceService;
import net.anotheria.anoprise.dualcrud.CrudServiceException;
import net.anotheria.anoprise.dualcrud.ItemNotFoundException;
import net.anotheria.anoprise.dualcrud.Query;
import net.anotheria.anoprise.dualcrud.SaveableID;
import net.anotheria.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


/**
 * File system implementation of {@link SecurityBoxPersistenceService}.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class FSSecurityBoxPersistenceServiceImpl implements SecurityBoxPersistenceService {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FSSecurityBoxPersistenceServiceImpl.class);

	@Override
	public SecurityBox create(SecurityBox t) throws CrudServiceException {
		return save(t);
	}

	@Override
	public SecurityBox read(final SaveableID boxOwner) throws CrudServiceException {
		File targetFile = getFile(boxOwner.getSaveableId());
		ObjectInputStream oIn = null;
		if (!targetFile.exists())
			throw new ItemNotFoundException(boxOwner.getSaveableId());

		try {
			synchronized (this) {
				oIn = new ObjectInputStream(new FileInputStream(targetFile));
				SecurityBox ret = SecurityBox.class.cast(oIn.readObject());
				oIn.close();
				return ret;
			}
		} catch (IOException e) {
			LOGGER.warn("read(" + boxOwner + ") fail. Target file: " + targetFile.getAbsolutePath(), e);
			throw new CrudServiceException("Reading fail.", e);
		} catch (ClassNotFoundException e) {
			LOGGER.warn("read(" + boxOwner + ") fail. Target file: " + targetFile.getAbsolutePath(), e);
			throw new CrudServiceException("Reading fail. Class not found: " + e.getMessage());
		} finally {
			IOUtils.closeIgnoringException(oIn);
		}
	}

	@Override
	public SecurityBox update(final SecurityBox t) throws CrudServiceException {
		return save(t);
	}

	@Override
	public void delete(final SecurityBox box) throws CrudServiceException {
		String owner = box.getOwnerId();
		File f = getFile(owner);

		if (LOGGER.isDebugEnabled())
			LOGGER.info("Deleting file[" + f.getAbsolutePath() + "] for user [" + owner + "]...");

		if (!f.exists())
			return;

		if (!f.delete())
			throw new CrudServiceException("Deletion of file [" + f.getAbsolutePath() + "for user [" + owner + "] failed.");
	}

	@Override
	public SecurityBox save(final SecurityBox box) throws CrudServiceException {
		File targetFile = getFile(box.getUserId());

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("save(" + box + ") Saving... ");

		ObjectOutputStream oOut = null;
		try {
			synchronized (this) {
				oOut = new ObjectOutputStream(new FileOutputStream(targetFile));
				oOut.writeObject(box);
				oOut.close();
			}
		} catch (IOException e) {
			LOGGER.warn("save(" + box + ") final. Target file: " + targetFile.getAbsolutePath(), e);
			throw new CrudServiceException("Saving fail.", e);
		} finally {
			IOUtils.closeIgnoringException(oOut);
		}
		return box;
	}

	@Override
	public boolean exists(SecurityBox t) throws CrudServiceException {
		throw new UnsupportedOperationException("yet unimplemented");
	}

	@Override
	public List<SecurityBox> query(Query q) throws CrudServiceException {
		throw new UnsupportedOperationException("yet unimplemented");
	}

	/**
	 * Returns the file where the data for the given user id has to be stored.
	 * 
	 * @param userId
	 *            - user id
	 * @return {@link File}
	 */
	protected File getFile(String userId) {
		// preparing storage directory
		File file = new File(FSSecurityBoxPersistenceServiceConfig.calculateDirPath(userId));
		if (!file.mkdirs())
			LOGGER.debug("Directory[" + FSSecurityBoxPersistenceServiceConfig.calculateDirPath(userId) + "] not created.");

		// preparing file
		return new File(FSSecurityBoxPersistenceServiceConfig.calculateFilePath(userId));
	}

}
