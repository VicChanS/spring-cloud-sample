package com.vicchan.scsample.svc.core.config;

import com.vicchan.scsample.svc.core.common.GlobalString;
import com.vicchan.scsample.svc.core.swagger.ModelCache;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger在线文档配置<br>
 * 项目启动后可通过地址：http://host:ip/swagger-ui.html 查看在线文档
 *
 * @author VicChan
 * @version 2019-10-01
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        ModelCache.getInstance().setParamClass(GlobalString.class);
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
            .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("spring-cloud-sample API")
            .description("spring-cloud-sample在线API文档")
            .termsOfServiceUrl("https://github.com/VicChanS/spring-cloud-sample")
            .contact(new Contact("VicChan","https://github.com/VicChanS/spring-cloud-sample","2734025186@qq.com"))
            .version("1.0")
            .build();
    }

}
