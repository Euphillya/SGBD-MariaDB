package fr.euphyllia.sgbd;

import com.zaxxer.hikari.HikariDataSource;
import fr.euphyllia.sgbd.configuration.MariaDBConfig;
import fr.euphyllia.sgbd.exceptions.DatabaseException;
import fr.euphyllia.sgbd.model.DBConnect;
import fr.euphyllia.sgbd.model.DBInterface;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;

public class MariaDB implements DBConnect, DBInterface {

    private final Logger logger = LogManager.getLogger(MariaDB.class);
    private final MariaDBConfig mariaDBConfig;
    private HikariDataSource pool;
    private boolean connected;

    public MariaDB(final MariaDBConfig configMariaDB) {
        this.mariaDBConfig = configMariaDB;
        this.connected = false;
    }


    @Override
    public boolean onLoad() throws DatabaseException {
        this.pool = new HikariDataSource();
        this.pool.setDriverClassName("org.mariadb.jdbc.Driver");
        this.pool.setJdbcUrl("jdbc:mariadb://%s:%s/".formatted(mariaDBConfig.hostname(), mariaDBConfig.port()));
        this.pool.setUsername(mariaDBConfig.user());
        this.pool.setPassword(mariaDBConfig.pass());
        this.pool.setConnectionTimeout(mariaDBConfig.timeOut());

        this.pool.setMaximumPoolSize(mariaDBConfig.maxPool());
        this.pool.setMinimumIdle(mariaDBConfig.maxPool());

        try (Connection connection = pool.getConnection()) {
            if (connection.isValid(2)) {
                this.connected = true;
                this.logger.log(Level.INFO, "MariaDB pool initialized (%s)".formatted(mariaDBConfig.maxPool()));
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.FATAL, e.getMessage(), e);
            throw new DatabaseException(e);
        }
        return false;
    }

    @Override
    public void onClose() {
        if (isConnected() && this.pool != null && !this.pool.isClosed()) {
            this.pool.close();
            connected = false;
        }
    }

    @Override
    public boolean isConnected() {
        return connected && this.pool != null && !this.pool.isClosed();
    }

    @Override
    public @Nullable Connection getConnection() throws DatabaseException {
        if (!isConnected()) {
            throw new DatabaseException("Non connecté à la base de données.");
        }

        try {
            return this.pool.getConnection();
        } catch (SQLException e) {
            this.logger.fatal("Erreur lors de l'obtention de la connexion : {}", e.getMessage(), e);
            throw new DatabaseException("Erreur lors de l'obtention de la connexion.", e);
        }
    }
}
