/// <reference types="cypress" />

// Tests de rol CLIENTE
describe("Register client", () => {
  it("deberia registrarse con credenciales invalidas", () => {
    cy.fixture("userClient").then(userClient => {
      cy.register(userClient.userName, "Password", userClient.role);
      cy.url().should("include", "/register");
    });
  });

  it("deberia registrarse con credenciales validas", () => {
    cy.fixture("userClient").then(userClient => {
      cy.register(userClient.userName, userClient.password, userClient.role);
      cy.url().should("not.include", "/register");
    });
  });
});

describe("Login client", () => {
  it("deberia ingresar con credenciales validas", () => {
    cy.fixture("userClient").then(userClient => {
      cy.login(userClient.userName, userClient.password);
      cy.url().should("not.include", "/login");

      // Ahora estoy en el home asique verifico la barra de navegación sea de cliente
      cy.get("nav").should("exist");
      cy.get("nav.navbar-container")
        .first() // hay un navbar igual oculto por eso el first
        .within(() => {
          cy.contains("a", "Compras").should(
            "have.attr",
            "href",
            "/user/purchases?page=1"
          );
          cy.contains("a", "Favoritos").should(
            "have.attr",
            "href",
            "/user/favourites?page=1"
          );
          cy.get('a[href="/user/cart"]').find("svg").should("exist");
        });
    });
  });
});

// Tests de rol ADMIN
describe("Register admin", () => {
  it("deberia registrarse con credenciales invalidas", () => {
    cy.fixture("userAdmin").then(userAdmin => {
      cy.register(userAdmin.userName, "Password", userAdmin.role);
      cy.url().should("include", "/register");
    });
  });
  it("deberia registrarse con credenciales validas", () => {
    cy.fixture("userAdmin").then(userAdmin => {
      cy.register(userAdmin.userName, userAdmin.password, userAdmin.role);
      cy.url().should("not.include", "/register");
    });
  });
});

describe("Login admin", () => {
  it("deberia ingresar con credenciales validas", () => {
    cy.fixture("userAdmin").then(userAdmin => {
      cy.login(userAdmin.userName, userAdmin.password);
      cy.url().should("not.include", "/login");
    });
    // Ahora estoy en el home asique verifico la barra de navegación sea de admin
    cy.get("nav").should("exist");
    cy.get("button.navbar-link:visible").click();
    cy.get("div.dropdown-menu")
      .first() // hay un navbar igual oculto por eso el first
      .within(() => {
        cy.contains("a", "Usuarios").should(
          "have.attr",
          "href",
          "/users?page=1"
        );
        cy.contains("a", "Todos los favoritos").should(
          "have.attr",
          "href",
          "/users/favourites?page=1"
        );
        cy.contains("a", "Todas las reseñas").should(
          "have.attr",
          "href",
          "/users/qualifications?page=1"
        );
        cy.contains("a", "Todas las compras").should(
          "have.attr",
          "href",
          "/users/purchases?page=1"
        );
        cy.contains("a", "Top vendidos").should(
          "have.attr",
          "href",
          "/products/topSellingProducts?page=1"
        );
        cy.contains("a", "Top compradores").should(
          "have.attr",
          "href",
          "/users/topBuyers?page=1"
        );
        cy.contains("a", "Top favoritos").should(
          "have.attr",
          "href",
          "/products/topFavouritesProducts?page=1"
        );
      });
  });
});
