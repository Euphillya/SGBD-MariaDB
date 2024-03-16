package fr.euphyllia.model;

import fr.euphyllia.exceptions.DatabaseException;

public interface DBInterface {
    @org.jetbrains.annotations.Nullable
    java.sql.Connection getConnection() throws DatabaseException;
}