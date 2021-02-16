package com.github.tingyugetc520.ali.dingtalk.util.crypto;

import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.error.DtRuntimeErrorEnum;
import com.github.tingyugetc520.ali.dingtalk.error.DtRuntimeException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 钉钉开放平台加解密方法
 */
public class DtCryptUtil {
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final Base64 BASE64 = new Base64();

    protected byte[] aesKey;
    protected String token;
    protected String appKeyOrCorpId;
    /**
     * ask getPaddingBytes key固定长度
     **/
    private static final Integer AES_ENCODE_KEY_LENGTH = 43;
    /**
     * 加密随机字符串字节长度
     **/
    private static final Integer RANDOM_LENGTH = 16;

    public DtCryptUtil(DtConfigStorage configStorage) {
        this(configStorage.getToken(), configStorage.getAesKey(), configStorage.getAppKeyOrCorpId());
    }

    /**
     * 构造函数
     *
     * @param token             钉钉开放平台上，开发者设置的token
     * @param aesKey            钉钉开放台上，开发者设置的EncodingAESKey
     * @param appKeyOrCorpId    企业的corpId
     */
    public DtCryptUtil(String token, String aesKey, String appKeyOrCorpId) {
        if (null == aesKey || aesKey.length() != AES_ENCODE_KEY_LENGTH) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.AES_KEY_ILLEGAL);
        }
        this.token = token;
        this.appKeyOrCorpId = appKeyOrCorpId;
        this.aesKey = Base64.decodeBase64(aesKey + "=");
    }

    public Map<String, String> getEncryptedMap(String plaintext) {
        return getEncryptedMap(plaintext, System.currentTimeMillis(), Utils.getRandomStr(16));
    }

    /**
     * 将和钉钉开放平台同步的消息体加密,返回加密Map
     *
     * @param plaintext 传递的消息体明文
     * @param timeStamp 时间戳
     * @param nonce     随机字符串
     * @return map
     */
    public Map<String, String> getEncryptedMap(String plaintext, Long timeStamp, String nonce) {
        if (null == plaintext) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.ENCRYPTION_PLAINTEXT_ILLEGAL);
        }
        if (null == timeStamp) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.ENCRYPTION_TIMESTAMP_ILLEGAL);
        }
        if (null == nonce || nonce.length() != 16) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.ENCRYPTION_NONCE_ILLEGAL);
        }
        // 加密
        String encrypt = encrypt(Utils.getRandomStr(RANDOM_LENGTH), plaintext);
        String signature = getSignature(token, String.valueOf(timeStamp), nonce, encrypt);
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("msg_signature", signature);
        resultMap.put("encrypt", encrypt);
        resultMap.put("timestamp", String.valueOf(timeStamp));
        resultMap.put("nonce", nonce);
        return resultMap;
    }

    /**
     * 密文解密
     *
     * @param msgSignature 签名串
     * @param timeStamp    时间戳
     * @param nonce        随机串
     * @param encryptMsg   密文
     * @return 解密后的原文
     */
    public String getDecryptMsg(String msgSignature, String timeStamp, String nonce, String encryptMsg) {
        //校验签名
        String signature = getSignature(token, timeStamp, nonce, encryptMsg);
        if (!signature.equals(msgSignature)) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.COMPUTE_SIGNATURE_ERROR);
        }
        // 解密
        return decrypt(encryptMsg);
    }

    /**
     * 对明文加密.
     * @param plaintext 需要加密的明文
     * @return 加密后base64编码的字符串
     */
    private String encrypt(String random, String plaintext) {
        try {
            byte[] randomBytes = random.getBytes(CHARSET);
            byte[] plainTextBytes = plaintext.getBytes(CHARSET);
            byte[] lengthByte = Utils.int2Bytes(plainTextBytes.length);
            byte[] corpIdBytes = appKeyOrCorpId.getBytes(CHARSET);

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            byteStream.write(randomBytes);
            byteStream.write(lengthByte);
            byteStream.write(plainTextBytes);
            byteStream.write(corpIdBytes);
            byte[] padBytes = PKCS7Padding.getPaddingBytes(byteStream.size());
            byteStream.write(padBytes);
            byte[] unencrypted = byteStream.toByteArray();
            byteStream.close();
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(unencrypted);

            return BASE64.encodeToString(encrypted);
        } catch (Exception e) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.COMPUTE_ENCRYPT_TEXT_ERROR);
        }
    }

    /**
     * 对密文进行解密.
     * @param text 需要解密的密文
     * @return 解密得到的明文
     */
    private String decrypt(String text) {
        byte[] originalArr;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(text);
            // 解密
            originalArr = cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.COMPUTE_DECRYPT_TEXT_ERROR);
        }

        String plainText;
        String fromAppKeyOrCorpId;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Padding.removePaddingBytes(originalArr);
            // 分离16位随机字符串,网络字节序和corpId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int plainTextLength = Utils.bytes2int(networkOrder);
            plainText = new String(Arrays.copyOfRange(bytes, 20, 20 + plainTextLength), CHARSET);
            fromAppKeyOrCorpId = new String(Arrays.copyOfRange(bytes, 20 + plainTextLength, bytes.length), CHARSET);
        } catch (Exception e) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.COMPUTE_DECRYPT_TEXT_LENGTH_ERROR);
        }

        // appKeyOrCorpId不相同的情况
        if (!fromAppKeyOrCorpId.equals(appKeyOrCorpId)) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.COMPUTE_DECRYPT_TEXT_CORPID_ERROR);
        }
        return plainText;
    }

    /**
     * 数字签名
     *
     * @param token     isv token
     * @param timestamp 时间戳
     * @param nonce     随机串
     * @param encrypt   加密文本
     * @return
     */
    public static String getSignature(String token, String timestamp, String nonce, String encrypt) {
        try {
            String[] array = new String[] {token, timestamp, nonce, encrypt};
            Arrays.sort(array);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuilder hexStr = new StringBuilder();
            String shaHex = "";
            for (byte b : digest) {
                shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
        } catch (Exception e) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.COMPUTE_SIGNATURE_ERROR);
        }
    }

    public static class Utils {
        public Utils() {
        }

        public static String getRandomStr(int count) {
            String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < count; ++i) {
                int number = random.nextInt(base.length());
                sb.append(base.charAt(number));
            }

            return sb.toString();
        }

        public static byte[] int2Bytes(int count) {
            return new byte[] {(byte)(count >> 24 & 255), (byte)(count >> 16 & 255), (byte)(count >> 8 & 255),
                    (byte)(count & 255)};
        }

        public static int bytes2int(byte[] byteArr) {
            int count = 0;

            for (int i = 0; i < 4; ++i) {
                count <<= 8;
                count |= byteArr[i] & 255;
            }

            return count;
        }
    }

    public static class PKCS7Padding {
        private static final Charset CHARSET = StandardCharsets.UTF_8;
        private static final int BLOCK_SIZE = 32;

        public PKCS7Padding() {
        }

        public static byte[] getPaddingBytes(int count) {
            int amountToPad = 32 - count % 32;

            char padChr = chr(amountToPad);
            StringBuilder tmp = new StringBuilder();

            for (int index = 0; index < amountToPad; ++index) {
                tmp.append(padChr);
            }

            return tmp.toString().getBytes(CHARSET);
        }

        public static byte[] removePaddingBytes(byte[] decrypted) {
            int pad = decrypted[decrypted.length - 1];
            if (pad < 1 || pad > 32) {
                pad = 0;
            }

            return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
        }

        private static char chr(int a) {
            byte target = (byte)(a & 255);
            return (char)target;
        }
    }

}
