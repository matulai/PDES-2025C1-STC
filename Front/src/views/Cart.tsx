import { removeFromCart, purchaseProducts } from "@/service/userService";
import type { Product } from "@/types";
import { ProductCard } from "@/components";
import { useNavigate } from "react-router-dom";
import { TrashIcon } from "@/icons";
import { useCart } from "@/hooks";
import "@/styles/Items.css";
import "@/styles/Cart.css";

const Cart = () => {
  const { cart, setCart } = useCart();
  const navigate = useNavigate();

  const onClickRemoveFromCart = (product: Product) => {
    removeFromCart(product)
      .then(res => {
        setCart(res.data.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  const onClickBuyCartProducts = () => {
    purchaseProducts()
      .then(_res => {
        setCart([]);
      })
      .catch(error => {
        console.log(error);
      });
  };

  return (
    <>
      <h1 className="items-title">
        <strong style={{ fontWeight: "600" }}>My cart</strong>
      </h1>
      <div className="items">
        <div className="items-content-wrap">
          {cart.map(product => (
            <div key={product.mlaId} className="cart-product-card">
              <ProductCard product={product} />
              <button onClick={() => onClickRemoveFromCart(product)}>
                <TrashIcon />
              </button>
            </div>
          ))}
        </div>
        <div className="cart-product-details">
          {cart.length !== 0 ? (
            <>
              <p style={{ fontWeight: "600", fontSize: "20px" }}>
                Total cost:
                {` $${cart
                  .map(p => p.price)
                  .reduce((acumulador, actual) => acumulador + actual, 0)
                  .toString()
                  .replace(/\B(?=(\d{3})+(?!\d))/g, ".")}`}
              </p>
              <button
                className="cart-product-details-buy-button"
                onClick={onClickBuyCartProducts}
              >
                Buy products
              </button>
            </>
          ) : (
            <button
              onClick={() => navigate("/")}
              className="cart-product-details-buy-button"
            >
              Start Buying
            </button>
          )}
        </div>
      </div>
    </>
  );
};

export default Cart;
