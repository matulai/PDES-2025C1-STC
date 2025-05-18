import { Home, Login, Register, NotFound, Search } from "@/views";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { PrivateRoute, PublicOnlyRoute, RoleRoute } from "@/routes";
import { AuthProvider } from "@/context";
import MainLayout from "@/layout/MainLayout";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/" element={<MainLayout />}>
            <Route index element={<Home />} />
            <Route path="/search/:text" element={<Search />} />

            <Route element={<PublicOnlyRoute />}>
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
            </Route>

            <Route path="*" element={<NotFound />} />
          </Route>
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
