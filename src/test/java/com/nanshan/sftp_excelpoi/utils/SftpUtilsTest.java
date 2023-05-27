package com.nanshan.sftp_excelpoi.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SftpUtilsTest {

    @Autowired
    private SftpUtils sftpUtils;

    @Test
    @Order(1)
    @DisplayName("Im test_001")
    // @Disabled
    void test_001(TestInfo testInfo) {
        System.out.println(" ================= " + testInfo.getDisplayName() + " ================= ");
        String host = "127.0.0.1";
        int port = 2222;
        String username = "zelda";
        String password = "sa123456";
        String remoteFilePath = "/upload/EmpData_003.xlsx";
        String localFilePath = "./downloadFiles/EmpData_003.xlsx";
        sftpUtils.downloadFileSftp(host, port, username, password, remoteFilePath, localFilePath);
    }

    @Test
    @Order(2)
    @DisplayName("Im test_002")
    @Disabled
    void test_002(TestInfo testInfo) {
        System.out.println(" ================= " + testInfo.getDisplayName() + " ================= ");
    }
}