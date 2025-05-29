import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "@/hooks";

const PrivateRoute = () => {
  const { user } = useAuth();
  return user ? <Outlet /> : <Navigate to="/login" replace />;
};

export default PrivateRoute;
