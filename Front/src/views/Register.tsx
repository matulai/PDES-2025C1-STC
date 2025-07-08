import { RegisterCard } from "@/components";
import { register } from "@/service/authService";
import { useAuth } from "@/hooks";
import { toast } from "react-hot-toast";

const Register = () => {
  const { contextLogin, setIsLoading } = useAuth();

  const handleRegister = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const form = event.currentTarget;
    const username = (form.elements.namedItem("username") as HTMLInputElement)
      ?.value;
    const password = (form.elements.namedItem("password") as HTMLInputElement)
      ?.value;
    const role = (form.elements.namedItem("rolesOptions") as HTMLSelectElement)
      ?.value;

    if (!username || !password || !role) {
      alert("Por favor completá todos los campos.");
      return;
    }
    setIsLoading(true);
    register(username, password, role)
      .then(response => {
        contextLogin(response.data, response.headers["authorization"]);
      })
      .catch(error => {
        toast.error("Error al registrarse");
        console.error("Registration failed", error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

  return <RegisterCard onSubmit={handleRegister} />;
};

export default Register;
