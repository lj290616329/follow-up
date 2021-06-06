package com.tsingtec.follow.config.cache;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author lj
 * @Date 2020/4/7 10:31
 * @Version 1.0
 */
@Slf4j
//@Configuration
public class EhCacheConfig {

    /**
     * ehcache缓存管理器；shiro整合ehcache：
     * 通过安全管理器：securityManager
     * @return EhCacheManager
     */
    /*@Order(1)
    @Bean(name="ehCacheManager")
    public EhCacheManager ehCacheManager() {
        log.error("=====shiro整合ehcache缓存：ShiroConfiguration.getEhCacheManager()");
        EhCacheManager ehcache = new EhCacheManager();
        ehcache.setCacheManager(ehCacheManagerFactoryBean().getObject());
        return ehcache;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("my-ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }*/

}