package com.javatechie.spring.mockito.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.javatechie.spring.mockito.api.model.Somme;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.spring.mockito.api.model.Employee;
import com.javatechie.spring.mockito.api.model.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringMockitoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper(); // convertir les objets en JSON

	/*@Before
	public void setUp() {

		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}*/

	@Test
	public void addEmployeeTest() throws Exception {
		// création de l'objet
		Employee employee = new Employee();
		employee.setName("jamel");
		employee.setDept("sante");

		// pour transformer l'ojet employee en format json
		String objectinjson = om.writeValueAsString(employee);

		// envoyer une requête d'ajout ( requête http)
		MvcResult queryresponse =
				mockMvc.perform(post("/EmployeeService/addEmployee").
						content(objectinjson)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).
						andExpect(status().isOk()).andReturn();

		// récupérer le contenu de la réponse sous forme chaine de caractères
		String resultContent = queryresponse.getResponse().getContentAsString();

		// transformer la réponse en format json en un objet de la classe Response
		Response response = om.readValue(resultContent, Response.class);

		Assert.assertTrue(response.isStatus() == Boolean.TRUE);

	}

	@Test
	public void deleteTest() throws Exception{

		Employee emp = new Employee();
		emp.setId(2);

		// envoyer une requête d'ajout ( requête http)
		MvcResult queryresponse =
				mockMvc.perform(delete("/EmployeeService/deleteEmployee/"+emp.getId())
				.contentType(MediaType.APPLICATION_JSON_VALUE)).
						andExpect(status().isOk()).andReturn();

		// récupérer le contenu de la réponse sous forme chaine de caractères
		String resultContent = queryresponse.getResponse().getContentAsString();

		// transformer la réponse en format json en un objet de la classe Response
		Response response = om.readValue(resultContent, Response.class);

		Assert.assertTrue(response.isStatus() == Boolean.FALSE);

	}

	@Test
	public void updateTest() throws Exception{

		// id = 5 , name: mahran, dept: BI

		Employee e = new Employee();
		e.setId(5);
		e.setName("mahran");
		e.setDept("Informatique");

		// pour transformer l'ojet employee en format json
		String jsonRequest = om.writeValueAsString(e);

		// envoyer une requête d'ajout ( requête http)
		MvcResult queryresponse = mockMvc.perform(put("/EmployeeService/updateEmployee/"+e.getId()).content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		// récupérer le contenu de la réponse sous forme chaine de caractères
		String resultContent = queryresponse.getResponse().getContentAsString();

		// transformer la réponse en format json en un objet de la classe Response
		Response response = om.readValue(resultContent, Response.class);

		Assert.assertTrue(response.isStatus() == Boolean.TRUE);
	}

	@Test
	public void getEmployeesTest() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/EmployeeService/getEmployees").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Response response = om.readValue(resultContent, Response.class);
		Assert.assertTrue(response.isStatus() == Boolean.TRUE);

	}

	@Test
	public void test_somme_add(){
		Somme agent = new Somme();
		int res = agent.add(2,3);
		Assert.assertEquals(res,5);
	}

}
