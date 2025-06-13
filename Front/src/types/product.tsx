import { Qualification } from "./";

interface Product {
  mlaId: string;
  name: string;
  price: number;
  description?: string;
  imageURL: string;
  qualifications?: Qualification[];
  domainId?: string;
}

export default Product;
