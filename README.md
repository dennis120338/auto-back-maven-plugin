# 简介

maven 打包自动备份源码插件

# 使用方式

```
<build>
    <plugins>
        <!-- 打包时自动备份源代码 -->
        <plugin>
            <groupId>com.laohand</groupId>
            <artifactId>auto-back-maven-plugin</artifactId>
            <version>1.0.0</version>
            <configuration>
                <!--备份目录，默认 /data0/back -->
                <path>/data0/test</path>
            </configuration>
        </plugin>
    </plugins>
</build>
```
