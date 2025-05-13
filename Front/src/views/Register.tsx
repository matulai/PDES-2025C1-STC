import { RegisterCard } from "@/components";

const Register = () => {
  const handleRegister = () => {
    console.log("Login");
    // hacer login...
  };

  return (
    <>
      <RegisterCard onSubmit={handleRegister} />
    </>
  );
};

export default Register;
