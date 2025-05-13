import { LoginCard } from "@/components";

const Login = () => {
  const handleLogin = () => {
    console.log("Login");
    // hacer login...
  };

  return (
    <>
      <LoginCard onSubmit={handleLogin} />
    </>
  );
};

export default Login;
