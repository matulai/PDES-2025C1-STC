import { RegisterCard } from "@/components";
import { useNavigate } from "react-router-dom";
import { register } from "@/service/authService";
import { useAuth } from "@/hooks";

const Register = () => {
  const navigate = useNavigate();
  const { contextLogin } = useAuth();

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
    register(username, password, role)
      .then(response => {
        contextLogin(response.data, response.headers["authorization"]);
        navigate("/");
      })
      .catch(error => {
        // Falta mostrar un mensaje de error al usuario
        console.error("Registration failed", error);
      });
  };

  return (
    <>
      <RegisterCard onSubmit={handleRegister} />
    </>
  );
};

export default Register;
