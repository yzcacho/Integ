/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Books;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Books {
    public static void main(String[]args) throws Exception{
        String message;
         Scanner consoleIn = new Scanner(System.in);
         
        System.out.print("Host name/IP address: ");
        String hostName = consoleIn.nextLine();

        System.out.print("Port # to connect to: ");
        int portNumber = Integer.parseInt(consoleIn.nextLine());
        
        try{
        Socket socket = new Socket(hostName, portNumber);
            BufferedReader streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter streamOut = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not get I/O for the connection to " + hostName);
            System.exit(1);
        }
        Class.forName("com.mysql.jdbc.Driver");

        String conStr = "jdbc:mysql://localhost:3306/integprog?user=root&password=";
        Connection con = DriverManager.getConnection(conStr);
        System.out.println("connection done");

        String stSel = "SELECT author_id, book_id, name, title FROM book NATURAL JOIN author ORDER BY name, title;";

        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(stSel);
        
        System.out.printf("%5s %5s %-5s %-20s %10s %n", "authorID","bookID"," ","name","title");
        
        rs.beforeFirst();
        while (rs.next()) {
            int authorID = rs.getInt(1);
            int bookID = rs.getInt(2);
            String name = rs.getString("name");
            String title = rs.getString("title");
            
            System.out.printf("%5d %5d %-5s %-20s %10s\n", authorID, bookID, " ",name, title);
        }
        rs.close();

        con.close();
    }
    
}
