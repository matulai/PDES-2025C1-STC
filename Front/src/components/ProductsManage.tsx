import ProductCard from "./ProductCard";
import { Product } from "@/types";
import "@/styles/ProductsManage.css";

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
          <ProductCard
            key={product.mlaId}
            mlaId={product.mlaId}
            name={product.name}
            imageURL={product.imageURL}
          />
        ))}
      </div>
      {/* <Pagination products={products.pagination}/> */}
    </div>
  );
};

export default ProductsManage;
