import { useState, useEffect } from "react";
import { getProductById } from "@/service/productService";
import { useParams } from "react-router-dom";
import { Product } from "@/types";

const ProductInfo = () => {
  const { id } = useParams();
  const [product, setProduct] = useState<Product | null>(null);

  useEffect(() => {
    if (id) {
      getProductById(id)
        .then(res => {
          setProduct(res.data);
        })
        .catch(err => {
          console.error(err);
        });
    }
  }, [id]);

  return (
    <div className="product-container">
      <img
        src={product?.imageURL}
        alt={product?.name}
        className="product-image"
      />
      <h1 className="product-title">{product?.name}</h1>
      <p className="product-description">{product?.description}</p>
    </div>
  );
};

export default ProductInfo;
