import { LoginCard } from "@/components";
import { toast } from "react-hot-toast";
import { login } from "@/service/authService";
import { useAuth } from "@/hooks";

const Login = () => {
  const { contextLogin, setIsLoading } = useAuth();

  const handleLogin = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const form = event.currentTarget;
    const username = (form.elements.namedItem("username") as HTMLInputElement)
      ?.value;
    const password = (form.elements.namedItem("password") as HTMLInputElement)
      ?.value;

    if (!username || !password) {
      alert("Por favor completá todos los campos.");
      return;
    }
    setIsLoading(true);
    login(username, password)
      .then(response => {
        contextLogin(
          {
            name: response.data.name,
            role: response.data.role,
            favorites: response.data.favorites,
            purchases: response.data.purchases,
            cart: response.data.cart,
            qualifications: response.data.qualifications,
          },
          response.headers["authorization"]
        );
      })
      .catch(error => {
        toast.error("Error al ingresar");
        console.error("Login failed", error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

  return <LoginCard onSubmit={handleLogin} />;
};

export default Login;
