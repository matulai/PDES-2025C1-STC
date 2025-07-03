import { Product } from ".";

interface PurchaseRecipe {
  purchasePrice: number;
  purchaseDate: string; // ISO 8601 format, ej: "2025-06-15T14:30:00"
  purchaseProducts: Product[];
}

export default PurchaseRecipe;
