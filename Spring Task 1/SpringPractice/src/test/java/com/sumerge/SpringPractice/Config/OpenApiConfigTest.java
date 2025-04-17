package com.sumerge.SpringPractice.Config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.junit.jupiter.api.Test;
import org.springdoc.core.customizers.OpenApiCustomizer;
import static org.junit.jupiter.api.Assertions.*;




class OpenApiConfigTest {

    @Test
    void testAddValidationHeader() {
        // Arrange
        OpenApiConfig openApiConfig = new OpenApiConfig();
        OpenApiCustomizer customizer = openApiConfig.addValidationHeader();
        OpenAPI openAPI = new OpenAPI();
        openAPI.setComponents(new Components());
        openAPI.setPaths(new Paths());

        // Act
        customizer.customise(openAPI);

        // Assert
        // Verify the parameter is added to components
        assertNotNull(openAPI.getComponents().getParameters().get("xValidationReport"));
        HeaderParameter headerParameter = (HeaderParameter) openAPI.getComponents().getParameters().get("xValidationReport");
        assertEquals("x-validation-report", headerParameter.getName());
        assertEquals("header", headerParameter.getIn());
        assertTrue(headerParameter.getRequired());
        assertEquals("true", headerParameter.getSchema().getDefault());
        assertEquals("Must be true to pass validation filter", headerParameter.getDescription());

        // Verify the parameter is added to all operations
        openAPI.getPaths().forEach((path, pathItem) -> 
            pathItem.readOperations().forEach(operation -> 
                assertTrue(operation.getParameters().stream()
                        .anyMatch(param -> "#/components/parameters/xValidationReport".equals(param.get$ref()))
                )
            )
        );
    }
@Test
void testAddValidationHeader_ComponentParameter() {
        // Arrange
        OpenApiConfig openApiConfig = new OpenApiConfig();
        OpenApiCustomizer customizer = openApiConfig.addValidationHeader();
        OpenAPI openAPI = new OpenAPI();
        openAPI.setComponents(new Components());
    Paths paths = new Paths();
    paths.addPathItem("/test", new io.swagger.v3.oas.models.PathItem()
            .get(new io.swagger.v3.oas.models.Operation())
            .post(new io.swagger.v3.oas.models.Operation()));
    openAPI.setPaths(paths);

        // Act
        customizer.customise(openAPI);

        // Assert
        // Verify the parameter is added to components
        assertNotNull(openAPI.getComponents().getParameters().get("xValidationReport"));
        HeaderParameter headerParameter = (HeaderParameter) openAPI.getComponents().getParameters().get("xValidationReport");
        assertEquals("x-validation-report", headerParameter.getName());
        assertEquals("header", headerParameter.getIn());
        assertTrue(headerParameter.getRequired());
        assertEquals("true", headerParameter.getSchema().getDefault());
        assertEquals("Must be true to pass validation filter", headerParameter.getDescription());
}

@Test
void testAddValidationHeader_AttachedToOperations() {
        // Arrange
        OpenApiConfig openApiConfig = new OpenApiConfig();
        OpenApiCustomizer customizer = openApiConfig.addValidationHeader();
        OpenAPI openAPI = new OpenAPI();
        openAPI.setComponents(new Components());
        Paths paths = new Paths();
        paths.addPathItem("/test", new io.swagger.v3.oas.models.PathItem()
                        .get(new io.swagger.v3.oas.models.Operation())
                        .post(new io.swagger.v3.oas.models.Operation()));
        openAPI.setPaths(paths);

        // Act
        customizer.customise(openAPI);

        // Assert
        // Verify the parameter is added to all operations
        openAPI.getPaths().forEach((path, pathItem) ->
                        pathItem.readOperations().forEach(operation ->
                                        assertTrue(operation.getParameters().stream()
                                                        .anyMatch(param -> "#/components/parameters/xValidationReport".equals(param.get$ref()))
                                        )
                        )
        );
}
}