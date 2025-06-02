import { Link } from "react-router-dom";
import { calculateProductPrice } from "@/utils/functions";
import "@/styles/ProductCard.css";

interface ProductCardProps {
  key: string;
  mlaId: string;
  name: string;
  imageURL: string;
}

const ProductCard = ({ mlaId, name, imageURL }: ProductCardProps) => {
  return (
    <Link to={`/products/${mlaId}`} className="product-card-container">
      <img src={imageURL} alt="Product" className="product-card-image" />
      <h2 className="product-card-title">{name}</h2>
      <div className="product-card-price">
        ${calculateProductPrice(mlaId, name)}
      </div>
    </Link>
  );
};

export default ProductCard;
