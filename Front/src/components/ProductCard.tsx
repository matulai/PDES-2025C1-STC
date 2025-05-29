import { Link } from "react-router-dom";
import "@/styles/ProductCard.css";

interface ProductCardProps {
  key: string;
  mlaId: string;
  name: string;
  imageURL: string;
}

const ProductCard = ({ mlaId, name, imageURL }: ProductCardProps) => {
  return (
    <div className="product-card-container">
      <Link to={`/products/${mlaId}`}>
        <img src={imageURL} alt="Product" className="product-card-image" />
        <h2 className="product-card-title">{name}</h2>
      </Link>
    </div>
  );
};

export default ProductCard;
