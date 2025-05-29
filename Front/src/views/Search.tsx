import { getProductsByKeyword } from "@/service/productService";
import { useState, useEffect } from "react";
import { ProductsManage } from "@/components";
import { useParams } from "react-router-dom";
import { Product } from "@/types";

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
      <h1 style={{ width: "100%", fontSize: "32px", textAlign: "left" }}>
        Resultados de: <strong style={{ fontWeight: "600" }}>"{text}"</strong>
      </h1>
      <ProductsManage products={products} setProducts={setProducts} />
    </>
  );
};

export default Search;
