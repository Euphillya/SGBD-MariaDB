package fr.euphyllia.sgbd.model;

import fr.euphyllia.sgbd.exceptions.DatabaseException;

public interface DBConnect {

    boolean onLoad() throws DatabaseException;

    void onClose();

    boolean isConnected();
}