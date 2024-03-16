package fr.euphyllia.sgbd.model;

import fr.euphyllia.sgbd.exceptions.DatabaseException;

public interface DBInterface {
    @org.jetbrains.annotations.Nullable
    java.sql.Connection getConnection() throws DatabaseException;
}