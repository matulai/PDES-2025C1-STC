import {
  Home,
  Login,
  Register,
  NotFound,
  Search,
  ProductInfo,
  Cart,
  Products,
  PurchaseRecipes,
  Qualifications,
  Users,
} from "@/views";
import { PublicOnlyRoute, RoleRoute } from "@/routes";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider, CartProvider } from "@/context";
import { Toaster } from "react-hot-toast";
import MainLayout from "@/layout/MainLayout";

function App() {
  return (
    <BrowserRouter>
      <Toaster position="top-left" reverseOrder={false} />
      <AuthProvider>
        <CartProvider>
          <Routes>
            <Route path="/" element={<MainLayout />}>
              <Route index element={<Home />} />
              <Route path="/search/:text" element={<Search />} />
              <Route path="/products/:id" element={<ProductInfo />} />

              <Route element={<PublicOnlyRoute />}>
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
              </Route>

              <Route element={<RoleRoute role="Client" />}>
                <Route path="/user/cart" element={<Cart />} />
                <Route
                  path="/user/purchases"
                  element={<PurchaseRecipes type="Mis compras" />}
                />
                <Route
                  path="/user/favourites"
                  element={<Products type="Favoritos" />}
                />
              </Route>

              <Route element={<RoleRoute role="Admin" />}>
                <Route
                  path="/users/favourites"
                  element={<Products type="Todos los favoritos" />}
                />
                <Route
                  path="/products/topSellingProducts"
                  element={<Products type="Top vendidos" />}
                />
                <Route
                  path="/products/topFavouritesProducts"
                  element={<Products type="Top favoritos" />}
                />
                <Route
                  path="/users/purchases"
                  element={<PurchaseRecipes type="Todas las compras" />}
                />
                <Route path="/users" element={<Users type="Usuarios" />} />
                <Route
                  path="/users/topBuyers"
                  element={<Users type="Top compradores" />}
                />
                <Route
                  path="/users/qualifications"
                  element={<Qualifications />}
                />
              </Route>

              <Route path="*" element={<NotFound />} />
            </Route>
          </Routes>
        </CartProvider>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
