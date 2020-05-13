package org.security.auth.dto;

import lombok.Data;
import org.csource.common.MyException;
import org.csource.fastdfs.FileInfo;
import org.security.auth.utils.FastDFSClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
public class FastDFSFile {

    //文件名字
    private String name;
    //文件内容
    private byte[] content;
    //文件扩展名
    private String ext;
    //文件MD5摘要值
    private String md5;
    //文件创建作者
    private String author;

    public FastDFSFile(String name, byte[] content, String ext, String
            height,
                       String width, String author) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public static void main(String[] args) throws IOException, MyException {
        //FileInfo fileInfo = FastDFSClient.getFileInfo("group1","M00/00/00/rBAgBF61bV2AbKuOAAAEAw9hWW414..png");
        //System.out.println(fileInfo.getSourceIpAddr());
     /* InputStream inputStream =  FastDFSClient.downLoad("group1","M00/00/00/rBAgBF61bV2AbKuOAAAEAw9hWW414..png");
        FileOutputStream fileOutputStream = new FileOutputStream("F:/1.jpg");
        byte[] b = new byte[1024];
        while (inputStream.read()!= -1){
            fileOutputStream.write(b);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();*/

        System.out.println(FastDFSClient.deleteFile("group1","M00/00/00/rBAgBF61eDyAbw5iAAAEAw9hWW497..png"));
    }
}
