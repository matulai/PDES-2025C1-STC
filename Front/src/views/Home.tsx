import { ProductsCategoryContainer } from "@/components";
const Home = () => {
  return (
    <>
      <ProductsCategoryContainer
        link="/search/Samsung"
        title="Samsung"
        category="MLA-CELLPHONES"
      />
      <ProductsCategoryContainer
        link="search/Libros"
        title="Libros"
        category="MLA-BOOKS"
      />
      <ProductsCategoryContainer
        link="search/Computadoras"
        title="Computadoras"
        category="MLA-DESKTOP_COMPUTER_KITS"
      />
    </>
  );
};

export default Home;
