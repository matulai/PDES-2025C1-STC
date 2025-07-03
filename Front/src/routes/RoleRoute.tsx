import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "@/hooks";

interface RoleRouteProps {
  role: string;
}

const RoleRoute = ({ role }: RoleRouteProps) => {
  const { user } = useAuth();
  if (!user) return <Navigate to="/login" replace />;
  return user.role === role ? <Outlet /> : <Navigate to="/" replace />;
};

export default RoleRoute;
