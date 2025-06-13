import { calculateProductPrice } from "@/utils/functions";
import { Product } from "@/types";
import { Link } from "react-router-dom";
import "@/styles/ProductCard.css";

interface ProductCardProps {
  product: Product;
}

const ProductCard = ({ product }: ProductCardProps) => {
  return (
    <Link to={`/products/${product.mlaId}`} className="product-card-container">
      <img
        src={product.imageURL}
        alt="Product"
        className="product-card-image"
      />
      <h2 className="product-card-title">{product.name}</h2>
      <div className="product-card-price">
        $
        {calculateProductPrice(product.mlaId, product.name)
          .toString()
          .replace(/\B(?=(\d{3})+(?!\d))/g, ".")}
      </div>
    </Link>
  );
};

export default ProductCard;
