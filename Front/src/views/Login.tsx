import { LoginCard } from "@/components";
import { login } from "@/service/authService";

const Login = () => {
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
        console.log("Login successful", response.data);
      })
      .catch(error => {
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
