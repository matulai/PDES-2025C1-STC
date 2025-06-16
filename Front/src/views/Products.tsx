import {
  allFavoritesProducts,
  topFavoriteProducts,
  topSellingProducts,
} from "@/service/adminService";
import { Spinner, ProductsManage } from "@/components";
import { useState, useEffect } from "react";
import { userFavourites } from "@/service/userService";
import type { Product } from "@/types";

interface ProductsProps {
  type: string;
}

const endpointMap = {
  favouritesProducts: userFavourites,
  allFavouritesProducts: allFavoritesProducts,
  topSellingProducts: topSellingProducts,
  topFavouritesProducts: topFavoriteProducts,
};

const Products = ({ type }: ProductsProps) => {
  const [products, setProducts] = useState<Product[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const endpoint = endpointMap[type as keyof typeof endpointMap];
    endpoint()
      .then(res => {
        setProducts(res.data);
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  if (isLoading) {
    return <Spinner />;
  }

  return (
    <>
      <h1 style={{ width: "100%", fontSize: "32px", textAlign: "left" }}>
        <strong style={{ fontWeight: "600" }}>{type}</strong>
      </h1>
      <ProductsManage products={products} setProducts={setProducts} />
    </>
  );
};

export default Products;
