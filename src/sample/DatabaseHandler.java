package sample;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbconnection;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String connectionDB = "jdbc:mysql://" + dbhost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.jdbc.Driver");
        dbconnection = DriverManager.getConnection(connectionDB, dbUser, dbPass);
        return dbconnection;
    }

    public void signUp(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + " (" + Const.USER_FIRSTNAME + "," +Const.USER_LASTNAME + "," + Const.USER_USERNAME + "," + Const.USER_PASSWORD
                + "," + Const.USER_LOCATION + "," + Const.USER_GENDER + ")" + " VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
        }
    }

    public ResultSet getUser(User user){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_USERNAME + "=? AND " + Const.USER_PASSWORD + "=?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
        }
        return resultSet;
    }

}
