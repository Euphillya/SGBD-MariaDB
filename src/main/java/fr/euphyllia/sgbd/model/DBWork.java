package fr.euphyllia.sgbd.model;

public interface DBWork {
    void run(java.sql.Connection connection);
}
