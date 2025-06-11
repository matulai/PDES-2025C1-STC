import { Product } from "@/types";
import { Link } from "react-router-dom";
import ProductCard from "./ProductCard";
import ScrollableCarousel from "./ScrollableCarousel";

interface CarouselProps {
  link: string;
  title: string;
  products: Product[];
}

const Carousel = ({ link, title, products }: CarouselProps) => {
  return (
    <>
      <div className="carousel-container-header">
        <h2 className="carousel-container-header-title">{title}</h2>
        <Link to={link} className="carousel-container-header-link">
          Ir a título
        </Link>
      </div>
      <ScrollableCarousel>
        {products.map(product => (
          <ProductCard product={product} />
        ))}
      </ScrollableCarousel>
    </>
  );
};

export default Carousel;
