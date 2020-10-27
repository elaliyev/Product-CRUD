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


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductCrudApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void testCaseShouldReturnAllCategories() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/category/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());

    }
    @Test
    public void testCaseShouldReturnCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.categoryName").exists())
                .andExpect(jsonPath("$.categoryName").value("BOOK"))
                .andDo(print());

    }

    @Test
    public void testCaseCategoryInvalidArgument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category/f")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void testCaseForInvalidCategoryId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category/30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testCaseDeleteCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void negativeTestCaseDeleteCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category/1001")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testCaseCreateCategory() throws Exception {
        String jsonRequestFoCreateCategory=
                "{\n" +
                        "    \"categoryName\": \"BOOK\"\n" +
                        "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/category/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestFoCreateCategory)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.categoryName").exists())
                .andExpect(jsonPath("$.categoryName").value("BOOK"))
                .andDo(print());
    }

    @Test
    public void testCaseUpdateCategory() throws Exception {

        String jsonRequestForUpdateCategory=
                "{\n" +
                        "    \"id\": 1,\n" +
                        "    \"categoryName\": \"BOOK\"\n" +
                        "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestForUpdateCategory)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.categoryName").exists())
                .andExpect(jsonPath("$.categoryName").value("BOOK"))
                .andDo(print());
    }

    @Test
    public void verifyInvalidCategoryUpdate() throws Exception {
        String invalidJsonRequestForUpdateCategory=
                "{\n" +
                        "    \"id\": 2,\n" +
                        "    \"categoryName\": \"Book\",\n" +
                        "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestForUpdateCategory)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void negativeTestCaseForUpdateCategoryWithDifferentId() throws Exception {
        String invalidJsonRequestForUpdateCustomer=
                "{\n" +
                        "    \"id\": 2,\n" +
                        "    \"l_name\": \"Book\",\n" +
                        "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/category/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestForUpdateCustomer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
