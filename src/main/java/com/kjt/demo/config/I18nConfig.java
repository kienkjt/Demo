package com.kjt.demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

@Configuration
public class I18nConfig implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver(); // Sử dụng AcceptHeaderLocaleResolver để lấy ngôn ngữ từ header
        localeResolver.setDefaultLocale(Locale.forLanguageTag("vi")); // Đặt ngôn ngữ mặc định là tiếng Việt
        localeResolver.setSupportedLocales(Arrays.asList(
                Locale.forLanguageTag("vi"),
                Locale.forLanguageTag("en")
        )); // Đặt các ngôn ngữ được hỗ trợ
        return localeResolver;
    }

    // Định nghĩa interceptor để thay đổi ngôn ngữ dựa trên tham số trong URL
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//        interceptor.setParamName("lang"); // Tham số để thay đổi ngôn ngữ
//        return interceptor;
//    }
    // Đăng ký interceptor để thay đổi ngôn ngữ
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }

    // Cấu hình MessageSource để nạp các file tin nhắn i18n/messages_xx.properties
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setCacheSeconds(3600); // Tải lại file tin nhắn sau mỗi giờ
        return messageSource;
    }
    // Cấu hình validator để sử dụng MessageSource cho các thông báo lỗi
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
