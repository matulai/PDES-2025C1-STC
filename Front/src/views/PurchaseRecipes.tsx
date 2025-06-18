import { Spinner, PurchaseRecipeCard } from "@/components";
import { useState, useEffect } from "react";
import type { PurchaseRecipe } from "@/types";
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
        {/* <Filter setProducts={setProducts} /> */}
        {purchaseRecipes.map((purchaseRecipe, index) => (
          <PurchaseRecipeCard key={index} purchaseRecipe={purchaseRecipe} />
        ))}
        {/* <Pagination products={products.pagination}/> */}
      </div>
    </>
  );
};

export default PurchaseRecipes;
