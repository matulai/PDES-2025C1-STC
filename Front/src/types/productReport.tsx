import { Product } from "./";

interface ProductReport {
  id: number;
  purchaseCount: number;
  favoritesCount: number;
  product: Product;
}

export default ProductReport;
