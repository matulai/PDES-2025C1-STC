import { defineConfig } from "cypress";

export default defineConfig({
  e2e: {
    baseUrl: "http://localhost:5173",
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
  retries: {
    runMode: 2,  // cuando se corre en CI
    openMode: 0, // cuando usás Cypress en modo interactivo
  },  
  viewportWidth: 1280,
  viewportHeight: 720,

  defaultCommandTimeout: 8000, // tiempo para comandos como click(), type(), etc.
  requestTimeout: 10000,       // espera a que el backend devuelva algo
  responseTimeout: 15000,
});
