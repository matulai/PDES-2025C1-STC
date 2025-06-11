interface Product {
  id?: string;
  mlaId: string;
  name: string;
  price: number;
  description?: string;
  imageURL: string;
  qualify?: number;
  domain_id?: string;
}

export default Product;
