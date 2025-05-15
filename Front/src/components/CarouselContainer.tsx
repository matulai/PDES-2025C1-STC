import { getProductsByKeywordAndCategory } from "@/service/productService";
import { useEffect, useState } from "react";
import Carousel from "./Carousel";
import "@/styles/Carousel.css";

interface CarouselContainerProps {
  link: string;
  title: string;
  category: string;
}

const CarouselContainer = ({
  link,
  title,
  category,
}: CarouselContainerProps) => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    getProductsByKeywordAndCategory(title, category)
      .then(response => {
        console.log(response.data);
        setProducts(response.data);
      })
      .catch(error => {
        console.error("Error fetching products:", error);
      });
  }, []);

  return (
    <section className="carousel-container">
      <Carousel link={link} title={title} products={products} />
    </section>
  );
};

export default CarouselContainer;
