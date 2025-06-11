import { Product, Qualification } from "./";

interface User {
  name: string;
  role: string;
  favorites: Product[];
  purchases: Product[];
  qualifications: Qualification[];
}

export default User;
