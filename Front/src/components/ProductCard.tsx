import "@/styles/ProductCard.css";

const ProductCard = () => {
  return (
    <div className="product-card-container">
      <img src="/image-test.jpg" alt="Product" className="product-card-image" />
      <h2 className="product-card-title">Product Name</h2>
      {/* <button className="product-card-button-cart">Add to Cart</button>
      <button className="product-card-button-fovorite">Favorite</button> */}
    </div>
  );
};

export default ProductCard;
