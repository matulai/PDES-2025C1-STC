describe("Home", () => {
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

describe("Footer", () => {
  it("muestra el footer con el texto esperado", () => {
    cy.visit("/");
    cy.contains("footer", "© 2025 Segui tus compras.").should("exist");
  });
});
