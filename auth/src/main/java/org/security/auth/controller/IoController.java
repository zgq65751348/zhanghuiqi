package org.security.auth.controller;

import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.security.auth.dto.FastDFSFile;
import org.security.auth.utils.FastDFSClient;
import org.security.common.exception.Code;
import org.security.common.exception.ExceptionHandlerClass;
import org.security.common.exception.HttpResult;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/file")
@Slf4j
public class IoController {

    /**
     *  FastDFS 上传文件
     * @param file
     * @return
     * @throws IOException
     * @throws MyException
     */
    @PostMapping("/upload")
    public HttpResult upload(@RequestParam("file") MultipartFile file) throws IOException, MyException {
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),file.getBytes(),
                Strings.getFilenameExtension(file.getOriginalFilename())
                );
        String [] uploads = FastDFSClient.upload(fastDFSFile);
        String url = FastDFSClient.getTracker()+"/"+uploads[0]+"/"+uploads[1];
        return HttpResult.success(url);
    }

    @PostMapping("/multipart/io")
    public HttpResult multiUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String filePath = "D:\\images\\";
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                log.error("---->(1) 上传第" + (i++) + "个文件失败");
               throw new ExceptionHandlerClass(Code.FILE_UPLOAD_ERROR);
            }
            String fileName = UUID.randomUUID()+ file.getOriginalFilename();
            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
                paths.add(fileName);
                log.info("第" + (i + 1) + "个文件上传成功");
            } catch (IOException e) {
                log.error(e.toString(), e);
                log.error("---->(2)   上传第" + (i++) + "个文件失败");
            }
        }
        return HttpResult.success(paths);
    }
}
