package com.sumerge.SpringPractice.Config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Course API", version = "v1"),
        security = @SecurityRequirement(name = "basicAuth")
)
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
    @Bean
    public OpenApiCustomizer addValidationHeader() {
        return openApi -> {
            // define a reusable component parameter
            openApi.getComponents()
                    .addParameters("xValidationReport",
                            new HeaderParameter()
                                    .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER.toString())
                                    .name("x-validation-report")
                                    .required(true)
                                    .schema(new StringSchema()._default("true"))
                                    .description("Must be true to pass validation filter")
                    );
            // attach it to every operation
            openApi.getPaths().values().forEach(pathItem ->
                    pathItem.readOperations().forEach(op ->
                            op.addParametersItem(new HeaderParameter().$ref("#/components/parameters/xValidationReport"))
                    )
            );
        };
    }
}