package org.security.auth.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.security.auth.utils.PathUtil;
import org.security.common.common.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@Slf4j
@ConfigurationProperties(prefix = "rsa.key.path")
public class RsaKeyProperties {

    private String pubKey ;
    private String priKey ;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void createRsaKey() throws Exception {
        //String path = System.getProperty("java.class.path");
        //log.debug("----->文件路径 ：{}",path);
        String path = PathUtil.getResourceBasePath();
        System.out.println("----->文件路径 "+path);
        publicKey = RsaUtils.getPublicKey(pubKey);
        privateKey = RsaUtils.getPrivateKey(priKey);
    }
}
