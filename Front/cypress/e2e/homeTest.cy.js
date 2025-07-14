describe("Home title", () => {
  it("muestra el título STC con el link a /", () => {
    cy.visit("/");
    cy.contains("a", "STC").should("have.attr", "href", "/");
  });
});

describe("Search bar", () => {
  it("muestra el input de búsqueda", () => {
    cy.visit("/");
    cy.get('input[placeholder="Buscar productos, marcas y más..."]').should(
      "exist"
    );
  });
});

describe("Nav bar", () => {
  it("debe mostrar un nav con los enlaces esperados", () => {
    cy.visit("/");

    // Verifica que exista un <nav>
    cy.get("nav").should("exist");

    // Verifica que haya al menos 3 enlaces <a> dentro del <nav>
    cy.get("nav a").should("have.length.at.least", 4);

    // Verifica que existan enlaces específicos por su texto
    cy.get("nav.navbar-container")
      .first()
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
        cy.contains("a", "Registrarse").should(
          "have.attr",
          "href",
          "/register"
        );
        cy.contains("a", "Ingresar").should("have.attr", "href", "/login");
      });
  });
});

describe("Home products", () => {
  it("muestra los productos del home", () => {
    cy.intercept("GET", "**/products/search*").as("getProducts");

    cy.visit("/");

    // Esperar a que se complete la llamada a la API
    cy.wait("@getProducts", { timeout: 60000 });

    // Asegura que haya al menos un producto
    cy.get(".product-card-container").should("have.length.at.least", 1);

    // Para cada producto, verifica los elementos internos
    cy.get(".product-card-container").each($card => {
      // Verifica que haya una imagen
      cy.wrap($card)
        .find("img.product-card-image")
        .should("have.attr", "src")
        .and("match", /^https?:\/\//); // o podés omitir esto si es local

      // Verifica que haya un título
      cy.wrap($card)
        .find("h2.product-card-title")
        .invoke("text")
        .should("not.be.empty");

      // Verifica que haya un precio
      cy.wrap($card)
        .find(".product-card-price")
        .invoke("text")
        .should("match", /^\$\d/); // ej: "$5.000"

      // Verifica que el link tenga href a /products/mlaId
      cy.wrap($card)
        .should("have.attr", "href")
        .and("match", /\/products\/\w+/);
    });
  });
});

describe("Footer", () => {
  it("muestra el footer con el texto esperado", () => {
    cy.visit("/");
    cy.contains("footer", "© 2025 Segui tus compras.").should("exist");
  });
});
