import { Product, Qualification, PurchaseRecipe } from "./";

interface User {
  name: string;
  role: string;
  favorites: Product[];
  purchases: PurchaseRecipe[];
  qualifications: Qualification[];
  cart: Product[];
}

export default User;
