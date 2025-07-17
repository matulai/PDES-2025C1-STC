/// <reference types="cypress" />
// ***********************************************
// This example commands.ts shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
//
declare global {
  namespace Cypress {
    interface Chainable {
      login(userName: string, password: string): Chainable<void>;
      register(
        userName: string,
        password: string,
        rol: string
      ): Chainable<void>;
      // Add other custom commands here if needed
      // drag(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
      // dismiss(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
      // visit(originalFn: CommandOriginalFn, url: string, options: Partial<VisitOptions>): Chainable<Element>
    }
  }
}

Cypress.Commands.add("login", (userName: string, password: string) => {
  cy.visit("/login");

  cy.get("#username").type(userName);
  cy.get("#password").type(password);

  cy.wait(5000);

  cy.intercept("POST", "**/auth/login").as("login");
  cy.get("button[type=submit].auth-card-container-form-button").click();
  cy.wait("@login");
});

Cypress.Commands.add(
  "register",
  (userName: string, password: string, rol: string) => {
    cy.visit("/register");

    cy.get("#username").type(userName);
    cy.get("#password").type(password);
    cy.get("#rolesOptions").select(rol);

    cy.wait(5000);

    cy.intercept("POST", "**/auth/register").as("register");
    cy.get("button[type=submit].auth-card-container-form-button").click();
    cy.wait("@register");
  }
);
