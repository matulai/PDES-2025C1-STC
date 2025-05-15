import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;
const PUBLIC_API_URL = import.meta.env.VITE_PUBLIC_API_URL;

/********************* Private requests *********************/

/********************* Public requests *********************/

function getProductsByKeyword(query: string) {
  return axios.get(`${API_URL}/products/search`, {
    params: { text: query },
  });
}

function getProductsByKeywordAndCategory(query: string, categoryId: string) {
  return axios.get(`${API_URL}/products/search`, {
    params: {
      text: query,
      category: categoryId,
    },
  });
}

function getProductById(id: string) {
  return axios.get(`${API_URL}/products/${id}`);
}

function getCategoryById(id: string) {
  return axios.get(`${PUBLIC_API_URL}/categories/${id}`);
}

export {
  getProductById,
  getCategoryById,
  getProductsByKeyword,
  getProductsByKeywordAndCategory,
};
