import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Home } from "@/views";
import MainLayout from "@/layout/MainLayout";
import "./App.css";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Home />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
