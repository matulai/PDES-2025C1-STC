import {
  allFavoritesProducts,
  topFavoriteProducts,
  topSellingProducts,
} from "@/service/adminService";
import { Spinner, ProductCard, PaginationNav } from "@/components";
import type { Product, PaginationElementDto } from "@/types";
import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { userFavourites } from "@/service/userService";
import "@/styles/Items.css";

interface ProductsProps {
  type: string;
}

const endpointMap = {
  Favoritos: userFavourites,
  "Todos los favoritos": allFavoritesProducts,
  "Top vendidos": topSellingProducts,
  "Top favoritos": topFavoriteProducts,
};

const Products = ({ type }: ProductsProps) => {
  const [searchParams] = useSearchParams();
  const [paginationProducts, setPaginationProducts] =
    useState<PaginationElementDto<Product>>();
  const [isLoading, setIsLoading] = useState(true);

  const page = Number(searchParams.get("page"));

  useEffect(() => {
    const endpoint = endpointMap[type as keyof typeof endpointMap];
    endpoint(page)
      .then(res => {
        setPaginationProducts(res);
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
          {paginationProducts?.data.map(product => (
            <ProductCard key={product.mlaId} product={product} />
          ))}
        </div>
      </div>
      <PaginationNav pagination={paginationProducts!.pagination} />
    </>
  );
};

export default Products;
