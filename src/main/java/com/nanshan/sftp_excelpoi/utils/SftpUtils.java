package com.nanshan.sftp_excelpoi.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.stereotype.Component;

/**
 * @author RogerLo
 * @date 2023/5/27
 */
@Component
public class SftpUtils {

    public void downloadFileSftp(String host, int port, String username, String password, String remoteFilePath, String localFilePath) {
        com.jcraft.jsch.JSch jsch = new JSch();
        com.jcraft.jsch.Session session = null;
        com.jcraft.jsch.ChannelSftp channelSftp = null;

        try {
            session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // StrictHostKeyChecking 為 "ask" (預設) → 首次連接到伺服器時，SSH 客戶端會詢問你是否信任該伺服器的主機密鑰
            // StrictHostKeyChecking 設定為 "no" 則是關閉了這種驗證機制，表示不再詢問用戶是否信任伺服器的主機密鑰
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            channelSftp.get(remoteFilePath, localFilePath);
            System.out.println("File downloaded successfully.");
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
