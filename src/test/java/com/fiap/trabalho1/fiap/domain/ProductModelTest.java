package com.fiap.trabalho1.fiap.domain;


import com.fiap.trabalho1.fiap.utils.EStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductModelTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidProductModel() {
        // Arrange
        ProductModel product = new ProductModel();
        product.setIdProduct(UUID.randomUUID());
        product.setName("Produto Teste");
        product.setValue(BigDecimal.valueOf(99.99));
        product.setDescription("Descrição do produto teste.");


        // Act
        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNameNotNullOrBlank() {
        // Arrange
        ProductModel product = new ProductModel();
        product.setName(""); // Nome inválido
        product.setValue(BigDecimal.valueOf(99.99));
        product.setDescription("Descrição válida.");


        // Act
        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testValueNotNull() {
        // Arrange
        ProductModel product = new ProductModel();
        product.setName("Produto Teste");
        product.setValue(null); // Valor inválido
        product.setDescription("Descrição válida.");


        // Act
        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testDescriptionNotNullOrBlank() {
        // Arrange
        ProductModel product = new ProductModel();
        product.setName("Produto Teste");
        product.setValue(BigDecimal.valueOf(99.99));
        product.setDescription(""); // Descrição inválida


        // Act
        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);
assertTrue(violations.isEmpty());

   }



    @Test
    void testCategoryAssociation() {
        // Arrange
        ProductModel product = new ProductModel();
        CategoryModel category = new CategoryModel();
        category.setIdCategory(UUID.randomUUID());
        category.setName("Categoria Teste");

        product.setCategory(category);

        // Assert
        assertNotNull(product.getCategory());
        assertEquals("Categoria Teste", product.getCategory().getName());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        UUID productId = UUID.randomUUID();
        CategoryModel category = new CategoryModel();
        category.setIdCategory(UUID.randomUUID());
        category.setName("Categoria Teste");

        ProductModel product = new ProductModel();
        product.setIdProduct(productId);
        product.setName("Produto Teste");
        product.setValue(BigDecimal.valueOf(199.99));
        product.setDescription("Descrição do produto teste.");
        product.setCategory(category);


        // Assert
        assertEquals(productId, product.getIdProduct());
        assertEquals("Produto Teste", product.getName());
        assertEquals(BigDecimal.valueOf(199.99), product.getValue());
        assertEquals("Descrição do produto teste.", product.getDescription());
        assertEquals(category, product.getCategory());

    }

    @Test
    void testSerializable() {
        // Arrange
        ProductModel product = new ProductModel();
        product.setIdProduct(UUID.randomUUID());
        product.setName("Produto Teste");
        product.setValue(BigDecimal.valueOf(99.99));
        product.setDescription("Descrição do produto teste.");

        // Act & Assert
        assertDoesNotThrow(() -> {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
            oos.writeObject(product);
            oos.close();

            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
            ProductModel deserializedProduct = (ProductModel) ois.readObject();
            ois.close();

            assertEquals(product.getIdProduct(), deserializedProduct.getIdProduct());
            assertEquals(product.getName(), deserializedProduct.getName());
            assertEquals(product.getValue(), deserializedProduct.getValue());
            assertEquals(product.getDescription(), deserializedProduct.getDescription());
        });
    }
}
