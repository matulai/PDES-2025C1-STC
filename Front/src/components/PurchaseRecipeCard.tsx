import type { PurchaseRecipe } from "@/types";
import { Carousel } from "@/components";
import "@/styles/ProductsManage.css";

interface PurchaseRecipeProps {
  purchaseRecipe: PurchaseRecipe;
}

const PurchaseRecipeCard = ({ purchaseRecipe }: PurchaseRecipeProps) => {
  const priceFormated = $;
  {
    purchaseRecipe.purchasePrice
      .toString()
      .replace(/\B(?=(\d{3})+(?!\d))/g, ".");
  }
  const title = `Date:${purchaseRecipe.purchaseDate} TotalPrice:${priceFormated}`;

  return (
    <div className="product-card-container">
      <h2 className="product-card-title">{purchaseRecipe.purchaseDate}</h2>
      <Carousel
        link="#"
        title={title}
        products={purchaseRecipe.purchaseProducts}
      />
    </div>
  );
};

export default PurchaseRecipeCard;
