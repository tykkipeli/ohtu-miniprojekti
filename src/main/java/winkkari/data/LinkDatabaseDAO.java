package winkkari.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkDatabaseDAO extends AbstractDatabaseDAO<LinkTip> {
    private static final String TABLE_NAME = "TIPS_LINKS";

    public LinkDatabaseDAO() {
        super();
    }

    public LinkDatabaseDAO(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    protected PreparedStatement getCreateTableQuery(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "CREATE TABLE " + TABLE_NAME +
                        "(ID SERIAL PRIMARY KEY, " +
                        "TITLE VARCHAR(512), " +
                        "URL VARCHAR(512), " +
                        "COMMENT VARCHAR(512), " +
                        "CHECKED BOOLEAN);");
    }

    @Override
    protected PreparedStatement getAddQuery(Connection conn, LinkTip tip) throws SQLException {
        var statement = conn.prepareStatement(
                "INSERT INTO " + TABLE_NAME +
                        "(TITLE, URL, COMMENT) " +
                        "VALUES(?,?,?);");

        statement.setString(1, tip.getTitle());
        statement.setString(2, tip.getUrl());
        statement.setString(3, tip.getComment());
        return statement;
    }

    @Override
    protected PreparedStatement getGetQuery(Connection connection, String id) throws SQLException {
        var statement = connection.prepareStatement(
                "SELECT " +
                        "ID as id, " +
                        "TITLE as title, " +
                        "URL as url, " +
                        "COMMENT as comment, " +
                        "CHECKED as checked " +
                        "FROM " + TABLE_NAME +
                        " WHERE ID = ?;");

        statement.setInt(1, Integer.parseInt(id));
        return statement;
    }

    @Override
    protected PreparedStatement getGetAllQuery(Connection conn) throws SQLException {
        return conn.prepareStatement(
                "SELECT " +
                        "ID as id, " +
                        "TITLE as title, " +
                        "URL as url, " +
                        "COMMENT as comment, " +
                        "CHECKED as checked " +
                        "FROM " + TABLE_NAME);
    }

    @Override
    protected LinkTip constructFromResultSet(ResultSet rs) throws SQLException {
        return new LinkTip(rs.getString("id"),
                           rs.getString("title"),
                           rs.getString("url"),
                           rs.getString("comment"),
                           rs.getBoolean("checked"));
    }

    @Override
    protected PreparedStatement getDeleteQuery(Connection conn, String id) throws SQLException {
        var statement = conn.prepareStatement(
                "DELETE " +
                        "FROM " + TABLE_NAME +
                        " WHERE ID = ?;");

        statement.setInt(1, Integer.parseInt(id));
        return statement;
    }

    @Override
    protected PreparedStatement getCheckQuery(
            Connection conn,
            String id,
            boolean check
    ) throws SQLException {
        var statement = conn.prepareStatement(
                "UPDATE " + TABLE_NAME +
                        " SET CHECKED=?" +
                        " WHERE ID = ?;");

        statement.setBoolean(1, !check);
        statement.setInt(2, Integer.parseInt(id));
        return statement;
    }
}