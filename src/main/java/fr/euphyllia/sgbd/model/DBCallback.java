package fr.euphyllia.sgbd.model;

public interface DBCallback {
    void run(java.sql.ResultSet resultSet);
}
