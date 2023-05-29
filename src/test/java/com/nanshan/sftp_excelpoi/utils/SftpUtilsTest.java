package com.nanshan.sftp_excelpoi.utils;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SftpUtilsTest {

    @Autowired
    private SftpUtils sftpUtils;

    @Test
    @Order(1)
    @DisplayName("test_001 - SFTP下載")
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
    @DisplayName("test_002 - SFTP 上傳")
    @Disabled
    void test_002(TestInfo testInfo) throws IOException, InterruptedException {
        System.out.println(" ================= " + testInfo.getDisplayName() + " ================= ");

        String fileName = MessageFormat.format("poem-{0}.txt", genRandomUUID());
        String localFilePathStr = "./fileReadyToUpload/" + fileName;

        this.writeFile(() -> FileUtils.getFile(localFilePathStr),
            () -> Stream.of("千山鳥飛絕", "萬徑人蹤滅", "孤舟簑笠翁", "獨釣寒江雪").collect(Collectors.toList()));

        String host = "127.0.0.1";
        int port = 2222;
        String username = "zelda";
        String password = "sa123456";
        String remoteFilePath = "/upload/" + fileName;
        String localFilePath = localFilePathStr;
        sftpUtils.uploadFile(host, port, username, password, localFilePath, remoteFilePath);
    }

    /**
     * 寫檔案到本地端
     * @param fileSupplier : 目標檔案的File物件
     * @param contentSupplier : 檔案內容提供者
     * @throws IOException
     * @throws InterruptedException
     */
    private void writeFile(Supplier<File> fileSupplier, Supplier<List<String>> contentSupplier) throws IOException, InterruptedException {
        File targetFile = fileSupplier.get();

        // 清除目錄下的所有檔案
        FileUtils.cleanDirectory(FileUtils.getFile("./fileReadyToUpload/"));

        // 檔案存在，就刪除
        // if (targetFile.exists()) {
        //     FileUtils.deleteQuietly(targetFile);
        //     Thread.sleep(1000);
        // }

        FileUtils.writeLines(fileSupplier.get(), StandardCharsets.UTF_8.name(), contentSupplier.get());
    }

    /**
     * 產生5碼 UUID
     * @return
     */
    private static String genRandomUUID() {
        UUID uuid = UUID.randomUUID();
        String randomUUID = uuid.toString().substring(0, 5);
        return randomUUID;
    }

}