import { getProductsByKeyword } from "@/service/productService";
import { useEffect, useState } from "react";
import Carousel from "./Carousel";

interface CarouselContainerProps {
  link: string;
  title: string;
  category: string;
}

const ProductsCategoryContainer = ({
  link,
  title,
  category,
}: CarouselContainerProps) => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    getProductsByKeyword({ text: title, category: category })
      .then(response => {
        setProducts(response.data);
      })
      .catch(error => {
        console.error("Error fetching products:", error);
      });
  }, []);

  return (
    <>
      <Carousel link={link} title={title} products={products} />
    </>
  );
};

export default ProductsCategoryContainer;
