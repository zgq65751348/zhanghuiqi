package org.security.main.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.security.common.common.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.annotation.PostConstruct;
import java.security.PublicKey;

@Data
@Slf4j
@ConfigurationProperties(prefix = "rsa.key.path")
public class RsaKeyProperties {

    private String pubKey  ;

    private PublicKey publicKey;

    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(pubKey);
    }
}
