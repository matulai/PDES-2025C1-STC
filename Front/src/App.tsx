import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Home } from "@/views";
import MainLayout from "@/layout/MainLayout";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Home />} />
          <Route path="/register" element={<div>Register</div>} />
          <Route path="/login" element={<div>Login</div>} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
