import { Header, Footer } from "@/components";
import { Outlet } from "react-router-dom";

const MainLayout = () => (
  <>
    <Header />
    <main style={{ padding: "1rem" }}>
      <Outlet />
    </main>
    <Footer />
  </>
);

export default MainLayout;
