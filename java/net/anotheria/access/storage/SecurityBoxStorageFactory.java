package net.anotheria.access.storage;

public class SecurityBoxStorageFactory {
	public static SecurityBoxStorage createStorage(){
		return new SecurityBoxStorageImpl();
	}
}
