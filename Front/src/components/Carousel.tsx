import { Product } from "@/types";
import { Link } from "react-router-dom";
import CarouselWrapper from "./CarouselWrapper";
import ProductCard from "./ProductCard";
import "@/styles/Carousel.css";

interface CarouselProps {
  link: string;
  title: string;
  products: Product[];
}

const Carousel = ({ link, title, products }: CarouselProps) => {
  return (
    <section className="carousel-container">
      <div className="carousel-container-header">
        <h2 className="carousel-container-header-title">{title}</h2>
        <Link to={link} className="carousel-container-header-link">
          Ir a título
        </Link>
      </div>
      <CarouselWrapper>
        {products.map((product, index) => (
          <ProductCard key={index} product={product} />
        ))}
      </CarouselWrapper>
    </section>
  );
};

export default Carousel;
