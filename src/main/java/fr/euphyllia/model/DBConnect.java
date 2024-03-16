package fr.euphyllia.model;

import fr.euphyllia.exceptions.DatabaseException;

public interface DBConnect {

    boolean onLoad() throws DatabaseException;

    void onClose();

    boolean isConnected();
}