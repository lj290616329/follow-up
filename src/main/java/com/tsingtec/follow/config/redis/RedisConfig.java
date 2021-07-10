package com.tsingtec.follow.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 继承 CachingConfigurerSupport，为了自定义生成 KEY 的策略。可以不继承。
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.cache.redis.time-to-live}")
    private Duration timeToLive = Duration.ZERO;







    /*@Bean
    public RedisConnectionFactory redisConnectionFactory(){
        *//**添加对应依赖 JedisConnectionFactory,LettLettuceConnectionFactory
         *RedisConnectionFactory工厂又两种方式创建类 :
         　　　*　JedisConnectionFactory()
         　　　*　LettLettuceConnectionFactory　
         　　　*//*

        return new JedisConnectionFactory();
    }
    *//**
     * 实例化RedisTemplate类
     *//*
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        //大多数情况，都是选用<String, Object>
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(redisConnectionFactory);

        // 使用JSON的序列化对象，对数据key和value进行序列化转换
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        //ObjectMapper是Jackson的一个工作类，顾名思义他的作用是将JSON映射到Java对象即反序列化，或将Java对象映射到JSON即序列化
        ObjectMapper mapper = new ObjectMapper();
        // 设置序列化时的可见性，第一个参数是选择序列化哪些属性，比如时序列化setter?还是filed?h第二个参数是选择哪些修饰符权限的属性来序列化，比如private或者public，这里的any是指对所有权限修饰的属性都可见(可序列化)
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 设置出现故障即错误的类型，第一个是指验证程序，此时的参数为无需验证，其他参数可以查看源码了解(作者还在啃源码中)，第二是指该类不能为final修饰，否则将会报错
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        // 设置RedisTemplate模板的序列化方式为jacksonSeial
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        return template;
    }
    *//**
     * 自定义缓存管理器
     * @param redisConnectionFactory
     * @return
     *//*
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 创建String和JSON序列化对象，分别对key和value的数据进行类型转换
        RedisSerializer<String> strSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(mapper);
        // 自定义缓存数据序列化方式和有效期限
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(timeToLive)   // 设置缓存有效期为1天
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(strSerializer)) // 使用strSerializer对key进行数据类型转换
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSeial)) // 使用jacksonSeial对value的数据类型进行转换
                .disableCachingNullValues();   // 对空数据不操作

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();
        return cacheManager;
    }*/




    /**
     * 配置Jackson2JsonRedisSerializer序列化策略
     * */
    /*private Jackson2JsonRedisSerializer<Object> serializer() {
        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }*/


    /*@Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        redisTemplate.setValueSerializer(serializer());

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(stringRedisSerializer);

        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(serializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }*/






    /*@Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 缓存有效期
                .entryTtl(timeToLive)
                // 使用StringRedisSerializer来序列化和反序列化redis的key值
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()))
                // 禁用空值
                .disableCachingNullValues();

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }*/
}
