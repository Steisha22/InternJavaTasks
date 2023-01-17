package com.example.cafe;

import com.example.cafe.dao.JdbcDao;
import com.example.cafe.data.DishData;
import com.example.cafe.dto.CategoryInfoDto;
import com.example.cafe.dto.DishInfoDto;
import com.example.cafe.dto.DishQueryDto;
import com.example.cafe.dto.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CafeApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private JdbcDao dao;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Order(1)
	void testGetAllDishes() throws Exception {
		mvc.perform(get("/cafe/mainPage"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(100)));
	}

	@Test
	@Order(2)
	public void testCreateDish() throws Exception {
		String dish_name = "Pizza";
		int weight = 180;
		double price = 156.8;
		String description = "very delicious pizza";
		int categoryId = 2;
		String body = """
				  {
				      "dish_name": "%s",
				      "weight": %d,
				      "price": 156.8,
				      "description": "%s",
				      "categoryId": %d
				  }               
				""".formatted(dish_name, weight, description, categoryId);
		MvcResult mvcResult = mvc.perform(post("/cafe")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body)
				)
				.andExpect(status().isCreated())
				.andReturn();

		RestResponse response = parseResponse(mvcResult, RestResponse.class);
		int dishId = Integer.parseInt(response.getResult());
		assertThat(dishId).isGreaterThanOrEqualTo(1);

		DishData dish = dao.getDishById(dishId);
		assertThat(dish).isNotNull();
		assertThat(dish.getDish_name()).isEqualTo(dish_name);
		assertThat(dish.getWeight()).isEqualTo(weight);
		assertThat(dish.getPrice()).isEqualTo(price);
		assertThat(dish.getDescription()).isEqualTo(description);
		assertThat(dish.getCategoryId()).isEqualTo(categoryId);
	}

	private <T>T parseResponse(MvcResult mvcResult, Class<T> c) {
		try {
			return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), c);
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			throw new RuntimeException("Error parsing json", e);
		}
	}

	@Test
	@Order(3)
	public void testCreateDish_validation() throws Exception {
		mvc.perform(post("/cafe")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}")
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(4)
	public void testSearchDish() throws Exception {
		String category_name = "Category №1";
		double price = 10000;
		int from = 1;
		int size = 10;

		DishQueryDto dto = new DishQueryDto();
		dto.setCategory_name(category_name);
		dto.setPrice(price);
		dto.setFrom(from);
		dto.setSize(size);

		List<DishInfoDto> allDishes = dao.search(dto);

		String body = """
				  {
				      "price": 10000,
				      "category_name": "%s",
				      "from": %d,
				      "size": %d
				  }
				""".formatted(category_name, from, size);

		mvc.perform(post("/cafe/_search")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(allDishes.size())));
	}

	@Test
	@Order(5)
	public void testUpdateDish() throws Exception {
		String dish_name = "test";
		int weight = 125;
		double price = 176.8;
		String description = "very delicious test";
		int categoryId = 1;
		String body = """
				  {
				      "dish_name": "%s",
				      "weight": %d,
				      "price": 176.8,
				      "description": "%s",
				      "categoryId": %d
				  }               
				""".formatted(dish_name, weight, description, categoryId);
		mvc.perform(put("/cafe/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body)
				)
				.andExpect(status().isOk());

		DishData dish = dao.getDishById(1);
		assertThat(dish).isNotNull();
		assertThat(dish.getDish_name()).isEqualTo(dish_name);
		assertThat(dish.getWeight()).isEqualTo(weight);
		assertThat(dish.getPrice()).isEqualTo(price);
		assertThat(dish.getDescription()).isEqualTo(description);
		assertThat(dish.getCategoryId()).isEqualTo(categoryId);
	}

	@Test
	@Order(6)
	void testGetAllCategories() throws Exception {
		mvc.perform(get("/cafe/categories"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(5)));
	}

	@Test
	@Order(7)
	public void deleteDish() throws Exception {
		mvc.perform(delete("/cafe/5"))
				.andExpect(status().isOk());
		List<DishInfoDto> allDishes = dao.getAllDishes();

		//Очікується 100 записів, оскільки раніше було додано нове блюдо і загальна кількість записів стала 101
		assertThat(allDishes.size()).isEqualTo(100);
	}

	@Test
	@Order(8)
	public void deleteAll() throws Exception {
		mvc.perform(delete("/cafe/mainPage"))
				.andExpect(status().isOk());
		List<DishInfoDto> allDishes = dao.getAllDishes();
		List<CategoryInfoDto> allCategories = dao.getAllCategories();
		assertThat(allDishes.size()).isEqualTo(0);
		assertThat(allCategories.size()).isEqualTo(0);
	}
}
