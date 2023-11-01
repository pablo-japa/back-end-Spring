package com.example.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class demoController {
	
	@GetMapping("/oi")
	public String oi() {
		return "oi";
	}
	@GetMapping("/compra")
	public String comprar() {
		return obterCotacao("bid");

	}

	@GetMapping("/venda")
	public String venda() {
		return obterCotacao("ask");
	}

	@GetMapping("/maximo")
	public String maximo() {
		return obterCotacao("high");
	}

	@GetMapping("/minimo")
	public String minimo() {
		return obterCotacao("low");
	}

	@GetMapping("/all")
	public String todos() {
		return obterCotacao("all");
	}

	private String obterCotacao(String tipo) {
		RestTemplate restTemplate = new RestTemplate();

		String url = "https://economia.awesomeapi.com.br/json/last/USD-BRL";

		try {
			String resposta = restTemplate.getForObject(url, String.class);

			try {
				if (tipo.equals("all")) {
					return resposta;
				} else {
					ObjectMapper objectMapper = new ObjectMapper();

					JsonNode rootNode = objectMapper.readTree(resposta);

					JsonNode campoNode = rootNode.get("USDBRL").get(tipo);

					return campoNode.asText();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "Erro ao obter o valor: " + e.getMessage();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao obter o valor: " + e.getMessage();

		}
	}
}
