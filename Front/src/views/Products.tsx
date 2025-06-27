import {
  allFavoritesProducts,
  topFavoriteProducts,
  topSellingProducts,
} from "@/service/adminService";
import { Spinner, ProductCard } from "@/components";
import { useState, useEffect } from "react";
import { userFavourites } from "@/service/userService";
import type { Product } from "@/types";
import "@/styles/Items.css";

interface ProductsProps {
  type: string;
}

const endpointMap = {
  "Favourites products": userFavourites,
  "All favourites products": allFavoritesProducts,
  "Top selling products": topSellingProducts,
  "Top favourites products": topFavoriteProducts,
};

const Products = ({ type }: ProductsProps) => {
  const [products, setProducts] = useState<Product[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const endpoint = endpointMap[type as keyof typeof endpointMap];
    endpoint()
      .then(res => {
        setProducts(res.data.data);
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
      <h1 className="items-title">
        <strong style={{ fontWeight: "600" }}>{type}</strong>
      </h1>
      <div className="items">
        <div className="items-content-wrap">
          {products.map(product => (
            <ProductCard key={product.mlaId} product={product} />
          ))}
        </div>
        {/* <Pagination products={products.pagination}/> */}
      </div>
    </>
  );
};

export default Products;
