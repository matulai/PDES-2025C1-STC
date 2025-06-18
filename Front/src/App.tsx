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
import { PublicOnlyRoute } from "@/routes";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider, CartProvider } from "@/context";
import MainLayout from "@/layout/MainLayout";

function App() {
  return (
    <BrowserRouter>
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

              <Route path="/user/cart" element={<Cart />} />
              <Route
                path="/user/favourites"
                element={<Products type="Favourites products" />}
              />
              <Route
                path="/users/favourites"
                element={<Products type="All favourites products" />}
              />
              <Route
                path="/products/topSellingProducts"
                element={<Products type="Top selling products" />}
              />
              <Route
                path="/products/topFavouritesProducts"
                element={<Products type="Top favourites products" />}
              />
              <Route
                path="/users/purchases"
                element={<PurchaseRecipes type="All users purchases" />}
              />
              <Route
                path="/user/purchases"
                element={<PurchaseRecipes type="User purchases" />}
              />
              <Route path="/users" element={<Users type="All users" />} />
              <Route
                path="/users/topBuyers"
                element={<Users type="Top buyers" />}
              />
              <Route
                path="/users/qualifications"
                element={<Qualifications />}
              />

              <Route path="*" element={<NotFound />} />
            </Route>
          </Routes>
        </CartProvider>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
