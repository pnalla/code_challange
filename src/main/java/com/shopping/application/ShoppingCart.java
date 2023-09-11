package com.shopping.application;

import com.shopping.api.Item;
import com.shopping.repository.Products;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.shopping.util.NumberUtil.roundNumber;

public class ShoppingCart {
  private final Logger log = LogManager.getLogger(ShoppingCart.class);
  private final List<Item> cart = new ArrayList<>();
  private final Products products = new Products();

  public void add(final Item item) {
    if (null == item) {
      throw new IllegalArgumentException("item cannot be added to the cart");
    }
    if (item.getQuantity() < 1) {
      throw new IllegalArgumentException("item should have a valid quantity");
    }
    cart.stream()
      .filter(cartItem -> item.getProductName().equals(cartItem.getProductName()))
      .findFirst()
      .ifPresentOrElse(cartItem -> cartItem.setQuantity(item.getQuantity() + cartItem.getQuantity()),
        () -> cart.add(item));
  }
  public double getTotalCartValue() {
    // get price of each item and calculate total cart value
    final double sum = cart
      .stream()
      .mapToDouble(item -> {
        try {
          return item.getQuantity() * products.getProductDetails(item.getProductName()).getPrice();
        } catch (IOException e) {
          log.error("Failed to get product details", e);
          throw new RuntimeException("Failed to get product details");
        }
      })
      .sum();
    return roundNumber(sum);
  }
  public double getTaxPayable() {
    double taxRate = 12.5;
    final double tax = getTotalCartValue() * taxRate / 100;
    return roundNumber(tax);
  }

  public double getTotalInvoiceAmount() {
    final double totalPrice = getTotalCartValue() + getTaxPayable();
    return roundNumber(totalPrice);
  }

  public List<Item> getItems() {
    return Collections.unmodifiableList(cart);
  }
}
