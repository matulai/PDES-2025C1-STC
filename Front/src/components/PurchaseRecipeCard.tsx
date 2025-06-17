import type { PurchaseRecipe } from "@/types";
import { Carousel } from "@/components";
import "@/styles/Carousel.css";

interface PurchaseRecipeProps {
  purchaseRecipe: PurchaseRecipe;
}

const PurchaseRecipeCard = ({ purchaseRecipe }: PurchaseRecipeProps) => {
  const priceFormated = purchaseRecipe.purchasePrice
    .toString()
    .replace(/\B(?=(\d{3})+(?!\d))/g, ".");
  const title = `Date:${purchaseRecipe.purchaseDate} TotalPrice:${priceFormated}`;

  return (
    <section className="carousel-container">
      <Carousel
        link="#"
        title={title}
        products={purchaseRecipe.purchaseProducts}
      />
    </section>
  );
};

export default PurchaseRecipeCard;
