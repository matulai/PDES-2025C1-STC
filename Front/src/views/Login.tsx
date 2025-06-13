import { useNavigate } from "react-router-dom";
import { LoginCard } from "@/components";
import { login } from "@/service/authService";
import { useAuth } from "@/hooks";

const Login = () => {
  const { contextLogin } = useAuth();
  const navigate = useNavigate();

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
    login(username, password)
      .then(response => {
        contextLogin(
          {
            name: response.data.name,
            role: response.data.role,
            favorites: response.data.favorites,
            purchases: response.data.purchases,
            qualifications: response.data.qualifications,
          },
          response.headers["authorization"]
        );
        navigate("/");
      })
      .catch(error => {
        // Falta mostrar un mensaje de error al usuario
        console.error("Login failed", error);
      });
  };

  return (
    <>
      <LoginCard onSubmit={handleLogin} />
    </>
  );
};

export default Login;
