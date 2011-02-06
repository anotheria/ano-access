package net.anotheria.access.storage;

public class BouncerStorageFactory {
	public static BouncerStorage createStorage(){
		return new PlainBouncerStorage();
	}
}
