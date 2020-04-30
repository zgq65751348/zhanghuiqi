import org.junit.Test;
import org.junit.runner.RunWith;
import org.security.common.common.RsaUtils;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RsaUtilsTest {

    private String publicFilePath = "D:\\rsa\\public_key_rsa.pub";

    private String privateFilePath = "D:\\rsa\\private_key_rsa";

    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFilePath,privateFilePath,"zhang_guo_qi",2048);
    }

    @Test
    public void getPrivateKey() throws Exception {
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }

    @Test
    public void getPublicKey() throws Exception {
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }
}
