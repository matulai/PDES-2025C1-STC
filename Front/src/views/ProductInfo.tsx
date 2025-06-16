import { addFavouriteProduct, addToCart } from "@/service/userService";
import { StarsQualify, CommentsSection } from "@/components";
import { Product, Qualification } from "@/types";
import { useParams, useNavigate } from "react-router-dom";
import { calculateProductPrice } from "@/utils/functions";
import { useState, useEffect } from "react";
import { getProductById } from "@/service/productService";
import { useAuth } from "@/hooks";
import "@/styles/ProductInfo.css";

const ProductInfo = () => {
  const { id } = useParams();
  const { user } = useAuth();
  const [product, setProduct] = useState<Product | null>(null);
  const [comments, setComments] = useState<Qualification[]>([]);
  const [isFavourite, setIsFavourite] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      getProductById(id)
        .then(res => {
          res.data.price = calculateProductPrice(res.data.mlaId, res.data.name);
          console.log(res.data);
          setProduct(res.data);
          setIsFavourite(res.data && user?.favorites.includes(res.data));
          setComments(res.data.qualifications);
        })
        .catch(err => {
          console.error(err);
        });
    }
  }, [id]);

  const handleOnClickAddFavourite = () => {
    if (product) {
      if (user) {
        addFavouriteProduct(product)
          .then(res => {
            user.favorites = res.data;
            setIsFavourite(
              res.data.some((fav: Product) => fav.name === product.name)
            );
          })
          .catch(error => {
            console.log(error);
          });
      } else {
        navigate("/register");
      }
    }
  };

  const handleOnClickAddToCart = () => {
    if (product) {
      if (user) {
        addToCart(product)
          .then((res: { data: Product[] }) => {
            user.cart = res.data;
          })
          .catch(error => {
            console.log(error);
          });
      } else {
        navigate("/register");
      }
    }
  };

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
          <button
            onClick={handleOnClickAddToCart}
            className="product-info-details-button"
          >
            Agregar al carrito
          </button>
          <button
            onClick={handleOnClickAddFavourite}
            className="product-info-details-button"
          >
            {`${isFavourite ? "Sacar de Favoritos" : "Agregar a Favoritos"}`}
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
