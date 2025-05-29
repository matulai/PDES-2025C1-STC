import { Header, Footer } from "@/components";
import { Outlet } from "react-router-dom";
import "@/styles/MainLayout.css";

const MainLayout = () => (
  <>
    <Header />
    <main className="layout-content">
      <Outlet />
    </main>
    <Footer />
  </>
);

export default MainLayout;
