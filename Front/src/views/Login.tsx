import { AuthCard } from "@/components";

const Login = () => {
  const handleLogin = () => {
    console.log("Login");
    // hacer login...
  };

  return (
    <>
      <AuthCard
        footerLinkPath="/register"
        footerLinkText="Registrarse"
        footerText="¿No tienes una cuenta?"
        onSubmit={handleLogin}
        fields={[
          {
            id: "username",
            type: "text",
            placeholder: "Nombre",
          },
          {
            id: "password",
            type: "password",
            placeholder: "Contraseña",
          },
        ]}
        title="Ingresar"
      />
    </>
  );
};

export default Login;
