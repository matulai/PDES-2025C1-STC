import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "@/hooks";

const PublicOnlyRoute = () => {
  const { user } = useAuth();
  return !user ? <Outlet /> : <Navigate to="/" replace />;
};

export default PublicOnlyRoute;
