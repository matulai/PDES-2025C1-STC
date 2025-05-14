import { RegisterCard } from "@/components";
import { register } from "@/service/authService";

const Register = () => {
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
        console.log("Registration successful", response.data);
      })
      .catch(error => {
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
