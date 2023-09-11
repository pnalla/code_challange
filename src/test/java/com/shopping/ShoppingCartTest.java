package com.shopping;

import com.shopping.api.Item;
import com.shopping.application.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {

	@Test
	void givenEmptyShoppingCart_whenAddedProductsUsingProductAPI_thenValidTotalCalculated() {

		// An empty shopping cart
		final ShoppingCart cart = new ShoppingCart();

		//And add a product, cornflakes x 1 quantity
		final Item cornflakes = new Item("cornflakes", 1);
		cart.add(cornflakes);

		//And add a product, cornflakes x 1 quantity
		final Item cornflakes1 = new Item("cornflakes", 1);
		cart.add(cornflakes1);

		//And add a product, weetabix x 1 quantity
		final Item weetabix = new Item("weetabix", 1);
		cart.add(weetabix);

		Item corflakesItem = cart.getItems().stream()
			.filter(i -> "cornflakes".equals(i.getProductName()))
			.findFirst()
			.orElse(null);

		//The shopping cart should contain 2 cornflakes and 1 weetabix
		Assertions.assertEquals(2, corflakesItem.getQuantity());

		Item weetabixItem = cart.getItems().stream()
			.filter(i -> "weetabix".equals(i.getProductName()))
			.findFirst()
			.orElse(null);

		Assertions.assertEquals(1, weetabixItem.getQuantity());

		//Add total value of the cart
		Assertions.assertEquals(15.02, cart.getTotalCartValue());

		//Add total tax of the cart
		Assertions.assertEquals(1.88, cart.getTaxPayable());

		//Add total invoice amount
		Assertions.assertEquals(16.90, cart.getTotalInvoiceAmount());
	}
	@Test
	void givenEmptyShoppingCart_whenAddedItems_thenValidStateCalculated() {

		// An empty shopping cart
		final ShoppingCart cart = new ShoppingCart();

		//And add a product, frosties x 1 quantity
		final Item frosties = new Item("frosties", 1);
		cart.add(frosties);

		//And add a product, cheerios x 2 quantity
		final Item cheerios = new Item("cheerios", 2);
		cart.add(cheerios);

		//And add a product, shreddies x 1 quantity
		final Item shreddies = new Item("shreddies", 1);
		cart.add(shreddies);

		//then total state of the cart
		Assertions.assertEquals(29.85, cart.getTotalInvoiceAmount());
	}
}