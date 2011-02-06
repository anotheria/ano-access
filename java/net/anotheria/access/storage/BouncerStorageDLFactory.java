package net.anotheria.access.storage;

public class BouncerStorageDLFactory {
	public static BouncerStorage createStorage(){
		return new PlainBouncerStorageDL();
	}
}
