import { removeFromCart, purchaseProducts } from "@/service/userService";
import { ProductCard, ButtonLoading } from "@/components";
import type { Product } from "@/types";
import { useNavigate } from "react-router-dom";
import { TrashIcon } from "@/icons";
import { useCart, useAuth } from "@/hooks";
import { toast } from "react-hot-toast";
import "@/styles/Items.css";
import "@/styles/Cart.css";

const Cart = () => {
  const { cart, setCart } = useCart();
  const { user } = useAuth();
  const navigate = useNavigate();

  const onClickRemoveFromCart = (product: Product) => {
    removeFromCart(product)
      .then(res => {
        setCart(res.data);
        toast.success("Producto removido con exito");
      })
      .catch(error => {
        console.log(error);
        toast.error("Error al remover producto");
      });
  };

  const onClickBuyCartProducts = async () => {
    try {
      const res = await purchaseProducts();
      setCart([]);
      user!.purchases = res.data;

      toast.success("Productos comprados con exito");
    } catch (error) {
      toast.error("Error al comprar productos");
      console.log(error);
    }
  };

  return (
    <>
      <h1 className="items-title">
        <strong style={{ fontWeight: "600" }}>My cart</strong>
      </h1>
      <div className="items">
        <div className="items-content-wrap">
          {cart.map((product, index) => (
            <div key={index} className="cart-product-card">
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
              <ButtonLoading
                handleFunction={onClickBuyCartProducts}
                text="Buy products"
              />
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
