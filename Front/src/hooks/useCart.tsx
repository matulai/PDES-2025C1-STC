import { CartContext } from "@/context";
import { useContext } from "react";

const useAuth = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error("cartContext debe usarse dentro de un CartProvider");
  }
  return context;
};

export default useAuth;
