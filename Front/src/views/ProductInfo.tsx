import { StarsQualify, CommentsSection } from "@/components";
import { useState, useEffect } from "react";
import { Product, Qualification } from "@/types";
import { getProductById } from "@/service/productService";
import { useParams } from "react-router-dom";
import "@/styles/ProductInfo.css";

const ProductInfo = () => {
  const { id } = useParams();
  const [product, setProduct] = useState<Product | null>(null);
  const [comments, setComments] = useState<Qualification[]>([
    {
      userName: "Juan",
      productName: "Producto A",
      comment: "Me encanta este producto, es increíble",
      score: 5,
    },
    {
      userName: "Ana",
      productName: "Producto A",
      comment: "No me gusta, no lo volvería a comprar",
      score: 1,
    },
    {
      userName: "Luis",
      productName: "Producto A",
      comment: "Es bueno, pero podría ser mejor",
      score: 3,
    },
    {
      userName: "Maria",
      productName: "Producto A",
      comment: "Es un producto normal, no me gusta",
      score: 2,
    },
    {
      userName: "Pedro",
      productName: "Producto A",
      comment: "Le gustó",
      score: 5,
    },
    {
      userName: "Lucia",
      productName: "Producto A",
      comment: "Es un producto normal",
      score: 3,
    },
  ]);

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
      <section className="product-info">
        <img
          src={product?.imageURL}
          alt={product?.name}
          className="product-info-image"
        />
        <section className="product-info-details">
          <h1 className="product-info-details-title">{product?.name}</h1>
          <p className="product-info-details-description">
            {product?.description}
          </p>
          <button className="product-info-details-button">Comprar ahora</button>
          <button className="product-info-details-button">
            Agregar a Favoritos
          </button>
        </section>
      </section>
      <section className="product-qualify">
        <h2 className="product-qualify-title">Opiniones del producto</h2>
        <div className="product-qualify-content">
          <p className="product-qualify-content-average">1.8</p>
          <div>
            <StarsQualify value={4} starsSize={24} />
            <div className="product-quealify-content-stadistics-amount">
              45 calificaciones
            </div>
          </div>
        </div>
        <CommentsSection
          productName={product?.name ?? ""}
          comments={comments}
          setComments={setComments}
        />
      </section>
    </div>
  );
};

export default ProductInfo;
