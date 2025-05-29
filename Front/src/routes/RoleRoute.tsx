import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "@/hooks";

const RoleRoute = (role: string) => {
  const { user } = useAuth();
  if (!user) return <Navigate to="/login" replace />;
  return user.role === role ? <Outlet /> : <Navigate to="/" replace />;
};

export default RoleRoute;
