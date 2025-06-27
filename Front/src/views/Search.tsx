import { getProductsByKeyword } from "@/service/productService";
import { useState, useEffect } from "react";
import { ProductCard } from "@/components";
import { useParams } from "react-router-dom";
import { Product } from "@/types";
import "@/styles/Items.css";

const Search = () => {
  const text = useParams().text;
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    getProductsByKeyword({ text: text! })
      .then(res => {
        setProducts(res.data);
      })
      .catch(err => {
        console.error(err);
      });
  }, [text]);

  return (
    <>
      <h1 className="items-title">
        Resultados de: <strong style={{ fontWeight: "600" }}>"{text}"</strong>
      </h1>
      <div className="items">
        {/* <Filter setProducts={setProducts} /> */}
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

export default Search;
