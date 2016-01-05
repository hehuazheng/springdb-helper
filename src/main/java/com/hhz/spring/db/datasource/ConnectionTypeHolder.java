package com.hhz.spring.db.datasource;

public class ConnectionTypeHolder {
    private static ThreadLocal<ConnectionType> connType = new ThreadLocal<ConnectionType>();

    public static void set(ConnectionType type) {
        connType.set(type);
    }

    public static ConnectionType get() {
        return connType.get();
    }

    public static void release() {
        connType.remove();
    }
}
