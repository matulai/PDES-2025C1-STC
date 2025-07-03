import {
  StarsQualify,
  CommentsSection,
  Spinner,
  ButtonLoading,
} from "@/components";
import { addFavouriteProduct, addToCart } from "@/service/userService";
import { Product, Qualification } from "@/types";
import { useParams, useNavigate } from "react-router-dom";
import { calculateProductPrice } from "@/utils/functions";
import { useState, useEffect } from "react";
import { useAuth, useCart } from "@/hooks";
import { getProductById } from "@/service/productService";
import { toast } from "react-hot-toast";
import "@/styles/ProductInfo.css";

const ProductInfo = () => {
  const { id } = useParams();
  const { user } = useAuth();
  const { setCart } = useCart();
  const [product, setProduct] = useState<Product>();
  const [comments, setComments] = useState<Qualification[]>([]);
  const [isFavourite, setIsFavourite] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      getProductById(id)
        .then(res => {
          res.data.price = calculateProductPrice(res.data.mlaId, res.data.name);
          setProduct(res.data);
          setIsFavourite(
            res.data && user?.favorites.some(p => p.name === res.data.name)
          );
          setComments(res.data.qualifications);
        })
        .catch(err => {
          console.error(err);
          toast.error("Error al obtener el producto");
          navigate("/");
        })
        .finally(() => {
          setIsLoading(false);
        });
    }
  }, [id]);

  const handleOnClickAddFavourite = async () => {
    if (product) {
      if (user) {
        try {
          const res = await addFavouriteProduct(product);
          user.favorites = res.data;
          setIsFavourite(!isFavourite);
          toast.success(
            isFavourite
              ? "Producto sacado de favoritos"
              : "Producto agregado a favoritos"
          );
        } catch (error) {
          toast.error("Error al agregar a favoritos");
          console.log(error);
        }
      } else {
        navigate("/register");
      }
    }
    return Promise.resolve();
  };

  const handleOnClickAddToCart = async () => {
    if (product) {
      if (user) {
        try {
          const res = await addToCart(product);
          setCart(res.data);
          toast.success("Producto agregado al carrito");
        } catch (error) {
          toast.error("Error al agregar al carrito");
          console.log(error);
        }
      } else {
        navigate("/register");
      }
    }
    return Promise.resolve();
  };

  const calculateAverageScore = () => {
    if (product) {
      return (
        product.qualifications!.map(q => q.score).reduce((a, b) => a + b, 0) /
        product.qualifications!.length
      );
    }
    return 0;
  };

  if (isLoading) {
    return <Spinner classType="spinner-fullscreen" />;
  }

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
          <ButtonLoading
            handleFunction={handleOnClickAddToCart}
            text="Agregar al carrito"
          />
          <ButtonLoading
            handleFunction={handleOnClickAddFavourite}
            text={isFavourite ? "Sacar de favoritos" : "Agregar a favoritos"}
          />
        </section>
      </section>
      <section className="product-qualify">
        <h2 className="product-qualify-title">Opiniones del producto</h2>
        <div className="product-qualify-content">
          {product?.qualifications?.length !== 0 ? (
            <>
              <p className="product-qualify-content-average">
                {calculateAverageScore()}
              </p>
              <div>
                <StarsQualify
                  value={Math.round(calculateAverageScore())}
                  starsSize={24}
                />
                <div className="product-quealify-content-stadistics-amount">
                  {product?.qualifications?.length} calificaciones
                </div>
              </div>
            </>
          ) : (
            <p className="product-quealify-content-stadistics-amount">
              Sin comentarios
            </p>
          )}
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
