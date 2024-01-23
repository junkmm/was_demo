package com.example.demo.cotroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class HelloController {
    @Value("${database.url}")
    private String dbUrl;

    @Value("${database.username}")
    private String dbUsername;

    @Value("${database.password}")
    private String dbPassword;

    @GetMapping("/")
    public String hello() {
        // Get current time
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        // Get IP address and host name
        String ipAddress;
        String hostName;
        String result;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ipAddress = inetAddress.getHostAddress();
            hostName = inetAddress.getHostName();
        } catch (UnknownHostException e) {
            ipAddress = "N/A";
            hostName = "N/A";
        }

        // MySQL database connection
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("DB Connect Success");
            result = "DB Connect Success";

            // 추가 작업을 수행할 수 있습니다.

            // 연결을 닫습니다.
            connection.close();
        } catch (SQLException e) {
            // 데이터베이스 연결 오류 처리
            System.err.println("DB Connect Error: " + e.getMessage());
            result = "DB Connect Error";
            e.printStackTrace();
        }

        // Build response string
        return "<b>Current time: </b>" + formattedTime + "<b><br>IP Address: </b>" + ipAddress + "<b><br>Host Name: </b>" + hostName + "<b><br>DB Connect: </b>" + result;


    }
}
