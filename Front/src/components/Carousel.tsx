import { Link } from "react-router-dom";
import ProductCard from "./ProductCard";
import ScrollableCarousel from "./ScrollableCarousel";
import "@/styles/Carousel.css";

const Carousel = () => {
  return (
    <section className="carousel-container">
      <div className="carousel-container-header">
        <h2 className="carousel-container-header-title">Título</h2>
        <Link to="/link" className="carousel-container-header-link">
          Ir a título
        </Link>
      </div>
      <ScrollableCarousel>
        <ProductCard />
        <ProductCard />
        <ProductCard />
        <ProductCard />
        <ProductCard />
        <ProductCard />
        <ProductCard />
        <ProductCard />
        <ProductCard />
        <ProductCard />
      </ScrollableCarousel>
    </section>
  );
};

export default Carousel;
