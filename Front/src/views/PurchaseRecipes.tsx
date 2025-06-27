import { useState, useEffect } from "react";
import type { PurchaseRecipe } from "@/types";
import { Spinner, Carousel } from "@/components";
import { allUsersPurchases } from "@/service/adminService";
import { userPurchases } from "@/service/userService";

interface ProductsProps {
  type: string;
}

const endpointMap = {
  "All users purchases": allUsersPurchases,
  "User purchases": userPurchases,
};

const PurchaseRecipes = ({ type }: ProductsProps) => {
  const [purchaseRecipes, setPurchaseRecipes] = useState<PurchaseRecipe[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const endpoint = endpointMap[type as keyof typeof endpointMap];
    endpoint()
      .then(res => {
        setPurchaseRecipes(res.data.data);
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
      <div className="search-content">
        {purchaseRecipes.map((purchaseRecipe, index) => {
          const priceFormated = purchaseRecipe.purchasePrice
            .toString()
            .replace(/\B(?=(\d{3})+(?!\d))/g, ".");
          const title = `Date:${purchaseRecipe.purchaseDate} TotalPrice:${priceFormated}`;
          return (
            <Carousel
              key={index}
              link="#"
              title={title}
              products={purchaseRecipe.purchaseProducts}
            />
          );
        })}
        {/* <Pagination products={products.pagination}/> */}
      </div>
    </>
  );
};

export default PurchaseRecipes;
