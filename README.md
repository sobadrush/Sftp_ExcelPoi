# Sftp_ExcelPoi

> 這是一個使用 com.jcraft.jsch.JSch 套件，透過SFTP讀寫Excel檔的專案

### How to use?
1. 使用 Docker 搭建 SFTP SERVER
```shell
$ docker run --rm ^
--name zelda-sftp ^
-v D:/excelSample:/home/zelda/upload ^
-p 2222:22 -d atmoz/sftp ^
zelda:sa123456:1001
```
2. 可嘗試使用 FileZilla 以 SFTP 連線測試
3. 執行 SftpUtilsTest 進行測試，應可成功抓下 Excel 檔


### 參考資料

| # |                說明                |                              URL                              |
|:-:|:--------------------------------:|:-------------------------------------------------------------:|
| 1 | SFTP連線設定教學——以FileZilla Potable為例 | https://blog.pulipuli.info/2008/10/sftpfilezilla-potable.html |
| 2 |            Java SFTP             |             File Transfer Using SFTP in Java JSCH             | https://www.javatpoint.com/java-sftp |
| 3 |        Image: atmoz/sftp         |              https://hub.docker.com/r/atmoz/sftp              |
| 4 |    Spring Boot 单元测试（五）自定义测试顺序    |          https://juejin.cn/post/7041886698464083998           |
| 5 |             ChatGPT              |                                                               |
| 6 |   🚩详解Java使用Jsch与sftp服务器实现ssh免密登录(憑證交換)      |      https://www.51cto.com/article/604692.html                  |
