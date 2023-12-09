package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnection {
    Connection connect() throws SQLException;

    void disconnect(Connection connection) throws SQLException;
}