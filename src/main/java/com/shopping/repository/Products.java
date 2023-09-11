package com.shopping.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Products {
  public static final String PRODUCTS_API = "https://equalexperts.github.io/backend-take-home-test-data/";
  private final Logger log = LogManager.getLogger(Products.class);
  private final ObjectMapper objectMapper = new ObjectMapper();
  public Product getProductDetails(final String productName) throws IOException {
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(PRODUCTS_API + productName + ".json"))
      .method("GET", HttpRequest.BodyPublishers.noBody())
      .build();
    HttpResponse<String> response = null;
    try {
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return objectMapper.readValue(response.body(), Product.class);
    } catch (IOException | InterruptedException io) {
      log.error("Failed connecting products api");
      throw new IOException("Failed connecting products api");
    }
  }
}
