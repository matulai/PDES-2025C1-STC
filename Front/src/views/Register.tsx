import { AuthCard } from "@/components";

const Register = () => {
  const handleRegister = () => {
    console.log("Login");
    // hacer login...
  };

  return (
    <>
      <AuthCard
        footerLinkPath="/login"
        footerLinkText="Ingresar"
        footerText="¿Ya tienes una cuenta?"
        onSubmit={handleRegister}
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
        title="Registrarse"
      />
    </>
  );
};

export default Register;
