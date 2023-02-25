package com.example.mongoWeb;

import com.example.mongoWeb.dto.NameStatisticDto;
import com.example.mongoWeb.dto.PageDto;
import com.example.mongoWeb.dto.PepInfoDto;
import com.example.mongoWeb.dto.PepQueryDto;
import com.example.mongoWeb.repository.ZipRepository;
import com.example.mongoWeb.servises.MongoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = MongoWebApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MongoWebApplicationTests {
	@Autowired
	MockMvc mvc;

	@Autowired
	MongoService mongoService;

	@Test
	@Order(1)
	void testUploadZip() throws Exception {
		File file = new File("F:\\Prodjects\\pep2.zip");
		FileInputStream inputStream = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/plain", inputStream);
		mvc.perform(MockMvcRequestBuilders.multipart("/mongoWeb/upload")
						.file(mockMultipartFile))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(2)
	void testSearch() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		String firstName = "Роман";
		String lastName = "Греба";
		String patronymic = "Володимирович";

		PepQueryDto dto = new PepQueryDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setPatronymic(patronymic);

		PageDto<PepInfoDto> pepInfo = mongoService.search(dto);

		String body = """
				  {
				      "firstName": "%s",
				      "lastName": "%s",
				      "patronymic": "%s"
				  }
				""".formatted(firstName, lastName, patronymic);

		mvc.perform(post("/mongoWeb/_search")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(pepInfo)));
	}

	@Test
	@Order(3)
	void testListOfTenMostPopularNames() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<NameStatisticDto> list = mongoService.listOfTenMostPopularNames();
		mvc.perform(get("/mongoWeb/popularNames"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(10)))
				.andExpect(content().json(mapper.writeValueAsString(list)));
	}

}
