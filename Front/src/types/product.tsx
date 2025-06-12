import { Qualification } from "./";

interface Product {
  id?: string;
  mlaId: string;
  name: string;
  price: number;
  description?: string;
  imageURL: string;
  qualifications?: Qualification[];
  domain_id?: string;
}

export default Product;
