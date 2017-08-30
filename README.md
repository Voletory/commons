# Common
公共类和工具类

### beanutils
Bean拷贝、序列化、反射

### codec
Base64,Hex,AES,DES,DESede,RSA,MD5,SHA1,SHA224,SHA256,SHA384,SHA512的实现

### codec.encryption
提供对bean的指定属性的加解密功能
key的配置：
<bean class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
     <property name="staticMethod" value="com.kunpu.framework.security.beanutil.SecretKeeper.setStringSecrets"/>
     <property name="arguments">
     	<map>
     		<!-- 加密卡信息的密匙;取默认名,在使用注解时就无需设置secretName -->
     		<entry key="DEFAULT" value="ea0d39c305765142ac9d91c89d42a605" />
     	</map>
     </property>
</bean>

### collections
集和工具、可计数迭代器、随机迭代器、顺序迭代器、滚动迭代集和

### config
配置文件装载分析

### fun
java.util.function包的重写、算术运算、断言

### funs
常用函数接口

### http
Http/Https连接器、上传/下载器、Http相关工具

### io
流关闭工具

### lang
类扫描、数学运算、数字工具、字符串分割

### log
日志记录器

### packet
包头/体定义

### scroll
集和滚动器、滚动策略

### strategy
策略定义

### util
日期对比、日期工具、文件工具、图片工具、正则工具等

### variable
计时器

### xml
XML读写

