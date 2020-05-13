package org.security.auth.utils;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.security.auth.dto.FastDFSFile;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FastDFSClient {

    static {
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            log.error("FastDFS Client Init Fail!", e);
        }
    }

    /**
     *  获取TrackerServer
     * @return
     * @throws IOException
     */
    public static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getConnection();
    }

    /**
     * 获取StorageClient
     * @param trackerServer
     * @return
     */
    public static StorageClient getStorageServer(TrackerServer trackerServer){
        return new StorageClient(trackerServer,null);
    }

    public static String[]  upload(FastDFSFile file) throws IOException, MyException {
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", file.getAuthor());
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient =getStorageServer(trackerServer);
       return storageClient.upload_file(file.getContent(),file.getExt(),meta_list);
    }

    /**
     * 获取文件信息
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws IOException
     * @throws MyException
     */
    public static FileInfo getFileInfo(String groupName,String remoteFileName) throws IOException, MyException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient =getStorageServer(trackerServer);
        return storageClient.get_file_info(groupName,remoteFileName);
    }

    /**
     *  下载文件
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws IOException
     * @throws MyException
     */
    public static InputStream downLoad(String groupName, String remoteFileName) throws IOException, MyException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient =getStorageServer(trackerServer);
       byte []  fileByte=  storageClient.download_file(groupName,remoteFileName);
       return new ByteArrayInputStream(fileByte);
    }

    /**
     *  删除文件
     * @param groupName
     * @param remoteFileName
     * @return int
     * @throws IOException
     * @throws MyException
     */
    public static int deleteFile(String groupName, String remoteFileName) throws IOException, MyException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient =getStorageServer(trackerServer);
       return storageClient.delete_file( groupName,  remoteFileName);
    }

    /**
     *  获取Storage信息
     * @return StorageServer
     * @throws IOException
     */
    public static StorageServer getStorageInfo() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        TrackerClient trackerClient = new TrackerClient();
      return  trackerClient.getStoreStorage(trackerServer);
    }

    /**
     *  返回StorageServer 组的ip 端口信息
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws IOException
     */
    public static ServerInfo[] StorageServerInfo(String groupName, String remoteFileName) throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
    }

    /**
     *  获取Tracker的ip地址和端口
     * @return
     * @throws IOException
     */
    public static  String getTracker() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        int trackerHttpPort = ClientGlobal.getG_tracker_http_port();
        String trackerHttpIDp = trackerServer.getInetSocketAddress().getHostString();
        return "http://"+trackerHttpIDp+":"+trackerHttpPort;
    }
}
