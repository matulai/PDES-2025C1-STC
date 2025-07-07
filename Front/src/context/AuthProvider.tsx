import {
  addToLocalStorage,
  getFromLocalStorage,
  removeItemFromLocalStorage,
} from "@/utils/localStorage";
import { useState, useEffect, createContext, useMemo } from "react";
import { getCurrentUser } from "@/service/userService";
import { toast } from "react-hot-toast";
import { Spinner } from "@/components";
import { User } from "@/types";

interface AuthContextType {
  user: User | null;
  contextLogin: (user: User, token: string) => void;
  contextLogout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  // Restaurar usuario del localStorage(cuando se refresca la pagina)
  useEffect(() => {
    const token = getFromLocalStorage("token");
    if (token) {
      getCurrentUser(token)
        .then(response => {
          const currentUser: User = response.data;
          setUser(currentUser);
        })
        .catch(error => {
          // Pensar si es necesario eliminar el token
          console.log(error);
          toast.error("Error al iniciar sesión");
        })
        .finally(() => {
          setIsLoading(false);
        });
    } else {
      setIsLoading(false);
    }
  }, []);

  const contextLogin = (userData: User, token: string) => {
    setUser(userData);
    addToLocalStorage("token", token);
  };

  const contextLogout = () => {
    setUser(null);
    removeItemFromLocalStorage("token");
  };

  const value = useMemo(() => ({ user, contextLogin, contextLogout }), [user]);

  if (isLoading) {
    return <Spinner classType="spinner-fullscreen" />;
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export { AuthProvider, AuthContext };
