package com.example.productcrud;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductCrudApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void testCaseShouldReturnAllProducts() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/product/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1))).andDo(print());

    }
    @Test
    public void testCaseShouldReturnProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/101")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.currency").exists())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.price").value(new BigDecimal("220.0")))
                .andExpect(jsonPath("$.name").value("DELL"))
                .andDo(print());

    }

    @Test
    public void testCaseProductInvalidArgument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/f")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void testCaseForInvalidProductId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testCaseDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/101")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void negativeTestCaseDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/1001")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testCaseCreateProduct() throws Exception {
        String jsonRequestFoCreateProduct=
                "{\n" +
                        "    \"id\": 141,\n" +
                        "    \"name\": \"Toshiba\",\n" +
                        "    \"price\": 220,\n" +
                        "    \"currency\": \"USD\",\n" +
                        "    \"date\": \"2019-05-12\",\n" +
                        "    \"detail\": \"Toshiba DETAIL\",\n" +
                        "    \"category\": {\n" +
                        "        \"id\": 1,\n" +
                        "        \"categoryName\": \"LAPTOP\"\n" +
                        "    }\n" +
                        "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestFoCreateProduct)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.detail").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.name").value("Toshiba"))
                .andExpect(jsonPath("$.detail").value("Toshiba DETAIL"))
                .andExpect(jsonPath("$.price").value(new BigDecimal("220")))
                .andDo(print());
    }
    @Test
    public void negativeTestCaseCreateProduct() throws Exception {
        String invalidJsonRequestFoCreateProduct=
                "{\"first_name\": \"Toshiba\",\"price\": \"500\",\"currency\": \"EURO\"," +
                        "\"detail\": \"TOSHIBA DETAIL\",\"category\": \"1\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestFoCreateProduct)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void testCaseUpdateProduct() throws Exception {

        String jsonRequestForUpdateProduct=
                "{\n" +
                        "    \"id\": 101,\n" +
                        "    \"name\": \"Toshiba\",\n" +
                        "    \"price\": 820,\n" +
                        "    \"currency\": \"USD\",\n" +
                        "    \"date\": \"2019-05-12\",\n" +
                        "    \"detail\": \"Toshiba DETAIL\",\n" +
                        "    \"category\": {\n" +
                        "        \"id\": 1,\n" +
                        "        \"categoryName\": \"LAPTOP\"\n" +
                        "    }\n" +
                        "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/product/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestForUpdateProduct)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.detail").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.name").value("Toshiba"))
                .andExpect(jsonPath("$.price").value(new BigDecimal("820")))
                .andDo(print());
    }

    @Test
    public void verifyInvalidProductUpdate() throws Exception {
        String invalidJsonRequestForUpdateProduct=
                "{\"id\": \"101\", \"first_name\": \"Toshiba laptop\",\"price\": \"500\",\"currency\": \"EURO\"," +
                        "\"detail\": \"TOSHIBA DETAIL\",\"category\": \"1\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestForUpdateProduct)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
    @Test
    public void negativeTestCaseForUpdatePrroductWithDifferentId() throws Exception {
        String invalidJsonRequestForUpdateCustomer="{\"id\":1,\"firstName\": \"Elvin\",\"lastName\": \"Aliyev\",\"age\": 20}";

        mockMvc.perform(MockMvcRequestBuilders.put("/product/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestForUpdateCustomer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
