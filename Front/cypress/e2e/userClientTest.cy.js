/// <reference types="cypress" />

describe("Interacciones con un producto", () => {
  beforeEach(() => {
    cy.fixture("userClient").then(userClient => {
      cy.login(userClient.userName, userClient.password);
    });
    cy.url().should("not.include", "/login");
  });

  it("deberia sacar un producto de favoritos", () => {
    cy.get("nav.navbar-container:visible").contains("a", "Favoritos").click();
    cy.get("div.items-content-wrap").find("a").should("have.length", 1);
    cy.get("div.items-content-wrap").find("a").first().click();
    cy.contains("button", "Sacar de favoritos").should("be.visible").click();
    cy.get("nav.navbar-container:visible").contains("a", "Favoritos").click();
    cy.get("div.items-content-wrap").find("a").should("have.length", 0);
  });

  it("deberia agregar un producto a favoritos", () => {
    cy.get(".product-card-container").should("have.length.at.least", 1);
    cy.get(".product-card-container").first().click();
    cy.contains("button", "Agregar a favoritos").should("be.visible").click();
    cy.get("nav.navbar-container:visible").contains("a", "Favoritos").click();
    cy.get("div.items-content-wrap").find("a").should("have.length", 1);
  });

  it("deberia agregar un producto al carrito", () => {
    cy.get(".product-card-container").should("have.length.at.least", 1);
    cy.get(".product-card-container").first().click();
    cy.contains("button", "Agregar al carrito").should("be.visible").click();
    cy.get("nav.navbar-container:visible").find('a[href="/user/cart"]').click();
    cy.get("div.items-content-wrap")
      .find("div.cart-product-card")
      .should("have.length", 1);
  });

  it("deberia comprar los productos del carrito", () => {
    cy.get(".product-card-container").should("have.length.at.least", 1);
    cy.get("nav.navbar-container:visible").find('a[href="/user/cart"]').click();
    cy.get("div.items-content-wrap")
      .find("div.cart-product-card")
      .should("have.length.at.least", 1);
    cy.get("button.button-loading").click();
    cy.get("div.items-content-wrap")
      .find("div.cart-product-card")
      .should("have.length", 0);
    cy.get("nav.navbar-container:visible").contains("a", "Compras").click();
    cy.get("div.items")
      .should("be.visible")
      .find("section.carousel-container")
      .should("have.length.at.least", 1);
  });

  it("deberia dejar un comentario en un producto ya comprado", () => {
    cy.get("nav.navbar-container:visible").contains("a", "Compras").click();
    cy.get("div.items")
      .should("be.visible")
      .find("section.carousel-container")
      .should("have.length.at.least", 1);
    cy.get("section.carousel-container")
      .first()
      .find(".product-card-container")
      .first()
      .click();
    cy.get("section.comments-section-qualify")
      .find("button:has(svg)")
      .each(($btn, index) => {
        if (index < 3) {
          cy.wrap($btn).click();
        }
      });
    cy.get("#comment-input").type(
      "El envio llego un poco mal pero el producto esta excelente"
    );
    cy.get("button[type=submit].input-text-container-form-button").click();
    cy.get("section.comments-section-list-container-content")
      .should("be.visible")
      .find("div.comment")
      .should("have.length.at.least", 1);
  });
});
