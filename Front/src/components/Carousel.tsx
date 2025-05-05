import { Link } from "react-router-dom";
import ChevronLeftIcon from "./ChevronLeftIcon";
import ProductCard from "./ProductCard";
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
      <div className="carousel-container-content">
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
      </div>
      <button className="carousel-container-buttons-prev">
        <ChevronLeftIcon />
      </button>
      <button className="carousel-container-buttons-next">
        <ChevronLeftIcon style={{ transform: "rotate(180deg)" }} />
      </button>
    </section>
  );
};

export default Carousel;
