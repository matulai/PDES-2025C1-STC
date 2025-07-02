import type { PurchaseRecipe, PaginationElementDto } from "@/types";
import { Spinner, Carousel, PaginationNav } from "@/components";
import { useState, useEffect } from "react";
import { allUsersPurchases } from "@/service/adminService";
import { useSearchParams } from "react-router-dom";
import { userPurchases } from "@/service/userService";
import "@/styles/Items.css";

interface ProductsProps {
  type: string;
}

const endpointMap = {
  "Todas las Compras": allUsersPurchases,
  "Mis compras": userPurchases,
};

const PurchaseRecipes = ({ type }: ProductsProps) => {
  const [searchParams] = useSearchParams();
  const [paginationPurchaseRecipes, setPaginationPurchaseRecipes] =
    useState<PaginationElementDto<PurchaseRecipe>>();
  const [isLoading, setIsLoading] = useState(true);

  // Tambien podes pasarle por query el limite
  const page = Number(searchParams.get("page"));

  useEffect(() => {
    const endpoint = endpointMap[type as keyof typeof endpointMap];
    endpoint(page)
      .then(res => {
        setPaginationPurchaseRecipes(res);
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [page]);

  if (isLoading) {
    return <Spinner />;
  }

  return (
    <>
      <h1 className="items-title">
        <strong style={{ fontWeight: "600" }}>{type}</strong>
      </h1>
      <div className="items">
        {paginationPurchaseRecipes!.data.map((purchaseRecipe, index) => {
          const priceFormated = purchaseRecipe.purchasePrice
            .toString()
            .replace(/\B(?=(\d{3})+(?!\d))/g, ".");
          const date = new Date(purchaseRecipe.purchaseDate);
          const fecha = date.toISOString().split("T")[0];
          const title = `Fecha:${fecha} MontoTotal:${priceFormated}`;
          return (
            <Carousel
              key={index}
              link="#"
              title={title}
              products={purchaseRecipe.purchaseProducts}
            />
          );
        })}
      </div>
      <PaginationNav pagination={paginationPurchaseRecipes!.pagination} />
    </>
  );
};

export default PurchaseRecipes;
