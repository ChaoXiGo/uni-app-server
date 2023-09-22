# 七牛云上传凭证SpringBoot配置

## [配置文档Java SDK](https://developer.qiniu.com/kodo/1239/java#upload-file)

### 安装 pom.xml 所需依赖

```xml  <dependencies>
<dependency>
  <groupId>com.qiniu</groupId>
  <artifactId>qiniu-java-sdk</artifactId>
  <version>[7.13.0, 7.13.99]</version>
  </dependency>

    <dependency>
      <groupId>com.squareup.okhttp3</groupId>
      <artifactId>okhttp</artifactId>
      <version>3.14.2</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.8.5</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>com.qiniu</groupId>
      <artifactId>happy-dns-java</artifactId>
      <version>0.1.6</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
  ```

### 七牛云属性配置信息存入 Application.yml 中

```yml
qi-niu-cloud:
  access-key: 3lSbT8J7zPMbLaaraN_1FW0N2NOavJ_2Y-xmlQBE
  access-secret: xWQzky2ptZkoCKMSF1L12JIqMkqkDrxJgFvhBVIX
  bucket-name: chaoxi-store
  host-name: http://s1blps18l.hn-bkt.clouddn.com/
  ```

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![Alt text](1695372256732.jpg)

新建属性类``/config/QiNiuProperties.java``

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "qi-niu-cloud")
@EnableConfigurationProperties
public class QiNiuProperties {

    private String accessKey;
    private String accessSecret;
    private String bucketName;
    private String hostName;
}
```

新建工具类 ``/utils/FileUploadUtil.java``

![Alt text](image.png)

将结果拼接hostname返回

``return qiNiuProperties.getHostName() + putRet.key;``

```java

@Component
public class FileUploadUtil {
    @Autowired
    QiNiuProperties qiNiuProperties;

    public String uploadByBytes(byte[] bytes,String fileName) {
        // 构造一个带指定 Region 对象的配置类,  注意参数更改为自己的仓库地址
        Configuration cfg = new Configuration(Region.huanan());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = qiNiuProperties.getAccessKey();
        String secretKey = qiNiuProperties.getAccessSecret();
        String bucket = qiNiuProperties.getBucketName();

        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        // String key = null;

        try {
            // 上传的文件
            // byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(bytes, fileName, upToken);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return qiNiuProperties.getHostName() + putRet.key;
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);

                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ex) {
            // ignore
        }
        return null;
    }
}

```

## controller测试

注意 ``admin/file/upload/element``路径名固定

```java
@RestController
@RequestMapping("test")
public class upLoadImageController {
    FileUploadUtil fileUploadUtil;

    @PostMapping("/admin/file/upload/element")
    R upLoadImage(@RequestParam("file")MultipartFile file) throws IOException {

        // 获取文件名
        String suffix = file.getOriginalFilename().substring(
                // 获取后缀名
                file.getOriginalFilename().lastIndexOf(".")
        );

        // 创建新的文件名称
        String newFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        String result = fileUploadUtil.uploadByBytes(
                file.getBytes(),
                suffix + newFileName
        );
        return R.success(result);
    }
}
```

注意key固定 ``file``
![Alt text](image-1.png)
