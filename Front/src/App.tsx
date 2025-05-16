import { Home, Login, Register, NotFound } from "@/views";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "@/context";
import MainLayout from "@/layout/MainLayout";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/" element={<MainLayout />}>
            <Route index element={<Home />} />
            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login />} />
            <Route path="*" element={<NotFound />} />
          </Route>
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
