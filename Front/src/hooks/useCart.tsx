import { CartContext } from "@/context";
import { useContext } from "react";

const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error("cartContext debe usarse dentro de un CartProvider");
  }
  return context;
};

export default useCart;
