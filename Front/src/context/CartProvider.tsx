import { useState, createContext, useMemo } from "react";
import { Product } from "@/types";
import { useAuth } from "@/hooks";

interface CartContextType {
  cart: Product[];
  setCart: React.Dispatch<React.SetStateAction<Product[]>>;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

const CartProvider = ({ children }: { children: React.ReactNode }) => {
  const { user } = useAuth();
  const [cart, setCart] = useState<Product[]>(user?.cart ?? []);

  const value = useMemo(() => ({ cart, setCart }), [cart]);

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
};

export { CartProvider, CartContext };
