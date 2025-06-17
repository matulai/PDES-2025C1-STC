import ProductCard from "./ProductCard";
import "@/styles/ProductsManage.css";
import type { Product } from "@/types";

interface ProductsManageProps {
  products: Product[];
  setProducts: (products: Product[]) => void;
}

const ProductsManage = ({ products }: ProductsManageProps) => {
  return (
    <div className="search-content">
      {/* <Filter setProducts={setProducts} /> */}
      <div className="search-content-results">
        {products.map(product => (
          <ProductCard key={product.mlaId} product={product} />
        ))}
      </div>
      {/* <Pagination products={products.pagination}/> */}
    </div>
  );
};

export default ProductsManage;
